import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {useDispatch, useSelector} from "react-redux";
import {selectStatus} from "../../redux/common-reducer";
import {fetchOrders} from "../../asyncActions/orders";

function SearchByStatus() {
  const dispatch = useDispatch();
  const common = useSelector((state) => state.common)

  let handleChange = (e) => {
    dispatch(selectStatus(e.target.value));
  }

  let handleSearchClick = () => {
    dispatch(fetchOrders(1, common.selectedStatus));
  }

  return <InputGroup className="mt-3">
    <Form.Select onChange={handleChange}>
      <option value={-1}>Активные</option>
      <option value="">Все</option>
      {common.statuses.map(s =>
        <option value={s.id} key={s.id}>
          {s.name}
        </option>)}
    </Form.Select>
    <Button variant="outline-secondary" id="button-addon2" onClick={handleSearchClick}>
      Искать
    </Button>
  </InputGroup>;
}

export default SearchByStatus;
