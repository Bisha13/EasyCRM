import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import {Tab, Tabs} from "react-bootstrap";
import ClientInfo from "./ClientInfo";
import {useEffect} from "react";
import {useDispatch} from "react-redux";
import {fetchClientById} from "../../asyncActions/clients";
import {useParams} from "react-router-dom";
import UpdateClientInfo from "./UpdateClientInfo";
import AllClientOrders from "./AllClientOrders";
import AllClientDevices from "./AllClientDevices";


function ClientPage() {
  const dispatch = useDispatch();
  const {id} = useParams();
  useEffect(() => {
    dispatch(fetchClientById(id));
  }, []);

  return (
    <Container>
      <Card className={"mt-4 mb-4"}>
        <Card.Body>
          <Tabs>
            <Tab eventKey='info' title="Инфо">
              <ClientInfo/>
            </Tab>
            <Tab eventKey='orders' title="Заказы">
              <AllClientOrders/>
            </Tab>
            <Tab eventKey='devices' title="Девайсы">
              <AllClientDevices/>
            </Tab>
            <Tab eventKey='reduct' title="Редактировать">
              <UpdateClientInfo/>
            </Tab>
          </Tabs>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default ClientPage;