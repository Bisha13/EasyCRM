import {newLoadClients, newToggleIsNewClient, selectClient} from "../redux/new-order-reducer";
import {fetchDevicesByClientId, fetchDevicesByClientIdForClient} from "./devices";
import {loadAllClients, loadClient, setClientPageCount, toggleClientPagination} from "../redux/client-reducer";
import {fetchOrdersByClientId} from "./orders";

export const fetchClientsByPhone = (phoneNumber) => {
  return function (dispatch) {
    dispatch(newLoadClients([]));
    fetch(`/rest/clients/by_phone?phone=${phoneNumber}`)
      .then(response => response.json())
      .then(json => {
        dispatch(newLoadClients(json))
        if (json.length === 1) {
          dispatch(selectClient(json[0].id))
          dispatch(fetchDevicesByClientId(json[0].id));
        }
        if (json.length === 0) {
          dispatch(newToggleIsNewClient(true))
        }
      })
  }
}

export const fetchClientById = (id) => {
  return function (dispatch) {
    fetch(`/rest/clients/${id}`)
      .then(response => response.json())
      .then(json => {
        dispatch(loadClient(json))
        return json
      }).then(json => {
        dispatch(fetchOrdersByClientId(json.id));
        dispatch(fetchDevicesByClientIdForClient(json.id));
      }
    )
  }
}

export const updateClient = (client) => {
  return function (dispatch) {
    fetch(`/rest/clients/`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(client)
    }).then(el => el);
  }
}

export const fetchClients = (page) => {
  return function (dispatch) {
    fetch(`/rest/clients?page=${page}`)
      .then(response => response.json())
      .then(json => {
        dispatch(loadAllClients(json.clients));
        dispatch(setClientPageCount(json.pageCount))
        dispatch(toggleClientPagination(false))
      })
  }
}

export const fetchClientsBySearch = (page, search) => {
  return function (dispatch) {
    fetch(`/rest/clients/find?page=${page}&search=${search}`)
      .then(response => response.json())
      .then(json => {
        dispatch(loadAllClients(json.clients));
        dispatch(setClientPageCount(json.pageCount))
        dispatch(toggleClientPagination(true))
      })
  }
}