import s from '../Placeholderhack.module.css';
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  newAddService, newChangeQty,
  newChangeServicesSum,
  newRemoveService,
  newSelectItem,
  newToggleIsCustom
} from "../../../redux/new-order-reducer";

function NewDefaultService(props) {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.newOrder);

  function getNumber(e) {
    if (Number(e.target.value) > Number(e.target.max)) {
      return e.target.max;
    }
    return e.target.value;
  }

  function getItemPrice() {
    let y = orderState.items.find(i => i.id === props.data.itemId);
    return y?.price;
  }

  function getItemName() {
    let item =  orderState.items.find(i => i.id === props.data.itemId);
    return item?.name;
  }

  let handleItemSelect = (e) => {
    if (e.target.value === '') return;
    let selectedItem = orderState.items.find(item => item.name === e.target.value);
    if (selectedItem) {
      dispatch(newSelectItem({mockId: props.data.mockId, newItemId: selectedItem.id}));
      dispatch(newChangeServicesSum());
    }
  }

  let handleChangeQty = (e) => {
    dispatch(newChangeQty({mockId: props.data.mockId, qty: getNumber(e)}))
    dispatch(newChangeServicesSum());
  }

  let handleRemove = (e) => {
    e.target.placeholder = '';
    dispatch(newRemoveService(props.data.mockId))
    dispatch(newChangeServicesSum());
  }

  let handleFocus = (e) => {
    e.target.placeholder = ''
  }

  let handleBlur = (e) => {
    e.target.placeholder = getItemName();
  }

  function hidePlus() {
    return (props.dataSize.index !== props.dataSize.size - 1);
  }

  return (!orderState.isItemsLoaded ? null :
  <div>
    <InputGroup className="mb-3">
      <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} onChange={handleChangeQty} min={1} max={10000000}/>
      <Form.Control list="datalistOptions" className={getItemName() ? s.inputId : ''}
                    placeholder={getItemName() ? getItemName() : 'Краткое описание'}
                    onChange={handleItemSelect}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
      />
      <InputGroup.Text style={{width: 100}} >{getItemPrice() ? getItemPrice() : 'Цена'}</InputGroup.Text>
      <Button variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
      { hidePlus() ? null : <Button variant="success" onClick={() => dispatch(newAddService())}><i className="fa fa-plus" aria-hidden="true"></i></Button>}
      <Button variant="primary" onClick={() => dispatch(newToggleIsCustom(props.data.mockId))}><i className="fa fa-exchange"></i></Button>
    </InputGroup>

    <datalist id="datalistOptions">
      {orderState.items.map(s => <option data-value={s.id} key={s.id}>{s.name}</option>)}
    </datalist>
  </div>
)
};


export default NewDefaultService;