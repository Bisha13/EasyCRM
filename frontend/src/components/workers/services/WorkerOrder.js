import {useSelector} from "react-redux";
import {Accordion} from "react-bootstrap";
import WorkerService from "./WorkerService";
import Table from "react-bootstrap/Table";

function WorkerOrder(props) {

    const state = useSelector((state) => state.worker)

    return (
        <Accordion.Item eventKey={props.eventKey}>
            <Accordion.Header><h6>{props.order.id + ' ' + props.order.device + ' ' + props.order.description}</h6></Accordion.Header>
            <Accordion.Body style={{padding: 0}}>
                <Table striped="columns" hover >
                    <tbody>
                    {props.order.services.map(s => <WorkerService service={s} key={s.id} percent={state.worker.percent}/>)}
                    </tbody>
                </Table>
            </Accordion.Body>
        </Accordion.Item>
    );
}

export default WorkerOrder;