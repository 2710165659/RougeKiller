<template>
  <div class="wrapper">
    <div class="loading" v-loading="store.loading"></div>
    <div v-show="!store.loading" class="container">
      <div class="row">

      </div>
      <div class="row">

      </div>
      <div class="row">

      </div>
    </div>
  </div>
</template>

<script setup>
import { useWebsitesDetailStore } from '@/store/websitesDetail'
import { onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'

const store = useWebsitesDetailStore()

import { useRoute } from 'vue-router'
const route = useRoute()

const id = route.params.id

// onBeforeMount(async () => {
//   try {
//     await websitesShowStore.getData()
//     window.dispatchEvent(new Event('resize'));
//   } catch (error) {
//     ElMessage.error(error.response?.data || error.message)
//   }
// })
</script>

<style scoped>
.loading {
  position: absolute;
  top: 50%;
  width: 80%;
}

.wrapper {
  height: calc(100vh - 20px);
  overflow-y: auto;
}

.container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 20px;
}

.row {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

/* 第一行元素 */
.row-item {
  width: 22%;
  height: 150px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
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
