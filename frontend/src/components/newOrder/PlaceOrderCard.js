import Card from "react-bootstrap/Card";
import {useDispatch, useSelector} from "react-redux";
import UserSearch from "./UserSearch";
import Button from "react-bootstrap/Button";
import {createNewOrder} from "../../asyncActions/orders";
import {useNavigate} from "react-router-dom";
import {resetState} from "../../redux/new-order-reducer";

function PlaceOrderCard() {

  const state = useSelector((state) => state.newOrder)
  const dispatch = useDispatch();
  let navigate = useNavigate();
  const routeChange = () =>{
    let path = `/`;
    navigate(path);
  }
  const createOrder = e => {
    dispatch(createNewOrder(state.order))
      .then(({status, json}) => {
      if (status >= 400) {
        alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
      } else {
        alert("Заказ сохранен")
        dispatch(resetState())
        routeChange();
      }
    })
  }

  return (<Card className={"mt-3"}>
          <Card.Body>
            <div className="price-text">
              <span className="price-text-inner ">Итого денег: </span>
              <span className="price-text-sum calculate">{state.totalSum} р.</span>
            </div>
            <UserSearch/>
            <div className="d-grid gap-2 mt-3">
              <Button variant="success" size="lg" onClick={createOrder}>Оформить заказ</Button>
            </div>
          </Card.Body>
        </Card>);
}

export default PlaceOrderCard;