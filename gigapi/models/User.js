'use strict';
const {
    Model
} = require('sequelize');
const bcrypt = require('bcrypt');


function cryptPassword(password, callback) {
    bcrypt.g(10, function(err, salt) { // Encrypt password using bycrpt module
        if (err)
            return callback(err);

        bcrypt.hash(password, salt, function(err, hash) {
            return callback(err, hash);
        });
    });
}

module.exports = (sequelize, DataTypes) => {
    class User extends Model {
        /**
         * Helper method for defining associations.
         * This method is not a part of Sequelize lifecycle.
         * The `models/index` file will call this method automatically.
         */
        static associate(models) {
            // define association here
        }
    }
    User.init({
        name: {
            type:DataTypes.STRING,
            allowNull:false,
        },
        username:{
            type:DataTypes.STRING,
            allowNull:false,
            unique:true,
        },
        email:{
            type:DataTypes.STRING,
            allowNull:false,
            unique:true,
            validate:{isEmail: {msg:"Value is not email"}}
        },
        password:{
            type:DataTypes.STRING,
            allowNull:false
        }

    }, {
        sequelize,
        modelName: 'User',
        tableName:'users',
        hooks:{
            beforeCreate(instance, options,cb) {
                let hash = bcrypt.hashSync(instance.password,10)
                instance.password = hash;
        }
    }});
    return User;
};
