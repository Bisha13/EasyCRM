import {createSlice} from '@reduxjs/toolkit'

const commonSlice = createSlice({
  name: 'client',
  initialState: {
    client: {},
    clients: [],
    devices: [],
    orders: [],
    activePage: 1,
    pageCount: 1,
    searchString: '',
    isClientSearchPagination: false,
  },
  reducers: {
    loadClient: (state, action) => {
      state.client = action.payload;
    },
    loadAllClients: (state, action) => {
      state.clients = action.payload;
    },
    loadClientOrders: (state, action) => {
      state.orders = action.payload;
    },
    loadClientDevices: (state, action) => {
      state.devices = action.payload;
    },
    changeName: (state, action) => {
      state.client.name = action.payload;
    },
    changePhone: (state, action) => {
      state.client.phone = action.payload;
    },
    changePhone2: (state, action) => {
      state.client.phone2 = action.payload;
    },
    changeAddress: (state, action) => {
      state.client.address = action.payload;
    },
    changeDiscount: (state, action) => {
      state.client.discount = action.payload;
    },
    changeSearchString: (state, action) => {
      state.searchString = action.payload;
    },
    setActiveClientPage: (state, action) => {
      state.activePage = action.payload;
    },
    setClientPageCount: (state, action) => {
      state.pageCount = action.payload;
    },
    toggleClientPagination: (state, action) => {
      state.isClientSearchPagination = action.payload;
    },
  }
})

export const {
  toggleClientPagination,
  changeSearchString,
  setClientPageCount,
  setActiveClientPage,
  loadAllClients,
  loadClientDevices,
  loadClientOrders,
  changeName,
  changePhone,
  changePhone2,
  changeAddress,
  changeDiscount,
  loadClient} = commonSlice.actions
export default commonSlice.reducer