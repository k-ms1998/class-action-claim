import React, { Component } from "react";
import { Table } from "react-bootstrap";
import { Route, Routes, NavLink, Link } from "react-router-dom";
import axios from "axios";
import $ from "jquery";
import {} from "jquery.cookie";
import BoardDetail from "./BoardDetail";
import Home from "./Home"
axios.defaults.withCredentials = true;
const headers = { withCredentials: true };

class BoardResult extends Component{
  state = {
    boardList: [],
    onClickedId: ""
  };

  componentDidMount() {
    this.getBoardList();
  }

  onClickListener=(props)=>{
    console.log(props)
  }

  getBoardList = () => {
    const send_param = {
      headers,
      _id: $.cookie("login_id")
    };
    axios
      .post("http://localhost:8080/board/getBoardList", send_param)
      .then(returnData => {

        let boardList;
        if (returnData.data.list.length > 0) {
          const boards = returnData.data.list;
          boardList = boards.map(item => (
          <tr
          onClick={this.onClickListener(item._id)}
          key={Date.now() + Math.random() * 500}
          _id={item._id}
          createdAt={item.createdAt}
          title={item.title}>
            <td>
              <NavLink to= {`/BoardHome/BoardDetail?id=${item._id}`}>
                {item.createdAt.substring(0, 10)}
              </NavLink>
            </td>
            <td>
            <NavLink to= {`/BoardHome/BoardDetail?id=${item._id}`}>
              {item.title}
            </NavLink>
            </td>
            </tr>
          ));
          this.setState({
            boardList: boardList
          });
        } 
        else {
          boardList = (
            <tr>
              <td colSpan="2">작성한 게시글이 존재하지 않습니다.</td>
            </tr>
          );
          this.setState({
            boardList: boardList
          });
        }
      })
      .catch(err => {
        console.log(err);
      });
  };

  render(){

    return(
      <Table striped bordered hover>
      <thead>
        <tr>
          <th>날짜</th>
          <th>글 제목</th>
        </tr>
      </thead>
      <tbody>
        {this.state.boardList}
      </tbody>
      </Table>
    );
  }
}

class BoardForm extends Component {
  
  render() {
    const divStyle = {
      margin: 50
    };

    return (
      <div>
        <div style={divStyle}>
          <BoardResult/>
        </div>
      </div>
    );
  }
}

export default BoardForm;