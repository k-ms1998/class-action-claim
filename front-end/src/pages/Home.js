import React, {useState} from 'react';

import '../components/navbar/Navbar.css';
import './Home.css'
import main_logo from '../img/main_logo.png';
import { MenuItems } from "../components/navbar/MenuItems";

import Button from '../components/Button'

const Home = () => {
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
            <body class='background'>
                <div id='wrap'>
                    <footer>
                        <p>
                            <span>제작 : izony</span><br/>
                            <span>저자 : izony</span><br/>
                            <span>저자 : izony</span>
                        </p>
                    </footer>
                </div>
            </body>
        </div>
    )
};
export default Home;