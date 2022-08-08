import React, {useState} from 'react';

import './Sign_up.module.css';
import '../components/navbar/Navbar.css';
import main_logo from '../img/main_logo.png';
import { MenuItems } from "../components/navbar/MenuItems";

const Sign_up = () => {
    const [clicked, setClicked] = useState(false);
    //false = bars, true = times
    const handleClick = () => {
        setClicked(!clicked);
    }
    return (
        <div>
            <nav className="Navbar">
                <h1 className="navbar-logo"><img src={main_logo} alt='React'/></h1>
                <div className="menu-icon" onClick={handleClick}>
                    <i className={clicked ? 'fas fa-times' : 'fas fa-bars'}></i>
                </div>
                <ul className={clicked ? 'nav-menu active' : 'nav-menu'}>
                    {MenuItems.map((item, index)=>{
                        return (
                            <li key={index}>
                                <a className={item.cName} href={item.url}>
                                    {item.title}
                                </a>
                            </li>
                        )
                    })}
                </ul>
            </nav>
            <body>
                <div id="wrapper">
                    <div id="content">
                        <form action="http://localhost:8080/student/signup" method="POST">
                        <div>
                            <h3 class="join_title">
                                <label for="id">아이디</label>
                            </h3>
                            <span class="box int_id">
                                <input type="text" id="username" class="int" maxlength="20"/>
                                <span class="step_url">@sju.ac.kr</span>
                            </span>
                            <span class="error_next_box"></span>
                        </div>

                        <div>
                            <h3 class="join_title"><label for="pswd1">비밀번호</label></h3>
                            <span class="box int_pass">
                                <input type="text" id="password" class="int" maxlength="20"/>
                                <span id="alertTxt">사용불가</span>
                                <img src="m_icon_pass.png" id="pswd1_img1" class="pswdImg"/>
                            </span>
                            <span class="error_next_box"></span>
                        </div>
                        <div>
                            <h3 class="join_title"><label for="pswd2">비밀번호 재확인</label></h3>
                            <span class="box int_pass_check">
                                <input type="text" id="pswd2" class="int" maxlength="20"/>
                                <img src="m_icon_check_disable.png" id="pswd2_img1" class="pswdImg"/>
                            </span>
                            <span class="error_next_box"></span>
                        </div>
                        <div>
                            <h3 class="join_title"><label for="name">이름</label></h3>
                            <span class="box int_name">
                                <input type="text" id="name" class="int" maxlength="20"/>
                            </span>
                            <span class="error_next_box"></span>
                        </div>

                        <div>
                            <h3 class="join_title"><label for="email">본인확인 이메일<span class="optional">(선택)</span></label></h3>
                            <span class="box int_email">
                                <input type="text" id="studentEmail" class="int" maxlength="100" placeholder="선택입력"/>
                            </span>
                            <span class="error_next_box">이메일 주소를 다시 확인해주세요.</span>    
                        </div>

                        <div class="btn_area">
                            <button type="submit" id="btnJoin">
                                <span>가입하기</span>
                            </button>
                        </div>
                        </form>
                    </div> 
                </div> 
            </body>
        </div>
    );
};

export default Sign_up;