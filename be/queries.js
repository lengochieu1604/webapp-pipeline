const Pool = require('pg').Pool
const pool = new Pool({
  user: process.env.DB_USER || 'admin',
  host: process.env.DB_HOST || '10.88.231.1821',
  database: process.env.DB_NAME || 'users',
  password: process.env.DB_PASSWORD || 'admin',
  port: 5432,
})

const getUsers = (request, response) => {
  
  pool.query('SELECT * FROM users', (error, results) => {
    if (error) {
      throw error
    }
    response.status(200).json(results.rows)
  })
}

module.exports = {
  getUsers
}