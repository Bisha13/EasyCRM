import Card from 'react-bootstrap/Card';
import Container from "react-bootstrap/Container";

import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchSingleOrder} from "../../asyncActions/orders";
import OrderInfo from "./OrderInfo";
import Services from "./service/Services";
import {useParams} from "react-router-dom";
import Parts from "./part/Parts";
import OrderPrices from "./OrderPrices";
import Buttons from "./Buttons";
import SingleOrderDescription from "./SingleOrderDescription";

function SingleOrder() {
  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);
  const {id} = useParams();

  useEffect(() => {
    dispatch(fetchSingleOrder(id));
  }, []);

  let renderElement = () => {
    if (!orderState.isLoaded) {
      return <div>Order Loading</div>
    } else if (orderState.isFetchError) {
      return <div>Не найдено</div>
    } else {
      return <Container>
          <Card className={"mt-4 mb-4"}>
            <Card.Body>
              <OrderInfo/>
              <br/>
              <br/>
              <Services />
              <Parts/>
              <SingleOrderDescription/>
              <OrderPrices />
            </Card.Body>
            <Buttons/>
          </Card>
      </Container>
    }
  }

  return (
        renderElement()
  )
    ;
}

export default SingleOrder;