import asyncio
import json
import re
import socket
from crawl4ai import *


def extract_ip(data, url):
    if '//' in url:
        hostname = url.split('//')[1].split('/')[0]
    else:
        hostname = url.split('/')[0]
    # 解析 IPv4 地址
    try:
        ip = socket.gethostbyname(hostname)
        data['ip'] = ip
    except socket.gaierror:
        print(f'获取{url}的IP地址失败')


def extract_domain_port(data, url):
    # 处理协议和端口
    url_parts = url.split('//')
    protocol_part = url_parts[0]
    if protocol_part == 'http:':
        data['port'] = '80'
    elif protocol_part == 'https:':
        data['port'] = '443'
    # 拆分域名部分
    domain_part = url_parts[-1].split('?')[0]  # 去掉查询参数
    domain_parts = domain_part.split('.')
    # 识别顶级域名（TLD）和主域名
    top_domain_list = ('com', 'cn', 'top', 'edu', 'net', 'org', 'gov')
    tld_index = -1
    while abs(tld_index) <= len(domain_parts) and domain_parts[tld_index] in top_domain_list:
        tld_index -= 1
    # 主域名是 TLD 及其前一部分（如 "google.com" -> "google" + "com"）
    if abs(tld_index) <= len(domain_parts):
        data['domain'] = '.'.join(domain_parts[tld_index - 1:])
    else:
        data['domain'] = domain_part  # 如果没有匹配的 TLD，返回整个域名
    # 子域名是主域名之前的部分（如 "sub.example.com" -> "sub"）
    if abs(tld_index) - 1 > 0:  # 如果存在子域名
        data['subdomain'] = '.'.join(domain_parts[:tld_index - 1])
    else:
        data['subdomain'] = ''  # 没有子域名
    return data


def extract_title_content(data, result: CrawlResult):
    if result.metadata:
        data['title'] = result.metadata.get("title")
    if result.cleaned_html:
        text = re.sub(re.compile(r'<.*?>'), '', result.cleaned_html)  # 去除HTML标签
        text = re.sub(r'\s+', ',', text.strip())  # 去除空格、换行、制表符等
        text_list = text.split(',')  # 以逗号分隔
        # 限制大小：MYSQL TEXT 65535
        while len(str(text_list)) > 65535:
            text_list.pop()
        data['content'] = text_list


def handle_result(index, result, url: str):
    data = {
        'id': index,  # 数据库自增ID
        'domain': '',  # 主域名
        'subdomain': '',  # 子域名
        'full_url': url,
        'port': '',  # 端口
        'title': '',  # 网页标题
        'ip': '',  # ip
        'is_malicious': 0,  # 是否恶意网站
        'content': '',  # 网页内容
    }
    extract_title_content(data, result)
    extract_ip(data, url)
    extract_domain_port(data, url)
    return data


async def crawl(url: list, index):
    async with AsyncWebCrawler() as crawler:
        result = await crawler.arun(url)
        if result.success:
            data = handle_result(index, result, result.url)
            print(data)
            return data
        else:
            print(f'<UNK>{index}<UNK>{result.url}<UNK>')
            return None


if __name__ == '__main__':
    start = 1
    urls = []
    datas = []

    while True:
        url = input().strip()
        if url == '0':
            break
        urls.append(url)

    print(f'共{len(urls)}个URL，开始爬取...')
    for url in urls:
        data = asyncio.run(crawl(url,start))
        if data:
            datas.append(data)
            start += 1
    print('爬取完成，开始保存数据...')


    with open('data.json', 'w', encoding='utf-8') as f:
        f.write(json.dumps(datas, ensure_ascii=False))
