require('dotenv').config();
const serverless = require('serverless-http');
var express = require('express');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var cors = require('cors');
var bodyParser = require('body-parser');
let auth = require('./middlewares/auth')

var indexRouter = require('./routes/index');
var userRouter = require('./routes/users')

var app = express();

app.use(cors({}))
app.use(logger(process.env.NODE_ENV));

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(cookieParser());
app.use(auth)

//Index Router
app.use('/', indexRouter);
app.use('/user',userRouter);

if(process.env.NODE_ENV === "dev"){
    module.exports = app;
}
else {
    module.exports.handler = serverless(app);
}
