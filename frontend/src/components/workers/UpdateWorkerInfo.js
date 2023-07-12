import {useDispatch, useSelector} from "react-redux";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {changeName, changePercent, changePhone} from "../../redux/worker-reducer";
import {updateWorker} from "../../asyncActions/workers";

function UpdateWorkerInfo() {

  const state = useSelector((state) => state.worker)
  const dispatch = useDispatch();

  let onChangeName = e => {
    dispatch(changeName(e.target.value));
  }
  let onChangePhone = e => {
    dispatch(changePhone(e.target.value));
  }
  let onChangePercent = e => {
    if (e.target.value >= 0 && e.target.value <= 100) {
      dispatch(changePercent(e.target.value));
    }
  }

  let onClick = e => {
    dispatch(updateWorker(state.worker)).then(({status, json}) => {
      if (status >= 400) {
        alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
      } else {
        alert("Работник обновлен");
      }
    })
  }

  return (<div>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>ФИО</InputGroup.Text>
        <Form.Control value={state.worker.name} onChange={onChangeName}/>
      </InputGroup>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>Телефон 1</InputGroup.Text>
        <Form.Control value={state.worker.phone} onChange={onChangePhone}/>
      </InputGroup>
      <InputGroup>
        <InputGroup.Text style={{minWidth: 110}}>Процент</InputGroup.Text>
        <Form.Control value={state.worker.percent} onChange={onChangePercent} type="number" min={0} max={100}/>
      </InputGroup>

      <Button variant="primary" onClick={onClick}>Сохранить</Button>
    </div>
  )
}
export default UpdateWorkerInfo;