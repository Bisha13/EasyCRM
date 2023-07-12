import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchDoneOrders, fetchPaidOrders} from "../../../asyncActions/workers";
import {useParams} from "react-router-dom";
import {Accordion} from "react-bootstrap";
import WorkerOrder from "./WorkerOrder";
import Button from "react-bootstrap/Button";
import {updateStatuses} from "../../../asyncActions/service";

function ServicesTab() {
    const dispatch = useDispatch();
    const state = useSelector(state => state.worker)
    const {id} = useParams();

    useEffect(() => {
        if (id > 0) {
            dispatch(fetchDoneOrders(id, "DONE"));
        }
    }, []);

    const onClick = () => {
        let request = {
            serviceIds: state.doneOrders.flatMap(o => o.services).map(s => s.id),
            status: 'PAID'
        }
        dispatch(updateStatuses(request, id)).then(() => {
            const date = new Date();
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            dispatch(fetchPaidOrders(id, "PAID", year, month));
        });
    }

    const withOrders = (
        <div>
            <Accordion alwaysOpen>
                {state.doneOrders.map((o, i) => <WorkerOrder order={o} eventKey={o.id} key={o.id}/>)}
            </Accordion>
            <br/>
            <div>Итого выполнено работ на <b>{state.doneTotalSum}</b> рублей</div>
            <Button onClick={onClick} className="mt-3">Выплатить работнику его деньги!</Button>
        </div>
    )

    const withOutOrders = (<div className='mt-3'>Здесь пока ничего нет</div>)

    const isWithOrders = () => {
        return state.doneOrders.length != 0 ? withOrders : withOutOrders;
    }

    return (isWithOrders());
}

export default ServicesTab;
