import CustomService from "./CustomService";
import DefaultService from "./DefaultService";
import {useSelector} from "react-redux";

function Services() {
  const orderState = useSelector((state) => state.singleOrder);

  let renderElement = () => {

    if (!orderState.isLoaded) {
      return <div>Services Loading</div>
    } else if (orderState.isFetchError) {
      return <div>Не найдено</div>
    } else if (orderState.order.services == null || orderState.order.services.length === 0) {
      return <DefaultService data={{qty: 1, price: 0, itemId: 0, isCustom: false}} dataSize={{index: 0, size: 1}} key={"mockId"}/>
    } else {
      return orderState.order.services.map((s, index) => s.isCustom ?
        <CustomService data={s} dataSize={{index: index, size: orderState.order.services.length}} key={s.mockId}/> :
        <DefaultService data={s} dataSize={{index: index, size: orderState.order.services.length}} key={s.mockId}/>)
    }
  }

  return (
    <div>
      <span>Работы</span>
      {renderElement()}
    </div>
  );
}

export default Services;