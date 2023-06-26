import {createSlice} from '@reduxjs/toolkit'

const workerSlice = createSlice({
  name: 'worker',
  initialState: {
    worker: {},
    workers: [],
    doneOrders: [],
    doneTotalSum: 0,
    paidOrders: [],
    paidTotalSum: 0
  },
  reducers: {
    loadMasterWorker: (state, action) => {
      state.worker = action.payload;
    },
    loadTotalSum: (state, action) => {
      state.totalPrice = action.payload;
    },
    loadAllMasterWorkers: (state, action) => {
      state.workers = action.payload.workers;
    },
    changeName: (state, action) => {
      state.worker.name = action.payload;
    },
    changePhone: (state, action) => {
      state.worker.phone = action.payload;
    },
    changePercent: (state, action) => {
      state.worker.percent = action.payload;
    },
    loadDoneOrders: (state, action) => {
      state.doneOrders = action.payload.orders;
      state.doneTotalSum = action.payload.totalSum;
    },
    loadPaidOrders: (state, action) => {
      state.paidOrders = action.payload.orders;
      state.paidTotalSum = action.payload.totalSum;
    },
  }
})

export const {
  loadDoneOrders,
  loadPaidOrders,
  loadMasterWorker,
  loadAllMasterWorkers ,
  changeName,
  changePercent,
  changePhone,
} = workerSlice.actions
export default workerSlice.reducer