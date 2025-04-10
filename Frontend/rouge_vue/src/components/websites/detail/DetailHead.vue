<template>
  <div class="head-container">
    <h1>网站详情及分析</h1>
    <p>
      相关网站信息及分析，包含恶意性判断，网站同源性分析，网站特征分析，关联图谱等。
    </p>
  </div>
</template>

<script setup>
</script>

<style scoped>
.head-container {
  background-color: #343A40;
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 100%;
}

h1 {
  position: absolute;
  color: whitesmoke;
  font-size: 20px;
  top: 10%;
  left: 2%;
  user-select: none;
}

p {
  position: absolute;
  color: white;
  font-size: 16px;
  top: 45%;
  left: 2%;
}
</style>
