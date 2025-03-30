const axios = require('axios')

const api1 = 'https://69dde.top/api/whois.php'
const token = '3b277ece60656b3caf8cdd2234ecad63'
const domain = 'www.onlinedown.net'

// axios.get(api1, {
//   params: {
//     token: token,
//     domain: domain
//   }
// })
//   .then(response => {
//     console.log('Response:', response.data)
//   })
//   .catch(error => {
//     console.error('Error:', error.message)
//   })

const api2 = 'https://v.api.aa1.cn/api/icp/index.php'
axios.get(`${api2}?url=${domain}`).then(resp => console.log(resp))

