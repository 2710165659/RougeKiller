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

::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
