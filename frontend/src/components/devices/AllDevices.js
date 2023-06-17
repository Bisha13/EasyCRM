import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {fetchAllDevices} from "../../asyncActions/devices";
import DevicesPagination from "./DevicesPagination";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import DevicesTable from "./DevicesTable";


function AllDevices() {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchAllDevices(1));
  }, []);

  return (
      <Container>
        <Row>
          <Col>
          </Col>
          <Col>
          </Col>
          <Card className={"mt-3"}>
            <DevicesTable/>
          </Card>
        </Row>
        <DevicesPagination/>
      </Container>);
}
export default AllDevices;