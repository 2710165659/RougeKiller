<template>
    <div class="search-container">
        <!-- 搜索栏 -->
        <el-row>
            <el-col :span="24">
                <div class="search-title">软件下载网站检索</div></el-col
            >
        </el-row>
        <div class="search-bar">
            <el-form :model="store.searchForm" label-width="80px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="网站标题">
                            <el-input
                                v-model="store.searchForm.title"
                                placeholder="请输入网站标题"
                                :prefix-icon="Search"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="网站URL">
                            <el-input
                                v-model="store.searchForm.url"
                                placeholder="请输入URL"
                                :prefix-icon="Connection"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="公司名称">
                            <el-input
                                v-model="store.searchForm.company"
                                placeholder="请输入公司名称"
                                :prefix-icon="OfficeBuilding"
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="5" :push="1">
                        <el-button
                            type="primary"
                            @click="handleSearch"
                            :icon="Search"
                            >查询</el-button
                        >
                        <el-button @click="resetSearch" :icon="Refresh"
                            >清空</el-button
                        >
                    </el-col>
                </el-row>
            </el-form>
        </div>

        <!-- 结果区域 -->
        <div class="result-area">
            <div class="left-space">
                <div class="result-stats">
                    <div class="stats-header">
                        <span>相关结果</span>
                        <el-icon><InfoFilled /></el-icon>
                    </div>
                    <div class="stats-count">{{ store.total }}</div>
                </div>
                <div class="chart">
                    <CountCirclrChart />
                </div>
            </div>
            <div class="result-list">
                <WebsiteCard
                    v-for="item in store.websiteList"
                    :key="item.id"
                    :data="item"
                />
            </div>
        </div>

        <!-- 分页 -->
        <div class="pagination">
            <el-pagination
                :current-page="store.searchForm.pageNum"
                :page-size="store.searchForm.pageSize"
                :background="true"
                layout="total, prev, pager, next, jumper"
                :total="store.total"
                @current-change="
                    (val) => {
                        store.searchForm.pageNum = val
                        store.searchWebsites()
                    }
                "
            />
        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue'
import {
    Search,
    Refresh,
    OfficeBuilding,
    Connection,
    User,
    InfoFilled
} from '@element-plus/icons-vue'
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
.search-title {
    color: #efefef;
    padding: 10px;
    font-size: 20px;
    border-bottom: 0.5px solid #999;
}
.search-bar {
    margin-top: 10px;
    padding-left: 20px;
}
.result-area {
    display: flex;
}
.left-space {
    width: 280px;
    padding: 20px;
}

.result-stats {
    background: #343a40;
    border-radius: 8px;
    padding: 16px;
    color: #e0e0e0;
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

.result-list {
    flex: 1;
    padding-right: 20px;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    /* grid-auto-rows: minmax(280px, auto); */
    gap: 20px;
}
.pagination {
    position: absolute;
    bottom: 10px;
    left: 20%;
}
::v-deep .el-form-item__label,
::v-deep .el-pagination__total,
::v-deep .el-pagination__goto {
    color: #eee;
}
::v-deep .el-input__wrapper,
::v-deep .el-pagination.is-background .btn-prev,
::v-deep .el-pagination.is-background .btn-next,
::v-deep .el-pagination.is-background .el-pager li {
    background-color: #343a40; /* 更深的按钮背景 */
    color: #d1d5db;
}

::v-deep .el-pagination.is-background .el-pager li.is-active {
    background-color: #20262c !important; /* 更柔和的选中色 */
    color: #ffffff !important;
}

::v-deep .el-pagination.is-background .btn-prev:hover,
::v-deep .el-pagination.is-background .btn-next:hover,
::v-deep .el-pagination.is-background .el-pager li:hover {
    background-color: #3e464e; /* 悬停效果 */
}
</style>
