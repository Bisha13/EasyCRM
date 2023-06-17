import Card from 'react-bootstrap/Card';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import WorkersTable from "./WorkersTable";

function AllWorkers() {
  return (
    <Container>
      <Row>
        <Card className={"mt-3"}>
          <WorkersTable/>
        </Card>
      </Row>
    </Container>);
}

export default AllWorkers;
