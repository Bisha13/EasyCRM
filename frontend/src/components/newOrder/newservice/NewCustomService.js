import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import {
  newAddService,
  newChangeDescription,
  newChangePrice,
  newChangeQty,
  newChangeServicesSum, newRemoveService, newToggleIsCustom
} from "../../../redux/new-order-reducer";

function NewCustomService(props) {

  const dispatch = useDispatch();
  const newOrder = useSelector((state) => state.newOrder);

  function getNumber(e) {
    if (Number(e.target.value) > Number(e.target.max)) {
      return e.target.max;
    }
    return e.target.value;
  }

  let handleChangeDescription = (e) => {
    dispatch(newChangeDescription({mockId: props.data.mockId, newDescription: e.target.value}))
  }
  let handlePriceChange = (e) => {
    dispatch(newChangePrice({mockId: props.data.mockId, newPrice: getNumber(e)}))
    dispatch(newChangeServicesSum());
  }

  let handleChangeQty = (e) => {
    dispatch(newChangeQty({mockId: props.data.mockId, qty: getNumber(e)}))
    dispatch(newChangeServicesSum());
  }

  let handleRemove = (e) => {
    dispatch(newRemoveService(props.data.mockId))
    dispatch(newChangeServicesSum());
  }

  function hidePlus() {
    return (props.dataSize.index !== props.dataSize.size - 1);
  }

  return (!newOrder.isItemsLoaded ? null :
    <div>
    <InputGroup className="mb-3">
      <Form.Control style={{maxWidth: 100}} type="number" value={props.data.qty} onChange={handleChangeQty} min={1} max={10000000}/>
      <Form.Control placeholder="Краткое описание" value={props.data.description} onChange={handleChangeDescription}/>
      <Form.Control style={{maxWidth: 100}} className="w-10" type="number" placeholder="Цена" min={0} max={10000000} value={props.data.price} onChange={handlePriceChange}/>
      <Button variant="danger" onClick={handleRemove}><i aria-hidden="true" className="fa fa-minus float-right"></i></Button>
      { hidePlus() ? null : <Button variant="success" onClick={() => dispatch(newAddService())}><i className="fa fa-plus" aria-hidden="true"></i></Button>}
      <Button variant="primary" onClick={() => dispatch(newToggleIsCustom(props.data.mockId))}><i className="fa fa-exchange"></i></Button>
    </InputGroup>
  </div>);
}

export default NewCustomService;