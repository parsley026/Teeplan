import './App.css';
import './After_login.css';
import './Add_coupon.css';
import React, { useState, useEffect } from 'react';
import {login, getUsers, getCoupons, getEvents} from './assets/firebase.js';
function App() {
    const [loggedIn, setLoggedIn] = useState(false);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [search, setSearch] = useState('');

    const [users, setUsers] = useState([]);
    const [coupons, setCoupons] = useState([]);
    const [events, setEvents] = useState([]);

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

  const logOutLogin = () => {
    setMiddlePanel(<div />);
    setEmail('');
    setPassword('');
    setLoggedIn(false);
  };

  const showOptionsEvents = () => {
    setMiddlePanel(
      <div id="section_panel">
        <div class="action_panel">
          <div class="option_panel" id="add_panel">
            <div class="text_panel">ADD</div>
            <div class="icon" id="add_icon"></div>
          </div>
          <div class="option_panel" id="search_panel">
            <div class="text_panel">FIND</div>
            <div class="icon" id="search_icon"></div>
          </div>
        </div>
        <div class="data_container">
          {events.map((event, index) => (
            <div class="information_container" key={index}>
              <div class="data_field">{event.name}</div>
              <div class="data_field">{event.description}</div>
              <div class="data_field">{event.date}</div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const showOptionsCoupon = () => {
    setMiddlePanel(
      <div id="section_panel">
        <div class="action_panel">
          <div class="option_panel">
            <div class="text_panel">ADD</div>
            <div class="icon" id="add_icon"></div>
          </div>
          <div class="option_panel">
            <div class="text_panel">FIND</div>
            <div class="icon" id="search_icon"></div>
          </div>
        </div>
        <div class="data_container">
          {coupons.map((coupon, index) => (
            <div class="information_container" key={index}>
              <div class="data_field"><p>{coupon.name}</p></div>
              <div class="data_field"><p>{coupon.description}</p></div>
              <div class="data_field"><p>{coupon.code}</p></div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const showOptionsUsers = () => {
    setMiddlePanel(
      <div id="section_panel">
        <div class="action_panel">
          <div class="option_panel">
            <div class="text_panel">ADD</div>
            <div class="icon" id="add_icon"></div>
          </div>
          <div class="option_panel">
            <div class="text_panel">FIND</div>
            <div class="icon" id="search_icon"></div>
          </div>
        </div>
        <div class="data_container">
          {users.map((user, index) => (
            <div class="information_container" key={index}>
              <div class="data_field"><p>{user.first_name}</p></div>
              <div class="data_field"><p>{user.last_name}</p></div>
              <div class="data_field"><p>{user.email}</p></div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const fetchUsers = async () => {
    try {
      const fetchedUsers = await getUsers(search);
      setUsers(fetchedUsers);
    } catch (error) {
      const errorCode = error.code;
      const errorMessage = error.message;

      console.error("fetchUsers faild")
      console.error(errorCode + errorMessage);
    }
  };

  const fetchCoupons = async () => {
    try {
      const fetchedUsers = await getCoupons(search);
      setCoupons(fetchedUsers);
    } catch (error) {
      const errorCode = error.code;
      const errorMessage = error.message;

      console.error("fetchCoupons faild")
      console.error(errorCode + errorMessage);
    }
  };

  const fetchEvents = async () => {
    try {
      const fetchedUsers = await getEvents(search);
      setEvents(fetchedUsers);
    } catch (error) {
      const errorCode = error.code;
      const errorMessage = error.message;

      console.error("fetchEvents faild")
      console.error(errorCode + errorMessage);
    }
  };

  function loadData() {
    fetchUsers();
    fetchCoupons();
    fetchEvents();
  }


  useEffect(() => {
    loadData();
  });

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
                <div id="logoutPanel_icon" onClick={logOutLogin}></div>
            </div>
        </div>
        </div>
  )
  }
  </div>
);
}

export default App;
