<template>
  <div class="wrapper">
    <div class="loading" v-loading="store.loading"></div>
    <div v-show="!store.loading" class="container">
      <detail-head class="head" />
      <div class="row">
        <web-detail class="detail" :detail="store.detail" />
        <web-owner class="owner" :ownerNature="store.ownerNature" :owner="store.owner" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useWebsitesDetailStore } from '@/store/websitesDetail'
import DetailHead from '@/components/websites/detail/DetailHead.vue'
import WebDetail from '@/components/websites/detail/WebDetail.vue'
import WebOwner from '@/components/websites/detail/WebOwner.vue'
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

.head {
  height: 100px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.row {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.row .detail {
  width: 25%;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.row .owner {
  width: 73%;
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
