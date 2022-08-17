import React, {useState} from 'react';
import { Link, useNavigate } from 'react-router-dom'
import styles from './Sign.module.css';

const Sign_up = () => {
    const [clicked, setClicked] = useState(false);
    //false = bars, true = times

    const [studentEmail, setStudentEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const member = { studentEmail, password, name };

        let errors = {};
        
        //정규식 표현
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
        
        //이메일 값이 없을시
        if (!studentEmail) {
          errors.studentEmail = "Cannot be blank";
          //이메일 정규식 표현이 옳지 않을시
        } else if (!regex.test(studentEmail)) {
          errors.studentEmail = "Invalid email format";
        }
        
        //비밀번호 값이 없을시
        if (!password) {
          errors.password = "Cannot be blank";
          //비밀번호의 길이(length)가 4글자 이하일 때
        } else if (password.length < 4) {
          errors.password = "Password must be more than 4 characters";
        }
        if(errors.studentEmail != undefined){
            alert(errors.studentEmail);
        }
        else if(errors.password != undefined){
            alert(errors.password);
        }
        else {
            window.location.href = './sign_up/validation';
            setClicked(!clicked);
            
            fetch('http://localhost:8080/mail/send_auth_code', {
                method: 'POST',
                headers: { "Content-Type" : "application/json" },
                body: JSON.stringify(member)
            })
            .then(() => {
                console.log('회원가입 완료');
            })
        }
    };

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
                                value={studentEmail}
                                onChange={(e) => setStudentEmail(e.target.value)}
                            />
                            <p>
                                <label>비밀번호</label>
                            </p>
                            <input 
                                type="password" 
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
                            <button type='submit' className={styles.button_background}>
                                회원가입
                            </button>
                        </form>
                    </body>
                </div>
            </body>
        </div>
    );
}
export default Sign_up;