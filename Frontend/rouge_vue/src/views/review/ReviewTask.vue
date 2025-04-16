<template>
    <div class="task-container">
        <div class="task-title">检测任务配置</div>
        <div class="search-container">
            <el-form label-width="80px">
                <el-row>
                    <el-col :span="6" :offset="1">
                        <el-form-item label="URL">
                            <el-input v-model="store.params.url" placeholder="请输入URL" class="search-input"
                                :prefix-icon="Search" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="1">
                        <el-form-item label="任务状态">
                            <el-select v-model="store.params.status" placeholder="请选择任务状态" effect="dark">
                                <el-option label="待定" value="待定" />
                                <el-option label="进行中" value="进行中" />
                                <el-option label="失败" value="失败" />
                                <el-option label="完成" value="完成" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="2">
                        <el-button type="primary" @click="search" :icon="Search">
                            搜索
                        </el-button>
                        <el-button @click="resetSearch" :icon="Refresh" type="info">清空</el-button>
                        <el-button type="primary" @click="handleAddTask">
                            新增检测任务
                        </el-button>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div class="table-container">
            <el-table class="table" :data="store.data" stripe table-layout="auto">
                <el-table-column prop="id" label="ID" sortable />
                <el-table-column prop="fullUrl" label="检测URL" />
                <el-table-column prop="name" label="添加人" align="center" sortable />
                <el-table-column prop="status" label="任务状态" align="center" sortable />
                <el-table-column label="任务操作" align="center">
                    <template #default="data">
                        <el-button type="success" @click.prevent="store.startTask(data.row.id)" :disabled="data.row.status === '完成' ||
                            data.row.status === '进行中'
                            ">
                            {{ data.row.status === '失败' ? '重试' : '开始' }}
                        </el-button>
                        <el-button type="danger" @click.prevent="handleDeleteTask(data.row.id)">
                            删除
                        </el-button>
                        <el-button type="info" @click.prevent="taskInfoRef.show(data.row)">
                            查看
                        </el-button>
                    </template>
                </el-table-column>
                >
                <template #append>
                    <div v-if="store.loading" class="loading-more">
                        加载中...
                    </div>
                    <div v-else-if="store.hasMore" class="load-more" @click="search(true)">
                        点击加载更多
                    </div>
                    <div v-else class="no-more">没有更多数据了</div>
                </template>
            </el-table>
        </div>
        <TaskInfo ref="taskInfoRef" />
        <AddTask ref="addTaskRef" />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { Search, Refresh } from '@element-plus/icons-vue'
import { useReviewTask } from '@/store/reviewTask'
import { onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import TaskInfo from '@/components/review/TaskInfo.vue'
import AddTask from '@/components/review/AddTask.vue'

const store = useReviewTask()
const taskInfoRef = ref()
const addTaskRef = ref()

onMounted(() => {
    search()
})

const handleAddTask = () => {
    addTaskRef.value.show()
}

const handleDeleteTask = async (id) => {
    try {
        await ElMessageBox.confirm('确定要删除此任务吗？', '删除确认', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            effect: 'dark'
        })
        store.deleteTask(id)
        ElMessage.error('删除成功')
    } catch (error) {
        // 用户点击取消
    }
}

const search = async (flag = false) => {
    try {
        await store.searchTasks(flag)
    } catch (error) {
        ElMessage.error(error.response?.data || error.message)
    }
}

const resetSearch = async () => {
    try {
        await store.resetSearch()
    } catch (error) {
        ElMessage.error(error.response?.data || error.message)
    }
}
</script>

<style scoped>
.task-container {
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    min-height: 90vh;
    width: 100%;
}

/* 标题栏 */
.task-title {
    color: #efefef;
    width: 97%;
    padding: 10px;
    font-size: 20px;
    border-bottom: 0.5px solid #999;
    user-select: none;
    margin-bottom: 10px;
}

.search-container {
    width: 100%;
}

/* 表格容器 */
.table-container {
    height: auto;
    padding-bottom: 20px;
}

.table {
    display: block;
    width: 70vw;
}

.loading-more,
.load-more,
.no-more {
    margin-top: 2px;
    text-align: center;
    color: #5f6468;
}

/* 表格样式 */
:deep(.el-table) {
    background-color: #343a40;
    color: #d1d5db;
    border: 1px solid #495057;
}

:deep(.el-table th) {
    background-color: #2c3136;
    color: #ffffff;
    font-weight: bold;
}

:deep(.el-table tr) {
    background-color: #343a40;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped td) {
    background-color: #3a4148;
}

:deep(.el-table td),
:deep(.el-table th.is-leaf) {
    border-bottom: 1px solid #495057;
}

:deep(.el-table--enable-row-hover .el-table__body tr:hover > td) {
    background-color: #3e464e;
}

/* element-plus */
:deep(.el-form-item__label),
:deep(.el-input__inner) {
    color: #eee;
}

:deep(.el-select__wrapper *),
:deep(.el-select__wrapper),
:deep(.el-input__wrapper) {
    background-color: #343a40;
    color: #d1d5db;
}

/* 按钮样式 */

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

.el-button--success {
    background-color: #28a746d6;
    border-color: #28a745;
    color: #ffffff;
}

.el-button--success:hover {
    background-color: #34c759d9;
    border-color: #34c759;
    color: #ffffff;
}

.el-button--danger {
    background-color: #dc3546dc;
    border-color: #dc3545;
    color: #ffffff;
}

.el-button--danger:hover {
    background-color: #ff3a30d7;
    border-color: #ff3b30;
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
