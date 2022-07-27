import React, { Component } from 'react';
import { Route, Routes } from 'react-router-dom';
import { Home, About } from '../pages';


const App = () => {
    return (
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />} />
      </Routes>
    );
  };
  

export default App;