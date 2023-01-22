import React, { Component } from "react";
import Sign_in from "./Sign_in";
import BoardForm from "../BoardForm";
import { Route, Routes } from "react-router-dom";
import $ from "jquery";
import {} from "jquery.cookie";
import BoardWriteForm from "../BoardWriteForm";
import BoardDetail from "../BoardDetail";
import MypageForm from "./MypageForm";
import { ThemeConsumer } from "react-bootstrap/esm/ThemeProvider";

class Body extends Component {
  render() {
    let resultForm;
    function getResultForm() {
      if ($.cookie("login_id")) {
        resultForm = <Route exact path="*" element={<BoardForm/>}/>;
        return resultForm;
      } else {
        resultForm = <Route exact path="/" element={<Sign_in/>}/>;
        return resultForm;
      }
    }
    getResultForm();
    return (
        <Routes>
          <Route path="/mypage" element={<MypageForm/>}/>
          <Route path="/BoardWriteForm" element={<BoardWriteForm/>}/>
          {resultForm}
        </Routes>
    );
  }
}

export default Body;