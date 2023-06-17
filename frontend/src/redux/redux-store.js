import {configureStore} from "@reduxjs/toolkit";
import ordersReducer from "./orders-reducer";
import singleOrderReducer from "./single-order-reducer";
import commonReducer from "./common-reducer";
import newOrderReducer from "./new-order-reducer";
import clientReducer from "./client-reducer";
import deviceReducer from "./device-reducer";
import itemReducer from "./items-reducer";
import stockReducer from "./stocks-reducer";
import workerReducer from "./worker-reducer";

export default configureStore({
  reducer: {
    orders: ordersReducer,
    singleOrder: singleOrderReducer,
    newOrder: newOrderReducer,
    common: commonReducer,
    client: clientReducer,
    device: deviceReducer,
    item: itemReducer,
    stock: stockReducer,
    worker: workerReducer,
  },
})
