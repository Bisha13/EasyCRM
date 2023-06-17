import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {deleteStock, fetchStockById, updateStock} from "../../asyncActions/stock";
import {
  changeArticle,
  changeExtra,
  changeName,
  changePrice,
  changePurchase,
  loadRootStock
} from "../../redux/stocks-reducer";

function UpdateStock() {
  const stock = useSelector(state => state.stock.stock)
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {id} = useParams();

  useEffect(() => {
    if (id != 'new') {
      dispatch(fetchStockById(id));
    } else {
      dispatch(loadRootStock({}))
    }
  }, []);

  function getNumber(e) {
    if (Number(e.target.value) > Number(e.target.max)) {
      return e.target.max;
    }
    return e.target.value;
  }

  const routeChange = () =>{
    let path = `/stocks`;
    navigate(path);
  }

  const onChangeArticle = (e) => {
    dispatch(changeArticle(e.target.value));
  }

  const onChangeName = (e) => {
    dispatch(changeName(e.target.value));
  }

  const onChangePurchase = (e) => {
    dispatch(changePurchase(getNumber(e)));
  }

  const onChangePrice = (e) => {
    dispatch(changePrice(getNumber(e)));
  }

 const onChangeExtra = (e) => {
    dispatch(changeExtra(getNumber(e)));
  }

  function onUpdate() {
      dispatch(updateStock(stock))
        .then(el => routeChange())
    dispatch(loadRootStock({}))
  }

  function onDelete() {
    const conf = window.confirm(`Подтверждаете удаление?`);
    if (conf) {
      dispatch(deleteStock(id))
          .then(el => routeChange())
      dispatch(loadRootStock({}))
    }
  }

  return (

    <Container>
      <Card className={"mt-4 mb-4"}>
        <Card.Body>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Артикл:</InputGroup.Text>
            <Form.Control value={stock.article} onChange={onChangeArticle}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Название:</InputGroup.Text>
            <Form.Control value={stock.name} onChange={onChangeName}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Закупка:</InputGroup.Text>
            <Form.Control  min='1' max='10000000' type='number' value={stock.purchase} onChange={onChangePurchase}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Наценка %:</InputGroup.Text>
            <Form.Control  min='0' max='2000' type='number' value={stock.extra} onChange={onChangeExtra}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Цена:</InputGroup.Text>
            <Form.Control min='1' max='10000000' type='number' step='0.01' value={stock.price} onChange={onChangePrice}/>
          </InputGroup>
          <div className="mt-2">
            <Button variant="primary" onClick={onUpdate}>Сохранить</Button>{' '}
            {id === "new" ? null : <Button variant="danger" onClick={onDelete}>Удалить</Button>}
          </div>
        </Card.Body>
      </Card>
    </Container>
  )
}
export default UpdateStock;