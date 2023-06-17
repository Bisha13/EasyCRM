import {createSlice} from '@reduxjs/toolkit'
import {shallowEqual} from "react-redux";


const newOrderSlice = createSlice({
  name: 'newOrder',
  initialState: {
    order: {
      services: [{
        qty: 1,
        isCustom: false,
        itemId: '0',
        mockId: (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2)
      }],
      parts: [{
        isStock: true,
        qty: 1,
        stockId: 0,
        mockId: (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2)
      }],
      isNewDevice: true,
      isNewClient: false,
      deviceName: '',
      deviceDescription: '',
      clientName: '',
      clientPhone: '',
    },
    devices: [],
    servicesSum: 0,
    partsSum: 0,
    totalSum: 0,
    isCustom: true,
    isLoaded: false,
    isFetchError: false,
    items: [],
    stock: [],
    clients: [],
    isItemsLoaded: false,
    isStockLoaded: false,
  },
  reducers: {
    newLoadStock: (state, action) => {
      state.stock = action.payload;
    },
    newLoadItems: (state, action) => {
      state.items = action.payload;
    },
    newToggleItemsLoaded: (state, action) => {
      state.isItemsLoaded = action.payload;
    },
    newToggleStockLoaded: (state, payload) => {
      state.isStockLoaded = payload;
    },
    newSelectDevice: (state, action) => {
      state.order.deviceId = action.payload;
    },
    newLoadClients: (state, action) => {
      let clients = action.payload;
      state.clients = clients;
      if (clients.length === 1) {
        state.order.clientName = clients[0].name;
        state.order.clientPhone = clients[0].phone;
      }
    },
    newLoadDevices: (state, action) => {
      state.devices = action.payload;
      if (state.devices.length > 0) {
        state.order.deviceId = state.devices[0].id;
      }
    },
    changeClientName: (state, action) => {
      state.order.clientName = action.payload;
    },
    changeClientPhone: (state, action) => {
      state.order.clientPhone = action.payload;
    },
    selectClient: (state, action) => {
      state.order.clientId = action.payload;
      let client = state.clients.find(el => el.id === action.payload);
      state.order.clientName = client.name;
      state.order.clientPhone = client.phone;
    },
    newChangeSmallDescription: (state, action) => {
      state.order.smallDescription = action.payload;
    },
    newChangeServicesSum: (state) => {
      let customServicesSum = state.order.services
        .filter(el => el.isCustom)
        .map(el => el.price * el.qty)
        .reduce((partialSum, a) => partialSum + a, 0);

      let defaultServicesSum = state.order.services
        .filter(el => !el.isCustom)
        .map(el => el.qty * state.items.find(i => '' + i.id === '' + el.itemId).price)
        .reduce((partialSum, a) => partialSum + a, 0);
      if (state.order.clientDiscount) {
        defaultServicesSum = defaultServicesSum / 100 * (100 - state.order.clientDiscount);
      }

      state.servicesSum = defaultServicesSum + customServicesSum;
      state.totalSum = defaultServicesSum + customServicesSum + state.partsSum;
    },
    newChangePartsSum: (state) => {
      let customPartsSum = state.order.parts
        .filter(el => !el.isStock)
        .map(el => el.price * el.qty)
        .reduce((partialSum, a) => partialSum + a, 0);
      let stockPartsSum = state.order.parts
        .filter(el => el.isStock)
        .map(el => el.qty * state.stock.find(i => '' + i.id === '' + el.stockId).price)
        .reduce((partialSum, a) => partialSum + a, 0);
      state.partsSum = customPartsSum + stockPartsSum;
      state.totalSum = customPartsSum + stockPartsSum + state.servicesSum;
      console.log('customPartsSum ' + customPartsSum)
      console.log('stockPartsSum ' + stockPartsSum)
    },
    newToggleIsCustom: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload);
      service.isCustom = !service.isCustom;
    },
    newToggleIsStock: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload);
      part.isStock = !part.isStock;
    },
    newChangePartName: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.name = action.payload.newName;
    },
    newChangePartPurchasePrice: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.purchasePrice = action.payload.newPurchasePrice;

      let purchasePrice = part.purchasePrice ? Number(part.purchasePrice) : 0;
      let percent = part.percent ? Number(part.percent) : 0;
      part.price = purchasePrice / 100 * (100 + percent);
    },
    newChangeQty: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.qty = action.payload.qty;
    },
    newChangePartQty: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.qty = action.payload.qty;
    },
    newChangeDescription: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.description = action.payload.newDescription;
    },
    newChangePrice: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.price = action.payload.newPrice;
    },
    newSelectItem: (state, action) => {
      let service = state.order.services.find(el => el.mockId === action.payload.mockId);
      service.itemId = action.payload.newItemId
    },
    newSelectStock: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.stockId = action.payload.newStockId
    },
    newSelectPercentValue: (state, action) => {
      let part = state.order.parts.find(el => el.mockId === action.payload.mockId);
      part.percent = action.payload.newPercent

      let purchasePrice = part.purchasePrice ? Number(part.purchasePrice) : 0;
      let percent = part.percent ? Number(part.percent) : 0;
      part.price = purchasePrice / 100 * (100 + percent);
    },
    newChangePartPrice: (state, action) => {
      let part = state.order.parts.find(el => shallowEqual(el, action.payload.part));
      part.price = action.payload.newPrice
    },
    newLoadStatuses: (state, action) => {
      state.statuses = action.payload;
    },
    newToggleIsLoaded: (state, action) => {
      state.isLoaded = action.payload;
    },
    newToggleIsNewDevice: (state, action) => {
      state.order.isNewDevice = action.payload;
    },
    newToggleIsNewClient: (state, action) => {
      state.order.isNewClient = action.payload;
    },
    newToggleIsFetchError: (state, action) => {
      state.isFetchError = action.payload;
    },
    newChangeFullDescription: (state, action) => {
      state.order.fullDescription = action.payload;
    },
    newChangeDeviceName: (state, action) => {
      state.order.deviceName = action.payload;
    },
    newChangeDeviceDescription: (state, action) => {
      state.order.deviceDescription = action.payload;
    },
    newAddService: (state) => {
      const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
      state.order.services.push({mockId: mockId, qty: 1, isCustom: false, itemId: 0});
    },
    newRemoveService: (state, action) => {
      let newServices = state.order.services.filter(el => el.mockId !== action.payload);
      if (newServices.length === 0) {
        const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
        newServices = [{mockId: mockId, qty: 1, isCustom: false, itemId: 0}]
      }
      state.order.services = newServices;
    },
    newAddPart: (state) => {
      const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
      state.order.parts.push({mockId: mockId, qty: 1, isStock: true, stockId: 0});
    },
    newRemovePart: (state, action) => {
      let newParts = state.order.parts.filter(el => el.mockId !== action.payload);
      if (newParts.length === 0) {
        const mockId = (new Date()).getTime().toString(36) + Math.random().toString(36).slice(2);
        newParts = [{mockId: mockId, qty: 1, isStock: true, stockId: 0}]
      }
      state.order.parts = newParts;
    }
  }
})

export const {
  newChangeDeviceName,
  newChangeDeviceDescription,
  newToggleIsNewClient,
  newToggleIsNewDevice,
  newLoadDevices,
  selectClient,
  changeClientName,
  changeClientPhone,
  newLoadClients,
  newLoadStock,
  newLoadItems,
  newToggleItemsLoaded,
  newToggleStockLoaded,
  newSelectDevice,
  newChangePartsSum,
  newChangeServicesSum,
  newChangeFullDescription,
  newSelectPercentValue,
  newChangePartPurchasePrice,
  newChangePartName,
  newToggleIsStock,
  newAddPart,
  newRemovePart,
  newSelectStock,
  newChangePartQty,
  newChangeQty,
  newToggleIsFetchError,
  newChangeSmallDescription,
  newChangePrice,
  newChangeDescription,
  newAddService,
  newRemoveService,
  newToggleIsCustom,
  newToggleIsLoaded,
  newSelectItem
} = newOrderSlice.actions
export default newOrderSlice.reducer