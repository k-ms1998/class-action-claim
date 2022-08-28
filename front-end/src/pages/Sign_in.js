import React, {useState} from 'react';

import styles from './Sign.module.css';

const Sign_in = () => {
    const [clicked, setClicked] = useState(false);
    //false = bars, true = times
    const handleClick = () => {
        setClicked(!clicked);
    }

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const member = { username, password, name };

        fetch('http://localhost:8080/student/signup', {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(member)
        })
        .then(() => {
            console.log('로그인 완료');
        })
    }

    return (
        <div className={styles.content}>
            <body>
                <div className={styles.main_content}>
                    <body>
                    <h2>로그인</h2>
                        <form onSubmit={handleSubmit}>
                            <p>
                                <label>이메일</label>
                            </p>
                            <input 
                                type="text" 
                                required
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                            <br/>
                            <p>
                                <label>비밀번호</label>
                            </p>
                            <input 
                                type="text" 
                                required
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                            <br/>
                            <br/>
                            <br/>
                            {<button className={styles.button_background}>로그인</button>}
                        </form>
                        <br/>
                        <a href='../sign_up'>회원가입&nbsp;&nbsp;&nbsp;</a>
                        <a href='../sign_up'>&nbsp;&nbsp;&nbsp;아이디 | 비밀번호</a>
                    </body>
                </div>
            </body>
        </div>
    );
}
export default Sign_in;