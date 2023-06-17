import Pagination from 'react-bootstrap/Pagination';
import {useDispatch, useSelector} from "react-redux";
import {fetchClients, fetchClientsBySearch} from "../../../asyncActions/clients";
import {setActiveClientPage} from "../../../redux/client-reducer";

function ClientsPagination() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.client)
  let items = [];

  let handleClick = (e) => {
    state.isClientSearchPagination ?
      dispatch(fetchClientsBySearch(e.target.text, state.searchString)) : 
      dispatch(fetchClients(e.target.text));
    dispatch(setActiveClientPage(Number(e.target.text)));
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

export default ClientsPagination;