<template>
  <router-view></router-view>
</template>

<script setup>
import { useAuthStore } from './store/auth'


// 恢复登录状态
const authStore = useAuthStore()
authStore.initializeAuth()

</script>

<style>
* {
  margin: 0;
  padding: 0;
}

body {
  background-color: #444C54;
}
</style>
