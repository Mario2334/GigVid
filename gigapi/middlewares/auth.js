let {allowed_routes} = require("../routes");
let jwt = require("jsonwebtoken")
let bcrypt = require("bcrypt");
let db = require('../models');


let auth = async (req,res,next) =>{
    console.log(`Logged  ${req.url}  ${req.method} -- ${new Date()}`)
    if (!allowed_routes.includes(req.url)){
        let token = req.header("Authorization");
        console.log(token);
        if(token){
            //Verify Token
            try {
                token = token.split("Bearer ")
                var cred_obj = jwt.verify(token[1],process.env.SECRET_KEY);
                console.log(cred_obj);
            }
            catch (e) {
                res.status(401).send(e.message)
                return;
            }
            let user = await db.User.findOne({
                email:cred_obj["email"],
                password:cred_obj["password"]
            })
            if(user){
                next();
            }
            else{
                res.status(401).send("Invalid User")
            }

        }
        else {
            console.log("Secure Route");
            res.status(401).send("Secure Route")
        }
    }
    else {
        next();
    }
}
module.exports = auth
