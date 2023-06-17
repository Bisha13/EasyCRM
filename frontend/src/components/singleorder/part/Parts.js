import {useSelector} from "react-redux";
import StockPart from "./StockPart";
import CustomPart from "./CustomPart";

function Parts() {

  const orderState = useSelector((state) => state.singleOrder);
  function render() {
    if (!orderState.isLoaded) {
      return <div>Parts Loading</div>
    } else if (orderState.isFetchError) {
      return <div>Не найдено</div>
    } else if (orderState.order.parts == null || orderState.order.parts.length === 0) {
      return <StockPart data={{qty: 1, price: 0, stockId: 0, isStock: true}} dataSize={{index: 0, size: 1}} key={"mockId"}/>
    } else {
      return !orderState.isLoaded ? <div>Parts Loading</div> :
        orderState.order.parts.map((p, index) =>
          p.isStock ?
            <StockPart data={p} dataSize={{index: index, size: orderState.order.parts.length}} key={p.partId}/> :
            <CustomPart data={p} dataSize={{index: index, size: orderState.order.parts.length}} key={p.partId}/>)
    }
  }


  return (
    <div>
      <span>Запчасти</span>
      { render() }
    </div>
  );
}

export default Parts;