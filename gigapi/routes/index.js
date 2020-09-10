var express = require('express');
const indexer = require("../controllers/indexer");
var router = express.Router();

/* GET home page. */
router.get('/', indexer.test);

module.exports = router;
