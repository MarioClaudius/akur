const http = require('http');
const express = require('express');
const app = express();
const bodyParser = require('body-parser');
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
const { Client } = require('pg');
const { response } = require('express');
const cors = require('cors');
app.use(cors());
const port = process.env.PORT || 210;

const client = new Client({
    // Lengkapi koneksi dengan database
    host: "localhost",
    port: 5432,
    user: "postgres",
    password: "mcmario",
    database: "akur",
  });

  client.connect((err) =>{
    if (err) {
        console.error(err);
        return;
    }
    console.log('Database Connected');
  });
  
  app.get("/show", async (req, res) => {
    try {
        const allTodos = await client.query("SELECT * FROM akun");
        res.json(allTodos.rows);
    } catch (err) {
        console.error(err.message);
    }
  });

  app.post('/insert', async(req, res) => {
    try {
      const username = req.body.username;
      const email  = req.body.email;
      const password  = req.body.password;

      const todo = await client.query(`INSERT INTO akun (username, email, password) VALUES ('${username}', '${email}', '${password}')`);
      
      console.log("Berhasil di insert bro");
      res.json(todo.rows[0]);
    } catch (err) {
      // console.log(req.body);
      console.error(err.message);
      console.log("Ga masuk cok");
    }
    // const query = `
    //                 INSERT INTO akun (username, email, password)
    //                 VALUES (${req.body.username}, ${req.body.email}, ${req.body.password})
    //             `;
  
    // client.query(query, (err, results) => {
    //     if (err) {
    //         res.statusCode = 404;
    //         console.log("Data tidak berhasil");
    //         console.error(err);
    //         res.send(null);
    //         return;
    //     }
    //     console.log(`Data [${req.body.username}, ${req.body.email}, ${req.body.password}] berhasil di-insert.`);
    //     res.send(`Data [${req.body.username}, ${req.body.email}, ${req.body.password}] berhasil di-insert.`);
    // });
  });

  app.post("/update", async (req, res) => {
    try {
        const { id } = req.params;
        const oldPassword = req.body.oldPassword;
        const newPassword = req.body.newPassword; 
        const username = req.body.username;
        let checkPassword = await client.query(`SELECT password FROM akun WHERE username = '${username}'`);
       // console.log(checkPassword.rows)
        if(checkPassword.rows[0].password == oldPassword){
          const todo = await client.query(`UPDATE akun SET password = '${newPassword}' WHERE username = '${username}'`);
          // res.json({
          //   success:true
          // })
          res.send(true);
        }
        else{
          res.send(false);
          // res.json({
          //   success:false
          // })
        }
        res.send("MASUK");
    } catch (err) {
        console.error(err.message);
    }
  });

  app.listen(port, () => {
    console.log(`Program sudah berjalan pada port ${port}`);
  });