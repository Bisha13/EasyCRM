import {createSlice} from '@reduxjs/toolkit'

const commonSlice = createSlice({
  name: 'common',
  initialState: {
    statuses: [],
    workers: [],
    selectedStatus: "0",
  },
  reducers: {
    loadStatuses: (state, action) => {
      state.statuses = action.payload;
    },
    loadWorkers: (state, action) => {
      state.workers = action.payload.workers;
    },
    selectStatus: (state, action) => {
      state.selectedStatus = action.payload;
    },
    toggleHide: (state, action) => {
      const status = state.statuses.find(el => ''+el.id === ''+action.payload);
      status.hide = !status.hide;
    },
    changeStatusName: (state, action) => {
      const status = state.statuses.find(el => ''+el.id === ''+action.payload.id);
      status.name = action.payload.newName;
    },
    changeStatusColor: (state, action) => {
      const status = state.statuses.find(el => ''+el.id === ''+action.payload.id);
      status.colour = action.payload.newColor;
    },
  }
})

export const {
  changeStatusColor,
  changeStatusName,
  toggleHide,
  loadStatuses,
  loadWorkers,
  selectStatus} = commonSlice.actions
export default commonSlice.reducer