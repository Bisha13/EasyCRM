import {newLoadDevices, newSelectDevice, newToggleIsNewDevice} from "../redux/new-order-reducer";
import {loadClientDevices} from "../redux/client-reducer";
import {loadDevice, loadDevices, setDevicesPageCount} from "../redux/device-reducer";
import {fetchClientById} from "./clients";

export const fetchDevicesByClientId = (clientId) => {
  return function (dispatch) {
    fetch(`/rest/devices/by_client?client=${clientId}`)
      .then(response => response.json())
      .then(json => {
        dispatch(newLoadDevices(json))
        if (json.length > 0) {
          dispatch(newSelectDevice(json[0].id))
          dispatch(newToggleIsNewDevice(false))
        }
      })
  }
}

export const fetchDevicesByClientIdForClient = (clientId) => {
  return function (dispatch) {
    fetch(`/rest/devices/by_client?client=${clientId}`)
      .then(response => response.json())
      .then(json => {
        dispatch(loadClientDevices(json))
      })
  }
}

export const fetchDeviceAndClientById = (id) => {
  return function (dispatch) {
    fetch(`/rest/devices/${id}`)
      .then(response => response.json())
      .then(json => {
        dispatch(loadDevice(json))
        return json;
      })
      .then(json => {
        dispatch(fetchClientById(json.clientId))
      })
  }
}

export const updateDevice = (device) => {
  return function (dispatch) {
    fetch(`/rest/devices`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(device)
    }).then(el => el);
  }
}

export const fetchAllDevices = (page) => {
  return function (dispatch) {
    fetch(`/rest/devices/all?page=${page}`)
        .then(response => response.json())
        .then(json => {
          dispatch(loadDevices(json.devices))
          dispatch(setDevicesPageCount(json.pageCount))
        })
  }
}
