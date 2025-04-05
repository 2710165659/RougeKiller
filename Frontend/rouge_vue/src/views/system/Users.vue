<template>
  <div class="container">
    <div class="loading" v-loading="userStore.loading"></div>
    <!-- 模版文件直接用store里的数据是响应式的 -->
    <div v-if="!userStore.loading" class="content">
      <span>用户名{{ user.name }}</span>
      <span>邮箱 {{ user.email }}</span>
      <span>身份 {{ user.role }}</span>
      <span>注册时间 {{ user.createdAt }}</span>
      <span>更新时间 {{ user.updatedAt }}</span>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'
import { onBeforeMount, computed } from 'vue'

const userStore = useUserStore()

// js里用store里的数据要保持响应式，用computed
const user = computed(() => userStore.user)

onBeforeMount(() => {
  userStore.fetchUser().catch(error => {
    ElMessage.error(error.message)
  })
})
</script>

<style scoped>
.loading {
  position: absolute;
  top: 50%;
  width: 80%;
}
</style>