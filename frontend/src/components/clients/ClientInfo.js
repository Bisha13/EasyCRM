import {useState} from "react";
import {useSelector} from "react-redux";

function ClientInfo() {

  const state = useSelector((state) => state.client)

  return (<div>
    <div>Имя {state.client.name}</div>
    <div>Телефон {state.client.phone}</div>
    <div>Телефон 2 {state.client.phone2}</div>
    <div>Адрес {state.client.address} </div>
    <div>Скидка {state.client.discount}</div>
    <div>Дата создания {state.client.createdAt}</div>
    </div>
  )
}
export default ClientInfo;