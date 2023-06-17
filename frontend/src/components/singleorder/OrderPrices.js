import Card from 'react-bootstrap/Card';

import {useDispatch, useSelector} from "react-redux";
import s from "./OrderPrice.module.css"
import {useEffect} from "react";
import {changePartsSum, changeServicesSum} from "../../redux/single-order-reducer";

function OrderPrices(props) {
  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);
  useEffect(() => {
    dispatch(changeServicesSum());
    dispatch(changePartsSum());
  }, []);


  return (
    <Card className={s.customCard + " mt-4"}>
      {
        !orderState.isLoaded ? <div>Order prices Loading</div> :
        <Card.Body>
        <div className="price-text">
          <span className="price-text-inner fs-5 fw-bolder ">Сумма работ: </span>
          <span className="price-text-sum calculate  fs-5 fw-bolder">{orderState.servicesSum} р.</span>
        </div>
        <div className="price-text">
          <span className="price-parts-text-inner fs-5 fw-bolder">Сумма за запчасти: </span>
          <span className="price-parts-text-sum calculate fs-5 fw-bolder">{orderState.partsSum} р.</span>
        </div>
        <div className="price-text">
          <span className="price-text-inner fs-5 fw-bolder">Итого денег: </span>
          <span className="total-sum fs-5 fw-bolder">{orderState.totalSum} р.</span>
        </div>
      </Card.Body>}
    </Card>
  )
    ;
}

export default OrderPrices;