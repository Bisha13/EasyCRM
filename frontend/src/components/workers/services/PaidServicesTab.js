import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchPaidOrders} from "../../../asyncActions/workers";
import {useParams} from "react-router-dom";
import {Accordion} from "react-bootstrap";
import WorkerOrder from "./WorkerOrder";
import Pagination from "react-bootstrap/Pagination";
import {changeActiveMonth} from "../../../redux/worker-reducer";

function PaidServicesTab() {
    const dispatch = useDispatch();
    const state = useSelector(state => state.worker)
    const {id} = useParams();
    let items = [];

    useEffect(() => {
        if (id > 0) {
            const date = new Date();
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            dispatch(fetchPaidOrders(id, "PAID", year, month));
        }
    }, []);


    function getMonthName(monthNumber) {
        const date = new Date();
        date.setMonth(monthNumber - 1);

        return date.toLocaleString('ru', {
            month: 'long',
        });
    }

    const onChangeMonth = (month) => {
        dispatch(changeActiveMonth(month));
        const date = new Date();
        const year = date.getFullYear();
        dispatch(fetchPaidOrders(id, "PAID", year, month));
    }

    const date = new Date();
    const month = date.getMonth() + 1;
    for (let i = 1; i <= month; i++) {
        items.push(
            <Pagination.Item key={i} month={i} active={i === state.paidOrdersActiveMonth}
                             onClick={() => onChangeMonth(i)}>
                {getMonthName(i)}
            </Pagination.Item>,
        );
    }

    return (
        <div>
            <Pagination>{items}</Pagination>
            <Accordion alwaysOpen>
                {state.paidOrders.map((o) => <WorkerOrder order={o} eventKey={o.id} key={o.id}/>)}
            </Accordion>
            <br/>
            <div>Итого выполнено работ на <b>{state.paidTotalSum}</b> рублей</div>
        </div>

    );
}

export default PaidServicesTab;
