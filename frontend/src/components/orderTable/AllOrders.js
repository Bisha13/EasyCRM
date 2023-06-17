import Card from 'react-bootstrap/Card';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import OrdersTable from "./OrdersTable";
import SearchById from "./SearchById";
import SearchByStatus from "./SearchByStatus";
import OrdersPagination from "./OrdersPagination";

function AllOrders() {
  return (
    <Container>
      <Row>
        <Col>
          <SearchByStatus/>
        </Col>
        <Col>
          <SearchById/>
        </Col>
        <Card className={"mt-3"}>
          <OrdersTable/>
        </Card>
      </Row>
      <OrdersPagination/>
    </Container>);
}

export default AllOrders;
