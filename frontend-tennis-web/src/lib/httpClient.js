import axios from "axios";
// `http://localhost:8080/springtennis/api/v1`  = > /jugadores ,etc

//async siempre lleva await

let headers = {//configuracion previa de headers
    "Content-Type": "application/json",
    Accept: `application/vnd.iman.v1+json, application/json, text/plain, */*`,
    "Access-Control-Allow-Origin": "*",
    "Cache-Control": "no-store, no-cache, must-revalidate",
    Pragma: "no-cache"
};
//funcion para destructurar , igual q reciba method y destructura rest 
const filterOptions = ({method, ...rest}) => rest;

//todo lo q trabajemos con axios va ser async(asincrona)
const fetch = async (url, options = {}) => {//esta url recibe lo q esta despues de esta url
    try {
        const instance = axios.create({
            baseURL: `http://localhost:8080/springtennis/api/v1`//api url de arriba
        });
/*
        // INTERCEPTOR REQUEST , nose usara pero porsiacaso
        instance.interceptors.request.use( //funcion de callback
            (conf) => {
                //console.log('1 Interceptor request conf: ', conf);
                return conf;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        // INTERCEPTOR RESPONSE
        instance.interceptors.response.use(
            (response) => {
                //console.log('Interceptor response: ', response);
                return response;
            },
            (error) => {
                //console.log('Interceptor response error: ', error);
                return Promise.reject(error);
            }
        );
*/

//await,q me espera la respuesta , request es como un get
        const {data} = await instance.request({
            url,
            data: options["data"],//data se utiliza para los post y put 
            params: options["params"],//
            method: options["method"],//options,de,arriba
            headers: {...headers, ...options["headers"]},//headres es info de los request
            cancelToken: options["cancelFn"]//canceltoken no se suele utilizar pero porsiacaso
                ? new axios.CancelToken(options["cancelFn"])
                : null
        });

        return data;
    } catch (err) {
        if (axios.isCancel(err)) {//si la peticion es cancelada
            throw new Error("request-cancelled");
        } else {//si no fue cancelada y fue error api retorna err
            throw err;
        }
    }
};

//q es igual a una funcion async,que  contiene una url y options 
//options = es un objeto vacio de configuracion
const get = async (url, options = {}) => {
    return await fetch(url, {
        method: "GET",
        ...filterOptions(options)
    });
};

const post = async (url, options = {}) => {
    return await fetch(url, {
        method: "POST",
        ...filterOptions(options) //destructuramos el filterOptions
    }); //options
};

const put = async (url, options = {}) => {
    return await fetch(url, {
        method: "PUT",
        ...filterOptions(options)
    });
};

const del = async (url, options = {}) => {
    return await fetch(url, {
        method: "DELETE",
        ...filterOptions(options)
    });
};
//y exportamos para consumir , en nuestras clases padres q viene ser los containers
export default {
    get,
    post,
    put,
    delete: del 
};
