import React, { Component, useState } from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_in, Sign_up, Validation} from '../pages';

const App = () => {
  const [user, setUser] = useState(null);
  const authenticated = false;

  //const login = ({ email, password }) => setUser(signIn({ email, password }));
  //const logout = () => setUser(null);
    return (
      <div>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/sign_in" element={<Sign_in />}/>
        <Route path="/sign_up" element={<Sign_up />}/>
        <Route path={authenticated ? "/validation" : "/sign_in"} element={authenticated ? <Validation/> : <Sign_in/>}/>
      </Routes>
      </div>
    );
  };
  

export default App;