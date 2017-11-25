var promise = require('bluebird');

var options = {
  // Initialization Options
  promiseLib: promise
};

const DEFAULT_DB_CONFIG = {
    host: "localhost",
    port: "5432",
    user: "postgres",
    password: "postgres",
    database: "kitchen",
    dialect: "postgres"
}

var pgp = require('pg-promise')(options);
var db = pgp(DEFAULT_DB_CONFIG);

function getAllDishis(req, res, next) {
  db.any('select * from dishis')
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ALL dishis'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

function getAllDishById(req, res, next) {
  var dishId = parseInt(req.params.id);
  db.one('select * from dishis where id = $1', dishId)
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ONE dish'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

function removeDishById(req, res, next) {
  var dishId = parseInt(req.params.id);
  db.result('delete from dishis where id = $1', dishId)
    .then(function (result) {
      /* jshint ignore:start */
      res.status(200)
        .json({
          status: 'success',
          message: `Removed ${result.rowCount} dish`
        });
      /* jshint ignore:end */
    })
    .catch(function (err) {
      return next(err);
    });
}

function createDish(req, res, next) {
  db.none('insert into dishis(title, description, image, userId)' +
      'values(${title}, ${description}, ${image}, ${userId})',
    req.body)
    .then(function () {
      res.status(200)
        .json({
          status: 'success',
          message: 'Inserted one dish'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

function getUserById(req, res, next) {
  var userId = parseInt(req.params.id);
  db.one('select * from users where id = $1', userId)
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Get specified user'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

module.exports = {
  getAllDishis: getAllDishis,
  getAllDishById: getAllDishById,
  removeDishById: removeDishById,
  createDish: createDish,
  getUserById: getUserById
};