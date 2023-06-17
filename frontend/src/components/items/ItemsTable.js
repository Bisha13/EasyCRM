import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import Table from "react-bootstrap/Table";
import {fetchItems} from "../../asyncActions/items";
import ItemRow from "./ItemRow";
import {fetchCategories} from "../../asyncActions/categories";

function ItemsTable() {
  const state = useSelector(state => state.item)
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchCategories());
    dispatch(fetchItems());
  }, []);

  return (
    <div>
    <Table striped hover>
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Название</th>
        <th scope="col">Описание</th>
        <th scope="col">Категория</th>
        <th scope="col">Приоритет</th>
        <th scope="col">Цена</th>
      </tr>
      </thead>
      <tbody>
      {state.items.filter(el => el.id !== '0').map(o => <ItemRow data={o} key={o.id}/>)}
      </tbody>
    </Table>
    </div>
  )
}
export default ItemsTable;