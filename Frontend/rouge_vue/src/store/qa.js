import { defineStore } from 'pinia'
import axios from 'axios'
import MarkdownIt from 'markdown-it'

export const useQaStore = defineStore('qa', {
  state: () => ({
    md: new MarkdownIt(),
    messages: [],
    isLoading: false,
    sessionId: '',
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

      this.callDashScope(content)
    },
    async callDashScope(content) {
      const apiKey = 'sk-c518b4cb469f49c6a9852625848b4d3c';
      const appId = '6124e78be439410d9207f89f10239931';

      const url = `https://dashscope.aliyuncs.com/api/v1/apps/${appId}/completion`;

      const data = {
        input: {
          prompt: content,
          session_id: this.sessionId,
        },
        parameters: {},
        debug: {}
      };
      try {
        const response = await axios.post(url, data, {
          headers: {
            'Authorization': `Bearer ${apiKey}`,
            'Content-Type': 'application/json',
          }
        });
        if (response.status === 200) {
          this.sessionId = response.data.output.session_id
          const aiMessage = {
            role: 'assistant',
            content: response.data.output.text,
            timestamp: new Date().toISOString()
          }
          this.messages.push(aiMessage)
          this.isLoading = false


          console.log(`${response.data.output.text}`);
          console.log(`session_id=${response.data.output.session_id}`);
        } else {
          console.log(`request_id=${response.headers['request_id']}`);
          console.log(`code=${response.status}`);
          console.log(`message=${response.data.message}`);
        }
      } catch (error) {
        console.error(`Error calling DashScope: ${error.message}`);
        if (error.response) {
          console.error(`Response status: ${error.response.status}`);
          console.error(`Response data: ${JSON.stringify(error.response.data, null, 2)}`);
        }
      }
    },
    clearMessages() {
      this.messages = []
      this.sessionId = ''
      this.isLoading = false
    }
  },
})