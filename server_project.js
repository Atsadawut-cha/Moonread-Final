var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

app.get('/',function(req,res){
    return res.send({error: true, message: 'Test Student Web API'})
});

var dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'moonreadproject'
});
dbConn.connect();

app.get('/allcus',function(req,res){
    dbConn.query('SELECT * FROM customer', function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

app.get('/allbook',function(req,res){
    dbConn.query('SELECT * FROM book', function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//search
app.get('/book/:keyword',function(req,res){
    var keyword = req.params.keyword
    dbConn.query('SELECT * FROM `book` WHERE book_name LIKE ?', keyword , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//searach Fiction
app.get('/bookFiction',function(req,res){
    dbConn.query('SELECT * FROM `book` WHERE book_type = "Fiction" OR book_type = "fiction"', function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});


//searach Comic
app.get('/bookComic',function(req,res){
    dbConn.query('SELECT * FROM `book`WHERE book_type = "Comic" or book_type = "comic"', function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//searach Ebook
app.get('/bookEbook',function(req,res){
    dbConn.query('SELECT * FROM `book`WHERE book_type = "Ebook" or book_type = "ebook"', function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});




//get_order_status_order
app.get('/getorder_id/:cusid', function (req,res){
    let cusid = req.params.cusid;
    if (!cusid){
        return res.status(400).send({error:true, message: 'Please provine order ' });
    }
    dbConn.query("SELECT * FROM `order` WHERE customer_id = ? AND status = 'order' ", cusid , function(error,results,fields){
        if (error) throw error;
        return res.send(results[0]);
    });
});


//get_orderID_status_ordered
app.get('/getordered_id/:cusid', function (req,res){
    let cusid = req.params.cusid;
    if (!cusid){
        return res.status(400).send({error:true, message: 'Please provine order ' });
    }
    dbConn.query("SELECT * FROM `order` WHERE customer_id = ? AND status != 'order' ", cusid , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//all_order_admin
app.get('/allorder',function(req,res){
    dbConn.query('SELECT * FROM `order` WHERE status = "ordered" OR status = "delivery"', function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//insert Book
app.post('/book', function (req, res) {
    var book = req.body
    if (!book ) {
        return res.status(400).send({ error:true, message: 'Please provide book ' });
    }
    dbConn.query("INSERT INTO book SET ? ", book, function (error, results, fields) {
        if (error) throw error;
        return res.send(results);
    });
});

//Update Book
app.put('/book/:id',function(req,res){
    var book_id = req.params.id;
    var book = req.body
    if(!book_id || !book){
        return res.status(400).send({error: true, message:'Please provide Book id and Book data'});
    }

    dbConn.query('UPDATE book SET ? WHERE book_id = ?', [book,book_id], function(error, results , fields){
        if (error) throw error;

        return res.send({error: false, message: 'Book has been updated seccessfully'});
    });
})

//Delete Book
app.delete('/book/:id',function(req,res){
    var book_id=req.params.id;
    if(!book_id){
        return res.status(400).send({error: true, message:'Please provide book id'});
    }
    dbConn.query('DELETE FROM book WHERE book_id = ?', book_id,function(error , results , fields){
        if (error) throw error;
        
        return res.send({error: false, message: 'Book has been deleted seccessfully'});
    });

})


//create_order
app.post('/createorder', function (req,res){
    var order = req.body
    if (!order){
        return res.status(400).send({error:true, message: 'Please provine order ' });
    }
    dbConn.query("INSERT INTO `order` SET ? ", order , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//insert_order_detail
app.post('/createorder_detail', function (req,res){
    var order = req.body
    if (!order){
        return res.status(400).send({error:true, message: 'Please provine order ' });
    }
    dbConn.query("INSERT INTO order_detail SET ? ", order , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//Show_order_detail
app.get('/show_cus_order/:order_id', function (req,res){
    var de_id = req.params.order_id
    if (!de_id){
        return res.status(400).send({error:true, message: 'Can not see order ' });
    }
    dbConn.query("SELECT order_detail.detail_id , order_detail.order_id , order_detail.book_id,book.book_name,book.book_price,order_detail.qty,order_detail.price FROM `order_detail` iNNER JOIN book WHERE order_detail.book_id = book.book_id AND order_detail.order_id = ? ", de_id , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//get total price
app.get('/total/:order_id', function (req,res){
    var order_id = req.params.order_id;
    if (!order_id){
        return res.status(400).send({error:true, message: 'Please provine order ' });
    }
    dbConn.query("SELECT sum(price) AS total FROM `order_detail` WHERE order_id = ? ", order_id , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//delete_order_detail
app.delete('/delete_order/:order_id/:book_id', function (req,res){
    var order_id = req.params.order_id
    var book_id = req.params.book_id
    if (!book_id){
        if(!order_id){
            return res.status(400).send({error:true, message: 'Can not delete' });
        }     
    }
    dbConn.query("DELETE FROM order_detail WHERE order_id = ? AND book_id = ? ", [order_id,book_id], function(error,results,fields){
        if (error) throw error;
        return res.send({ error: false, message: 'order has been deleted successfully'});
    });
});

//update order detail
app.put('/update/:order_id/:detail_id', function (req,res){
    var order_id = req.params.order_id;
    var detail_id = req.params.detail_id;
    var qty = req.body.qty;
    var price = req.body.price;
    if (!detail_id || !order_id){
        return res.status(400).send({error:true, message: 'Please provine order ' });
    }
    dbConn.query("UPDATE `order_detail` SET `qty`= ?, price= ? WHERE order_id = ? AND detail_id = ?",[qty,price,order_id,detail_id] , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//when_customer_confirm_order
app.put('/order/:id', function (req,res){
    var order_id = req.params.id;
    //var price = req.params.price;
    var order = req.body;
    if (!order_id){
        return res.status(400).send({error:true, message: 'Please provine student id and student data' });
    }
    //dbConn.query("UPDATE `order` SET total_price=(SELECT sum(price) AS sump FROM `order_detail` WHERE order_id = ?) WHERE order_id = ?", [order_id, order_id] , function(error,results,fields)
    dbConn.query("UPDATE `order` SET ? WHERE `order_id` = ?", [order,order_id] , function(error,results,fields)
    {
        if (error) throw error;
        return res.send({ error: false, message: 'Student has been updated successfully'});    
    });
});

//register
app.post('/cus', function (req,res){
    var std = req.body
    if (!std){
        return res.status(400).send({error:true, message: 'Please provine customer ' });
    }
    dbConn.query("INSERT INTO customer SET ? ", std , function(error,results,fields){
        if (error) throw error;
        return res.send(results);
    });
});

//check login
app.get('/logincus/:username/:password', function (req,res){
    var cus_username = req.params.username;
    var cus_password = req.params.password;
    var cus = req.body
    if (!cus_password || !cus_username){
        return res.status(400).send({error:true, message: 'Please provine student id and student data' });
    }
    dbConn.query("SELECT * FROM customer WHERE cus_username = ? AND cus_password = ?", [cus_username, cus_password] ,function(error,results,fields){
        if (error) throw error;
        console.log(results);
        return res.send(results[0]);
    });
});

//update
app.get('/cus/:id', function (req,res){
    var std_id = req.params.id;
    if (!std_id){
        return res.status(400).send({error:true, message: 'Please provine customer id' });
    }
    dbConn.query("SELECT * FROM customer WHERE cus_id = ? ", std_id , function(error,results,fields){
        if (error) throw error;
        if (results[0]){
            return res.send(results[0]);
        } else {
            return res.status(400).send({ error: true, message: 'Customer id Not Found!!'});
        }
        
    });
});

//edit_customer
app.put('/cus/:id', function (req,res){
    var cus_id = req.params.id;
    var cus = req.body
    if (!cus_id || !cus){
        return res.status(400).send({error:true, message: 'Please provine student id and student data' });
    }
    dbConn.query("UPDATE customer SET ? WHERE cus_id = ?", [cus, cus_id] , function(error,results,fields){
        if (error) throw error;
        return res.send({ error: false, message: 'Student has been updated successfully'});    
    });
});






app.listen(3000, function(){
    console.log('Node app is running on port 3000');
});

module.exports = app;