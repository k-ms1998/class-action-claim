import React, {useState} from 'react';

import styles from './Sign_up.module.css';
import logo from '../img/class_action_claim.png';

const Sign_up = () => {
    const [clicked, setClicked] = useState(false);
    //false = bars, true = times
    const handleClick = () => {
        setClicked(!clicked);
    }

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('izony');

    const handleSubmit = (e) => {
        e.preventDefault();
        const member = { username, password, name };

        fetch('http://localhost:8080/student/signup', {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(member)
        })
        .then(() => {
            console.log('회원가입 완료');
        })
    }

    return (
        <div className={styles.content}>
            <body>
                <div className={styles.main_content}>
                    <body>
                    <h2>회원가입</h2>
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
                            <p>
                                <label>이름</label>
                            </p>
                            <input 
                                type="text" 
                                required
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            />
                            <br/>
                            <br/>
                            <br/>
                            {<button>회원가입</button>}
                        </form>
                    </body>
                </div>
            </body>
        </div>
    );
}
export default Sign_up;