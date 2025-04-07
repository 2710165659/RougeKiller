<template>
  <div class="not-found-container">
    <div class="error-content">
      <div class="error-code">404</div>
      <h1 class="error-title">页面未找到</h1>
      <p class="error-message">您访问的页面不存在或已被移除</p>
      <div class="action-buttons">
        <el-button type="primary" @click="router.push('/')">返回首页</el-button>
        <el-button @click="router.back()">返回上一页</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
</script>

<style scoped>
.not-found-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 80vh;
  background-color: #444C54;
  color: #ECF0F1;
  padding: 2rem;
  text-align: center;
}

.error-content {
  max-width: 600px;
  margin-bottom: 2rem;
}

.error-code {
  font-size: 8rem;
  font-weight: bold;
  color: #E74C3C;
  line-height: 1;
  margin-bottom: 1rem;
  user-select: none;
}

.error-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: #ECF0F1;
  user-select: none;
}

.error-message {
  font-size: 1.2rem;
  margin-bottom: 2rem;
  color: #BDC3C7;
}

.action-buttons {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

@media (max-width: 768px) {
  .error-code {
    font-size: 6rem;
  }

  .error-title {
    font-size: 2rem;
  }

  .action-buttons {
    flex-direction: column;
    width: 100%;
    max-width: 200px;
    margin: 0 auto;
  }

  .action-buttons .el-button {
    width: 100%;
  }
}
</style>