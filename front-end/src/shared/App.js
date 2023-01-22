import React from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_up, BoardHome, BoardWriteForm, BoardDetail, BoardForm} from '../pages';

const App = (props) => {
    return (
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/BoardHome/*" element={<BoardHome />}/>
        <Route path="/BoardWriteForm" element={<BoardWriteForm />}/>
        <Route path="/SignUp" element={<Sign_up />}/>
      </Routes>
    );
  };
  

export default App;