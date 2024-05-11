import './App.css';
import './After_login.css';
import React, { useState } from 'react';


function App() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loggedIn, setLoggedIn] = useState(false);
    const [middlePanel, setMiddlePanel] = useState(null);
    const [couponForm, setCouponForm] = useState({
      name: '',
      description: '',
      code: ''
  });

    const handleEmailChange = (event) => {
      setEmail(event.target.value);
    };
  
    const handlePasswordChange = (event) => {
      setPassword(event.target.value);
    };
    
  const handleSubmit = (event) => {
    event.preventDefault();
    // Tutaj możesz obsłużyć wysłanie danych formularza, np. wysłać żądanie do serwera
    // i dodać kupon do bazy danych. Na potrzeby tego przykładu po prostu zaktualizujemy stan middlePanel.
    setMiddlePanel();
  };

    const handleAddClick = () => {
      setMiddlePanel(
        <div id="CouponForm">
        <form onSubmit={handleSubmit}>
            <input type="text" name="name" placeholder="ENTER COUPON NAME..." value={couponForm.name} />
            <input type="text" name="description" placeholder="COUPON DESCRIPTION" value={couponForm.description} />
            <input type="text" name="discount" placeholder="COUPON CODE" value={couponForm.discount}  />
            <button type="submit">Dodaj</button>
          </form>
          </div>
      );
  };

    const checkCredentials = () => {
      if (email === "123" && password === "123") {
        setLoggedIn(true)
      } else {
        alert("Nieprawidłowy email lub hasło. Spróbuj ponownie.");
      }
    };

    const backToLogin = () => {
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
                  <div id="login_button" onClick={checkCredentials}>
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
                <div id="logoutPanel_text"><p>{email}</p></div>
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
