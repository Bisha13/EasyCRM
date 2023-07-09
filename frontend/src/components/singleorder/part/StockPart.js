import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  addPart,
  changePartQty,
  changePartsSum,
  removePart,
  selectStock,
  toggleIsStock
} from "../../../redux/single-order-reducer";
import s from "../Placeholderhack.module.css";

function StockPart(props) {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);

  function getPartPrice() {
    let stockPart = orderState.stock.find(i => ''+i.id === props.data.stockId);
    return stockPart?.price;
  }

  function getPartName() {
    if (!props.data.stockId > 0) {
      return 'Краткое описание';
    }

    let stockPart =  orderState.stock.find(i => ''+i.id === props.data.stockId);
    return stockPart?.name || 'Краткое описание';
  }

  let handlePartSelect = (e) => {
    if (e.target.value === '') {
      dispatch(selectStock({mockId: props.data.mockId, newStockId: '0'}))
      dispatch(changePartsSum());
    }

    let selectedStock = orderState.stock.find(stock => stock.name === e.target.value)
    dispatch(selectStock({mockId: props.data.mockId, newStockId: selectedStock ? ''+selectedStock.id : 0}))
    dispatch(changePartsSum());
  }

  let handleChangeQty = (e) => {
    dispatch(changePartQty({mockId: props.data.mockId, qty: e.target.value}))
    dispatch(changePartsSum());
  }

  let handleRemove = () => {
    dispatch(removePart(props.data.mockId))
    dispatch(changePartsSum());
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
          <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} onChange={handleChangeQty}/>
          <Form.Control list="partsOptions" className={props.data.stockId > 0 ? s.inputId : ''}
                        placeholder={getPartName()}
                        onChange={handlePartSelect}
                        onFocus={handleFocus}
                        onBlur={handleBlur}
          />
          <InputGroup.Text style={{width: 100}} >{getPartPrice() ? getPartPrice() : 'Цена'}</InputGroup.Text>
          <Button variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
          { hidePlus() ? null : <Button variant="success" onClick={() => dispatch(addPart())}><i className="fa fa-plus" aria-hidden="true"></i></Button>}
          <Button variant="primary" onClick={() => dispatch(toggleIsStock(props.data.mockId))}><i className="fa fa-exchange"></i></Button>
        </InputGroup>

        <datalist id="partsOptions">
          {orderState.stock.map(s => <option data-value={s.id} key={s.id}>{s.name}</option>)}
        </datalist>
      </div>
  )
}


export default StockPart;