import {createSlice} from '@reduxjs/toolkit'

const workerSlice = createSlice({
  name: 'worker',
  initialState: {
    worker: {},
    workers: [],
    workerOrders: [],
    totalSum: 0
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
    loadWorkerOrders: (state, action) => {
      state.workerOrders = action.payload.orders;
      state.totalSum = action.payload.totalSum;
    },
  }
})

export const {
  loadWorkerOrders,
  loadMasterWorker,
  loadAllMasterWorkers ,
  changeName,
  changePercent,
  changePhone,
} = workerSlice.actions
export default workerSlice.reducer