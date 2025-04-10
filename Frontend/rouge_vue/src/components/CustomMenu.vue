<template>
  <div class="container">
    <router-link to="/" class="logo">
      <img src="@/assets/images/logo.png" alt="logo">
    </router-link>
    <h1>慧眼追踪</h1>

    <el-menu class="menu" :default-active="activeIndex" mode="vertical" router>

      <el-sub-menu index="websites">
        <template #title>
          <el-icon class="icon">
            <Monitor />
          </el-icon>
          <span>网站分析系统</span>
        </template>
        <el-menu-item index="/websites/show">
          <el-icon class="icon">
            <PieChart />
          </el-icon>
          <span>网站情报总览</span>
        </el-menu-item>
        <el-menu-item index="/websites/search">
          <el-icon class="icon">
            <Search />
          </el-icon>
          <span>网站检索</span>
        </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="review">
        <template #title>
          <el-icon class="icon">
            <AlarmClock />
          </el-icon>
          <span>实时审查系统</span>
        </template>
        <el-menu-item index="/review/detect">
          <el-icon class="icon">
            <VideoPlay />
          </el-icon>
          <span>实时监测</span>
        </el-menu-item>
        <el-menu-item index="/review/task">
          <el-icon class="icon">
            <Setting />
          </el-icon>
          <span>检测任务配置</span>
        </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="trace">
        <template #title>
          <el-icon class="icon">
            <Connection />
          </el-icon>
          <span>追踪溯源系统</span>
        </template>
        <el-menu-item index="/trace/homology">
          <el-icon class="icon">
            <Link />
          </el-icon>
          <span>同源性分析</span>
        </el-menu-item>
        <el-menu-item index="/trace/person">
          <el-icon class="icon">
            <User />
          </el-icon>
          <span>人员追踪</span>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item index="/qa">
        <el-icon class="icon">
          <ChatDotRound />
        </el-icon>
        <span>在线问答</span>
      </el-menu-item>

      <el-sub-menu index="system">
        <template #title>
          <el-icon class="icon">
            <Setting />
          </el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/system/users">
          <el-icon class="icon">
            <UserFilled />
          </el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/system/extension">
          <el-icon class="icon">
            <ChromeFilled />
          </el-icon>
          <span>浏览器插件</span>
        </el-menu-item>
      </el-sub-menu>

    </el-menu>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { Monitor, PieChart, Histogram, AlarmClock, VideoPlay, Connection, Link, User, ChatDotRound, Setting, UserFilled, ChromeFilled, Search } from '@element-plus/icons-vue/dist'
import { computed } from 'vue'

const route = useRoute()

// 路由和菜单项的映射关系
const activeIndex = computed(() => {
  return route.path.slice(1) || '/'
})
</script>

<style scoped>
.container {
  height: 100vh;
  background-color: #32393A;
  user-select: none;
  overflow-y: auto;
}

img {
  display: block;
  width: 30%;
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
  margin-bottom: 20px;
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

/* 美化滚动条 */
.container::-webkit-scrollbar {
  width: 8px;
}

.container::-webkit-scrollbar-track {
  background: #2c3333;
}

.container::-webkit-scrollbar-thumb {
  background-color: #5a6a6a;
  border-radius: 4px;
}

.container::-webkit-scrollbar-thumb:hover {
  background-color: #6d7f7f;
}
</style>
