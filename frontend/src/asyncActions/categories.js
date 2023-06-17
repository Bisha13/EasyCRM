import {loadCategories} from "../redux/items-reducer";

export const fetchCategories = () => {
  return function (dispatch) {
    fetch('/rest/categories/all')
      .then(response => response.json())
      .then(json => {
        dispatch(loadCategories(json));
      })
  }
}