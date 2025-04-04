<template>
  <div class="container">
    <router-link to="/">
      <img src="@/assets/images/logo.png" alt="logo">
    </router-link>
    <h1>Rouge Killer</h1>
    <el-menu class="menu" :default-active="activeIndex" mode="vertical" router>
      <el-sub-menu>
        <template #title>

        </template>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import router from '@/router'
import { computed } from 'vue'

const route = useRoute()

// 路由和菜单项的映射关系
const activeIndex = computed(() => {
  return route.path.slice(1) || '/'
})
</script>

<style scoped>
.container {
  height: 100%;
  background-color: #222832;
  padding: 20px;
}

img {
  display: block;
  width: 50%;
  margin: 0 auto;
}

h1 {
  text-align: center;
  color: #98A1AA;
}
</style>
