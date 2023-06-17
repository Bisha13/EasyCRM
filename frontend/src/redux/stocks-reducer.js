import {createSlice} from '@reduxjs/toolkit'

const stockSlice = createSlice({
  name: 'stock',
  initialState: {
    stocks: [],
    stock: {}
  },
  reducers: {
    loadRootStocks: (state, action) => {
      state.stocks = action.payload;
    },
    loadRootStock: (state, action) => {
      state.stock = action.payload;
    },
    changeArticle: (state, action) => {
      state.stock.article = action.payload;
    },
    changeName: (state, action) => {
      state.stock.name = action.payload;
    },
    changePurchase: (state, action) => {
      state.stock.purchase = action.payload;
      state.stock.price = Number(state.stock.purchase) + Number(state.stock.purchase) / 100 * Number(state.stock.extra);
    },
    changePrice: (state, action) => {
      state.stock.price = action.payload;
      state.stock.extra = '';
    },
    changeExtra: (state, action) => {
      state.stock.extra = action.payload;
      state.stock.price = Number(state.stock.purchase) + Number(state.stock.purchase) / 100 * Number(state.stock.extra);
    },
  }
})

export const {
  changeArticle,
  changeName,
  changePurchase,
  changePrice,
  changeExtra,
  loadRootStocks,
  loadRootStock,
} = stockSlice.actions
export default stockSlice.reducer