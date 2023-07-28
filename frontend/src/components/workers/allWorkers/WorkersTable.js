import Table from 'react-bootstrap/Table';
import {useDispatch, useSelector} from "react-redux";

import {useEffect} from "react";
import {fetchWorkers} from "../../../asyncActions/workers";
import WorkerRow from "./WorkerRow";

function WorkersTable() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.worker)

  useEffect(() => {
    dispatch(fetchWorkers());
  }, []);

  return (
    <Table striped hover>
    <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Имя</th>
      <th scope="col">Телефон</th>
      <th scope="col">Процент</th>
    </tr>
    </thead>
    <tbody>
    {state.workers.map(o => <WorkerRow data={o}/>)}
    </tbody>
  </Table>)
}

export default WorkersTable;
