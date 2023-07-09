import s from '../Placeholderhack.module.css';
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  addService,
  changeQty, changeServicesSum,
  removeService,
  selectExecutor,
  selectItem,
  toggleIsCustom
} from "../../../redux/single-order-reducer";

function DefaultService(props) {

  const dispatch = useDispatch();
  const common = useSelector((state) => state.common);
  const orderState = useSelector((state) => state.singleOrder);

  function getItemPrice() {
    let y = orderState.items.find(i => i.id === props.data.itemId);
    return y?.price;
  }

  function getItemName() {
    if (!props.data.itemId > 0) {
      return 'Краткое описание';
    }
    let item =  orderState.items.find(i => i.id === props.data.itemId);
    return item?.name || 'Краткое описание';
  }

  let handleItemSelect = (e) => {
    if (e.target.value === '') {
      dispatch(selectItem({mockId: props.data.mockId, newItemId: 0}));
      dispatch(changeServicesSum());
      return;
    }

    let selectedItem = orderState.items.find(item => item.name === e.target.value);
    dispatch(selectItem({mockId: props.data.mockId, newItemId: selectedItem ? selectedItem.id : 0}));
    dispatch(changeServicesSum());
  }

  let handleExecutorSelect = (e) => {
    dispatch(selectExecutor({mockId: props.data.mockId, executorId: e.target.value}))
  }

  let handleChangeQty = (e) => {
    dispatch(changeQty({mockId: props.data.mockId, qty: e.target.value}))
    dispatch(changeServicesSum());
  }

  let handleRemove = (e) => {
    e.target.placeholder = '';
    dispatch(removeService(props.data.mockId))
    dispatch(changeServicesSum());
  }

  let handleFocus = (e) => {
    e.target.placeholder = '';
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
      <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} onChange={handleChangeQty} min={1} disabled={props.data.status === 'PAID'}/>
      <Form.Control list="datalistOptions" className={props.data.itemId > 0 ? s.input : ''}
                    placeholder={getItemName()}
                    onChange={handleItemSelect}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    disabled={props.data.status === 'PAID'}
      />
      <InputGroup.Text style={{width: 100}} disabled={props.data.status === 'PAID'}>{getItemPrice() ? getItemPrice() : 'Цена'}</InputGroup.Text>
      <Form.Select style={{maxWidth: 200}} onChange={handleExecutorSelect} disabled={props.data.status === 'PAID'}>
        {common.workers.map(s => <option value={s.id} selected={props.data.executorId === s.id} key={s.id}>{s.name}</option>)}
      </Form.Select>
      <Button disabled={props.data.status === 'PAID'} variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
      { hidePlus() ? null : <Button disabled={props.data.status === 'PAID'} variant="success" onClick={() => dispatch(addService())}><i className="fa fa-plus" aria-hidden="true"></i></Button>}
      <Button disabled={props.data.status === 'PAID'} variant="primary" onClick={() => dispatch(toggleIsCustom(props.data.mockId))}><i className="fa fa-exchange"></i></Button>
    </InputGroup>

    <datalist id="datalistOptions">
      {orderState.items.map(s => <option data-value={s.id} key={s.id}>{s.name}</option>)}
    </datalist>
  </div>
)
};


export default DefaultService;