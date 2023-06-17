import {useNavigate} from "react-router-dom";

function DeviceRow(props) {
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/devices/${props.data.id}`;
        navigate(path);
    }

    function formatDate(date) {
        try {
            const newDate = new Date(date);
            return new Intl.DateTimeFormat('ru-RU', {
                day: '2-digit',
                month: '2-digit',
                year: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            }).format(newDate);
        } catch (e) {
            return "time"
        }
    }


    return <tr onClick={routeChange}>
        <td>{props.data.id}</td>
        <td>{props.data.name}</td>
        <td>{props.data.description}</td>
        <td>{formatDate(props.data.createdAt)}</td>
    </tr>;
}

export default DeviceRow;
