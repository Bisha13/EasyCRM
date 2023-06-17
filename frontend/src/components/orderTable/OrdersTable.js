import OrderRow from "./OrderRow";
import Table from 'react-bootstrap/Table';
import {useDispatch, useSelector} from "react-redux";
import {fetchOrders} from "../../asyncActions/orders";
import {useEffect} from "react";

function OrdersTable() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.orders)

  useEffect(() => {
    dispatch(fetchOrders(1, -1));
  }, []);

  return (
    <Table striped hover>
    <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Клиент</th>
      <th scope="col">Что чиним</th>
      <th scope="col">Описание</th>
      <th scope="col">Статус</th>
      <th scope="col"> Дн</th>
    </tr>
    </thead>
    <tbody>
    {state.orders.map(o => <OrderRow data={o}/>)}
    </tbody>
  </Table>)
}

export default OrdersTable;
