import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
<<<<<<< HEAD
import { HashRouter } from 'react-router-dom';
import Header from "./Header";
import Body from "./Body";
import Footer from "./Footer";
import ReactDOM from 'react-dom';


class BoardHome extends Component {
  render() {
    return (
      <HashRouter>
        <Header/>
          <Body/>
        <Footer/>
      </HashRouter>
    );
  }
}

export default BoardHome;
=======
import Header from "./Header";
import Body from "./Body";
import Footer from "./Footer";

const BoardHome = () => {
  return(
    <div>
      <Header/>
      <Body/>
      <Footer/>
    </div>
  );
}

export default BoardHome
>>>>>>> main
