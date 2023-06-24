import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import {useDispatch, useSelector} from "react-redux";
import Status from "./Status";
import {updateStatuses} from "../../asyncActions/statuses";

function AllStatuses() {
  const statuses = useSelector(state => state.common.statuses);
  const dispatch = useDispatch();

  const onClick = () => {
    dispatch(updateStatuses(statuses))
      .then(({status, json}) => {
        if (status >= 400) {
          alert("Что-то пошло не так, нужно попробовать еще раз, или позвать Бишу.")
        } else {
          alert("Статус обновлен");
        }
      })
  }

  return (
    <Container>
      <Row>
        <Card className={"mt-3"}>
          {statuses.map(el => <Status status={el} key={el.id}/>)}
          <Button variant="primary" className="mt-3 mb-3" onClick={onClick}>Сохранить</Button>
        </Card>
      </Row>
    </Container>);
}

export default AllStatuses;