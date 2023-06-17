import Card from 'react-bootstrap/Card';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ClientSearch from "./ClientSearch";
import ClientsTable from "./ClientsTable";
import ClientsPagination from "./ClientsPagination";

function AllClients() {
  return (
    <Container>
      <Row>
        <Col>
        </Col>
        <Col>
          <ClientSearch/>
        </Col>
        <Card className={"mt-3"}>
          <ClientsTable/>
        </Card>
      </Row>
      <ClientsPagination/>
    </Container>);
}

export default AllClients;
