import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import Table from "react-bootstrap/Table";
import StockRow from "./StockRow";
import {fetchStock} from "../../asyncActions/stock";


function StockTable() {
  const state = useSelector(state => state.stock)
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(fetchStock());
  }, []);

  return (
    <div>
    <Table striped hover>
      <thead>
      <tr>
        <th scope="col">Id</th>
        <th scope="col">Артикул</th>
        <th scope="col">Название</th>
        <th scope="col">Закупочная цена</th>
        <th scope="col">Наценка %</th>
        <th scope="col">Цена</th>
      </tr>
      </thead>
      <tbody>
      {state.stocks.filter(el => el.id !== 0).map(o => <StockRow data={o} key={o.id}/>)}
      </tbody>
    </Table>
    </div>
  )
}
export default StockTable;