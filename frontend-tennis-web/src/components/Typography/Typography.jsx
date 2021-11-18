import React from "react";
import { Title } from './styles';

//colocamos props para q pueda ser consumido desde la principal q seria container home

const Typography = (props) => {
    return <Title id={props.id}>  {props.children}</Title>;
    //este viene ser el Home
}

export default Typography;
