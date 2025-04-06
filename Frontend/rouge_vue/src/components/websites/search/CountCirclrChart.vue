<template>
    <div ref="chartRef" style="width: 100%; height: 300px"></div>
</template>

<script setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import { useWebsitesSearchStore } from '@/store/websitesSearch'

const store = useWebsitesSearchStore()
const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
    if (!chartRef.value) return

    chartInstance = echarts.init(chartRef.value)
    updateChart()
}

const updateChart = () => {
    if (!chartInstance) return

    const option = {
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            orient: 'horizontal',
            right: 10,
            top: 10,
            textStyle: {
                color: '#eee'
            }
        },
        series: [
            {
                name: '网站分类',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                itemStyle: {
                    borderColor: '#eee',
                    borderWidth: 1
                },
                label: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    label: {
                        show: true,
                        fontSize: '18',
                        fontWeight: 'bold',
                        color: '#ffffff'
                    }
                },
                labelLine: {
                    show: false
                },
                data: [
                    { value: store.maliciousCount, name: '恶意网站' },
                    { value: store.normalCount, name: '正常网站' }
                ],
                color: ['#598BF0', '#59D0A1']
            }
        ]
    }

    chartInstance.setOption(option)
}

onMounted(() => {
    initChart()
    window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
    if (chartInstance) {
        chartInstance.dispose()
        chartInstance = null
    }
    window.removeEventListener('resize', handleResize)
})

const handleResize = () => {
    if (chartInstance) {
        chartInstance.resize()
    }
}

watch(
    () => [store.maliciousCount, store.normalCount],
    () => {
        updateChart()
    },
    { deep: true }
)
</script>