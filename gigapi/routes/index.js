var express = require('express');
const indexer = require("../controllers/indexer");
var router = express.Router();

let allowed_routes = [
    // "/product/get_product",
    //"/users/",
    "/user/create_user",
    "/user/login"
]

/* GET home page. */
router.get('/', indexer.test);


module.exports = router;
module.exports.allowed_routes = allowed_routes;
