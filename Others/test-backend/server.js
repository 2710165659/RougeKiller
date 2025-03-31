const express = require('express');
const app = express();
const port = 3000;

// 网址列表数据
const websites = [
  'https://www.baidu.com',
  'https://www.taobao.com',
  'https://www.jd.com',
  'https://www.zhihu.com',
  'https://www.bilibili.com',
  'https://www.sina.com.cn',
  'https://www.qq.com',
  'https://www.163.com'
];

// 设置路由
app.get('/websites', (req, res) => {
  res.json(websites);
});

// 启动服务器
app.listen(port, () => {
  console.log(`服务器运行在 http://localhost:${port}`);
});