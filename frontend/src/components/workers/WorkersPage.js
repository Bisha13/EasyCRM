import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import {Tab, Tabs} from "react-bootstrap";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import {fetchWorkerById} from "../../asyncActions/workers";
import UpdateWorkerInfo from "./UpdateWorkerInfo";
import ServicesTab from "./services/ServicesTab";


function ClientPage() {
  const dispatch = useDispatch();
  const {id} = useParams();
  const worker = useSelector(state => state.worker.worker)
  useEffect(() => {
    dispatch(fetchWorkerById(id));
  }, []);

  return (
    <Container>
      <Card className={"mt-4 mb-4"}>
        <Card.Body>
          <div>{worker.name}</div>
          <Tabs>
            <Tab eventKey='works' title="Работы">
              <ServicesTab/>
            </Tab>
            <Tab eventKey='reduct' title="Редактировать">
              <UpdateWorkerInfo/>
            </Tab>
          </Tabs>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default ClientPage;