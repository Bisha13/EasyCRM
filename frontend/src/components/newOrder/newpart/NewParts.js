import {useSelector} from "react-redux";
import NewStockPart from "./NewStockPart";
import NewCustomPart from "./NewCustomPart";

function NewParts() {

  const orderState = useSelector((state) => state.newOrder);
  function render() {
    if (orderState.order.parts == null || orderState.order.parts.length === 0) {
      return <NewStockPart data={{qty: 1, price: 0, stockId: 0, isStock: true}} dataSize={{index: 0, size: 1}} key={"mockId"}/>
    } else {
      return orderState.order.parts.map((p, index) =>
          p.isStock ?
            <NewStockPart data={p} dataSize={{index: index, size: orderState.order.parts.length}} key={p.partId}/> :
            <NewCustomPart data={p} dataSize={{index: index, size: orderState.order.parts.length}} key={p.partId}/>)
    }
  }


  return (
    <div>
      <span>Запчасти</span>
      { render() }
    </div>
  );
}

export default NewParts;