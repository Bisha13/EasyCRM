import Pagination from 'react-bootstrap/Pagination';
import {useDispatch, useSelector} from "react-redux";
import {fetchOrders} from "../../asyncActions/orders";
import {setActivePage} from "../../redux/orders-reducer";

function OrdersPagination() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.orders)
  let items = [];

  let handleClick = (e) => {
    dispatch(fetchOrders(e.target.text, state.selectedStatus));
    dispatch(setActivePage(Number(e.target.text)));
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

export default OrdersPagination;