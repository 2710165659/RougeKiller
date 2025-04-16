<template>
    <div class="qa-container">
        <!-- 消息列表区域 -->
        <div class="messages-container" ref="messagesContainer">
            <div
                v-for="(message, index) in qaStore.messages"
                :key="index"
                :class="['message', message.role]"
            >
                <div class="message-content">
                    <div v-if="message.role === 'assistant'" class="avatar">
                        AI
                    </div>
                    <div v-else class="avatar">我</div>
                    <div class="bubble">
                        <div
                            class="markdown-body"
                            v-if="message.role === 'assistant'"
                            v-html="qaStore.md.render(message.content)"
                        ></div>
                        <div v-else>{{ message.content }}</div>
                        <div class="timestamp">
                            {{ formatTime(message.timestamp) }}
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 输入区域 -->
        <div class="input-container">
            <el-input
                v-model="inputMessage"
                placeholder="输入您的问题..."
                @keyup.enter="!isLoading && sendMessage()"
                type="textarea"
                :rows="3"
            />
            <div class="button-group">
                <el-button @click="clearMessages"> 清空对话 </el-button>
                <el-button
                    type="primary"
                    @click="sendMessage"
                    :disabled="qaStore.isLoading"
                >
                    发送
                </el-button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useQaStore } from '@/store/qa'
import { ElMessage } from 'element-plus'
import { ref, watch, nextTick, computed } from 'vue'

const qaStore = useQaStore()
const inputMessage = ref('')
const messagesContainer = ref(null)
const isLoading = computed(() => qaStore.isLoading)

const sendMessage = async () => {
    if (inputMessage.value.trim()) {
        try {
            const content = inputMessage.value
            inputMessage.value = ''
            await qaStore.sendMessage(content)
        } catch (error) {
            ElMessage.error(error.message || '发送消息失败')
        }
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
watch(
    () => qaStore.messages,
    async () => {
        await nextTick()
        if (messagesContainer.value) {
            messagesContainer.value.scrollTop =
                messagesContainer.value.scrollHeight
        }
    },
    { deep: true }
)
</script>

<style scoped>
.qa-container {
    height: calc(100vh - 40px);
    display: flex;
    flex-direction: column;
    background-color: #444c54;
    color: white;
    padding: 20px;
}

.messages-container {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
    margin-bottom: 20px;
}

.message {
    margin-bottom: 20px;
}

.message-content {
    display: flex;
    align-items: flex-start;
    gap: 10px;
}

.avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    flex-shrink: 0;
}

.user .avatar {
    background-color: #95a5a6;
}

.assistant .avatar {
    background-color: #2ecc71;
}

.bubble {
    background: #2d3436;
    padding: 12px;
    border-radius: 10px;
    max-width: 80%;
    word-break: break-word;
}

.markdown-body {
    all: initial;
    color: whitesmoke;
    line-height: 2;
    list-style-position: inside;
}
:deep(.markdown-body p) {
    display: inline;
}

.timestamp {
    font-size: 12px;
    color: #7f8c8d;
    margin-top: 5px;
    text-align: right;
}

.input-container {
    background: #2d3436;
    padding: 20px;
    border-radius: 10px;
}

.button-group {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 10px;
}

:deep(.el-textarea__inner) {
    background-color: #34495e;
    color: white;
    border: none;
}

:deep(.el-textarea__inner:focus) {
    box-shadow: none;
}

:deep(pre) {
    background-color: #1e272e !important;
    border-radius: 5px;
    padding: 10px;
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
</style>