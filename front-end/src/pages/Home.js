import React from 'react';

import main_logo from '../img/main_logo.png';

const Home = () => {
    return (    
        <div class="main">
            <nav class="navbar">
                <div class="navbar_logo">
                     <img src={main_logo} className='App-logo' alt='React'/>
                </div>
                <ul class="navbar_menu">
                    <li>
                        <a href="/about/">소개</a>
                    </li>
                    <li>
                        <a href="/sign_in/">로그인</a>
                    </li>
                    <li>
                        <a href="/sign_up/">회원가입</a>
                    </li>
                </ul>
            </nav>
        </div>
        
    );
};

export default Home;