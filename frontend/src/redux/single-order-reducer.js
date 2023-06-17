import {createSlice} from '@reduxjs/toolkit'
import {shallowEqual} from "react-redux";

const singleOrderSlice = createSlice({
  name: 'singleOrder',
  initialState: {
    order: {
      services: [],
      parts: [{isStock: true}],
    },
    servicesSum: 0,
    partsSum: 0,
    totalSum: 0,
    isCustom: true,
    isLoaded: false,
    isFetchError: false,
    items: [],
    stock: [],
    isItemsLoaded: false,
    isStockLoaded: false,
  },
  reducers: {
    loadOrder: (state, action) => {
      state.order = action.payload;
      if (state.order.services.length === 0) {
        state.order.services = [{qty: 1, isCustom: false, itemId: '0'}]
      }
      if (state.order.parts.length === 0) {
        state.order.parts = [{isStock: true, qty: 1, stockId: 0}]
      }
      state.order.services = state.order.services.map(el => ({...el, mockId: (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2)}))
      state.order.parts = state.order.parts
        .map(el => ({...el,
          mockId: (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2)
        }))
        .map(el => {
          if (!el.isStock) { return {
              ...el,
              percent: 100 * el.price  / el.purchasePrice - 100
            }
          } else {
            return {...el}
          }
        })
    },
    loadStock: (state, action) => {
      state.stock = action.payload;
    },
    loadItems: (state, action) => {
      state.items = action.payload;
    },
    toggleItemsLoaded: (state, payload) => {
      state.isItemsLoaded = payload;
    },
    toggleStockLoaded: (state, payload) => {
      state.isStockLoaded = payload;
    },
    changeSmallDescription: (state, action) => {
      state.order.smallDescription = action.payload;
    },
    changeServicesSum: (state) => {
      let customServicesSum = state.order.services
        .filter(el => el.isCustom)
        .map(el => el.price * el.qty)
        .reduce((partialSum, a) => partialSum + a, 0);

      let defaultServicesSum = state.order.services
        .filter(el => !el.isCustom)
        .map(el => el.qty * state.items.find(i => ''+i.id === ''+el.itemId).price)
        .reduce((partialSum, a) => partialSum + a, 0);
      if (state.order.clientDiscount) {
        defaultServicesSum = defaultServicesSum/100 * (100 - state.order.clientDiscount);
        customServicesSum = customServicesSum/100 * (100 - state.order.clientDiscount);
      }

      state.servicesSum = defaultServicesSum + customServicesSum;
      state.totalSum = defaultServicesSum + customServicesSum + state.partsSum;
    },
    changePartsSum: (state) => {
      let customPartsSum = state.order.parts
        .filter(el => !el.isStock)
        .map(el => el.price * el.qty)
        .reduce((partialSum, a) => partialSum + a, 0);


      let stockPartsSum = state.order.parts
        .filter(el => el.isStock)
        .map(el => el.qty * state.stock.find(i => ''+i.id === ''+el.stockId).price)
        .reduce((partialSum, a) => partialSum + a, 0);
       state.partsSum = customPartsSum + stockPartsSum;
       state.totalSum = customPartsSum + stockPartsSum + state.servicesSum;
       console.log('customPartsSum ' + customPartsSum)
       console.log('stockPartsSum ' + stockPartsSum)
    },
    changeAllExecutors: (state, action) => {
      state.order.services.forEach(s => s.executorId = action.payload);
    },
    toggleIsCustom: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload);
      service.isCustom = !service.isCustom;
    },
    toggleIsStock: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload);
      part.isStock = !part.isStock;
    },
    changePartName: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.name = action.payload.newName;
    },
    changePartPurchasePrice: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.purchasePrice = action.payload.newPurchasePrice;

      let purchasePrice = part.purchasePrice ? Number(part.purchasePrice) : 0;
      let percent = part.percent ? Number(part.percent) : 0;
      part.price = purchasePrice / 100 * (100 + percent);
    },
    changeQty: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.qty = action.payload.qty;
    },
    changePartQty: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.qty = action.payload.qty;
    },
    changeDescription: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.description = action.payload.newDescription;
    },
    changePrice: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.price = action.payload.newPrice;
    },
    selectItem: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.itemId = action.payload.newItemId
    },
    selectStock: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.stockId = action.payload.newStockId
    },
    selectPercentValue: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.percent = action.payload.newPercent

      let purchasePrice = part.purchasePrice ? Number(part.purchasePrice) : 0;
      let percent = part.percent ? Number(part.percent) : 0;
      part.price = purchasePrice / 100 * (100 + percent);
    },
    changePartPrice: (state, action) => {
      let part = state.order.parts.find(el => shallowEqual(el, action.payload.part));
      part.price = action.payload.newPrice
    },
    selectExecutor: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.executorId = action.payload.executorId;
    },
    loadStatuses: (state, action) => {
      state.statuses = action.payload;
    },
    toggleIsLoaded: (state, action) => {
      state.isLoaded = action.payload;
    },
    toggleIsFetchError: (state, action) => {
      state.isFetchError = action.payload;
    },
    changeFullDescription: (state, action) => {
      state.order.fullDescription = action.payload;
    },
    addService: (state) => {
      const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
      state.order.services.push({mockId: mockId, qty: 1, isCustom: false, itemId: 0});
    },
    removeService: (state, action) => {
      let newServices = state.order.services.filter(el => el.mockId !== action.payload);
      if (newServices.length === 0) {
        const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
        newServices = [{mockId: mockId, qty: 1, isCustom: false, itemId: 0}]
      }
      state.order.services = newServices;
    },
    addPart: (state) => {
      const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
      state.order.parts.push({mockId: mockId, qty: 1, isStock: true, stockId: 0});
    },
    removePart: (state, action) => {
      let newParts = state.order.parts.filter(el => el.mockId !== action.payload);
      if (newParts.length === 0) {
        const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
        newParts = [{mockId: mockId, qty: 1, isStock: true, stockId: 0}]
      }
      state.order.parts = newParts;
    },
    selectStatus: (state, action) => {
      let originalStatus = state.order.statusId;
      let status = action.payload;
      if (originalStatus !== '10' && status === '10') {
        state.order.services.filter(el => el.status !== 'PAID').map(el => el.status = 'DONE')
      }
      if (originalStatus === '10' && status !== '10') {
        state.order.services.filter(el => el.status !== 'PAID').map(el => el.status = 'NEW')
      }
      state.order.statusId = action.payload;
    }
  }
})

export const {
  selectStatus,
  changePartsSum,
  loadStock,
  loadItems,
  toggleItemsLoaded,
  toggleStockLoaded,
  changeServicesSum,
  changeFullDescription,
  selectPercentValue,
  changePartPurchasePrice,
  changePartName,
  toggleIsStock,
  addPart,
  removePart,
  selectStock,
  changePartQty,
  changeQty,
  toggleIsFetchError,
  changeAllExecutors,
  changeSmallDescription,
  changePrice,
  changeDescription,
  addService,
  loadOrder,
  removeService,
  toggleIsCustom,
  toggleIsLoaded,
  selectExecutor,
  selectItem
} = singleOrderSlice.actions
export default singleOrderSlice.reducer