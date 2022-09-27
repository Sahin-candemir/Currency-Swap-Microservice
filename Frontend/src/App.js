import './App.css';
import { BrowserRouter as Router,Route, Routes } from 'react-router-dom';
import UserLoginPage from './components/UserLoginPage';
import HeaderComponent from './components/HeaderComponent';
import Home from './components/Home';
import ExchangeComponent from './components/ExchangeComponent';

function App() {
  return (
    <div className="App">
      <Router>
          <HeaderComponent/>
          <div className="container">
            <Routes>
            <Route path='/' element ={<Home/>}/>
            <Route path='/login' element ={<UserLoginPage/>}/>
            <Route path='/exchange' element ={<ExchangeComponent/>}/>
            </Routes>
          </div>
      </Router>
    </div>
  );
}

export default App;
