import React, { Component } from "react";
import Sign_in from "./Sign_in";
<<<<<<< HEAD
import BoardForm from "../BoardForm";
=======
import BoardForm from "./BoardForm";
import BoardWriteForm from "./BoardWriteForm";
import BoardDetail from "./BoardDetail";
import MypageForm from "./MypageForm";
>>>>>>> 49139fd59d9fdbbcb31babc5db9b06b585ab5f59
import { Route, Routes } from "react-router-dom";
import $ from "jquery";
import {} from "jquery.cookie";
import BoardWriteForm from "../BoardWriteForm";
import BoardDetail from "../BoardDetail";
import MypageForm from "./MypageForm";

class Body extends Component {
  render() {
    let resultForm;
    function getResultForm() {
      //console.log($.cookie("login_id"));
      if ($.cookie("login_id")) {
        resultForm = <Route path="/" element={<BoardForm/>}/>;
        return resultForm;
      } else {
        resultForm = <Route path="/" element={<Sign_in/>}/>;
        return resultForm;
      }
    }
    getResultForm();
    return (
        <Routes>
          {resultForm}
          <Route path="/mypage" element={<MypageForm/>}/>
          <Route path="/BoardWriteForm" element={<BoardWriteForm/>}/>
          <Route path="/BoardDetail" element={<BoardDetail/>}/>
        </Routes>
    );
  }
}

export default Body;