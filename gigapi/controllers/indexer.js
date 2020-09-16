let db = require('../models');

exports.test = async () => {
    let user = await db.User.findAll()
    return user
}
