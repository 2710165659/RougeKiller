const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, // 关闭eslint检查
  pages: {
    index: {
      entry: 'src/main.js',
      title: "RougeKiller管理界面"
    }
  },
  // 代理服务器
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:3000',
        // pathRewrite: { '^/api': '' },
        changeOrigin: true
      }
    }
  }
})
