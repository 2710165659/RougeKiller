<template>
    <el-dialog
        v-model="visible"
        title="检测任务详情"
        width="600px"
        :before-close="handleClose"
    >
        <div class="task-info-container" v-if="currentData">
            <div class="info-row">
                <span class="label">ID:</span>
                <span class="value">{{ currentData.id }}</span>
            </div>
            <div class="info-row">
                <span class="label">URL:</span>
                <span class="value">{{ currentData.fullUrl }}</span>
            </div>
            <div class="info-row">
                <span class="label">状态:</span>
                <el-tag
                    :type="
                        currentData.status === '完成'
                            ? 'success'
                            : currentData.status === '失败'
                            ? 'danger'
                            : 'primary'
                    "
                >
                    {{ currentData.status }}
                </el-tag>
            </div>
            <div class="info-row">
                <span class="label">添加人:</span>
                <span class="value">{{ currentData.name }}</span>
            </div>
            <div class="info-row">
                <span class="label">创建时间:</span>
                <span class="value">{{
                    formatDate(currentData.createdAt)
                }}</span>
            </div>
            <div class="info-row">
                <span class="label">更新时间:</span>
                <span class="value">{{
                    formatDate(currentData.updatedAt)
                }}</span>
            </div>
            <div v-if="currentData.errorMessage" class="info-row">
                <span class="label">错误信息:</span>
                <span class="value error">{{ currentData.errorMessage }}</span>
            </div>
        </div>
        <template #footer>
            <el-button @click="visible = false">关闭</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref } from 'vue'

const visible = ref(false)
const currentData = ref(null)

const handleClose = (done) => {
    done()
}

const formatDate = (dateString) => {
    if (!dateString) return ''
    const date = new Date(dateString)
    return date.toLocaleString('zh-CN')
}

defineExpose({
    show: (data) => {
        currentData.value = data
        visible.value = true
    }
})
</script>

<style scoped>
.task-info-container {
    padding-left: 20px;
    background-color: #2d3338;
    border-radius: 4px;
}

.info-row {
    display: flex;
    margin-bottom: 8px;
    line-height: 1.5;
    padding: 8px 0;
    border-bottom: 1px solid #3a4249;
}

.info-row:last-child {
    border-bottom: none;
}

.label {
    width: 80px;
    color: #a8b3cf;
    font-weight: bold;
}

.value {
    flex: 1;
    color: #e0e6ed;
}

.error {
    color: #f56c6c;
}

:global(.el-dialog) {
    background-color: #2d3338 !important;
    color: #e0e6ed !important;
    border: none !important;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5) !important;
    padding-left: 30px;
    padding-top: 30px;
}

.el-dialog__header {
    background-color: #2d3338 !important;
    border-bottom: 1px solid #3a4249;
    padding: 15px 20px;
}

:global(.el-dialog__title) {
    color: #e0e6ed !important;
    font-size: 18px;
    font-weight: bold;
}

.el-dialog__body {
    background-color: #2d3338 !important;
    padding: 0;
    color: #e0e6ed;
}

:deep(.el-dialog__footer) {
    height: 30px;
    background-color: #2d3338 !important;
    border-top: 1px solid #3a4249;
    padding-top: 0;
}

.el-dialog__headerbtn {
    color: #a8b3cf;
}
</style>