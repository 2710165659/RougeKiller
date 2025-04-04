<template>
  <div class="container">
    <router-link to="/" class="logo">
      <img src="@/assets/images/logo.png" alt="logo">
    </router-link>
    <h1>Rouge Killer</h1>

    <el-menu class="menu" :default-active="activeIndex" mode="vertical" router>

      <el-sub-menu index="websites">
        <template #title>
          <el-icon class="icon">
            <DataAnalysis />
          </el-icon>
          <span>网站分析系统</span>
        </template>
        <el-menu-item index="/websites/show">
          <el-icon class="icon">
            <DataBoard />
          </el-icon>
          <span>网站情报总览</span>
        </el-menu-item>
        <el-menu-item index="/websites/diagram">
          <el-icon class="icon">
            <Document />
          </el-icon>
          <span>网站画像</span>
        </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="review">
        <template #title>
          <el-icon class="icon">
            <DataAnalysis />
          </el-icon>
          <span>实时审查系统</span>
        </template>
        <el-menu-item index="/review/detect">
          <el-icon class="icon">
            <DataBoard />
          </el-icon>
          <span>实时监测</span>
        </el-menu-item>
        <el-menu-item index="/review/task">
          <el-icon class="icon">
            <Document />
          </el-icon>
          <span>检测任务配置</span>
        </el-menu-item>
      </el-sub-menu>

    </el-menu>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { DataAnalysis, DataBoard, Document } from '@element-plus/icons-vue/dist'
import { computed } from 'vue'

const route = useRoute()

// 路由和菜单项的映射关系
const activeIndex = computed(() => {
  return route.path.slice(1) || '/'
})
</script>

<style scoped>
.container {
  min-height: 100vh;
  background-color: #32393A;
  user-select: none;
}

img {
  display: block;
  width: 50%;
  margin: 20px auto;
  transition: transform 0.3s ease;
  user-select: none;
}

img:hover {
  transform: scale(1.05);
}

h1 {
  text-align: center;
  color: white;
  margin-bottom: 30px;
  font-weight: 300;
  letter-spacing: 1px;
}

/* 菜单样式 */
.menu,
.menu .el-menu-item {
  background-color: #32393A;
}

.menu span,
.menu .icon,
.menu :deep(.el-sub-menu__icon-arrow) {
  color: white;
}

.menu :deep(.el-sub-menu__title:hover),
.menu .el-menu-item:hover {
  background-color: #8C9B9D;
}
</style>
