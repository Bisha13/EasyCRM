import {loadItems, toggleItemsLoaded} from "../redux/single-order-reducer";
import {newLoadItems, newToggleItemsLoaded} from "../redux/new-order-reducer";
import {loadRootItem, loadRootItems} from "../redux/items-reducer";

export const fetchItems = () => {
  return function (dispatch) {
    dispatch(toggleItemsLoaded(false));
    dispatch(newToggleItemsLoaded(false));
    fetch('/rest/items/all')
      .then(response => response.json())
      .then(json => {
        dispatch(loadItems(json));
        dispatch(newLoadItems(json));
        dispatch(loadRootItems(json));
        return json;
      })
      .then(() => dispatch(toggleItemsLoaded(true)))
      .then(() => dispatch(newToggleItemsLoaded(true)))
  }
}

export const fetchItemById = (id) => {
    return function (dispatch) {
        fetch(`/rest/items/${id}`)
            .then(response => response.json())
            .then(json => {
                dispatch(loadRootItem(json))
            })
    }
}

export const updateItem = (item) => {
    return function (dispatch) {
        return fetch(`/rest/items`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(item)
        });
    }
}

export const deleteItem = (id) => {
    return function (dispatch) {
        return fetch(`/rest/items/${id}`, {
            method: 'DELETE',
        });
    }
}