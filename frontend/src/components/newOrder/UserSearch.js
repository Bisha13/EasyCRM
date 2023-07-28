import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import {useDispatch, useSelector} from "react-redux";
import {
  changeClientName,
  changeClientPhone,
  newToggleIsNewClient,
  newToggleIsNewDevice,
  selectClient
} from "../../redux/new-order-reducer";
import Button from "react-bootstrap/Button";
import {fetchClientsByPhone} from "../../asyncActions/clients";
import {fetchDevicesByClientId} from "../../asyncActions/devices";

function UserSearch() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.newOrder);

  const handleSearch = (e) => {
    dispatch(fetchClientsByPhone(state.order.clientPhone))
  }

  const handlePhoneChange = (e) => {
    if (e.key === "Enter") {
      dispatch(fetchClientsByPhone(state.order.clientPhone))
    }
    dispatch(changeClientPhone(e.target.value));
  }

  const handleEnter = (e) => {
    if (e.key === "Enter") {
      dispatch(fetchClientsByPhone(state.order.clientPhone))
    }
  }

  const handleNameChange = (e) => {
    dispatch(changeClientName(e.target.value));
  }

  const handleClientSelect = e => {
    dispatch(selectClient(e.target.value));
    dispatch(fetchDevicesByClientId(e.target.value));
  }

  const toggleIsNewClient = e => {
    dispatch(newToggleIsNewClient(!state.order.isNewClient))
    dispatch(newToggleIsNewDevice(true))
  }

  return (
    <div>
      {
        state.order.isNewClient ? <InputGroup className="mt-2">
          <Form.Control placeholder="Телефон" value={state.order.clientPhone} onChange={handlePhoneChange} />
              <Button variant="outline-secondary"  onClick={toggleIsNewClient}><i className={"fa fa-exchange"}></i></Button>
          <Form.Control placeholder="Имя" value={state.order.clientName} onChange={handleNameChange}/>
        </InputGroup> :
        <InputGroup className="mt-2">
          <Form.Control placeholder="Телефон" value={state.order.clientPhone} onChange={handlePhoneChange} onKeyDown={handleEnter}/>
          <Button variant="outline-secondary" onClick={handleSearch}>{"Искать"}</Button>
          <Button variant="outline-secondary" onClick={toggleIsNewClient}><i className={"fa fa-exchange"}></i></Button>
        </InputGroup>

      }
      {
        state.clients.length < 1 || state.order.isNewClient ? null : (<InputGroup className="mb-3 mt-3">
          <Form.Select onChange={handleClientSelect} >
            <option value="">Выбрать</option>
            {state.clients.map(d =>
              <option value={d.id} key={d.id}
                      selected={d.id === state.order.clientId}>{d.phone + ': ' + d.name}</option>)}
          </Form.Select>
        </InputGroup>)
      }
    </div>
  );
}

export default UserSearch;