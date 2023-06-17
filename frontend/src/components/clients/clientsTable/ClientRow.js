import {useNavigate} from "react-router-dom";

function ClientRow(props) {
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/clients/${props.data.id}`;
        navigate(path);
    }
    return <tr onClick={routeChange}>
        <td>{props.data.id}</td>
        <td>{props.data.name}</td>
        <td>{props.data.phone}</td>
        <td>{props.data.createdAt}</td>
    </tr>;
}

export default ClientRow;
