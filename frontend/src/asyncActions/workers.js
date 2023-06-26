import {loadWorkers} from "../redux/common-reducer";
import {
    loadAllMasterWorkers,
    loadDoneOrders,
    loadMasterWorker,
    loadPaidOrders
} from "../redux/worker-reducer";

export const fetchWorkers = () => {
    return function (dispatch) {
        fetch('/rest/workers/all')
            .then(response => response.json())
            .then(json => {
                dispatch(loadWorkers(json));
                dispatch(loadAllMasterWorkers(json));
            })
    }
}

export const fetchWorkerById = (id) => {
    return function (dispatch) {
        fetch(`/rest/workers/${id}`)
            .then(response => response.json())
            .then(json => dispatch(loadMasterWorker(json)))
    }
}

export const updateWorker = (worker) => {
    return function (dispatch) {
        return fetch(`/rest/workers`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(worker)
        });
    }
}

export const fetchDoneOrders = (workerId, status) => {
    return function (dispatch) {
        return fetch(`/rest/orders/byUser?userId=${workerId}&status=${status}`)
            .then(response => response.json())
            .then(json => dispatch(loadDoneOrders(json)))
            .then()
    }
}

export const fetchPaidOrders = (workerId, status, year, month) => {
    return function (dispatch) {
        return fetch(`/rest/orders/byUser?userId=${workerId}&status=${status}&year=${year}&month=${month}`)
            .then(response => response.json())
            .then(json => dispatch(loadPaidOrders(json)))
            .then()
    }
}
