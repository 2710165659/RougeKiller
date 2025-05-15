import mysql.connector
from mysql.connector import Error
import json

def connect_to_database():
    """
    连接到MySQL数据库
    请在此函数中填写您的数据库连接信息
    """
    try:
        connection = mysql.connector.connect(
            host='8.130.33.169',       # MySQL服务器地址
            user='root',       # 用户名
            password='Qq2710165659',   # 密码
            database='rouge_killer_test'    # 数据库名称
        )
        return connection
    except Error as e:
        print(f"数据库连接错误: {e}")
        return None

def load_data_from_json(file_path):
    """
    从JSON文件加载网站数据
    :param file_path: JSON文件路径
    :return: 网站数据列表
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            return json.load(file)
    except Exception as e:
        print(f"读取JSON文件错误: {e}")
        return []

def upsert_website(connection, website_data):
    """
    更新或插入网站数据
    :param connection: 数据库连接
    :param website_data: 包含网站信息的字典
    """
    try:
        cursor = connection.cursor()
        
        # 检查记录是否存在
        check_query = """
        SELECT * FROM websites 
        WHERE id = %(id)s
        """
        cursor.execute(check_query, website_data)
        result = cursor.fetchone()
        
        if result:
            # 更新现有记录
            update_query = """
                           UPDATE websites
                           SET domain       = %(domain)s,
                               full_url     = %(full_url)s,
                               subdomain    = %(subdomain)s,
                               port         = %(port)s,
                               title        = %(title)s,
                               ip           = %(ip)s,
                               is_malicious = %(is_malicious)s,
                               content      = %(content)s,
                               labels       = %(labels)s,
                               updated_at   = CURRENT_TIMESTAMP
                           WHERE id = %(id)s \
                           """
            website_data['id'] = result[0]
            cursor.execute(update_query, website_data)
            print(f"更新网站: {website_data.get('domain')}")
        else:
            # 插入新记录
            insert_query = """
            INSERT INTO websites (
                domain, subdomain, full_url, port, 
                title, ip, is_malicious,
                content, labels
            ) VALUES (
                %(domain)s, %(subdomain)s, %(full_url)s, %(port)s,
                %(title)s, %(ip)s, %(is_malicious)s,
                %(content)s, %(labels)s
            )
            """
            cursor.execute(insert_query, website_data)
            print(f"插入新网站: {website_data.get('domain')}")
            
        connection.commit()
        return cursor.lastrowid
    except Error as e:
        print(f"操作网站数据错误: {e}")
        return None

def main():
    # 连接到数据库
    connection = connect_to_database()
    if connection is None:
        return
    
    try:
        # 从JSON文件加载数据
        websites = load_data_from_json('data.json')
        if not websites:
            print("没有找到可用的网站数据")
            return
        
        # 处理每个网站数据
        for website in websites:
            # 确保content是字符串格式
            if isinstance(website.get('content'), list):
                website['content'] = ' '.join(website['content'])
            elif not isinstance(website.get('content'), str):
                website['content'] = str(website.get('content'))
                
            # 设置默认值
            website.setdefault('subdomain', '')
            website.setdefault('port', None)
            website.setdefault('title', '')
            website.setdefault('ip', '')
            website.setdefault('is_malicious', False)
            website.setdefault('labels', '')
            
            # 更新或插入数据
            upsert_website(connection, website)
            
    finally:
        if connection and connection.is_connected():
            connection.close()
            print("数据库连接已关闭")

if __name__ == "__main__":
    main()