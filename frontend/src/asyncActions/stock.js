import {loadStock, toggleStockLoaded} from "../redux/single-order-reducer";
import {newLoadStock, newToggleStockLoaded} from "../redux/new-order-reducer";
import {loadRootStock, loadRootStocks} from "../redux/stocks-reducer";

export const fetchStock = () => {
  return function (dispatch) {
    dispatch(newToggleStockLoaded(false));
    dispatch(toggleStockLoaded(false));
    fetch('/rest/stock/all')
      .then(response => response.json())
      .then(json => {
        dispatch(loadStock(json));
        dispatch(loadRootStocks(json));
        return json;
      })
      .then(json => dispatch(newLoadStock(json)))
      .then(() => dispatch(toggleStockLoaded(true)))
      .then(() => dispatch(newToggleStockLoaded(true)))
  }
}

export const fetchStockById = (id) => {
  return function (dispatch) {
    fetch(`/rest/stock/${id}`)
      .then(response => response.json())
      .then(json => {
        dispatch(loadRootStock(json))
      })
  }
}

export const updateStock = (stock) => {
  return function (dispatch) {
    return fetch(`/rest/stock`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(stock)
    });
  }
}

export const deleteStock = (id) => {
  return function (dispatch) {
    return fetch(`/rest/stock/${id}`, {
      method: 'DELETE',
    });
  }
}
