<template>
    <div class="website-card" :class="{ malicious: data.isMalicious }">
        <div class="icon-container" @click="gotoDetail">
            <el-icon :size="40">
                <Link />
            </el-icon>
        </div>

        <div class="url-button" :class="{ bad: data.isMalicious }" @click="gotoDetail">
            <span>{{ text }}</span>
            {{ data.url }}
        </div>

        <div class=" info-list">
            <div class="info-item">
                <span class="info-label">标题：</span>
                <span class="info-value">{{ data.title }}</span>
            </div>
            <div class="info-item">
                <span class="info-label">URL：</span>
                <span class="info-value">{{ data.url }}</span>
            </div>
            <div class="info-item">
                <span class="info-label">端口：</span>
                <span class="info-value">{{ data.port }}</span>
            </div>
            <div class="info-item">
                <span class="info-label">所属：</span>
                <span class="info-value">{{ data.company }}</span>
            </div>
            <div class="info-item">
                <span class="info-label">备案：</span>
                <span class="info-value">{{ data.serviceCode }}</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from '@/router'
import { Link } from '@element-plus/icons-vue'
import { ref } from 'vue'

const props = defineProps(['data'])
const text = ref(props.data.isMalicious ? '恶意' : '正常')

const gotoDetail = () => {
    router.push('/websites/detail/' + props.data.id)
}
</script>

<style scoped>
.website-card {
    transition: all 0.3s;
    border-radius: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
    overflow: hidden;
    background: #343a40;
    color: #e0e0e0;
}

.icon-container {
    text-align: center;
    margin-top: 8px;
    margin-bottom: 3px;
}

.url-button {
    font-size: 12px;
    padding: 7px;
    text-align: center;
    color: #507cff;
}

.url-button span {
    border: 1px solid #409eff;
    padding: 2px 8px;
    border-radius: 12px;
    margin-right: 5px;
    color: #409eff;
}

.bad {
    color: #f56c6c;
}

.bad span {
    color: #f56c6c;
    border-color: #f56c6c;
}

.website-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 18px 0 rgba(0, 0, 0, 0.15);
}

.malicious .icon-container :deep(svg),
.malicious .url-button :deep(.el-button) {
    color: #f56c6c;
}

.website-card:not(.malicious) .icon-container :deep(svg),
.website-card:not(.malicious) .url-button :deep(.el-button) {
    color: #409eff;
}

.info-list {
    padding-left: 30px;
    margin-top: 10px;
    margin-bottom: 10px;
}

.info-item {
    display: flex;
    margin-bottom: 7px;
    line-height: 1.2;
}

.info-label {
    width: 60px;
    color: #a0a8b0;
    font-weight: bold;
}

.info-value {
    flex: 1;
    color: #e0e0e0;
}
</style>
