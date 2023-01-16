import React from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_up, BoardHome, BoardWriteForm, BoardDetail, BoardForm} from '../pages';

const App = (props) => {

    return (
      <div>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/BoardHome" element={<BoardHome />}/>
        <Route path="/BoardWriteForm" element={<BoardWriteForm />}/>
        <Route path="/BoardDetail" element={<BoardDetail />}/>
        <Route path="/BoardForm" element={<BoardForm />}/>
        <Route path="/sign_up" element={<Sign_up />}/>
      </Routes>
      </div>
    );
  };
  

export default App;