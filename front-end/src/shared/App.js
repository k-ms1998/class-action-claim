import React from 'react';
import { Route, Routes, Router } from 'react-router-dom';
import { Home, About, Sign_in, Sign_up, Validation} from '../pages';

const App = (props) => {
  //user != null

  //const login = ({ email, password }) => setUser(signIn({ email, password }));
  //const logout = () => setUser(null);
    return (
      <div>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/about" element={<About />}/>
        <Route path="/board/BoardHome" element={<Sign_in />}/>
        <Route path="/sign_up" element={<Sign_up />}/>
        <Route path="/validation" element={<Validation/>}/>
      </Routes>
      </div>
    );
  };
  

export default App;