import {useDispatch, useSelector} from "react-redux";
import Button from "react-bootstrap/Button";
import {closeOrder, finishOrder, updateSingleOrder} from "../../asyncActions/orders";
import {useNavigate} from "react-router-dom";

function Buttons() {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);
  const navigate = useNavigate();

  let handleSave = () => {
    dispatch(updateSingleOrder(orderState.order)).then(navigate("/"))
  }

  let handleClose = () => {
    dispatch(closeOrder(orderState.order)).then(navigate("/"))
  }

  const handleFinish = () => {
    dispatch(finishOrder(orderState.order)).then(navigate("/"))
  }

  return (
    <div className="ms-3 mb-3">
      <Button className='me-2' variant="primary" onClick={handleSave}>Сохранить</Button>
      { orderState.order.statusId != '10' ? <Button className='me-2' variant="success" onClick={handleFinish}>Готов к выдаче</Button> : null}
      <Button className='me-2' variant="danger" onClick={handleClose}>Закрыть</Button>
    </div>
  );
}

export default Buttons;