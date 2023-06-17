import OrderRow from "../orderTable/OrderRow";
import Table from "react-bootstrap/Table";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchOrdersByClientId} from "../../asyncActions/orders";

function AllClientOrders() {
  const state = useSelector(state => state.client)

  return (
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
    </Table>
  )
}
export default AllClientOrders;