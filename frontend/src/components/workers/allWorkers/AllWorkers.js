import Card from 'react-bootstrap/Card';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import WorkersTable from "./WorkersTable";
import Button from "react-bootstrap/Button";
import {useNavigate} from "react-router-dom";

function AllWorkers() {
    const navigate = useNavigate();
  return (
    <Container>
      <Row>
        <Card className={"mt-3"}>
          <WorkersTable/>
        </Card>
          <Button className="mt-3" onClick={() => navigate('/workers/new')}>Создать</Button>
      </Row>
    </Container>);
}

export default AllWorkers;
