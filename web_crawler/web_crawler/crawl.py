import asyncio
import re
from crawl4ai import AsyncWebCrawler, CrawlResult


def handle_result(result: CrawlResult):
    """
    处理爬取结果
    :param result: 爬虫结果
    :return: 处理后的列表形式数据
    """
    if result.metadata:
        print("Title:", result.metadata.get("title"))
    if result.cleaned_html:
        text = re.sub(re.compile(r'<.*?>'), '', result.cleaned_html)  # 去除HTML标签
        text = re.sub(r'\s+', ',', text.strip())  # 去除空格、换行、制表符等
        text_list = text.split(',')  # 以逗号分隔
        # 限制大小：MYSQL TEXT 65535
        while len(str(text_list)) > 65535:
            text_list.pop()
        return text_list
    return None


async def crawl(href: str):
    """
    异步爬取网页
    :param href: 链接
    :return: 网站首页
    """
    url = '/'.join(href.split('/')[:3])
    async with AsyncWebCrawler() as crawler:
        result = await crawler.arun(url=url)
        if result.success:
            return url, handle_result(result)
        else:
            print(f'爬取{url}失败，错误信息：{result.error}')
            return None


if __name__ == '__main__':
    href = input()
    url, data = asyncio.run(crawl(href))
    if data:
        print(f"爬取{url},数据{data}")
