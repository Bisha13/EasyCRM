import Table from "react-bootstrap/Table";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchDevicesByClientId} from "../../asyncActions/devices";
import DeviceRow from "../devices/DeviceRow";
import {Link} from "react-router-dom";

function AllClientDevices() {
  const state = useSelector(state => state.client)
  const dispatch = useDispatch();
  useEffect(() => {
    console.log(JSON.stringify(state.client))
    dispatch(fetchDevicesByClientId(state.client.id));
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
      <Link to={`/devices/new`}>Новый</Link>
    </div>
  )
}
export default AllClientDevices;