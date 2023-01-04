import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
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