import {createSlice} from '@reduxjs/toolkit'

const commonSlice = createSlice({
  name: 'device',
  initialState: {
    devices: [{}],
    device: {},
    activePage: 1,
    pageCount: 1
  },
  reducers: {
    loadDevices: (state, action) => {
      state.devices = action.payload;
    },
    loadDevice: (state, action) => {
      state.device = action.payload;
    },
    changeName: (state, action) => {
      state.device.name = action.payload;
    },
    changeDescription: (state, action) => {
      state.device.description = action.payload;
    },
    setDevicesActivePage: (state, action) => {
      state.activePage = action.payload;
    },
    setDevicesPageCount: (state, action) => {
      state.pageCount = action.payload;
    }
  }
})

export const {
  setDevicesPageCount,
  setDevicesActivePage,
  loadDevices,
  loadDevice,
  changeName,
  changeDescription,
} = commonSlice.actions
export default commonSlice.reducer