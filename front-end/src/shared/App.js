import React, { Component } from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home, About, Sign_in, Sign_up } from '../pages';


const App = () => {
    return (
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/sign_in" element={<Sign_in />}/>
        <Route path="/sign_up" element={<Sign_up />}/>
      </Routes>
    );
  };
  

export default App;