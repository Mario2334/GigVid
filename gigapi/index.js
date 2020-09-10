require('dotenv').config();
const serverless = require('serverless-http');
var express = require('express');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var cors = require('cors');

var indexRouter = require('./routes/index');

var app = express();

app.use(cors({}))
app.use(logger(process.env.NODE_ENV));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

//Index Router
app.use('/', indexRouter);

module.exports.handler = serverless(app);
