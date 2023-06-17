import { createSlice } from '@reduxjs/toolkit'

const ordersSlice = createSlice({
  name: 'orders',
  initialState: {
    orders: [],
    statuses: [],
    text: "",
    selectedStatus: "0",
    activePage: 1,
    pageCount: 10,
    orderToSearch: ""
  },
  reducers: {
    loadOrders: (state, action) => {
      state.orders = action.payload.orders;
      state.pageCount = action.payload.pageCount;
    },
    loadStatuses: (state, action) => {
      state.statuses = action.payload;
    },
    setActivePage: (state, action) => {
      state.activePage = action.payload;
    },
    updateOrderToSearch: (state, action) => {
      state.orderToSearch = action.payload;
    },
  }
})

export const {updateOrderToSearch,
  loadOrders,
  setActivePage} = ordersSlice.actions
export default ordersSlice.reducer