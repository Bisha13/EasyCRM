import Pagination from 'react-bootstrap/Pagination';
import {useDispatch, useSelector} from "react-redux";
import {fetchAllDevices} from "../../asyncActions/devices";
import {setDevicesActivePage} from "../../redux/device-reducer";

function DevicesPagination() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.device)
  let items = [];

  let handleClick = (e) => {
    dispatch(fetchAllDevices(e.target.text));
    dispatch(setDevicesActivePage(Number(e.target.text)));
  }

  if (state.pageCount > 1) {
    for (let number = 1; number <= state.pageCount; number++) {
      items.push(
        <Pagination.Item key={number} active={number === state.activePage} onClick={handleClick}>
          {number}
        </Pagination.Item>,
      );
    }
  }

  return (
    <div className={"mt-3 d-flex justify-content-end"}>
    <Pagination>{items}</Pagination>
    <br/>
  </div>
);
}

export default DevicesPagination;