import {loadOrders} from "../redux/orders-reducer";
import {loadOrder, toggleIsFetchError, toggleIsLoaded} from "../redux/single-order-reducer";
import {loadClientOrders} from "../redux/client-reducer";

export const fetchOrders = (page, status) => {
  return function (dispatch) {
    fetch(`/rest/orders/all?page=${page}&statusId=${status}`)
      .then(response => response.json())
      .then(json => dispatch(loadOrders(json)))
  }
}

export const fetchOrdersByClientId = (clientId) => {
  return function (dispatch) {
    fetch(`/rest/orders?clientId=${clientId}`)
      .then(response => response.json())
      .then(json => dispatch(loadClientOrders(json)))
  }
}

export const fetchSingleOrder = (id) => {
  return function (dispatch) {
    dispatch(toggleIsLoaded(false));
    fetch(`/rest/orders/${id}`)
      .then(response =>
        response.json().then(json => ({
            status: response.status,
            json
          })
        ))
      .then(({status, json}) => {
        if (status >= 400) {
          dispatch(toggleIsFetchError(true));
          dispatch(toggleIsLoaded(true));
          return;
        }
        dispatch(loadOrder(json));
        dispatch(toggleIsFetchError(false));
        dispatch(toggleIsLoaded(true))
      });
  }
}

export const updateSingleOrder = (order) => {
  return function (dispatch) {
    return fetch(`/rest/orders/${order.id}`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(order)
    })
  }
}

export const closeOrder = (order) => {
  return function (dispatch) {
    return fetch(`/rest/orders/close`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(order)
    })
  }
}

export const finishOrder = (order) => {
  return function (dispatch) {
    return fetch(`/rest/orders/readyForCustomer`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(order)
    })
  }
}

export const createNewOrder = (order) => {
  return function (dispatch) {
    return fetch(`/rest/orders/new`, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(order)
    })
  }
}