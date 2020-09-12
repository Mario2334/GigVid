var express = require('express');
var router = express.Router();
const user_controller = require('../controllers/user_controller');

/* GET users listing. */
router.post('/create_user', user_controller.create_user);
router.post('/login',user_controller.login)

module.exports = router;
