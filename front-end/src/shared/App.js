import React, { Component } from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home, About, Sign_in, Sign_up, Validation} from '../pages';


const App = () => {
    return (
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/sign_in" element={<Sign_in />}/>
        <Route path="/sign_up" element={<Sign_up />}/>
        <Route path="/sign_up/validation" element={<Validation />}/>
      </Routes>
    );
  };
  

export default App;