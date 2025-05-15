<template>
  <div class="analysis-container">
    <h1>网站关联图谱</h1>
    <div ref="chartRef" class="chart"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { computed, watchEffect, ref } from 'vue';


const props = defineProps(['webArea'])
const data = computed(() => {
  if (!props.webArea) return { area: [], normalNum: [], maliciousNum: [] }
  return {
    area: props.webArea.area.slice(0, 10),
    normalNum: props.webArea.normalNum.slice(0, 10),
    maliciousNum: props.webArea.maliciousNum.slice(0, 10)
  }
})

const chartRef = ref(null)

watchEffect(() => {
  if (!chartRef.value) return

  const chart = echarts.init(chartRef.value)
  const option = {
    title: {
      text: '网站关系图谱',
      top: 'bottom',
      left: 'right',
      textStyle: {
        color: '#eee'
      }
    },
    backgroundColor: '#2c343c',  // 深色背景
    tooltip: {
      trigger: 'item',
      formatter: '{b}'
    },
    legend: {
      data: ['网站', '信息', '单位', '关键词', '相似网站'],
      textStyle: {
        color: '#ddd'
      }
    },
    series: [{
      name: '关系图谱',
      type: 'graph',
      layout: 'force',
      data: [
        // 主网站节点
        {
          id: '0',
          name: 'https://www.32r.com',
          category: 0,
          symbolSize: 35,
          itemStyle: { color: '#5470c6' }
        },
        // 网站信息节点
        { id: '1', name: '网站信息', category: 1, itemStyle: { color: '#91cc75' } },
        { id: '2', name: '是否恶意: 否', category: 1, itemStyle: { color: '#91cc75' } },
        { id: '3', name: '标题: 32软件站', category: 1, itemStyle: { color: '#91cc75' } },
        { id: '4', name: 'IP地址: 218.24.86.80', category: 1, itemStyle: { color: '#91cc75' } },
        { id: '5', name: '端口: 443', category: 1, itemStyle: { color: '#91cc75' } },

        // 所属单位节点
        { id: '6', name: '所属单位', category: 2, itemStyle: { color: '#fac858' } },
        { id: '7', name: '性质: 个人', category: 2, itemStyle: { color: '#fac858' } },
        { id: '8', name: '姓名: 万海林', category: 2, itemStyle: { color: '#fac858' } },
        { id: '9', name: '注册时间: 2022-04-15', category: 2, itemStyle: { color: '#fac858' } },
        { id: '10', name: '备案信息: 赣ICP备20001389号', category: 2, itemStyle: { color: '#fac858' } },

        // 关键词节点
        { id: '11', name: '关键词', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '12', name: '软件站', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '13', name: '火爆推荐', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '14', name: '热门集合', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '15', name: '搞机玩玩', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '16', name: '安卓应用', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '17', name: '游戏', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '18', name: '移植', category: 3, itemStyle: { color: '#ee6666' } },
        { id: '19', name: '破解', category: 3, itemStyle: { color: '#ee6666' } },

        // 相似网站节点
        { id: '20', name: '相似网站', category: 4, itemStyle: { color: '#73c0de' } },
        {
          id: '21',
          name: 'https://www.downcode.com',
          category: 4,
          symbolSize: 30,
          itemStyle: { color: '#5470c6' }
        },
        { id: '22', name: '恶意', category: 4, itemStyle: { color: '#ee6666' } },
        { id: '23', name: '未备案', category: 4, itemStyle: { color: '#ee6666' } },
        {
          id: '24',
          name: 'https://dl.bee-link.cn',
          category: 4,
          symbolSize: 30,
          itemStyle: { color: '#5470c6' }
        },
        { id: '25', name: '正常', category: 4, itemStyle: { color: '#91cc75' } },
        { id: '26', name: '粤ICP备2023003758号-2', category: 4, itemStyle: { color: '#fac858' } },
        { id: '27', name: '所属公司', category: 4, itemStyle: { color: '#fac858' } },
        { id: '28', name: '深圳市安卓微科技有限公司', category: 4, itemStyle: { color: '#fac858' } },
        { id: '29', name: '统一社会信用代码: 91440300567083265K', category: 4, itemStyle: { color: '#fac858' } },
        { id: '30', name: '法定代表人: 李军', category: 4, itemStyle: { color: '#fac858' } },
        { id: '31', name: '注册资本: 5263.158万人民币', category: 4, itemStyle: { color: '#fac858' } },
        { id: '32', name: '成立日期: 2011-01-07', category: 4, itemStyle: { color: '#fac858' } },
        { id: '33', name: '电话: 1800256****', category: 4, itemStyle: { color: '#fac858' } },
        { id: '34', name: '邮箱: tanghong@azw-online.com', category: 4, itemStyle: { color: '#fac858' } },
        { id: '35', name: '网址: www.bee-link.com.cn', category: 4, itemStyle: { color: '#5470c6' } },
        { id: '36', name: '地址: 深圳市龙华区...', category: 4, itemStyle: { color: '#fac858' } },
        { id: '37', name: '国标行业: 软件开发', category: 4, itemStyle: { color: '#fac858' } },
        { id: '38', name: '企业规模: 小型', category: 4, itemStyle: { color: '#fac858' } },
        { id: '39', name: '员工人数: 81人 (2023年)', category: 4, itemStyle: { color: '#fac858' } }
      ],
      links: [
        // 主网站连接
        { source: '0', target: '1', lineStyle: { width: 2 } },
        { source: '0', target: '6', lineStyle: { width: 2 } },
        { source: '0', target: '11', lineStyle: { width: 2 } },
        { source: '0', target: '20', lineStyle: { width: 2 } },

        // 网站信息连接 (实线)
        { source: '1', target: '2', lineStyle: { width: 1.5 } },
        { source: '1', target: '3', lineStyle: { width: 1.5 } },
        { source: '1', target: '4', lineStyle: { width: 1.5 } },
        { source: '1', target: '5', lineStyle: { width: 1.5 } },

        // 所属单位连接 (实线)
        { source: '6', target: '7', lineStyle: { width: 1.5 } },
        { source: '6', target: '8', lineStyle: { width: 1.5 } },
        { source: '6', target: '9', lineStyle: { width: 1.5 } },
        { source: '6', target: '10', lineStyle: { width: 1.5 } },

        // 关键词连接 (实线)
        { source: '11', target: '12', lineStyle: { width: 1.5 } },
        { source: '11', target: '13', lineStyle: { width: 1.5 } },
        { source: '11', target: '14', lineStyle: { width: 1.5 } },
        { source: '11', target: '15', lineStyle: { width: 1.5 } },
        { source: '11', target: '16', lineStyle: { width: 1.5 } },
        { source: '11', target: '17', lineStyle: { width: 1.5 } },
        { source: '11', target: '18', lineStyle: { width: 1.5 } },
        { source: '11', target: '19', lineStyle: { width: 1.5 } },

        // 相似网站连接
        { source: '20', target: '21', lineStyle: { type: 'dashed', width: 1.5, color: '#73c0de' } },  // 网站间虚线
        { source: '20', target: '24', lineStyle: { type: 'dashed', width: 1.5, color: '#73c0de' } }, // 网站间虚线

        // downcode.com 子节点
        { source: '21', target: '22', lineStyle: { type: 'dashed', width: 1, color: '#ee6666' } },  // 恶意信息虚线
        { source: '21', target: '23', lineStyle: { type: 'dashed', width: 1, color: '#ee6666' } },  // 备案信息虚线

        // bee-link.cn 子节点
        { source: '24', target: '25', lineStyle: { type: 'dashed', width: 1, color: '#91cc75' } },  // 是否恶意虚线
        { source: '24', target: '26', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },  // 备案信息虚线
        { source: '24', target: '27', lineStyle: { width: 1.5 } },

        // 公司信息连接 (实线)
        { source: '27', target: '28', lineStyle: { width: 1.5 } },
        { source: '28', target: '29', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },  // 公司信息虚线
        { source: '28', target: '30', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },  // 法人信息虚线
        { source: '28', target: '31', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '32', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '33', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '34', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '35', lineStyle: { type: 'dashed', width: 1, color: '#5470c6' } },  // 网址虚线
        { source: '28', target: '36', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '37', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '38', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } },
        { source: '28', target: '39', lineStyle: { type: 'dashed', width: 1, color: '#fac858' } }
      ],
      categories: [
        { name: '网站', itemStyle: { color: '#5470c6' } },
        { name: '信息', itemStyle: { color: '#91cc75' } },
        { name: '单位', itemStyle: { color: '#fac858' } },
        { name: '关键词', itemStyle: { color: '#ee6666' } },
        { name: '相似网站', itemStyle: { color: '#73c0de' } }
      ],
      roam: true,
      label: {
        show: true,
        position: 'right',
        color: '#eee',  // 浅色字体
        fontSize: 12,
        formatter: function (params) {
          // 简化长文本显示
          if (params.name.length > 20) {
            return params.name.substring(0, 18) + '...';
          }
          return params.name;
        }
      },
      lineStyle: {
        color: 'source',
        curveness: 0.2
      },
      force: {
        repulsion: 150,
        edgeLength: [50, 100],
        layoutAnimation: true
      },
      emphasis: {
        focus: 'adjacency',
        lineStyle: {
          width: 3
        },
        label: {
          show: true,
          fontSize: 14,
          color: '#fff',
          fontWeight: 'bold'
        }
      }
    }]
  };

  chart.setOption(option)

  const handleResize = () => chart.resize()
  window.addEventListener('resize', handleResize)

  return () => {
    window.removeEventListener('resize', handleResize)
    chart.dispose()
  }
})
</script>

<style scoped>
.analysis-container {
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
  left: 2%;
  user-select: none;
}

.chart {
  position: absolute;
  width: 100%;
  height: 90%;
  top: 13%;
}
</style>