<template>
  <div class="qa-container">
    <div class="messages" ref="messagesContainer">
      <el-card v-for="(msg, index) in qaStore.messages" :key="index" :class="['message-card', msg.role]" shadow="hover">
        <div class="message-content" v-html="qaStore.md.render(msg.content)"></div>
        <div class="message-time">{{ formatTime(msg.timestamp) }}</div>
      </el-card>
      <el-loading v-if="qaStore.isLoading" />
    </div>

    <div class="input-area">
      <el-input v-model="inputMessage" type="textarea" :rows="3" placeholder="输入您的问题..."
        @keyup.enter.native="sendMessage" />
      <div class="button-group">
        <el-button type="primary" @click="sendMessage" :loading="qaStore.isLoading">
          发送
        </el-button>
        <el-button type="danger" @click="clearMessages">
          清除对话
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useQaStore } from '@/store/qa'
import { ElMessage } from 'element-plus'
import { ref, watch, nextTick } from 'vue'

const qaStore = useQaStore()
const inputMessage = ref('')
const messagesContainer = ref(null)

const sendMessage = () => {
  if (qaStore.isLoading) return
  if (inputMessage.value.trim()) {
    qaStore.sendMessage(inputMessage.value)
    inputMessage.value = ''
  }
}

const clearMessages = () => {
  qaStore.clearMessages()
  ElMessage.success('清空成功')
}

const formatTime = (timestamp) => {
  return new Date(timestamp).toLocaleTimeString()
}

// 自动滚动到底部
watch(() => qaStore.messages, async () => {
  await nextTick()
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}, { deep: true })
</script>

<style scoped>
.qa-container {
  height: calc(100vh - 40px);
  display: flex;
  flex-direction: column;
  background-color: #444C54;
  color: white;
  padding: 20px;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  margin-bottom: 20px;
}

.message-card {
  margin-bottom: 15px;
  max-width: 80%;
  border-radius: 10px;
  border: none !important;
}

.message-card.user {
  background-color: #5C6BC0;
  margin-left: auto;
}

.message-card.assistant {
  background-color: #424242;
  margin-right: auto;
}

.message-content {
  margin-bottom: 5px;
  color: white;
}

.message-time {
  font-size: 0.8em;
  opacity: 0.7;
  text-align: right;
  color: white;
}

.input-area {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  background-color: #3a3a3a;
  border-radius: 8px;
}

.button-group {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

:deep(.el-textarea__inner) {
  background-color: #2d2d2d;
  color: white;
  border: none;
}

:deep(.el-loading-mask) {
  background-color: transparent;
}

/* 滚动条样式 */
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

/* 按钮样式 */
.el-button {
  border-color: #444C54;
  color: #E5E5E5;
  transition: all 0.3s ease;
}

.el-button--primary {
  background-color: #3D444D;

  &:hover {
    background-color: #4B535D;
    border-color: #4B535D;
  }
}

.el-button--danger {
  background-color: #5C3D3D;

  &:hover {
    background-color: #6C4747;
    border-color: #6C4747;
  }
}
</style>