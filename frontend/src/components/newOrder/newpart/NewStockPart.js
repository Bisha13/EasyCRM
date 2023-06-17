import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import s from "../Placeholderhack.module.css";
import {
  newAddPart,
  newChangePartQty,
  newChangePartsSum,
  newRemovePart,
  newSelectStock,
  newToggleIsStock
} from "../../../redux/new-order-reducer";

function NewStockPart(props) {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.newOrder);

  function getNumber(e) {
    if (Number(e.target.value) > Number(e.target.max)) {
      return e.target.max;
    }
    return e.target.value;
  }

  function getPartPrice() {
    let stockPart = orderState.stock.find(i => ''+i.id === props.data.stockId);
    return stockPart?.price;
  }

  function getPartName() {
    let stockPart =  orderState.stock.find(i => ''+i.id === props.data.stockId);
    return stockPart?.name;
  }

  let handlePartSelect = (e) => {
    console.log(orderState.stock[3].name);
    console.log(e.target.value);
    console.log(e.target.value === orderState.stock[3].name);
    if (e.target.value === '') return;
    let selectedStock = orderState.stock
      .filter(stock => stock.name != null)
      .find(stock => stock.name === e.target.value)
    if (selectedStock) {
      dispatch(newSelectStock({mockId: props.data.mockId, newStockId: ''+selectedStock.id}))
      dispatch(newChangePartsSum());
    }
  }

  let handleChangeQty = (e) => {
    dispatch(newChangePartQty({mockId: props.data.mockId, qty: getNumber(e)}))
    dispatch(newChangePartsSum());
  }

  let handleRemove = () => {
    dispatch(newRemovePart(props.data.mockId))
    dispatch(newChangePartsSum());
  }

  let handleFocus = (e) => {
    e.target.placeholder = ''
  }

  let handleBlur = (e) => {
    e.target.placeholder = getPartName();
  }

  function hidePlus() {
    return (props.dataSize.index !== props.dataSize.size - 1);
  }

  return (!orderState.isStockLoaded ? null :
      <div>
        <InputGroup className="mb-3">
          <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} min={1} max={10000000} onChange={handleChangeQty}/>
          <Form.Control list="partsOptions" className={getPartName() ? s.inputId : ''}
                        placeholder={getPartName() ? getPartName() : 'Краткое описание'}
                        onChange={handlePartSelect}
                        onFocus={handleFocus}
                        onBlur={handleBlur}
          />
          <InputGroup.Text style={{width: 100}} >{getPartPrice() ? getPartPrice() : 'Цена'}</InputGroup.Text>
          <Button variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
          { hidePlus() ? null : <Button variant="success" onClick={() => dispatch(newAddPart())}><i className="fa fa-plus" aria-hidden="true"></i></Button>}
          <Button variant="primary" onClick={() => dispatch(newToggleIsStock(props.data.mockId))}><i className="fa fa-exchange"></i></Button>
        </InputGroup>

        <datalist id="partsOptions">
          {orderState.stock.map(s => <option data-value={s.id} key={s.id}>{s.name}</option>)}
        </datalist>
      </div>
  )
};

export default NewStockPart;