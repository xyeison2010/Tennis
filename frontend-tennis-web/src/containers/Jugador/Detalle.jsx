import React from "react";
import Typography from "../../components/Typography/Typography";
import Button from 'react-bootstrap/Button';
import Table from '../../components/Tables/Table';
//esto fue las primeras clases, con componentes , actualmente no se usa mucho
//pero es un bueno verlo pocas veces
class Detalle extends React.Component{

    constructor(props) {
        super(props);
        this.state={
            dataForTable:{
                headers:[],
                body:[]
            }
        }
    }

    componentDidMount() {
        const data = this.props.location.state.data; //es una prop del router react
        this.setState({
            dataForTable:{
                headers:['id', 'Nombre', 'Puntos'],
                body: [{...data}],
            }
        })
    }

    render(){
        const {match: {params: { id }}} = this.props;//esto de typography
        const { dataForTable } = this.state;

        return (
            <>
                <Button onClick={()=> this.props.history.goBack()} variant="link"> {`<`} Volver</Button>
                <Typography id={"title-id"}>Jugador: {id}</Typography>
                <Table
                    dataForTable={dataForTable}
                />
            </>
        )
    }
}

/*const Detalle = (props) => {
    const {match: {params: {id}}} = props;
    return (
        <>
            <Typography id={"title-id"}>Jugador: {id}</Typography>
        </>
    )
}*/

export default Detalle;
