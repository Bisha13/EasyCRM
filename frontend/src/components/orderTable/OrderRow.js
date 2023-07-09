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

    return <tr style={{'line-height': '23px', 'background-color': props.data.status.colour}} onClick={routeChange}>
        <td style={{'background-color': props.data.status.colour}}>{props.data.id}</td>
        <td style={{'background-color': props.data.status.colour}}>{props.data.clientName}</td>
        <td style={{'background-color': props.data.status.colour}}>{props.data.device}</td>
        <td style={{'background-color': props.data.status.colour}}>{props.data.description}</td>
        <td style={{'background-color': props.data.status.colour}}>{props.data.status.name}</td>
        <td style={{'background-color': props.data.status.colour}}>{getDays()}</td>
    </tr>;
}

export default OrderRow;
