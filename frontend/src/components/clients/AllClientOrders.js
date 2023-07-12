import OrderRow from "../orderTable/OrderRow";
import Table from "react-bootstrap/Table";
import {useDispatch, useSelector} from "react-redux";
import Button from "react-bootstrap/Button";
import {useNavigate} from "react-router-dom";
import {fetchClientByIdForNewOrder} from "../../asyncActions/clients";

function AllClientOrders() {
  const state = useSelector(state => state.client)
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const onClick = () => {
    dispatch(fetchClientByIdForNewOrder(state.client.id))
      .then(() => navigate("/new_order"))
  }

    return  (
    <Table striped hover>
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Клиент</th>
        <th scope="col">Что чиним</th>
        <th scope="col">Кратко</th>
        <th scope="col">Статус</th>
        <th scope="col">Когда приняли</th>
      </tr>
      </thead>
      <tbody>
      {state.orders.map(o => <OrderRow data={o}/>)}
      </tbody>
      <Button className="mt-3" onClick={onClick}>Новый заказ</Button>
    </Table>
  )
}
export default AllClientOrders;