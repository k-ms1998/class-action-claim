import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "./board/Header";
import Body from "./board/Body";
import Footer from "./board/Footer";


const BoardHome = () => {
  return(
    <div>
      <Header/>
      <Body/>
      <Footer/>
    </div>
  );
}

export default BoardHome;