import React, { Component } from "react";
import Sign_in from "./Sign_in";
import BoardForm from "./BoardForm";
import BoardWriteForm from "./BoardWriteForm";
import BoardDetail from "./BoardDetail";
import MypageForm from "./MypageForm";
<<<<<<< HEAD
import { Route } from "react-router-dom";
=======
import { Route, Routes } from "react-router-dom";
>>>>>>> main
import $ from "jquery";
import {} from "jquery.cookie";

class Body extends Component {
  render() {
    let resultForm;
    function getResultForm() {
<<<<<<< HEAD
      console.log($.cookie("login_id"));
      if ($.cookie("login_id")) {
        resultForm = <Route exact path="/" component={BoardForm}></Route>;
        return resultForm;
      } else {
        resultForm = <Route exact path="/" component={Sign_in}></Route>;
=======
      //console.log($.cookie("login_id"));
      if ($.cookie("login_id")) {
        resultForm = <Route path="/" element={<BoardForm/>}/>;
        return resultForm;
      } else {
        resultForm = <Route path="/" element={<Sign_in/>}/>;
>>>>>>> main
        return resultForm;
      }
    }
    getResultForm();
    return (
<<<<<<< HEAD
      <div>
        <Route path="/mypage" component={MypageForm}></Route>
        <Route path="/boardWrite" component={BoardWriteForm}></Route>
        <Route path="/board/detail" component={BoardDetail}></Route>
        {resultForm}
      </div>
=======
        <Routes>
          {resultForm}
          <Route path="/mypage" element={<MypageForm/>}/>
          <Route path="/boardWrite" element={<BoardWriteForm/>}/>
          <Route path="/board/detail" element={<BoardDetail/>}/>
        </Routes>
>>>>>>> main
    );
  }
}

export default Body;