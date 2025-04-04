<template>
  <h2>Login</h2>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/store/auth'
import { useRouter } from 'vue-router'

const form = ref({
  email: '',
  password: ''
})

const authStore = useAuthStore()
const router = useRouter()

const handleLogin = async () => {
  try {
    await authStore.login(form.value)
    router.push('/dashboard')
  } catch (error) {
    alert('Login failed')
  }
}
</script>

<style scoped></style>
