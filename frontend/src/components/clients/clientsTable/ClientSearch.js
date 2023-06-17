import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {useDispatch, useSelector} from "react-redux";

import {useNavigate} from "react-router-dom";
import {updateOrderToSearch} from "../../../redux/orders-reducer";
import {changeSearchString} from "../../../redux/client-reducer";
import {fetchClients, fetchClientsBySearch} from "../../../asyncActions/clients";

function ClientSearch() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.client);
  let navigate = useNavigate();

  let handleChange = (e) => {
    dispatch(changeSearchString(e.target.value));
  }

  const routeChange = () =>{
    dispatch(fetchClientsBySearch(1, state.searchString))
  }

  const clearSearch = () => {
    dispatch(changeSearchString(""));
    dispatch(fetchClients(1));
  }

  return <InputGroup className="mt-3">
    <Form.Control
      placeholder="Поиск по клиентам"
      aria-label="Search"
      aria-describedby="basic-addon2"
      value={state.searchString}
      onChange={handleChange}
    />
    <Button variant="primary" id="button-addon2" onClick={routeChange}>
      <i className="fa fa-search"></i>
    </Button>
    <Button variant="danger" id="button-addon2" onClick={clearSearch}>
      <i className="fa fa-window-close"></i>
    </Button>
  </InputGroup>;
}

export default ClientSearch;
