import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  newAddPart,
  newChangePartName, newChangePartPurchasePrice,
  newChangePartQty,
  newChangePartsSum,
  newRemovePart, newSelectPercentValue,
  newToggleIsStock
} from "../../../redux/new-order-reducer";

function NewCustomPart(props) {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.newOrder);

  function getNumber(e) {
    if (Number(e.target.value) > Number(e.target.max)) {
      return e.target.max;
    }
    return e.target.value;
  }

  let handleChangeQty = (e) => {
    dispatch(newChangePartQty({mockId: props.data.mockId, qty: getNumber(e)}))
    dispatch(newChangePartsSum());
  }

  let handleRemove = (e) => {
    dispatch(newRemovePart(props.data.mockId));
    dispatch(newChangePartsSum());
  }

  let handleNameChange = (e) => {
    dispatch(newChangePartName({mockId: props.data.mockId, newName: e.target.value}))
  }

  let handlePecentSelect = (e) => {
    dispatch(newSelectPercentValue({mockId: props.data.mockId, newPercent: e.target.value}))
    dispatch(newChangePartsSum());
  }

  let handlePurchasePriceChange = (e) => {
    dispatch(newChangePartPurchasePrice({mockId: props.data.mockId, newPurchasePrice: getNumber(e)}))
    dispatch(newChangePartsSum());
  }

  function hidePlus() {
    return (props.dataSize.index !== props.dataSize.size - 1);
  }

  function getDefaultPercent() {
    let purchasePrice = props.data.purchasePrice ? Number(props.data.purchasePrice) : 0;
    let price = props.data.price ? Number(props.data.price) : 0;

    let percent = 100 * price / purchasePrice - 100;
    return percent;
  }


  return (!orderState.isStockLoaded ? null :
      <div>

        <InputGroup className="mb-3">
          <Form.Control style={{maxWidth: 100}} type="number" min={1} max={1000000} value={props.data.qty} onChange={handleChangeQty}/>
          <Form.Control placeholder="Краткое описание" value={props.data.name} onChange={handleNameChange}/>
          <Form.Control type='number' style={{maxWidth: 100}} placeholder="Цена" min={0} max={10000000} value={props.data.purchasePrice} onChange={handlePurchasePriceChange}/>
          <Form.Select style={{maxWidth: 100}} onChange={handlePecentSelect} defaultValue={getDefaultPercent()}>
            <option value={0} key={0} selected={props.data.percent === 0}>{'0%'}</option>
            <option value={20} key={20} selected={props.data.percent === 20}>{'20%'}</option>
            <option value={30} key={30} selected={props.data.percent === 30}>{'30%'}</option>
          </Form.Select>
          <InputGroup.Text style={{width: 100}}>{props.data.price ? props.data.price : 'Цена'}</InputGroup.Text>
          <Button variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
          {hidePlus() ? null : <Button variant="success" onClick={() => dispatch(newAddPart())}><i className="fa fa-plus"
                                                                                                aria-hidden="true"></i></Button>}
          <Button variant="primary" onClick={() => dispatch(newToggleIsStock(props.data.mockId))}><i
            className="fa fa-exchange"></i></Button>
        </InputGroup>

        <datalist id="partsOptions">
          {orderState.stock.map(s => <option data-value={s.id} key={s.id}>{s.name}</option>)}
        </datalist>
      </div>
  )
};

export default NewCustomPart;