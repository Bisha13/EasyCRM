import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {changeDescription, changeName, loadDevice} from "../../redux/device-reducer";
import {fetchDeviceAndClientById, updateDevice} from "../../asyncActions/devices";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {useEffect} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";

function UpdateDevice() {
  const device = useSelector(state => state.device.device)
  const client = useSelector(state => state.client.client)
  const dispatch = useDispatch();
  const {id} = useParams();

  useEffect(() => {
    if (id != 'new') {
      dispatch(fetchDeviceAndClientById(id));
    } else {
      dispatch(loadDevice({"clientId": client.id}))
    }
  }, []);

  const onChangeName = (e) => {
    dispatch(changeName(e.target.value));
  }
  const onChangeDescription = (e) => {
    dispatch(changeDescription(e.target.value));
  }

  const onClick = (e) => {
    dispatch(updateDevice(device))
  }

  return (

    <Container>
      <Card className={"mt-4 mb-4"}>
        <Card.Body>
          <Link to={`/clients/${client.id}`}>Хозяин {client.name}</Link>
          <InputGroup className="mt-2">
            <InputGroup.Text style={{minWidth: 110}}>Название</InputGroup.Text>
            <Form.Control value={device.name} onChange={onChangeName}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Описание</InputGroup.Text>
            <Form.Control value={device.description} onChange={onChangeDescription}/>
          </InputGroup>
          <div className="mt-2">
          <Button variant="primary" onClick={onClick}>Сохранить</Button>
          </div>
        </Card.Body>
      </Card>
    </Container>
  )
}
export default UpdateDevice;