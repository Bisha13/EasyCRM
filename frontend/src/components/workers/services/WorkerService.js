import {useSelector} from "react-redux";

function WorkerService(props) {

    let items = useSelector(state => state.item.items)

    const  getDescription  = () => {
        if (props.service.isCustom) {
            return props.service.description
        }
        let service = items.find(el => ''+el.id === ''+props.service.itemId);
        return service?.name;
    }

    function getPrice() {
        if (props.service.isCustom) {
            let price = props.service.price;
            return props.service.qty * price / 100 * props.percent
        }
        let service = items.find(el => ''+el.id === ''+props.service.itemId);
        let price = service?.price
        return props.service.qty * price / 100 * props.percent
    }

    return (
            <tr>
                <td style={{width: 60}}><span style={{float: 'left', marginLeft: 15}}>{props.service.qty}</span></td>
                <td>{getDescription()}</td>
                <td style={{width: 70}}><span style={{float: 'right', marginRight: 15}}>{getPrice()}</span></td>
            </tr>
    );
}

export default WorkerService;