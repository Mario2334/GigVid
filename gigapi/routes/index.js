var express = require('express');
const indexer = require("../controllers/indexer");
var router = express.Router();

let allowed_routes = [
    // "/product/get_product",
    //"/users/",
    "/user/create_user",
    "/user/login",
    "/test_this_api"
]

/* GET home page. */
router.get('/test_this_api', (req, res, next)=>{
    indexer.test().then(user =>{
        return res.status(200).send(user)
    }).catch(e=>console.log(e.stack));
});


module.exports = router;
module.exports.allowed_routes = allowed_routes;
