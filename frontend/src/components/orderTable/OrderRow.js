import {useNavigate} from "react-router-dom";

function OrderRow(props) {
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/order/${props.data.id}`;
        navigate(path);
    }

    function getDays() {
        try {
            const startedAt = new Date(props.data.startedAt);
            const now = new Date();
            const millisecondsDiff = now.getTime() - startedAt.getTime();
            return Math.round(millisecondsDiff / (24 * 60 * 60 * 1000));
        } catch (e) {
            return "?";
        }
    }

    return <tr bgcolor={props.data.status.colour} style={{'line-height': '23px'}} onClick={routeChange}>
        <td>{props.data.id}</td>
        <td>{props.data.clientName}</td>
        <td>{props.data.device}</td>
        <td>{props.data.description}</td>
        <td>{props.data.status.name}</td>
        <td>{getDays()}</td>
    </tr>;
}

export default OrderRow;
