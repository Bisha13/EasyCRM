import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";

function ItemRow(props) {
    let categories = useSelector((state) => state.item.categories)
    let navigate = useNavigate();
    const routeChange = () =>{
        let path = `/items/${props.data.id}`;
        navigate(path);
    }

    const getCategoryName = () => {
        let name = categories.find(el => ''+el.id === ''+props.data.categoryId)?.name;
        return name || "category";
    }

    return <tr onClick={routeChange}>
        <td>{props.data.id}</td>
        <td>{props.data.name}</td>
        <td>{props.data.description}</td>
        <td>{getCategoryName()}</td>
        <td>{props.data.priority}</td>
        <td>{props.data.price}</td>
    </tr>;
}

export default ItemRow;
