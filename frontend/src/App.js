import './App.css';
import './components/orderTable/OrderRow';
import MyNavbar from "./components/navbar/MyNavbar";
import SingleOrder from "./components/singleorder/SingleOrder";
import {useDispatch} from "react-redux";
import {fetchStatuses} from "./asyncActions/statuses";
import {fetchWorkers} from "./asyncActions/workers";
import {fetchItems} from "./asyncActions/items";
import AllOrders from "./components/orderTable/AllOrders";
import {Route, Routes} from "react-router-dom";
import {fetchStock} from "./asyncActions/stock";
import NewOrder from "./components/newOrder/NewOrder";
import ClientPage from "./components/clients/ClientPage";
import UpdateDevice from "./components/devices/UpdateDevice";
import AllClients from "./components/clients/clientsTable/AllClients";
import AllDevices from "./components/devices/AllDevices";
import AllItems from "./components/items/AllItems";
import UpdateItem from "./components/items/UpdateItem";
import AllStocks from "./components/stock/AllStocks";
import UpdateStock from "./components/stock/UpdateStock";
import AllStatuses from "./components/statuses/AllStatuses";
import Settings from "./components/Settings";
import AllWorkers from "./components/workers/allWarkers/AllWorkers";
import WorkersPage from "./components/workers/WorkersPage";

function App() {
  const dispatch = useDispatch();
  dispatch(fetchItems());
  dispatch(fetchStatuses());
  dispatch(fetchWorkers());
  dispatch(fetchStock());

  return (<div>
    <MyNavbar/>
    <Routes>
      <Route path='/' element={<AllOrders/>}/>
      <Route path='/order/:id' element={<SingleOrder/>}/>
      <Route path='/devices' element={<AllDevices/>}/>
      <Route path='/devices/:id' element={<UpdateDevice/>}/>
      <Route path='/clients/:id' element={<ClientPage/>}/>
      <Route path='/clients' element={<AllClients/>}/>
      <Route path='/items' element={<AllItems/>}/>
      <Route path='/items/:id' element={<UpdateItem/>}/>
      <Route path='/stocks' element={<AllStocks/>}/>
      <Route path='/stocks/:id' element={<UpdateStock/>}/>
      <Route path='/new_order' element={<NewOrder/>}/>
      <Route path='/statuses' element={<AllStatuses/>}/>
      <Route path='/settings' element={<Settings/>}/>
      <Route path='/workers' element={<AllWorkers/>}/>
      <Route path='/workers/:id' element={<WorkersPage/>}/>
    </Routes>
  </div>)
    ;
}

export default App;
