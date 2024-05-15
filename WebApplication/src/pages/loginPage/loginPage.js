import React from 'react';

export function loginPage(email, password, handleEmailChange, handlePasswordChange, logInUser) {
    return (
    <div id="middle_container">
        {
        <div id="middle_inner_container">
            <div id="icon_container"></div>
            <div id="login_containers">
                <div id="insert_email">
                    <input id="email_input" type="email" placeholder="E-mail" required value={email} onChange={handleEmailChange}></input>
                </div>
                <div id="insert_password">
                    <input id="password_input" type="password" placeholder="Password"required value={password} onChange={handlePasswordChange}/>
                </div>
            <div id="login_button" onClick={logInUser}>
                <p>log in</p>
            </div>
        </div>
    </div>  
        }
    </div>
    )
}