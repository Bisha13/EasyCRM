import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import ItemsTable from "./ItemsTable";
import {useNavigate} from "react-router-dom";
import Button from "react-bootstrap/Button";


function AllItems() {
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/items/new`;
        navigate(path);
    }

  return (
      <Container>
        <Row>
          <Col>
          </Col>
          <Col>
          </Col>
          <Card className={"mt-3"}>
            <ItemsTable/>
          </Card>
        </Row>
        <Button variant="success" onClick={routeChange}>Создать</Button>
      </Container>);
}
export default AllItems;