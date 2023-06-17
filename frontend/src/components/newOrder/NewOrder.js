import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Device from "./Device";
import NewServices from "./newservice/NewServices";
import {useSelector} from "react-redux";
import NewParts from "./newpart/NewParts";
import Descriptions from "./Descriptions";
import PlaceOrderCard from "./PlaceOrderCard";

function NewOrder() {

  const state = useSelector((state) => state.newOrder)

  return <Container>
    <Row>
      <Col/>
      <Col sm={8}>
        <Card className={"mt-4"}>
          <Card.Body>
            <Device/>
            <NewServices/>
            <div className="text-end">
              <span>Сумма работ: </span>
              <span>{state.servicesSum} р.</span>
            </div>
            <NewParts/>
            <div className="text-end">
              <span>Сумма за запчасти: </span>
              <span>{state.partsSum} р.</span>
            </div>
            <Descriptions/>
          </Card.Body>
        </Card>
        <PlaceOrderCard/>
      </Col>
      <Col/>
    </Row>
  </Container>;
}

export default NewOrder;