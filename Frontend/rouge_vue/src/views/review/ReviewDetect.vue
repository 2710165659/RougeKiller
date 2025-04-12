<template>
    <div class="container">
        <div class="bg" ref="bgRef"></div>
        <div class="content">
            <h1 class="title">慧眼追踪</h1>
            <div class="search-container">
                <el-input
                    ref="inputRef"
                    v-model="inputUrl"
                    placeholder="输入待检测的网址链接"
                    class="search-input"
                    :suffix-icon="Search"
                    size="large"
                    @blur="handleBlur"
                    @focus="handleFocus"
                />
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'

const inputUrl = ref('')
const inputRef = ref(null)
const bgRef = ref(null)
const handleBlur = () => {
    inputRef.value?.$el.classList.remove('search-input-focused')
    bgRef.value?.classList.remove('blur-background')
}

const handleFocus = () => {
    inputRef.value?.$el.classList.add('search-input-focused')
    bgRef.value?.classList.add('blur-background')
}
</script>

<style scoped>
.content {
    position: absolute;
    top: 20%;
    width: 80%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.bg {
    background-image: url('@/assets/images/auth-bg.png');
    background-size: 100%;
    width: 100%;
    height: 100%;
    line-height: 100%;
    transition: background-size 0.5s ease, filter 0.5s ease;
}

.blur-background {
    -webkit-filter: blur(5px);
    -moz-filter: blur(5px);
    -o-filter: blur(5px);
    -ms-filter: blur(5px);
    filter: blur(5px);
    background-size: 110%;
    transition: background-size 0.5s ease, filter 0.5s ease;
}

.search-input-focused {
    transform: scale(1.1);
    transition: transform 0.5s ease;
    opacity: 1 !important;
}

.title {
    font-family: cursive, sans-serif;
    font-size: 4rem;
    color: #b1b2b2;
    text-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    margin-bottom: 1.5rem;
}

.search-container {
    width: 50%;
}

.search-input {
    opacity: 0.8;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 25px;
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: transform 0.5s ease;
}

.search-input :deep(.el-input__wrapper) {
    background: transparent;
    box-shadow: none;
}

.search-input :deep(.el-input__inner) {
    color: #fff;
}

.search-input :deep(.el-input__inner::placeholder) {
    color: rgba(255, 255, 255, 0.7);
}
</style>
