<template>
  <div class="owner-container">
    <div class="person" v-if="ownerNature === '个人'">
      <div class="person-content">
        <el-icon class="person-icon">
          <UserFilled />
        </el-icon>
        <div class="person-name">{{ owner.name }}</div>
      </div>
    </div>
    <div class="company" v-else>

    </div>
  </div>
</template>

<script setup>
import { UserFilled } from '@element-plus/icons-vue'
defineProps(['ownerNature', 'owner'])
</script>

<style scoped>
.owner-container {
  background-color: #343A40;
  width: 100%;
  min-height: 50vh;
  position: relative;
}

/* 个人信息展示 */
.person {
  position: absolute;
  width: 100%;
  top: 25%;
}

.person-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.person-content .person-icon {
  font-size: 50px;
  border-radius: 100px;
  padding: 15px;
  background-color: #84519B;
  color: white;
  margin-bottom: 20px;
}

.person-content .person-name {
  border-radius: 100px;
  padding: 10px 20px;
  background-color: #80007F;
  color: whitesmoke;
  font-size: 30px;
  font-weight: bold;
}

/* 企业信息展示 */
</style>
