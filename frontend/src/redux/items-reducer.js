import {createSlice} from '@reduxjs/toolkit'

const itemSlice = createSlice({
  name: 'item',
  initialState: {
    items: [],
    categories: [],
    item: {"categoryId": '1'}
  },
  reducers: {
    loadCategories: (state, action) => {
      state.categories = action.payload;
    },
    loadRootItems: (state, action) => {
      state.items = action.payload;
    },
    loadRootItem: (state, action) => {
      state.item = action.payload;
    },
    changeName: (state, action) => {
      state.item.name = action.payload;
    },
    changeDescription: (state, action) => {
      state.item.description = action.payload;
    },
    changeCategoryId: (state, action) => {
      state.item.categoryId = action.payload;
    },
    changePriority: (state, action) => {
      state.item.priority = action.payload;
    },
    changePrice: (state, action) => {
      state.item.price = action.payload;
    },
  }
})

export const {
  loadCategories,
  loadRootItems,
  loadRootItem,
  changeName,
  changeDescription,
  changeCategoryId,
  changePriority,
  changePrice,
} = itemSlice.actions
export default itemSlice.reducer