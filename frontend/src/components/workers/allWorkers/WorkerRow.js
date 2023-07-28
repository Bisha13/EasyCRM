import {useNavigate} from "react-router-dom";

function WorkerRow(props) {
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/workers/${props.data.id}`;
        navigate(path);
    }
    return <tr onClick={routeChange}>
        <td>{props.data.id}</td>
        <td>{props.data.name}</td>
        <td>{props.data.phone}</td>
        <td>{props.data.percent}</td>
    </tr>;
}

export default WorkerRow;
