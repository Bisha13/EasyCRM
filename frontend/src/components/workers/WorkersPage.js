import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import {Tab, Tabs} from "react-bootstrap";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {fetchWorkerById} from "../../asyncActions/workers";
import UpdateWorkerInfo from "./UpdateWorkerInfo";
import ServicesTab from "./services/ServicesTab";
import PaidServicesTab from "./services/PaidServicesTab";
import {changeActiveMonth} from "../../redux/worker-reducer";


function WorkersPage() {
  const dispatch = useDispatch();
  const {id} = useParams();
  const worker = useSelector(state => state.worker.worker)
  useEffect(() => {
    if (id >= 0) {
      dispatch(fetchWorkerById(id));
    }
    dispatch(changeActiveMonth(new Date().getMonth() + 1));
  }, []);

  return (
    <Container>
      <Card className={"mt-4 mb-4"}>
        <Card.Body>
          <div>{worker.name}</div>
          <Tabs defaultActiveKey={id > 0 ? "works" : "redact"}>
            <Tab eventKey='works' title="Готовые работы">
              <ServicesTab/>
            </Tab>
            <Tab eventKey='paid' title="Оплаченные работы">
              <PaidServicesTab/>
            </Tab>
            <Tab eventKey='redact' title="Редактировать">
              <UpdateWorkerInfo/>
            </Tab>
          </Tabs>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default WorkersPage;