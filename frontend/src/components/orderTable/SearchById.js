import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {useDispatch, useSelector} from "react-redux";
import {updateOrderToSearch} from "../../redux/orders-reducer";
import {useNavigate} from "react-router-dom";

function SearchById() {
  const dispatch = useDispatch();
  const state = useSelector((state) => state.orders);
  let navigate = useNavigate();

  let handleChange = (e) => {
    dispatch(updateOrderToSearch(e.target.value));
  }

  const routeChange = () =>{
    let path = `/order/${state.orderToSearch}`;
    navigate(path);
  }

  const handleEnter = (e) => {
    if (e.key === "Enter") {
      routeChange();
    }
  }

  return <InputGroup className="mt-3">
    <Form.Control
      placeholder="Номер заказа"
      aria-label="Recipient's username"
      aria-describedby="basic-addon2"
      value={state.orderToSearch}
      onChange={handleChange}
      onKeyDown={handleEnter}
    />
    <Button variant="primary" id="button-addon2" onClick={routeChange}>
      <i className="fa fa-search"></i>
    </Button>
  </InputGroup>;
}

export default SearchById;
