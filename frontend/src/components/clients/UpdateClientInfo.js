import {useDispatch, useSelector} from "react-redux";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {changeAddress, changeDiscount, changeName, changePhone, changePhone2} from "../../redux/client-reducer";
import {updateClient} from "../../asyncActions/clients";

function UpdateClientInfo() {

  const state = useSelector((state) => state.client)
  const dispatch = useDispatch();

  let onChangeName = e => {
    dispatch(changeName(e.target.value));
  }
  let onChangePhone = e => {
    dispatch(changePhone(e.target.value));
  }
  let onChangePhone2 = e => {
    dispatch(changePhone2(e.target.value));
  }
  let onChangeAddress = e => {
    dispatch(changeAddress(e.target.value));
  }
  let onChangeDiscount = e => {
    dispatch(changeDiscount(e.target.value));
  }

  let onClick = e => {
    dispatch(updateClient(state.client));
  }

  return (<div>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>ФИО</InputGroup.Text>
        <Form.Control value={state.client.name} onChange={onChangeName}/>
      </InputGroup>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>Телефон 1</InputGroup.Text>
        <Form.Control value={state.client.phone} onChange={onChangePhone}/>
      </InputGroup>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>Телефон 2</InputGroup.Text>
        <Form.Control value={state.client.phone2} onChange={onChangePhone2}/>
      </InputGroup>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>Адрес</InputGroup.Text>
        <Form.Control value={state.client.address} onChange={onChangeAddress}/>
      </InputGroup>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>Скидка</InputGroup.Text>
        <Form.Control value={state.client.discount} onChange={onChangeDiscount} type="number"/>
      </InputGroup>
      <div>Дата создания {state.client.createdAt}</div>

      <Button variant="primary" onClick={onClick}>Сохранить</Button>
    </div>
  )
}
export default UpdateClientInfo;