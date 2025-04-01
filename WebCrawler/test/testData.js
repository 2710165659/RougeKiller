const express = require('express')

const app = express()
const port = 3000;

app.use(express.json())

// Basic route
app.get('/', (req, res) => {

  console.log(req.query.domain)
  res.json({ message: 'Welcome to Express Server' })
})


// Start server
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});