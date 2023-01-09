import React from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_up, Validation} from '../pages';
import BoardHome from '../pages/board/BoardHome';

const App = (props) => {
<<<<<<< HEAD
  //user != null
=======
>>>>>>> main

    return (
      <div>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
<<<<<<< HEAD
        <Route path="/board/BoardHome" element={<Sign_in />}/>
=======
        <Route path="/board/BoardHome/*" element={<BoardHome />}/>
>>>>>>> main
        <Route path="/sign_up" element={<Sign_up />}/>
        <Route path="/validation" element={<Validation/>}/>
      </Routes>
      </div>
    );
  };
  

export default App;