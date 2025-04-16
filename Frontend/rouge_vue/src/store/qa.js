import { defineStore } from 'pinia'
import MarkdownIt from 'markdown-it'
import http from '@/axios'

export const useQaStore = defineStore('qa', {
  state: () => ({
    md: new MarkdownIt(),
    messages: [],
    isLoading: false,
    sessionId: '',
    socket: null,  // 用于保存websocket连接实例
    currentMessage: null
  }),
  actions: {
    async sendMessage(content) {
      this.isLoading = true;
      const userMessage = {
        role: 'user',
        content: content,
        timestamp: new Date().toISOString()
      };
      this.messages.push(userMessage);

      // 创建AI消息占位
      this.currentMessage = {
        role: 'assistant',
        content: '',
        timestamp: new Date().toISOString()
      };
      this.messages.push(this.currentMessage);

      if (!this.socket || this.socket.readyState !== WebSocket.OPEN) {
        await this.connect();
      }

      // 发送消息到WebSocket
      this.socket.send(JSON.stringify({
        content: content,
        sessionId: this.sessionId
      }));
    },
    clearMessages() {
      this.messages = [];
      this.isLoading = false;
      this.sessionId = '';
      this.currentMessage = null;
    },
    async connect() {
      if (this.socket) {
        this.socket.close()
      }
      // WebSocket连接URL
      const wsUrl = process.env.NODE_ENV === 'development'
        ? `ws://${window.location.hostname}:3000/qa/ws`
        : `ws://${window.location.host}/qa/ws`;

      this.socket = new WebSocket(wsUrl)

      this.socket.onmessage = (event) => {
        const data = event.data
        // sessionId-12346
        // text-你好
        // end-output stopped
        const [type, content] = data.split('-')
        if (type === 'sessionId') this.sessionId = content
        if (type === 'text') this.currentMessage.content += content
        if (type === 'end') this.isLoading = false
      };

      return new Promise((resolve, reject) => {
        this.socket.onopen = () => {
          console.log('WebSocket connected')
          resolve()
        }

        this.socket.onerror = (error) => {
          console.error('WebSocket错误:', error)
          reject(error)
        }

        this.socket.onclose = () => {
          console.log('WebSocket连接已关闭')
        }
      })
    },
    disconnect() {
      if (this.socket) {
        this.socket.close()
        this.socket = null
      }
      this.isLoading = false
    }

  }
})