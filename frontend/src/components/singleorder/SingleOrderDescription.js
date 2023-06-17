import {useDispatch, useSelector} from "react-redux";

import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import {changeFullDescription} from "../../redux/single-order-reducer";

function SingleOrderDescription() {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);

  function getRowsCount() {
    let description = orderState.order.fullDescription;
    return description ? description.split('\n').length + 2 : 5;
  }

  let handleChange = e => {
    dispatch(changeFullDescription(e.target.value))
  }

  return (
    <div>
      <span>Описание</span>
      {
        <InputGroup>
          <Form.Control rows={getRowsCount()} as="textarea" aria-label="With textarea" onChange={handleChange} >{orderState.order.fullDescription}</Form.Control>
        </InputGroup>
      }
    </div>
  );
}

export default SingleOrderDescription;