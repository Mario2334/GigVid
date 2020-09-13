let db = require('../models');
var sequelize = require("sequelize")
var bcrypt = require('bcryptjs');
let jwt = require("jsonwebtoken")

exports.create_user = async (req, res, next) => {
    let data = req.body;

    try {
        let user = await db.User.create({
            name: data.name,
            password: data.password,
            email: data.email,
            username: data.username,
        })
        res.send(user);

    }
    catch (e) {
        console.log(e)
        if(e.name === "SequelizeValidationError"){
            res.status(400).send({errors:e.errors.map(value => value["message"])});

        }
        res.status(500).send(e.stack)
    }
}

exports.login = async (req, res, next) => {
    let data = req.body;
    let user = await db.User.findOne({
        where:{
            email:data.email,
        }
    })
    if(user){
        try{
            let compare = await bcrypt.compare(data.password,user.password)
            if(compare) {
                const token = jwt.sign({
                    user: {
                        email: user.email,
                        password: data.password
                    }
                }, process.env.SECRET_KEY)
                console.log(token);
                res.status(200).send(token)
            }
            else {
                res.status(404).send("User and Password Doesnt Match")
            }
        }
        catch (e) {
            res.status(500).send(e.toString())
        }
    }
    else {
        return [404,"User and Password Doesnt Match"]
    }
}
