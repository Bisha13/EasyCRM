import Table from 'react-bootstrap/Table';
import {useDispatch, useSelector} from "react-redux";

import {useEffect} from "react";
import ClientRow from "./ClientRow";
import {fetchClients} from "../../../asyncActions/clients";

function ClientsTable() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.client)

  useEffect(() => {
    dispatch(fetchClients(1));
  }, []);

  return (
    <Table striped hover>
    <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Имя</th>
      <th scope="col">Телефон</th>
      <th scope="col">Дата</th>
    </tr>
    </thead>
    <tbody>
    {state.clients.map(o => <ClientRow data={o}/>)}
    </tbody>
  </Table>)
}

export default ClientsTable;
