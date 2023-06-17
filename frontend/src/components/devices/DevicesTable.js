import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchAllDevices} from "../../asyncActions/devices";
import Table from "react-bootstrap/Table";
import DeviceRow from "./DeviceRow";


function DevicesTable() {
  const state = useSelector(state => state.device)
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchAllDevices(1));
  }, []);

  return (
    <div>
    <Table striped hover>
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Название</th>
        <th scope="col">Описание</th>
        <th scope="col">Когда приняли</th>
      </tr>
      </thead>
      <tbody>
      {state.devices.map(o => <DeviceRow data={o} key={o.id}/>)}
      </tbody>
    </Table>
    </div>
  )
}
export default DevicesTable;