
var express = require('express');
var router = express.Router();
var db = require('../models/database');

router.get('/api/dishis', db.getAllDishis);
router.get('/api/dishis/:id', db.getAllDishById);
router.post('/api/createDish', db.createDish);
router.get('/api/users/:id', db.getUserById);
router.delete('/api/removeDish/:id', db.removeDishById);

// application -------------------------------------------------------------
router.get('/', function (req, res) {
    res.render('index', {title: 'node-postgres-promises'}); // load the single view file (angular will handle the page changes on the front-end)
});

module.exports = router;
