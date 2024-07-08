const express = require('express');
const cors = require('cors');

require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

app.use(cors({
    origin: ['http://localhost:' + PORT],
    credentials: true,
}))

app.use("/api", require('./routes/routes'));

app.listen(PORT, () => {
  console.log("user-service on " + PORT);
})

app.get('/email', (req, res) => {
 res.json("I am mail service")
})

const eurekaHelper = require('./eureka-helper');
eurekaHelper.registerWithEureka('mail-service', PORT);