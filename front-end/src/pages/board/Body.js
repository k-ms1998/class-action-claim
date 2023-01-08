import React, { Component } from "react";
import Sign_in from "./Sign_in";
import BoardForm from "./BoardForm";
import BoardWriteForm from "./BoardWriteForm";
import BoardDetail from "./BoardDetail";
import MypageForm from "./MypageForm";
import { Route, Routes } from "react-router-dom";
import $ from "jquery";
import {} from "jquery.cookie";

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
          <Route path="/boardWrite" element={<BoardWriteForm/>}/>
          <Route path="/board/detail" element={<BoardDetail/>}/>
        </Routes>
    );
  }
}

export default Body;