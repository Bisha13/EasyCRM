import Form from "react-bootstrap/Form";
import {useDispatch, useSelector} from "react-redux";

import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import {changeAllExecutors, changeSmallDescription, selectStatus} from "../../redux/single-order-reducer";
import {Link} from "react-router-dom";

function OrderInfo() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.singleOrder)
  const common = useSelector((state) => state.common)
  let handleChange = (e) => {
    dispatch(selectStatus(e.target.value));
  }
  let handleChangeSmallDescription = (e) => {
    dispatch(changeSmallDescription(e.target.value))
  }

  let handleChangeAllExecutors = (e) => {
    dispatch(changeAllExecutors(e.target.value));
  }

  return (
    <Row>
      <Col>
        <div>
          <div>
            <span>Номер заказа: </span>
            <span>{state.order.id}</span>
          </div>

          <div>
            <span>Статус заказа: </span>
            <select onChange={handleChange}>
              {common.statuses.map(s =>
                <option value={s.id} selected={state.order.statusId === s.id.toString()} key={s.id}>{s.name}</option>)}
            </select>
          </div>

          <div>
            <Link to={`/clients/${state.order.clientId}`}>Клиент: {state.order.clientName}</Link>
          </div>

          <div>
            <span>Телефон: </span>
            <span>{state.order.clientPhone}</span>
          </div>

          <div>
            <span>Открыт: </span>
            <span>{state.order.startedAt.replace("T", " ")}</span>
          </div>


          {!state.order.closedAt ? null :
            <div>
              <span>Закрыт: {state.order.closedAt}</span>
            </div>
          }


          {!state.order.clientDiscount ? null :
            <div>
              <span>Скидка: {state.order.clientDiscount}%</span>
            </div>
          }

        </div>

        <div>
          <Link to={`/devices/${state.order.deviceId}`}>Что чиним: {(state.order.deviceName || "безымянный")}</Link>
        </div>

        <div>
          <span>Исполнитель: </span>
          <Form.Select onChange={handleChangeAllExecutors} className="w-50">
            {common.workers.map(s =>
              <option value={s.id} key={s.id}>{s.name}</option>)}
          </Form.Select>
        </div>
      </Col>
      <Col>
        <span>Краткое описание:</span>
        <Form.Control className="w-75" type="email" placeholder="Краткое описание"
                      value={state.order.smallDescription} onChange={handleChangeSmallDescription}/>
        <br/>
      </Col>
    </Row>
  )
}

export default OrderInfo;