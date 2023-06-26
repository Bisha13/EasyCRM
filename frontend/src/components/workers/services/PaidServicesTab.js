import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchPaidOrders} from "../../../asyncActions/workers";
import {useParams} from "react-router-dom";
import {Accordion} from "react-bootstrap";
import WorkerOrder from "./WorkerOrder";
function PaidServicesTab() {
    const dispatch = useDispatch();
    const state = useSelector(state => state.worker)
    const {id} = useParams();

    useEffect(() => {
        const date = new Date();
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        console.log(year); // Example output: 2023
        console.log(month); // Example output: 6
        dispatch(fetchPaidOrders(id, "PAID", year, month));
    }, []);


    return (
        <div>
            <Accordion alwaysOpen>
                { state.paidOrders.map((o ) => <WorkerOrder order={o} eventKey={o.id} key={o.id}/>) }
            </Accordion>
            <br/>
            <div>Итого выполнено работ на {state.paidTotalSum} рублей</div>
        </div>

);
}

export default PaidServicesTab;
