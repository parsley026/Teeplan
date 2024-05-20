import React, { useState, useEffect } from 'react';

import {loginPage} from './pages/loginPage/loginPage.js';
import {mainPage} from './pages/mainPage/mainPage.js';

import {login, getUsers, getCoupons, getEvents} from './services/firebase.js';

function App() {
  // State variables
  const [loggedIn, setLoggedIn] = useState(false);

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [search, setSearch] = useState('');

  const [users, setUsers] = useState([]);
  const [coupons, setCoupons] = useState([]);
  const [events, setEvents] = useState([]);

  const [middlePanel, setMiddlePanel] = useState(null);

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleSearchChange = (event) => {
    setSearch(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const logInUser = () => {
    login(email, password, (isLoggedIn, errorMessage) => {
      if (isLoggedIn) {
        setLoggedIn(true);
      } else {
        console.error(errorMessage); 
        setEmail('');
        setPassword('');
        alert(errorMessage); 
      }
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
    //TODO pasek wyszukiwania
    setMiddlePanel(
      <div id="section_panel">
        <div class="action_panel">
          
        </div>
        <div class="data_container">
          {users.map((user, index) => (
            <div class="information_container_user" key={index}>
              <div class="data_field"><p>{user.first_name}</p></div>
              <div class="data_field"><p>{user.last_name}</p></div>
              <div class="data_field"><p>{user.email}</p></div>
              <div class='trash_bin_container'></div>
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
      {!loggedIn ? (
        loginPage(email, password, handleEmailChange, handlePasswordChange, logInUser)
      ) : (
        mainPage(middlePanel, showOptionsUsers, showOptionsEvents, showOptionsCoupon, logOutLogin)
      )}
    </div>
  );
}

export default App;
