import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import {changeStatusColor, changeStatusName, toggleHide} from "../../redux/common-reducer";
import {useDispatch} from "react-redux";

function Status(props) {

  const dispatch = useDispatch();
  const onToggleHide = () => {
    dispatch(toggleHide(props.status.id));
  }

  const onChangeStatusName = (e) => {
    dispatch(changeStatusName({id: props.status.id, newName: e.target.value}))
  }

  const onChangeStatusColor = (e) => {
    dispatch(changeStatusColor({id: props.status.id, newColor: e.target.value}))
  }

  return (
    <InputGroup className="mt-3">
      <InputGroup.Checkbox checked={props.status.hide} onClick={onToggleHide}/>
      <Form.Control value={props.status.name} onChange={onChangeStatusName}/>
      <Form.Control type="color" style={{maxWidth: 40}} value={props.status.colour} onChange={onChangeStatusColor}/>
    </InputGroup>
  );
}
export default Status;