<template>
  <div class="app-container">
    <custom-menu class="sidebar" />
    <router-view class="main-content" />
  </div>
</template>

<script setup>
import CustomMenu from '@/components/CustomMenu.vue'

</script>

<style scoped>
.app-container {
  height: 100vh;
  display: flex;
}

.sidebar {
  position: fixed;
  flex: 1;
}

.main-content {
  margin-left: 17vw;
  flex: 5;
}
</style>