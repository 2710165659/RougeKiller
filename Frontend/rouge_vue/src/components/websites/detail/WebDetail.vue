<template>
  <div class="detail-container">
    <div class="content">
      <div class="url-header" :class="{ 'malicious': detail.isMalicious }">
        <el-icon class=" url-icon">
          <Link />
        </el-icon>
        <span class="url-text">{{ detail.url }}</span>
      </div>

      <div class="info-section">
        <div class="info-item">
          <span class="label">URL:</span>
          <span class="value">{{ detail.url }}</span>
        </div>

        <div class="info-item">
          <span class="label">标题:</span>
          <span class="value">{{ detail.title }}</span>
        </div>

        <div class="info-item">
          <span class="label">检测结果:</span>
          <span class="value">{{ detail.result }}</span>
        </div>

        <div class="info-item">
          <span class="label">IP地址:</span>
          <span class="value">{{ detail.ip }}</span>
        </div>

        <div class="info-item">
          <span class="label">端口:</span>
          <span class="value">{{ detail.port }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Link } from '@element-plus/icons-vue'

defineProps(['detail'])
</script>

<style scoped>
.detail-container {
  background-color: #343A40;
  width: 100%;
  min-height: 50vh;
  display: flex;
  flex-direction: column;
}

.content {
  padding: 20px;
  color: #f8f9fa;
}

/* header */

.url-header {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;
}

.url-icon {
  font-size: 50px;
}

.url-text {
  font-size: 20px;
}

.url-icon,
.url-text {
  color: #409EFF;
}

.malicious .url-icon,
.malicious .url-text {
  color: #ff3b3b;
}


/* 列表项 */
.info-section {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 0;
  border-bottom: 1px dashed #495057;
}

.info-item:last-child {
  border-bottom: none;
}

.label {
  font-weight: 700;
  min-width: 80px;
  color: #f8f9fa;

}

.value {
  flex: 1;
  text-align: right;
  padding: 4px 8px;
  border: 1px solid #495057;
  border-radius: 4px;
  color: #adb5bd;
  word-break: break-word;
}
</style>
