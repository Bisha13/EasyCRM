import {useSelector} from "react-redux";

function WorkerService(props) {

    let items = useSelector(state => state.item.items)

    const  getDescription  = () => {
        if (props.service.isCustom) {
            return props.service.description
        }
        let service = items.find(el => '' + el.id === '' + props.service.itemId);
        return service?.name;
    }

    return (
            <tr>
                <td style={{width: 60}}><span style={{float: 'left', marginLeft: 15}}>{props.service.qty}</span></td>
                <td>{getDescription()}</td>
                <td style={{width: 70}}><span style={{float: 'right', marginRight: 15}}>{props.service.executorMoney}</span></td>
            </tr>
    );
}

export default WorkerService;