import asyncio
from crawl4ai import AsyncWebCrawler, CrawlResult


async def handle_result(result: CrawlResult):
    if result.success:
        if result.metadata:
            print("Title:", result.metadata.get("title"))
        print(result)
    else:
        print("Error:", result.error)


async def crawl(href: str):
    # href示例: https://blog.csdn.net/captain5339/article/details/128017400
    url = '/'.join(href.split('/')[:3])
    async with AsyncWebCrawler() as crawler:
        result = await crawler.arun(url=url)
        if result.success:
            await handle_result(result)


async def main():
    href = 'https://blog.csdn.net/captain5339/article/details/128017400'
    # 同步
    # asyncio.run(crawl(href))

    # 异步
    task = asyncio.create_task(crawl(href))
    await task


if __name__ == '__main__':
    asyncio.run(main())
