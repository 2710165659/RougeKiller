<template>
  <div class="container">
    <el-row :gutter="20">
      <!-- 主内容区 -->
      <el-col :span="16">
        <el-card class="intro-card">
          <template #header>
            <div class="card-header">
              <h1>慧眼追踪插件</h1>
            </div>
          </template>

          <div class="feature-section">
            <h2>核心功能</h2>
            <el-row :gutter="20">
              <el-col :span="12" v-for="(feat, index) in features" :key="index">
                <div class="feature-item">
                  <h3>{{ feat.title }}</h3>
                  <p>{{ feat.desc }}</p>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>

      <!-- 安装指南 -->
      <el-col :span="8">
        <el-card class="install-card">
          <h2>安装指南</h2>
          <el-steps direction="vertical" :active="4">
            <el-step title="打开扩展页面" description="地址栏输入 chrome://extensions/" />
            <el-step title="开启开发者模式" description="右上角切换开发者模式" />
            <el-step title="加载扩展程序" description="点击'加载已解压的扩展程序'" />
            <el-step title="选择插件目录" description="选中本插件的解压目录" />
          </el-steps>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
const features = [
  {
    title: '黑名单同步',
    desc: '一键同步官网最新黑名单数据，保持防护及时性'
  },
  {
    title: '名单管理',
    desc: '支持导入/导出及增删改查操作，灵活管理规则'
  },
  {
    title: '智能屏蔽',
    desc: '自动过滤搜索结果中的危险链接'
  },
  {
    title: '访问预警',
    desc: '实时监控网页访问，拦截危险站点'
  }
]
</script>

<style scoped>
.container {
  padding: 20px;
  min-height: calc(100vh - 84px);
  background-color: #444C54;
  display: flex;
  align-items: stretch;
}

/* 卡片通用样式 */
:deep(.el-card) {
  background-color: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.12);
  color: #e0e0e0;
  height: 90vh;
  display: flex;
  flex-direction: column;
}

:deep(.el-card__body) {
  flex: 1;
}

.card-header h1 {
  color: #fff;
  margin: 0 0 15px 0;
  font-size: 26px;
  letter-spacing: 1.5px;
}

.feature-section {
  padding: 15px;
}

.feature-item {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
  min-height: 140px;
}

.feature-item h3 {
  color: #409EFF;
  margin: 0 0 12px 0;
  font-size: 18px;
  letter-spacing: 0.8px;
}

.feature-item p {
  line-height: 1.8;
  margin: 0;
  color: #cfd8dc;
}

.install-card {
  margin-top: 0;
}

:deep(.el-step__title) {
  color: #fff !important;
  font-size: 15px;
  line-height: 1.8;
}

:deep(.el-step__description) {
  color: #b0bec5 !important;
  font-size: 13px;
  line-height: 1.5;
}

@media (max-width: 992px) {
  .el-col {
    width: 100%;
    margin-bottom: 20px;
  }
}
</style>