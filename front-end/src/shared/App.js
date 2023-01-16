import React from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_up, BoardHome, BoardWriteForm, BoardDetail, BoardForm} from '../pages';

const App = (props) => {

    return (
      <div>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
<<<<<<< HEAD
        <Route path="/BoardHome" element={<BoardHome />}/>
        <Route path="/BoardWriteForm" element={<BoardWriteForm />}/>
        <Route path="/BoardDetail" element={<BoardDetail />}/>
        <Route path="/BoardForm" element={<BoardForm />}/>
=======
        <Route path="/board/BoardHome/*" element={<BoardHome />}/>
>>>>>>> 49139fd59d9fdbbcb31babc5db9b06b585ab5f59
        <Route path="/sign_up" element={<Sign_up />}/>
      </Routes>
      </div>
    );
  };
  

export default App;