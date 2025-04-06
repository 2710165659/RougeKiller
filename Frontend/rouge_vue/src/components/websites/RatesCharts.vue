<template>
  <div class="row-container">
    <h1>比例统计</h1>
    <div ref="chartRef1" class="chart chart1"></div>
    <div ref="chartRef2" class="chart chart2"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { computed, watchEffect, ref } from 'vue';

const props = defineProps(['rates'])
const data = computed(() => { return props.rates })

const chartRef1 = ref(null)
const chartRef2 = ref(null)

watchEffect(() => {
  if (!chartRef1.value || !chartRef2.value) return

  const chart1 = echarts.init(chartRef1.value)
  const chart2 = echarts.init(chartRef2.value)

  const option1 = {

    tooltip: {
      trigger: 'item',
      backgroundColor: '#252B3366',
      borderColor: '#333',
      textStyle: {
        color: '#fff'
      }
    },
    series: [
      {
        type: 'pie',
        radius: '60%',
        data: [
          { value: data.value.maliciousWebNum, name: '恶意网站' },
          { value: data.value.normalWebNum, name: '正常网站' },
        ],
        label: {
          formatter: (params) => {
            return `{a|${params.name}}\n{b|${params.value} (${params.percent}%)}`;
          },
          fontSize: 14,
          rich: {
            a: {
              color: 'inherit'
            },
            b: {
              color: '#fff'
            }
          }
        },
        labelLine: {
          show: true
        },
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }

  const option2 = {

    tooltip: {
      trigger: 'item',
      backgroundColor: '#252B3366',
      borderColor: '#333',
      textStyle: {
        color: '#fff'
      }
    },
    series: [
      {
        type: 'pie',
        radius: '60%',
        data: [
          { value: data.value.maliciousIpNum, name: '恶意Ip' },
          { value: data.value.normalIpNum, name: '正常Ip' },
        ],
        label: {
          formatter: (params) => {
            return `{a|${params.name}}\n{b|${params.value} (${params.percent}%)}`;
          },
          fontSize: 14,
          rich: {
            a: {
              color: 'inherit'
            },
            b: {
              color: '#fff'
            }
          }
        },
        labelLine: {
          show: true
        },
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }

  chart1.setOption(option1)
  chart2.setOption(option2)

  const handleResize = () => {
    chart1.resize()
    chart2.resize()
  }
  window.addEventListener('resize', handleResize)

  return () => {
    window.removeEventListener('resize', handleResize)
    chart1.dispose()
    chart2.dispose()
  }
})
</script>

<style scoped>
.row-container {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
  background-color: #343A40;
}

h1 {
  position: absolute;
  color: whitesmoke;
  font-size: 20px;
  height: 10%;
  top: 3%;
  left: 3%;
}

.chart {
  position: absolute;
  width: 90%;
  height: 40%;
  border: 1px solid whitesmoke;
  border-radius: 10px;
}

.chart1 {
  top: 12%;
  left: 5%;
}

.chart2 {
  top: 54%;
  left: 5%;
}
</style>
