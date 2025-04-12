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
      top: 20,
      textStyle: { fontSize: 12 }
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
          name: '科技公司A',
          category: 0,
          symbolSize: 35,
          industry: '互联网服务',
          desc: '上市公司'
        },
        {
          name: '电子商务B',
          category: 0,
          symbolSize: 30,
          industry: '电商平台',
          desc: '跨境电商'
        },
        {
          name: '数据科技C',
          category: 0,
          symbolSize: 28,
          industry: '大数据',
          desc: '初创企业'
        },
        {
          name: '云服务D',
          category: 0,
          symbolSize: 32,
          industry: '云计算',
          desc: '基础设施提供商'
        },
        // 相似企业
        {
          name: '科技公司A-相似',
          category: 1,
          symbolSize: 25,
          industry: '互联网服务',
          similarity: '75%'
        },
        {
          name: '电子商务B-相似',
          category: 1,
          symbolSize: 25,
          industry: '电商平台',
          similarity: '68%'
        },

        // 恶意网站
        {
          name: 'malicious-1.com',
          category: 2,
          symbolSize: 25,
          ip: '192.168.1.100',
          threatLevel: '高',
          similarity: '82%'
        },
        {
          name: 'phishing-2.net',
          category: 2,
          symbolSize: 28,
          ip: '103.45.67.89',
          threatLevel: '极高',
          similarity: '90%'
        },
        {
          name: 'scam-site.org',
          category: 2,
          symbolSize: 22,
          ip: '192.168.1.101',
          threatLevel: '中',
          similarity: '65%'
        },

        // 正常网站
        {
          name: 'company-a.com',
          category: 3,
          symbolSize: 20,
          ip: '45.67.89.123',
          desc: '企业官网'
        },
        {
          name: 'shop-b.com',
          category: 3,
          symbolSize: 20,
          ip: '45.67.89.123',
          desc: '官方商城'
        },
        {
          name: 'api-c.com',
          category: 3,
          symbolSize: 18,
          ip: '203.156.34.12',
          desc: '企业API'
        },

        // 共享IP
        {
          name: '45.67.89.123',
          category: 4,
          symbolSize: 15,
          desc: '共享主机IP',
          sharedSites: 2
        },
        {
          name: '192.168.1.100',
          category: 4,
          symbolSize: 15,
          desc: '恶意网站常用IP'
        },
        {
          name: '103.45.67.89',
          category: 4,
          symbolSize: 15,
          desc: '钓鱼IP'
        },
        {
          name: '192.168.1.101',
          category: 4,
          symbolSize: 15
        },
        {
          name: '203.156.34.12',
          category: 4,
          symbolSize: 15
        }
      ],
      links: [
        // 企业关系
        {
          source: '科技公司A',
          target: '电子商务B',
          relation: '合作伙伴',
          lineStyle: { width: 2, curveness: 0.2 }
        },
        {
          source: '科技公司A',
          target: '数据科技C',
          relation: '投资关系',
          lineStyle: { width: 1.5 }
        },
        {
          source: '电子商务B',
          target: '云服务D',
          relation: '云服务商',
          lineStyle: { width: 1.5 }
        },

        // 相似企业关系
        {
          source: '科技公司A',
          target: '科技公司A-相似',
          relation: '相似企业',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: '电子商务B',
          target: '电子商务B-相似',
          relation: '相似企业',
          lineStyle: { width: 1, type: 'dashed' }
        },

        // 企业拥有网站
        {
          source: '科技公司A',
          target: 'company-a.com',
          relation: '官方网站',
          lineStyle: { width: 1.5 }
        },
        {
          source: '电子商务B',
          target: 'shop-b.com',
          relation: '电商平台',
          lineStyle: { width: 1.5 }
        },
        {
          source: '数据科技C',
          target: 'api-c.com',
          relation: '数据接口',
          lineStyle: { width: 1.5 }
        },

        // 恶意网站关系
        {
          source: 'malicious-1.com',
          target: 'phishing-2.net',
          relation: '高度相似',
          lineStyle: { width: 2, curveness: 0.3 }
        },
        {
          source: 'phishing-2.net',
          target: 'scam-site.org',
          relation: '部分相似',
          lineStyle: { width: 1.5, curveness: 0.3 }
        },

        // IP关联
        {
          source: 'company-a.com',
          target: '45.67.89.123',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'shop-b.com',
          target: '45.67.89.123',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'malicious-1.com',
          target: '192.168.1.100',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'phishing-2.net',
          target: '103.45.67.89',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'scam-site.org',
          target: '192.168.1.101',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },
        {
          source: 'api-c.com',
          target: '203.156.34.12',
          relation: '解析到',
          lineStyle: { width: 1, type: 'dashed' }
        },

        // 共享IP关系
        {
          source: '45.67.89.123',
          target: 'company-a.com',
          relation: '托管',
          lineStyle: { width: 1, curveness: 0.1 }
        },
        {
          source: '45.67.89.123',
          target: 'shop-b.com',
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
