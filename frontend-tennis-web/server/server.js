const express = require('express');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const cors = require('cors');//seguridad de peticion a nuestra apis

const port = parseInt(process.env.PORT, 10) || 8080;
const dev = process.env.ENV !== 'production';//true o false

const server = express();

server.use(bodyParser.json());
server.use(bodyParser.urlencoded({ extended: true }));
server.use(cookieParser());
server.use(cors());


if (dev) {
    const mockApiRouter = require('./server-mock');
    server.use('/springtennis/api/v1/', mockApiRouter);
} //link principal


//para arrancar el servidor
server.listen(port, (err) => {
    if (err) throw err;
    console.log(`> Ready on http://localhost:${port}`);
});
