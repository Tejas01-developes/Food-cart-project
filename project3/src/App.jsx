import React from 'react'
import {Route, Routes} from 'react-router-dom';
import Adminadditerm from './Components/Adminadditerm';
import Home from './Components/Home';
import Login from './Components/Login';
import Cartpage from './Components/Cartpage';
const App = () => {
  return (
    <div>
      <Routes>

      {/* 1.Admin add iterm code */}
      <Route path='/adminpage' element={<Adminadditerm/>}/>

      {/* 2.Home page code */}
      <Route path='/' element={<Home/>}/>

      {/* 3.login page code */}
      <Route path='/login' element={<Login/>}/>


      {/* 4.Cart page code */}
      <Route path='/cart' element={<Cartpage/>}/>




      </Routes>
    </div>
  )
}

export default App
