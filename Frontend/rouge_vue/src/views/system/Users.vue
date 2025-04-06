<template>
  <div class="container">
    <div class="loading" v-loading="userStore.loading"></div>
    <div v-if="!userStore.loading" class="user-card">
      <div class="user-info">
        <h2>用户信息</h2>
        <div class="info-item">
          <span class="label">用户名：</span>
          <span class="value">{{ user.name }}</span>
        </div>
        <div class="info-item">
          <span class="label">邮箱：</span>
          <span class="value">{{ user.email }}</span>
        </div>
        <div class="info-item">
          <span class="label">身份：</span>
          <span class="value">{{ user.role }}</span>
        </div>
        <div class="info-item">
          <span class="label">注册时间：</span>
          <span class="value">{{ user.createdAt }}</span>
        </div>
        <div class="info-item">
          <span class="label">更新时间：</span>
          <span class="value">{{ user.updatedAt }}</span>
        </div>
      </div>

      <div class="actions">
        <el-button type="primary" @click="handleEdit">修改账户</el-button>
        <el-button type="warning" @click="handleLogout">退出登录</el-button>
        <el-button type="danger" @click="confirmDelete">注销账户</el-button>
      </div>
    </div>

    <!-- 修改账户弹窗 -->
    <el-dialog v-model="showEditDialog" title="修改账户信息" width="30%">
      <el-form :model="editForm" :rules="rules" label-width="80px" ref="formRef">
        <el-form-item label="用户名">
          <el-input v-model="editForm.name" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password" type="password" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="handleUpdate">确认</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import router from '@/router'
import { useUserStore } from '@/store/user'
import { useAuthStore } from '@/store/auth'
import { ElMessage, ElMessageBox } from 'element-plus'
import { onBeforeMount, ref } from 'vue'
import { storeToRefs } from 'pinia'

const userStore = useUserStore()
const { user } = storeToRefs(userStore)
const showEditDialog = ref(false)
const editForm = ref({
  name: '',
  password: ''
})

const rules = {
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ]
}

onBeforeMount(async () => {
  try {
    await userStore.fetchUser()
  } catch (error) {
    handleError(error)
  }
})
const handleEdit = () => {
  editForm.value.password = ''
  editForm.value.name = user.value.name
  showEditDialog.value = true
}

const formRef = ref(null)

const handleUpdate = async () => {
  try {
    await formRef.value.validate()
    await userStore.updateUser(editForm.value)
    ElMessage.success('账户信息更新成功')
    showEditDialog.value = false
  } catch (error) {
    handleError(error)
  }
}

const confirmDelete = () => {
  ElMessageBox.confirm('确定要注销账户吗？此操作不可恢复', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await userStore.deleteUser()
      ElMessage.success('账户已注销')
      window.location.href = '/login'
    } catch (error) {
      handleError(error)
    }
  }).catch(() => {

  })
}

const handleError = (error) => {
  ElMessage.error(error.response?.data || error.message)
  setTimeout(() => router.push('/login'), 500)
}

const authStore = useAuthStore()
const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    authStore.clearAuth()
    router.push('/login')
  }).catch(() => {

  })
}
</script>

<style scoped>
.container {
  padding-top: 10%;
}

.user-card {
  background-color: #2D333B;
  border-radius: 8px;
  padding: 20px;
  color: #E5E5E5;
  max-width: 600px;
  margin: 0 auto;
}

.user-info h2 {
  color: #FFFFFF;
  margin-bottom: 20px;
  border-bottom: 1px solid #444C54;
  padding-bottom: 10px;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
}

.label {
  font-weight: bold;
  width: 100px;
  color: #ADBAC7;
}

.value {
  flex: 1;
}

.actions {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  gap: 20px;
}

.actions .el-button {
  border-color: #444C54;
  color: #E5E5E5;
}

.actions .el-button--primary {
  background-color: #3D444D;
}

.actions .el-button--warning {
  background-color: #5C4D3D;
}

.actions .el-button--danger {
  background-color: #5C3D3D;
}

.actions .el-button:hover {
  opacity: 0.9;
}

:deep(.el-dialog) {
  background-color: #252B33;
  color: #E5E5E5;
}

:deep(.el-dialog__title) {
  color: #FFFFFF;
}

:deep(.el-form-item__label) {
  color: #ADBAC7 !important;
}

.loading {
  position: absolute;
  top: 50%;
  margin-left: 20%;
  width: 80%;
}
</style>