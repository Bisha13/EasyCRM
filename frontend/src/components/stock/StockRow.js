import {useNavigate} from "react-router-dom";

function StockRow(props) {
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/stocks/${props.data.id}`;
        navigate(path);
    }

    return <tr onClick={routeChange}>
        <td>{props.data.id}</td>
        <td>{props.data.article}</td>
        <td>{props.data.name}</td>
        <td>{props.data.purchase}</td>
        <td>{props.data.extra}</td>
        <td>{props.data.price}</td>
    </tr>;
}

export default StockRow;
