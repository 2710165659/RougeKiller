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
      <div class="company-content">
        <el-icon class="company-icon">
          <UserFilled />
        </el-icon>
        <div class="company-name">{{ owner.name }}</div>
        <div class="company-info">
          <el-descriptions :column="2">
            <el-descriptions-item label="公司名称">{{ owner.name }}</el-descriptions-item>
            <el-descriptions-item label="公司类型">{{ owner.companyType }}</el-descriptions-item>
            <el-descriptions-item label="统一社会信用代码">{{ owner.socialCreditCode }}</el-descriptions-item>
            <el-descriptions-item label="法定代表人">{{ owner.legalRepresentative }}</el-descriptions-item>
            <el-descriptions-item label="成立日期">{{ owner.establishMentDate }}</el-descriptions-item>
            <el-descriptions-item label="核准日期">{{ owner.approvalDate }}</el-descriptions-item>
            <el-descriptions-item label="注册资本">{{ owner.registeredCapital }}元</el-descriptions-item>
            <el-descriptions-item label="登记号">{{ owner.registrationNumber }}</el-descriptions-item>
            <el-descriptions-item label="经营范围">{{ owner.businessScope }}</el-descriptions-item>
            <el-descriptions-item label="地址">{{ owner.address }}</el-descriptions-item>
            <el-descriptions-item label="营业期限开始">{{ owner.businessTermStart }}</el-descriptions-item>
            <el-descriptions-item label="营业期限结束">{{ owner.businessTermEnd }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>
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
  color: #e0e0e0;
}

/* 个人信息展示 */
.person {
  position: absolute;
  width: 100%;
  top: 25%;
}

.person-content,
.company-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.person-content .person-icon,
.company-content .company-icon {
  font-size: 50px;
  border-radius: 100px;
  padding: 15px;
  background-color: #84519B;
  color: white;
  margin-bottom: 20px;
}

.person-content .person-name,
.company-content .company-name {
  border-radius: 100px;
  padding: 10px 20px;
  background-color: #80007F;
  color: whitesmoke;
  font-size: 30px;
  font-weight: bold;
}

/* 企业信息*/
.company-content {
  display: flex;
  align-items: center;
  flex-direction: column;
}

.company-content .company-icon {
  font-size: 50px;
  padding: 5px;
  margin: 15px 0;
}

.company-content .company-name {
  padding: 5px 10px;
  font-size: 16px;
}

/* 公司信息表格样式 */
.company-info {
  width: 90%;
  padding: 20px;
}

.company-info :deep(.el-descriptions) {
  background-color: #2c3034;
  padding: 20px;
  border-radius: 8px;
  border: 1px solid white;
}

.company-info :deep(.el-descriptions__label) {
  color: #a0a0a0;
  display: inline-block;
  width: 120px;
}

.company-info :deep(.el-descriptions__content) {
  color: #e0e0e0;
}

.company-info :deep(.el-descriptions__body) {
  background-color: transparent;
}

.company-info :deep(.el-descriptions__table) {
  width: 100%;
}
</style>
