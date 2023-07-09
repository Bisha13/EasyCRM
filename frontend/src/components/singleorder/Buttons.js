import {useDispatch, useSelector} from "react-redux";
import Button from "react-bootstrap/Button";
import {closeOrder, finishOrder, updateSingleOrder} from "../../asyncActions/orders";
import {useNavigate} from "react-router-dom";

function Buttons() {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);
  const navigate = useNavigate();

  const handleSave = () => {
    dispatch(updateSingleOrder(orderState.order))
      .then(({status}) => {
        if (status >= 400) {
          alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
        } else {
          alert("Заказ сохранен");
          navigate("/");
        }
      })
  }

  const handleClose = () => {
    dispatch(closeOrder(orderState.order))
      .then(({status}) => {
        if (status >= 400) {
          alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
        } else {
          alert("Заказ закрыт");
          navigate("/");
        }
      })
  }

  const handleFinish = () => {
    dispatch(finishOrder(orderState.order))
      .then(({status}) => {
        if (status >= 400) {
          alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
        } else {
          alert("Заказ обработан");
          navigate("/");
        }
      })
  }

  function isEnabled() {
      return orderState.order.services.every(s => s.executorId > 0)
  }

  return (
    <div className="ms-3 mb-3">
      <Button className='me-2' variant="primary" onClick={handleSave} disabled={!isEnabled() && orderState.order.statusId == '10'}>Сохранить</Button>
      {orderState.order.statusId != '10' ?
        <Button className='me-2' variant="success" onClick={handleFinish} disabled={!isEnabled()}>Готов к выдаче</Button> : null}
      <Button className='me-2' variant="danger" onClick={handleClose} disabled={!isEnabled()}>Закрыть</Button>
    </div>
  );
}

export default Buttons;