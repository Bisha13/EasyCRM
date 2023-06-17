import NewCustomService from "./NewCustomService";
import NewDefaultService from "./NewDefaultService";
import {useSelector} from "react-redux";

function NewServices() {
  const state = useSelector((state) => state.newOrder);

  let renderElement = () => {

    if (state.order.services == null || state.order.services.length === 0) {
      return <NewDefaultService data={{qty: 1, price: 0, itemId: 0, isCustom: false}} dataSize={{index: 0, size: 1}} key={"mockId"}/>
    } else {
      return state.order.services.map((s, index) => s.isCustom ?
        <NewCustomService data={s} dataSize={{index: index, size: state.order.services.length}} key={s.mockId}/> :
        <NewDefaultService data={s} dataSize={{index: index, size: state.order.services.length}} key={s.mockId}/>)
    }
  }

  return (
    <div>
      <span>Работы</span>
      {renderElement()}
    </div>
  );
}

export default NewServices;