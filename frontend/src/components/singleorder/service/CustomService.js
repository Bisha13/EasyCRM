import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  addService,
  changeDescription,
  changePrice, changeQty, changeServicesSum, removeService,
  selectExecutor,
  toggleIsCustom
} from "../../../redux/single-order-reducer";

function CustomService(props) {

  const dispatch = useDispatch();
  const common = useSelector((state) => state.common);
  const orderState = useSelector((state) => state.singleOrder);

  let handleChangeDescription = (e) => {
    dispatch(changeDescription({mockId: props.data.mockId, newDescription: e.target.value}))
  }
  let handlePriceChange = (e) => {
    dispatch(changePrice({mockId: props.data.mockId, newPrice: e.target.value}))
    dispatch(changeServicesSum());
  }

  let handleChangeQty = (e) => {
    dispatch(changeQty({mockId: props.data.mockId, qty: e.target.value}))
    dispatch(changeServicesSum());
  }

  let handleExecutorSelect = (e) => {
    dispatch(selectExecutor({mockId: props.data.mockId, executorId: e.target.value}))
  }

  let handleRemove = (e) => {
    dispatch(removeService(props.data.mockId))
    dispatch(changeServicesSum());
  }

  function hidePlus() {
    return (props.dataSize.index !== props.dataSize.size - 1);
  }

  return (<div>
    <InputGroup className="mb-3">
      <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} onChange={handleChangeQty} min={1} disabled={props.data.status === 'PAID'}/>
      <Form.Control placeholder="Краткое описание" value={props.data.description} onChange={handleChangeDescription} disabled={props.data.status === 'PAID'}/>
      <Form.Control style={{maxWidth: 100}} className="w-10" type="number" placeholder="Цена" min={0} value={props.data.price} onChange={handlePriceChange} disabled={props.data.status === 'PAID'}/>
      <Form.Select style={{maxWidth: 200}} onChange={handleExecutorSelect} disabled={props.data.status === 'PAID'}>
        {common.workers.map(s => <option value={s.id} selected={props.data.executorId === s.id} key={s.id}>{s.name}</option>)}
      </Form.Select>
      <Button  disabled={props.data.status === 'PAID'} variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
      { hidePlus() ? null : <Button disabled={props.data.status === 'PAID'} variant="success" onClick={() => dispatch(addService())}><i className="fa fa-plus" aria-hidden="true"></i></Button>}
      <Button  disabled={props.data.status === 'PAID'} variant="primary" onClick={() => dispatch(toggleIsCustom(props.data.mockId))}><i className="fa fa-exchange"></i></Button>
    </InputGroup>
  </div>);
}

export default CustomService;