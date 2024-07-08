const express = require('express');

const router = express.Router();

const {sendEmail} = require('../controllers/EmailController');

router.post("/mail", sendEmail);
module.exports = router;