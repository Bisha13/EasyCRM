import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {fetchAllDevices} from "../../asyncActions/devices";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import StockTable from "./StockTable";
import Button from "react-bootstrap/Button";
import {useNavigate} from "react-router-dom";
import {loadRootStock} from "../../redux/stocks-reducer";


function AllStocks() {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchAllDevices(1));
  }, []);

  let navigate = useNavigate();
  const routeChange = () => {
    dispatch(loadRootStock({}));
    navigate(`/stocks/new`);
  }

  return (
    <Container>
      <Row>
        <Card className={"mt-3"}>
          <StockTable/>
        </Card>
      </Row>
      <Button variant="success" onClick={routeChange}>Создать</Button>
    </Container>);
}

export default AllStocks;