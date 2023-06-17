import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  addPart,
  changePartName,
  changePartPurchasePrice,
  changePartQty,
  changePartsSum,
  removePart,
  selectPercentValue,
  toggleIsStock
} from "../../../redux/single-order-reducer";

function CustomPart(props) {

  const dispatch = useDispatch();
  const orderState = useSelector((state) => state.singleOrder);

  let handleChangeQty = (e) => {
    dispatch(changePartQty({mockId: props.data.mockId, qty: e.target.value}))
    dispatch(changePartsSum());
  }

  let handleRemove = (e) => {
    dispatch(removePart(props.data.mockId));
    dispatch(changePartsSum());
  }

  let handleNameChange = (e) => {
    dispatch(changePartName({mockId: props.data.mockId, newName: e.target.value}))
  }

  let handlePecentSelect = (e) => {
    dispatch(selectPercentValue({mockId: props.data.mockId, newPercent: e.target.value}))
    dispatch(changePartsSum());
  }
  let handlePurchasePriceChange = (e) => {
    dispatch(changePartPurchasePrice({mockId: props.data.mockId, newPurchasePrice: e.target.value}))
    dispatch(changePartsSum());
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
          <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} onChange={handleChangeQty}/>
          <Form.Control placeholder="Краткое описание" value={props.data.name} onChange={handleNameChange}/>
          <Form.Control type='number' style={{maxWidth: 100}}  placeholder="Цена" min={0} value={props.data.purchasePrice} onChange={handlePurchasePriceChange}/>
          <Form.Select style={{maxWidth: 100}} onChange={handlePecentSelect} defaultValue={getDefaultPercent()}>
            <option value={0} key={0} selected={props.data.percent === 0}>{'0%'}</option>
            <option value={20} key={20} selected={props.data.percent === 20}>{'20%'}</option>
            <option value={30} key={30} selected={props.data.percent === 30}>{'30%'}</option>
          </Form.Select>
          <InputGroup.Text style={{width: 100}}>{props.data.price ? props.data.price : 'Цена'}</InputGroup.Text>
          <Button variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
          {hidePlus() ? null : <Button variant="success" onClick={() => dispatch(addPart())}><i className="fa fa-plus"
                                                                                                aria-hidden="true"></i></Button>}
          <Button variant="primary" onClick={() => dispatch(toggleIsStock(props.data.mockId))}><i
            className="fa fa-exchange"></i></Button>
        </InputGroup>

        <datalist id="partsOptions">
          {orderState.stock.map(s => <option data-value={s.id} key={s.id}>{s.name}</option>)}
        </datalist>
      </div>
  )
};

export default CustomPart;