<template>
  <div class="wrapper">
    <div class="loading" v-loading="store.loading"></div>
    <div v-show="!store.loading" class="container">
      <detail-head class="head item" />
      <div class="row item">
        <web-detail class="detail item" :detail="store.detail" />
        <web-owner class="owner item" :ownerNature="store.ownerNature" :owner="store.owner" />
      </div>
      <web-analysis class="diagram item" />
    </div>
  </div>
</template>

<script setup>
import { useWebsitesDetailStore } from '@/store/websitesDetail'
import DetailHead from '@/components/websites/detail/DetailHead.vue'
import WebDetail from '@/components/websites/detail/WebDetail.vue'
import WebOwner from '@/components/websites/detail/WebOwner.vue'
import WebAnalysis from '@/components/websites/detail/WebAnalysis.vue'
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

.item {
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.head {
  height: 100px;
}

.row {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.row .detail {
  width: 30%;
}

.row .owner {
  width: 68%;
}

.diagram {
  width: 100%;
  height: 70vh;
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
