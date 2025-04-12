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
    tooltip: {
      formatter: function (params) {
        if (params.dataType === 'node') {
          let content = `<div style="margin:5px"><strong>${params.name}</strong><div>类型: ${params.data.category}</div>`;
          if (params.data.threatLevel) content += `<div>威胁等级: ${params.data.threatLevel}</div>`;
          if (params.data.ip) content += `<div>IP: ${params.data.ip}</div>`;
          if (params.data.similarity) content += `<div>相似度: ${params.data.similarity}</div>`;
          if (params.data.industry) content += `<div>行业: ${params.data.industry}</div>`;
          return content + '</div>';
        } else {
          return `${params.source} → ${params.target}<br>关系: ${params.data.relation}`;
        }
      }
    },
    legend: {
      data: ['正常企业', '相似企业', '恶意网站', '正常网站', '共享IP'],
      orient: 'vertical',
      right: 10,
      textStyle: {
        fontSize: 12,
        color: "#ffffff"
      }
    },
    series: [{
      name: '企业网站关系网络',
      type: 'graph',
      layout: 'force',
      force: {
        repulsion: 500,
        edgeLength: [100, 300],
        gravity: 0.1
      },
      roam: true,
      draggable: true,
      categories: [
        { name: '正常企业', itemStyle: { color: '#1890ff' } },
        { name: '相似企业', itemStyle: { color: '#722ed1' } },
        { name: '恶意网站', itemStyle: { color: '#ff4d4f' } },
        { name: '正常网站', itemStyle: { color: '#52c41a' } },
        { name: '共享IP', itemStyle: { color: '#fa8c16' } }
      ],
      label: { show: true, position: 'right', fontSize: 10 },
      edgeLabel: { show: true, formatter: '{@relation}', fontSize: 8 },
      emphasis: { label: { show: true, fontSize: 14 } },
      edgeSymbol: ['none', 'arrow'],
      edgeSymbolSize: [4, 8],
      data: [
        // 企业节点
        {
          name: '星辰科技集团',
          category: 0,
          symbolSize: 35,
          industry: '互联网服务',
          desc: '上市公司，主要提供企业云服务'
        },
        {
          name: '极速电子商务',
          category: 0,
          symbolSize: 30,
          industry: '电商平台',
          desc: '国内知名B2C电商平台'
        },
        {
          name: '迅捷软件',
          category: 0,
          symbolSize: 28,
          industry: '软件开发',
          desc: '专业软件开发商'
        },
        {
          name: '云端数据服务',
          category: 0,
          symbolSize: 32,
          industry: '云计算',
          desc: '企业级云存储解决方案'
        },
        // 相似企业
        {
          name: '星辰科技(国际)',
          category: 1,
          symbolSize: 25,
          industry: '互联网服务',
          similarity: '78%'
        },
        {
          name: '极速商城',
          category: 1,
          symbolSize: 25,
          industry: '电商平台',
          similarity: '72%'
        },

        // 恶意网站 - 伪装成合法下载站
        {
          name: 'fastdownloads-pro.com',
          category: 2,
          symbolSize: 25,
          ip: '185.143.223.47',
          threatLevel: '高',
          similarity: '85%',
          desc: '伪装成专业软件下载站'
        },
        {
          name: 'softarchive.cc',
          category: 2,
          symbolSize: 28,
          ip: '91.234.56.78',
          threatLevel: '极高',
          similarity: '92%',
          desc: '分发捆绑恶意软件的破解程序'
        },
        {
          name: 'premium-cracks.net',
          category: 2,
          symbolSize: 22,
          ip: '185.143.223.48',
          threatLevel: '中',
          similarity: '68%',
          desc: '提供携带木马的"激活工具"'
        },
        {
          name: 'driver-updates.io',
          category: 2,
          symbolSize: 26,
          ip: '104.28.156.33',
          threatLevel: '高',
          similarity: '79%',
          desc: '伪装驱动程序更新网站'
        },

        // 正常网站
        {
          name: 'star-tech.com',
          category: 3,
          symbolSize: 20,
          ip: '203.86.92.15',
          desc: '星辰科技集团官网'
        },
        {
          name: 'js-eshop.com',
          category: 3,
          symbolSize: 20,
          ip: '203.86.92.16',
          desc: '极速电子商务官方商城'
        },
        {
          name: 'api.xunjiesoft.com',
          category: 3,
          symbolSize: 18,
          ip: '45.67.123.89',
          desc: '迅捷软件开发者API'
        },

        // 共享IP
        {
          name: '185.143.223.47',
          category: 4,
          symbolSize: 15,
          desc: '恶意软件分发网络',
          sharedSites: 3
        },
        {
          name: '91.234.56.78',
          category: 4,
          symbolSize: 15,
          desc: '已知钓鱼IP段'
        },
        {
          name: '104.28.156.33',
          category: 4,
          symbolSize: 15,
          desc: '恶意软件托管服务器'
        },
        {
          name: '203.86.92.15',
          category: 4,
          symbolSize: 15,
          desc: '企业服务器IP'
        },
        {
          name: '45.67.123.89',
          category: 4,
          symbolSize: 15,
          desc: '云服务IP'
        }
      ],
      links: [
        // 企业关系
        {
          source: '星辰科技集团',
          target: '极速电子商务',
          relation: '云服务客户',
          lineStyle: { width: 2, curveness: 0.2 }
        },
        {
          source: '星辰科技集团',
          target: '迅捷软件',
          relation: '战略投资',
          lineStyle: { width: 1.5 }
        },
        {
          source: '极速电子商务',
          target: '云端数据服务',
          relation: '数据存储服务',
          lineStyle: { width: 1.5 }
        },

        // 相似企业关系
        {
          source: '星辰科技集团',
          target: '星辰科技(国际)',
          relation: '相似企业',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: '极速电子商务',
          target: '极速商城',
          relation: '相似企业',
          lineStyle: { width: 1, type: 'dashed' }
        },

        // 企业拥有网站
        {
          source: '星辰科技集团',
          target: 'star-tech.com',
          relation: '官方网站',
          lineStyle: { width: 1.5 }
        },
        {
          source: '极速电子商务',
          target: 'js-eshop.com',
          relation: '电商平台',
          lineStyle: { width: 1.5 }
        },
        {
          source: '迅捷软件',
          target: 'api.xunjiesoft.com',
          relation: '开发者平台',
          lineStyle: { width: 1.5 }
        },

        // 恶意网站关系
        {
          source: 'fastdownloads-pro.com',
          target: 'softarchive.cc',
          relation: '相同C&C服务器',
          lineStyle: { width: 2, curveness: 0.3 }
        },
        {
          source: 'softarchive.cc',
          target: 'premium-cracks.net',
          relation: '相同攻击组织',
          lineStyle: { width: 1.5, curveness: 0.3 }
        },
        {
          source: 'driver-updates.io',
          target: 'fastdownloads-pro.com',
          relation: '相似恶意代码',
          lineStyle: { width: 1.5 }
        },

        // IP关联
        {
          source: 'star-tech.com',
          target: '203.86.92.15',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'js-eshop.com',
          target: '203.86.92.16',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'fastdownloads-pro.com',
          target: '185.143.223.47',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'softarchive.cc',
          target: '91.234.56.78',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'premium-cracks.net',
          target: '185.143.223.48',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'api.xunjiesoft.com',
          target: '45.67.123.89',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'driver-updates.io',
          target: '104.28.156.33',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },

        // 共享IP关系
        {
          source: '185.143.223.47',
          target: 'fastdownloads-pro.com',
          relation: '托管',
          lineStyle: { width: 1, curveness: 0.1 }
        },
        {
          source: '185.143.223.47',
          target: 'premium-cracks.net',
          relation: '托管',
          lineStyle: { width: 1, curveness: 0.1 }
        }
      ],
      lineStyle: { opacity: 0.8 }
    }],
    animationDuration: 1500
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
