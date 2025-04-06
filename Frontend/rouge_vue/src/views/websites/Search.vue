<template>
    <div class="search-container">
        <!-- 标题栏 -->
        <div class="search-title">网站检索</div>

        <!-- 搜索栏 -->
        <div class="search-bar">
            <el-form :model="store.searchForm" label-width="80px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="网站标题">
                            <el-input v-model="store.searchForm.title" placeholder="请输入网站标题" :prefix-icon="Search" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="网站URL">
                            <el-input v-model="store.searchForm.url" placeholder="请输入URL" :prefix-icon="Connection" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="公司名称">
                            <el-input v-model="store.searchForm.company" placeholder="请输入公司名称"
                                :prefix-icon="OfficeBuilding" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="4" :offset="1">
                        <el-button type="primary" @click="handleSearch" :icon="Search">查询</el-button>
                        <el-button @click="resetSearch" :icon="Refresh">清空</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </div>

        <!-- 搜索结果结果区域 -->
        <div class="result-area">
            <div class="left-space">
                <div class="result-stats">
                    <div class="stats-header">
                        <span>相关结果</span>
                        <el-icon>
                            <InfoFilled />
                        </el-icon>
                    </div>
                    <div class="stats-count">{{ store.total }}</div>
                </div>
                <div class="chart">
                    <CountCirclrChart />
                </div>
            </div>
            <div class="result-list">
                <WebsiteCard v-for="item in store.websiteList" :key="item.id" :data="item" />
            </div>
        </div>

        <!-- 分页 -->
        <div class="pagination">
            <el-pagination :current-page="store.searchForm.pageNum" :page-size="store.searchForm.pageSize"
                :background="true" layout="prev, pager, next" :total="store.total" @current-change="
                    (val) => {
                        store.searchForm.pageNum = val
                        store.searchWebsites()
                    }
                " />
        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { Search, Refresh, OfficeBuilding, Connection, InfoFilled } from '@element-plus/icons-vue'
import WebsiteCard from '@/components/websites/search/WebsiteCard.vue'
import { useWebsitesSearchStore } from '@/store/websitesSearch'
import CountCirclrChart from '@/components/websites/search/CountCirclrChart.vue'

const store = useWebsitesSearchStore()

onMounted(() => {
    store.searchWebsites()
})

const handleSearch = () => {
    store.searchForm.pageNum = 1
    store.searchWebsites()
}

const resetSearch = () => {
    store.resetSearch()
    store.searchWebsites()
}
</script>

<style scoped>
/* 上面的 */
.search-title {
    color: #efefef;
    width: 97%;
    padding: 10px;
    font-size: 20px;
    border-bottom: 0.5px solid #999;
    user-select: none;
    margin-bottom: 10px;
}


/* 中间部分：搜索结果 */
.result-area {
    display: flex;
    justify-content: space-between;
    padding: 0 20px;
}

.left-space {
    width: 24%;
    height: 77vh;
}

.result-list {
    width: 74%;
    height: 77vh;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
}


.result-stats {
    background: #343a40;
    border-radius: 10px;
    padding: 20px;
    color: whitesmoke;
    margin-bottom: 20px;
}

.stats-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
    font-size: 14px;
}

.stats-header .el-icon {
    color: #a0a8b0;
}

.stats-count {
    font-size: 22px;
    font-weight: 500;
    color: #ffffff;
}

/* 底部分页 */
.pagination {
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    margin-left: 7%;
}

/* element-plus */
:deep .el-form-item__label,
:deep .el-pagination__total,
:deep .el-pagination__goto,
:deep .el-input__inner {
    color: #eee;
}

:deep .el-input__wrapper,
:deep .el-pagination.is-background .btn-prev,
:deep .el-pagination.is-background .btn-next,
:deep .el-pagination.is-background .el-pager li {
    background-color: #343a40;
    color: #d1d5db;
}

:deep .el-pagination.is-background .el-pager li.is-active {
    background-color: #20262c !important;
    color: #ffffff !important;
}

:deep .el-pagination.is-background .btn-prev:hover,
:deep .el-pagination.is-background .btn-next:hover,
:deep .el-pagination.is-background .el-pager li:hover {
    background-color: #3e464e;
}

/* 按钮样式 */
.el-button {
    background-color: #343a40;
    border-color: #495057;
    color: #d1d5db;
}

.el-button:hover {
    background-color: #3e464e;
    border-color: #6c757d;
    color: #ffffff;
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
</style>
