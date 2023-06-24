import {loadStatuses} from "../redux/common-reducer";

export const fetchStatuses = () => {
  return function (dispatch) {
    fetch('/rest/status/all')
      .then(response => response.json())
      .then(json => dispatch(loadStatuses(json)))
  }
}

export const updateStatuses = (statuses) => {
  return function (dispatch) {
    return fetch(`/rest/status`, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(statuses)
    });
  }
}

