import React, { useState, useEffect } from "react";

import styles from './Sign.module.css';

const Validation = () => {
    const [minutes, setMinutes] = useState(10);
    const [seconds, setSeconds] = useState(0);

    useEffect(() => {
        const countdown = setInterval(() => {
          if (parseInt(seconds) > 0) {
            setSeconds(parseInt(seconds) - 1);
          }
          if (parseInt(seconds) === 0) {
            if (parseInt(minutes) === 0) {
              clearInterval(countdown);
            } else {
              setMinutes(parseInt(minutes) - 1);
              setSeconds(59);
            }
          }
        }, 1000);
        return () => clearInterval(countdown);
      }, [minutes, seconds]);

    return (
        <div className={styles.content}>
            <body>
                <div className={styles.main_content}>
                    <body>
                        <h2>회원가입</h2>
                        <form>
                            <p>
                                <label>인증번호</label>
                            </p>
                            <input className={styles.input_valid}
                                type="text" 
                                required
                            />
                            <label>
                                &nbsp;{minutes}:{seconds < 10 ? `0${seconds}` : seconds}
                            </label>
                            <br/>
                            <br/>
                            <br/>
                            {<button className={styles.button_background}>회원가입</button>}
                        </form>
                    </body>
                </div>
            </body>
        </div>
    );
}
export default Validation;