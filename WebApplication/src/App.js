import './App.css';
import './After_login.css';
import './Add_coupon.css';
import React, { useState } from 'react';
import {login} from './assets/firebase.js';

function App() {
    const [loggedIn, setLoggedIn] = useState(false);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [middlePanel, setMiddlePanel] = useState(null);
    const [couponForm, setCouponForm] = useState({
      name: '',
      description: '',
      code: ''
    });

    const handleInputChange = (event) => {
    const { name, value } = event.target;
    setCouponForm({ ...couponForm, [name]: value });
    };
  

    const handleEmailChange = (event) => {
      setEmail(event.target.value);
    };
  
    const handlePasswordChange = (event) => {
      setPassword(event.target.value);
    };
    
  const handleSubmit = (event) => {
    couponForm.name=null;
    couponForm.description=null;
    couponForm.code=null;
    event.preventDefault();
    // Tutaj możesz obsłużyć wysłanie danych formularza, np. wysłać żądanie do serwera
    // i dodać kupon do bazy danych. Na potrzeby tego przykładu po prostu zaktualizujemy stan middlePanel.
    setMiddlePanel(<div/>);
  };

    const handleAddClick = () => {
      setMiddlePanel(
        <div id="CouponForm">
        <form onSubmit={handleSubmit}>
          <div id="couponNameContainer">
            <input type="text" name="name" tex placeholder="COUPON NAME"  onChange={handleInputChange} required />
          </div>
          <div id="couponDescriptionContainer">
            <textarea type="text" name="description" placeholder="COUPON DESCRIPTION"  onChange={handleInputChange} required />
          </div>
          <div id="couponCodeContainer">
            <input type="text"  name="code" placeholder="COUPON CODE"  onChange={handleInputChange} required/>
          </div>
          <div id="couponSubmitContainer">
            <button type="submit">accept</button>
          </div>
          </form>
          </div>
      );
      setCouponForm({
        name: '',
        description: '',
        code: ''
      });
  };

  const logInUser = () => {
    login(email, password, (callback) => {
      setLoggedIn(callback);
    });
  };

    const backToLogin = () => {
      setMiddlePanel(<div/>);
      setEmail(null)
      setPassword(null)
      setLoggedIn(false)
    }

    const showOptionsEvents = () => {
      setMiddlePanel(
        <div id="section_panel">
          <div id="option_add">
            <div id="add_text">ADD</div>
            <div id="add_icon"></div>
          </div>
          <div  id="option_search">
            <div id="search_text">FIND</div>
            <div id="search_icon"></div>
          </div>
        </div>
      );
    };

    const showOptionsCoupon = () => {
      setMiddlePanel(
        <div id="section_panel">
          <div id="option_add" onClick={handleAddClick}>
            <div id="add_text">ADD</div>
            <div id="add_icon"></div>
          </div>
          <div  id="option_search">
            <div id="search_text">FIND</div>
            <div id="search_icon"></div>
          </div>
        </div>
      );
    };

    const showOptionsUsers = () => {
      setMiddlePanel(null)
    }

  return (
    <div className="App">
      { !loggedIn ? (
        <div id="middle_container">
            <div id="icon_container"></div>
            <div id="login_containers">
                <div id="insert_email">
                    <input id="email_input" 
                    type="email" 
                    placeholder="E-mail" 
                    required
                    value={email}
                    onChange={handleEmailChange}></input>
                  </div>
                  <div id="insert_password">
                    <input id="password_input" type="password" placeholder="Password"required value={password}
                    onChange={handlePasswordChange}/>
                  </div>
                  <div id="login_button" onClick={logInUser}>
                          <p>log in</p>
                  </div>
              </div>
      </div>
  ) : (
    <div id="screen_after_login">
    <div id="leftPanel">
            <div id="userPanel" onClick={showOptionsUsers}>
                <div id="userPanel_icon"></div>
                <div id="userPanel_text"><p>USERS</p></div>
            </div>
            <div id="eventPanel" onClick={showOptionsEvents}>
                <div id="eventPanel_icon"></div>
                <div id="eventPanel_text"><p>EVENTS</p></div>
            </div>
            <div id="couponPanel" onClick={showOptionsCoupon}>
                <div id="couponPanel_icon"></div>
                <div id="couponPanel_text"><p>COUPONS</p></div>
            </div>
        </div>
        <div id="middlePanel"> 
        {middlePanel}
        </div>
        <div id="rightPannel">
            <div id="logoutPanel">
                <div id="leftPadding"></div>
                <div id="logoutPanel_text"><p>ADMIN</p></div>
                <div id="logoutPanel_icon" onClick={backToLogin}></div>
            </div>
        </div>
        </div>
  )
  }
  </div>
);
}

export default App;
