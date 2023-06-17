import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchWorkerOrders} from "../../../asyncActions/workers";
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
        dispatch(fetchWorkerOrders(id, "DONE"));
    }, []);

    const onClick = () => {
        let request = {
            serviceIds: state.workerOrders.flatMap(o => o.services).map(s => s.id),
            status: 'PAID'
        }
        dispatch(updateStatuses(request, id));
    }

    return (
        <div>
            <Accordion alwaysOpen>
                { state.workerOrders.map((o, i ) => <WorkerOrder order={o} eventKey={o.id} key={i}/>) }
            </Accordion>
            <br/>
            <div>Итого выполнено работ на {state.totalSum} рублей</div>
            <Button onClick={onClick} className="mt-3">Выплатить работнику его деньги!</Button>
        </div>

);
}

export default ServicesTab;
