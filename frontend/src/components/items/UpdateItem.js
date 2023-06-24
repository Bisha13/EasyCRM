import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import Card from "react-bootstrap/Card";
import Container from "react-bootstrap/Container";
import {useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {deleteItem, fetchItemById, updateItem} from "../../asyncActions/items";
import {
  changeCategoryId,
  changeDescription,
  changeName,
  changePrice,
  changePriority,
  loadRootItem
} from "../../redux/items-reducer";
import {fetchCategories} from "../../asyncActions/categories";

function UpdateItem() {
  const item = useSelector(state => state.item.item)
  const categories = useSelector(state => state.item.categories)
  const dispatch = useDispatch();
  const {id} = useParams();

  let navigate = useNavigate();
  const routeChange = () => {
    let path = `/items`;
    navigate(path);
  }

  useEffect(() => {
    dispatch(fetchCategories());
    if (id != 'new') {
      dispatch(fetchItemById(id));
    } else {
      dispatch(loadRootItem({categoryId: 1}))
    }
  }, []);

  function getNumber(e) {
    if (Number(e.target.value) > Number(e.target.max)) {
      return e.target.max;
    }
    return e.target.value;
  }

  const onChangeName = (e) => {
    dispatch(changeName(e.target.value));
  }
  const onChangeDescription = (e) => {
    dispatch(changeDescription(e.target.value));
  }
  const onSelectCategoryId = (e) => {
    dispatch(changeCategoryId(e.target.value));
  }
  const onChangePriority = (e) => {
    dispatch(changePriority(getNumber(e)));
  }
  const onChangePrice = (e) => {
    dispatch(changePrice(getNumber(e)));
  }

  const onUpdate = () => {
    dispatch(updateItem(item))
      .then(({status, json}) => {
        if (status >= 400) {
          alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
        } else {
          dispatch(loadRootItem({categoryId: 1}))
          alert("Обновлено");
          routeChange();
        }
      })
  }
  const onDelete = () => {
    const conf = window.confirm(`Подтверждаете удаление?`);
    if (conf) {
      dispatch(deleteItem(item.id)).then(() => {
          dispatch(loadRootItem({categoryId: 1}))
          routeChange();
        }
      );
    }
  }

  return (

    <Container>
      <Card className={"mt-4 mb-4"}>
        <Card.Body>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Название</InputGroup.Text>
            <Form.Control value={item.name} onChange={onChangeName}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Описание</InputGroup.Text>
            <Form.Control as="textarea" value={item.description} onChange={onChangeDescription}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Категория</InputGroup.Text>
            <Form.Select onChange={onSelectCategoryId}>
              {categories.map(d =>
                <option value={d.id} key={d.id}
                        selected={"" + d.id === "" + item.categoryId}>{d.name}</option>)}
            </Form.Select>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Приоритет</InputGroup.Text>
            <Form.Control value={item.priority} type='number' min='0' max='1000' onChange={onChangePriority}/>
          </InputGroup>
          <InputGroup>
            <InputGroup.Text style={{minWidth: 110}}>Цена</InputGroup.Text>
            <Form.Control value={item.price} min='0' max='9999999' type='number' onChange={onChangePrice}/>
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

export default UpdateItem;