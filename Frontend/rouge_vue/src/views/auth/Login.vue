<template>
  <div class="login-container">
    <h1>欢迎登录Rouge Killer</h1>
    <div class="form-container">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" class="login-form">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" plain>登录</el-button>
        </el-form-item>
        <div class="register-link">
          还没有账号？<router-link to="/register">立即注册</router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/store/auth'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const formRef = ref(null)
const form = ref({
  email: '',
  password: ''
})

const rules = {
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少为6位', trigger: 'blur' }
  ]
}

const authStore = useAuthStore()
const router = useRouter()

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await authStore.login(form.value)
        ElMessage.success('登录成功')
        router.push('/')
      } catch (error) {
        ElMessage.error(error.response.data || error.message)
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-container {
  justify-content: center;
  align-items: center;
  background-color: #02a6b511;
}

h1 {
  display: block;
  margin-top: 20vh;
  margin-bottom: 5vh;
  color: white;
}

.login-form {
  width: 400px;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px 0 rgba(2, 166, 181, 0.2);
  border: 1px solid rgba(2, 166, 181, 0.5);
  background-color: rgba(2, 166, 181, 0.05);
  backdrop-filter: blur(5px);
  text-align: center;
}

.login-form :deep(label) {
  display: block;
  width: 100px !important;
  text-align: center;
  color: white;
}

.login-form :deep(.el-input__wrapper) {
  background-color: transparent;

}

.login-form :deep(.el-input__inner) {
  color: white;
}

.login-form :deep(.el-button) {
  color: white;
  background-color: rgba(2, 166, 181, 0.3);
  width: 80%;
  margin: 10px auto 0;
}

.login-form :deep(.el-form-item__content) {
  margin-left: 0 !important;
}

.register-link {
  margin: 15px auto 0;
  text-align: center;
  color: rgba(255, 255, 255, 0.7);
  width: 80%;
}

.register-link a {
  color: #02a6b5;
  text-decoration: none;
  font-weight: bold;
}

.register-link a:hover {
  text-decoration: underline;
}
</style>
