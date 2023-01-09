import React from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_up, Validation} from '../pages';
import BoardHome from '../pages/board/BoardHome';

const App = (props) => {

    return (
      <div>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/board/BoardHome/*" element={<BoardHome />}/>
        <Route path="/sign_up" element={<Sign_up />}/>
        <Route path="/validation" element={<Validation/>}/>
      </Routes>
      </div>
    );
  };
  

export default App;