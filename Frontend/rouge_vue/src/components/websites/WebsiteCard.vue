<template>
    <div class="website-card" :class="{ malicious: data.isMalicious }">
        <div class="icon-container">
            <el-icon :size="40">
                <Link />
            </el-icon>
        </div>

        <div class="url-button">
            <el-button type="text">{{ data.url }}</el-button>
        </div>
        <div class="info-list">
            <div class="info-item">
                <span class="info-label">网站标题：</span>
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
                <span class="info-label">所属公司：</span>
                <span class="info-value">{{ data.company }}</span>
            </div>
            <div class="info-item">
                <span class="info-label">服务代码：</span>
                <span class="info-value">{{ data.serviceCode }}</span>
            </div>
        </div>
    </div>
</template>

<script setup>
import { Link } from '@element-plus/icons-vue'

defineProps({
    data: {
        type: Object,
        required: true
    }
})
</script>

<style scoped>
.website-card {
    transition: all 0.3s;
    border-radius: 8px;
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
    margin-bottom: 8px;
    text-align: center;
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
}

.info-item {
    display: flex;
    margin-bottom: 10px;
    line-height: 1.5;
}

.info-label {
    width: 80px;
    color: #a0a8b0;
    font-weight: bold;
}

.info-value {
    flex: 1;
    color: #e0e0e0;
}
</style>
