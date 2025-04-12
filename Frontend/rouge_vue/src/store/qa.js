import { defineStore } from 'pinia'
import MarkdownIt from 'markdown-it'

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
      const aiIndex = this.messages.length - 1

      // 关闭现有连接
      if (this.eventSource) {
        this.eventSource.close()
      }

      this.eventSource = new EventSource(`/api/qa/stream?content=${encodeURIComponent(content)}`)

      this.eventSource.onmessage = (event) => {
        this.messages[aiIndex].content += event.data
      }

      this.eventSource.onerror = () => {
        this.eventSource.close()
        this.isLoading = false
      }
    }
  },
  clearMessages() {
    this.messages = []
    this.sessionId = ''
    // 清除时关闭现有连接
    if (this.eventSource) {
      this.eventSource.close()
      this.eventSource = null
    }
  }

})