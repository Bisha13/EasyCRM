import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {LinkContainer} from 'react-router-bootstrap'


function MyNavbar() {
  return (
    <Navbar expand="sm" bg="dark" variant="dark">
      <Container fluid>
        <LinkContainer to='/'>
          <Navbar.Brand>EasyCrm</Navbar.Brand>
        </LinkContainer>
        <Navbar.Toggle aria-controls="basic-navbar-nav"/>
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <LinkContainer to="/"><Nav.Link>Главная</Nav.Link></LinkContainer>
            <LinkContainer to="/new_order"><Nav.Link>Новый заказ</Nav.Link></LinkContainer>
            <LinkContainer to="/clients"><Nav.Link>Клиенты</Nav.Link></LinkContainer>
            <LinkContainer to="/devices"><Nav.Link>Девайсы</Nav.Link></LinkContainer>
            <LinkContainer to="/workers"><Nav.Link>Мастера</Nav.Link></LinkContainer>
            <LinkContainer to="/settings"><Nav.Link>Настройки</Nav.Link></LinkContainer>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default MyNavbar;
