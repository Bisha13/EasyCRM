import {useDispatch, useSelector} from "react-redux";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import {newChangeFullDescription, newChangeSmallDescription} from "../../redux/new-order-reducer";

function Descriptions() {

  const state = useSelector((state) => state.newOrder);
  const dispatch = useDispatch();

  function getRowsCount() {
    let description = state.order.fullDescription;
    return description ? description.split('\n').length + 1 : 2;
  }

  let handleChange = e => {
    dispatch(newChangeFullDescription(e.target.value))
  }

  let handleChangeSmallDescription = (e) => {
    dispatch(newChangeSmallDescription(e.target.value))
  }

  return (
    <div>
      <InputGroup className="mt-3">
        <Form.Control placeholder="Особые отметки" value={state.order.smallDescription} onChange={handleChangeSmallDescription}/>
        <InputGroup.Text><i className={"fa fa-pencil"}></i></InputGroup.Text>
      </InputGroup>
      <InputGroup className="mt-3">
        <Form.Control rows={getRowsCount()} as="textarea" aria-label="With textarea" placeholder={"Комментарии"}
                      onChange={handleChange}>{state.order.fullDescription}</Form.Control>
      </InputGroup>
    </div>
  );
}


export default Descriptions;