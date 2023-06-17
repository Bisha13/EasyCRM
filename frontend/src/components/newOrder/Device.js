import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import {useDispatch, useSelector} from "react-redux";
import {
  newChangeDeviceDescription,
  newChangeDeviceName,
  newSelectDevice,
  newToggleIsNewDevice
} from "../../redux/new-order-reducer";

function Device() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.newOrder);
  const handleChange = (e) => {
    dispatch(newSelectDevice(e.target.value));
  }
  const toggleIsNewDeviceFalse = e => {
    dispatch(newToggleIsNewDevice(false))
  }
  const toggleIsNewDeviceTrue = e => {
    dispatch(newToggleIsNewDevice(true))
  }
  const handleDeviceNameChange = e => {
    dispatch(newChangeDeviceName(e.target.value))
  }
  const handleDeviceDescriptionChange = e => {
    dispatch(newChangeDeviceDescription(e.target.value))
  }

    return  (
    state.devices.length === 0 || state.order.isNewDevice ?
      <InputGroup className="mb-3">
        <Form.Control placeholder="Что чиним?" value={state.order.deviceName} onChange={handleDeviceNameChange}/>
        <InputGroup.Text onClick={toggleIsNewDeviceFalse}><i className={"fa fa-bicycle"}></i></InputGroup.Text>
        <Form.Control placeholder="Описание" value={state.order.deviceDescription} onChange={handleDeviceDescriptionChange}/>
      </InputGroup> :
      <InputGroup className="mb-3" onChange={handleChange}>
        <Form.Select>
          <option value="">Выбрать</option>
          {state.devices.map(d =>
            <option value={d.id} key={d.id}
                    selected={d.id === state.order.deviceId}>{(d.name || '') + ' ' + (d.description || '')}</option>)}
        </Form.Select>
        <InputGroup.Text onClick={toggleIsNewDeviceTrue}><i className={"fa fa-bicycle"}></i></InputGroup.Text>
      </InputGroup>);
}

export default Device;