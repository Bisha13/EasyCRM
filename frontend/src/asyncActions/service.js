import {fetchDoneOrders} from "./workers";

export const updateStatuses = (request, id) => {
    return function (dispatch) {
        return fetch(`/rest/services/updateStatus`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(request)
        }).then(({status, json}) => {
            if (status >= 400) {
                alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
            }
            alert("Готово!")
            dispatch(fetchDoneOrders(id, "DONE"))
        });
    }
}
