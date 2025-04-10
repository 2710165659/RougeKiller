<template>
  <div class="login-container">
    <h1>欢迎注册慧眼追踪</h1>
    <div class="form-container">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" class="login-form">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" placeholder="请再次输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" plain>注册</el-button>
        </el-form-item>
        <div class="register-link">
          已有账号？<router-link to="/login">立即登录</router-link>
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
  password: '',
  confirmPassword: ''
})

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.value.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度至少为6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validatePass2, trigger: 'blur' }
  ]
}

const authStore = useAuthStore()
const router = useRouter()

const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await authStore.register(form.value)
        ElMessage.success('注册成功')
        router.push('/login')
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
