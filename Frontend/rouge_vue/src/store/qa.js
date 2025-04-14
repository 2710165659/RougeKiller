import { defineStore } from 'pinia'
import MarkdownIt from 'markdown-it'
import http from '@/axios'

export const useQaStore = defineStore('qa', {
  state: () => ({
    md: new MarkdownIt(),
    messages: [],
    isLoading: false,
    sessionId: '',
    eventSource: null  // 用于保存SSE连接实例
  }),
  actions: {
    async sendMessage(content) {
      try {
        const userMessage = {
          role: 'user',
          content: content,
          timestamp: new Date().toISOString()
        }
        this.messages.push(userMessage)
        this.isLoading = true

        const aiMessage = {
          role: 'assistant',
          content: '',
          timestamp: new Date().toISOString()
        }
        this.messages.push(aiMessage)

        // 如果SSE连接已经存在，则关闭连接
        if (this.eventSource) {
          this.eventSource.close()
        }

        const resp = await http.post('/qa/ask', { content: content, sessionId: this.sessionId })
        // 建立SSE连接
        this.eventSource = new EventSource(`/api/qa/sse/${resp.data}`)

        this.eventSource.addEventListener('sessionId', (event) => {
          this.sessionId = event.data
        })

        this.eventSource.addEventListener('text', (event) => {
          const lastMessage = this.messages[this.messages.length - 1]
          lastMessage.content = event.data // 不增量式，保持响应式更新
        })

        this.eventSource.onerror = (error) => {
          this.eventSource.close()
          this.eventSource = null
          this.isLoading = false
          throw new Error('SSE连接错误')
        }
      } catch (error) {
        this.isLoading = false
        if (this.eventSource) {
          this.eventSource.close()
          this.eventSource = null
        }
        throw error // 重新抛出错误，让调用者处理
      }
    }
    ,
    clearMessages() {
      this.messages = []
      this.isLoading = false
      this.sessionId = ''
      // 清除时关闭现有连接
      if (this.eventSource) {
        this.eventSource.close()
        this.eventSource = null
      }
    }
  }
})