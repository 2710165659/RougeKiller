<template>
    <el-dialog
        v-model="visible"
        title="新增检测任务"
        width="600px"
        :before-close="handleClose"
    >
        <el-form :model="form" label-width="80px">
            <el-form-item label="URL" required>
                <el-input
                    v-model="form.url"
                    placeholder="请输入待检测的URL"
                    clearable
                />
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button @click="visible = false" type="info">取消</el-button>
            <el-button type="primary" @click="handleSubmit">添加</el-button>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref } from 'vue'
import { useReviewTask } from '@/store/reviewTask'
import { ElMessage } from 'element-plus'

const store = useReviewTask()
const visible = ref(false)
const form = ref({
    url: ''
})

const handleClose = (done) => {
    done()
}

const handleSubmit = () => {
    store.addTask(form.value.url)
    visible.value = false
    ElMessage.success('新增检测任务成功')
}

defineExpose({
    show: () => {
        form.value.url = ''
        visible.value = true
    }
})
</script>

<style scoped>
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
    padding: 20px;
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

.el-form-item__label {
    color: #a8b3cf;
}

.el-button--primary {
    background-color: #3a5a9c;
    border-color: #4a6db3;
    color: #ffffff;
}
.el-button--primary:hover {
    background-color: #4a6db3;
    border-color: #5a7dc3;
    color: #ffffff;
}
.el-button--info {
    background-color: #343a40;
    border-color: #495057;
    color: #d1d5db;
}
.el-button--info:hover {
    background-color: #3e464e;
    border-color: #6c757d;
    color: #ffffff;
}
</style>