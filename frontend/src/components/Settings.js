import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import {Link} from "react-router-dom";

function Settings() {

  return (
    <Container>
      <Row>
        <Card className={"mt-3"}>
          <Link to={`/items`}>Список работ</Link>
          <Link to={`/stocks`}>Запчасти</Link>
          <Link to={`/statuses`}>Статусы</Link>
        </Card>
      </Row>
    </Container>);
}

export default Settings;