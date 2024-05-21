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
  const [popupPanel, setPopupPanel] = useState(null)

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

  const showChooseMenuEvents = () =>{
    setMiddlePanel(
      <div class="add_search_menu">
        <div class="text_panel">EVENTS</div>
        <div class="choose_menu">
          <div class="small_panel" onClick={showOptionsEvents} >
            <div class="small_panel_text">SEARCH</div>
            <div class="icon" id="search_icon"></div>
          </div>
          <div class="small_panel" onClick={addOptionsEvents}>
            <div class="small_panel_text">ADD</div>
            <div class="icon" id="add_icon"></div>
          </div>
        </div>
      </div>
    )
  };

  const addOptionsEvents = () => {
    setMiddlePanel(
      <div class="add_form">
        <div class="text_panel">ADD NEW EVENT</div>
          <div class="outline">
            <input class="input_field" type="text" name="name" tex placeholder="EVENT NAME"  required />
            </div>
            <div class="outline">
            <textarea class="input_field" type="text" name="description" placeholder="EVENT DESCRIPTION"  required />
            </div>
            <div class="outline">
            <input  class="input_field" type="date"  name="code"  required/>
            </div>
            <div class="button">accept</div>
      </div>
    );
  }

  const showOptionsEvents = () => {
    setMiddlePanel(
      <div id="section_panel">
          <div class="action_panel">
          <div class="search_bar">
              <input class="searchbar_input" placeholder='search'></input>
              <div class ="icon" id="search_icon"></div>
          </div>
        </div>
        <div class="data_container">
          {events.map((event, index) => (
            <div class="information_container" key={index}>
              <div class="data_field_container">
              <div class="data_field">{event.name}</div>
              <div class="data_field">{event.description}</div>
              <div class="data_field">{event.date}</div>
              </div>
              <div class='trash_bin_container' onClick={popupAgreementPanelEvents}></div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const showChooseMenuCoupon = () =>{
    setMiddlePanel(
      <div class="add_search_menu">
        <div class="text_panel">COUPONS</div>
        <div class="choose_menu">
          <div class="small_panel" onClick={showOptionsCoupon} >
            <div class="small_panel_text">SEARCH</div>
            <div class="icon" id="search_icon"></div>
          </div>
          <div class="small_panel" onClick={addOptionsCoupon}>
            <div class="small_panel_text">ADD</div>
            <div class="icon" id="add_icon"></div>
          </div>
        </div>
      </div>
    )
  };

  const addOptionsCoupon = () => {
    setMiddlePanel(
      <div class="add_form">
        <div class="text_panel">ADD NEW COUPON</div>
          <div class="outline">
            <input class="input_field" type="text" name="name" tex placeholder="COUPON NAME"  required />
            </div>
            <div class="outline">
            <textarea class="input_field" type="text" name="description" placeholder="COUPON DESCRIPTION"  required />
            </div>
            <div class="outline">
            <input  class="input_field" type="text"  name="code" placeholder="COUPON CODE"   required/>
            </div>
            <div class="button">accept</div>
      </div>
    );
  }


  const showOptionsCoupon = () => {
    setMiddlePanel(
      <div id="section_panel">
        <div class="action_panel">
        <div class="search_bar">
            <input class="searchbar_input" placeholder='search'></input>
            <div class ="icon" id="search_icon"></div>
          </div>
        </div>
        <div class="data_container">
          {coupons.map((coupon, index) => (
            <div class="information_container" key={index}>
              <div class="data_field_container">
              <div class="data_field"><p>{coupon.name}</p></div>
              <div class="data_field"><p>{coupon.description}</p></div>
              <div class="data_field"><p>{coupon.code}</p></div>
              </div>
              <div class='trash_bin_container' onClick={popupAgreementPanelCoupon}></div>
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
          <div class="search_bar">
              <input class="searchbar_input" placeholder='search'></input>
              <div class ="icon" id="search_icon"></div>
          </div>
        </div>
        <div class="data_container">
          {users.map((user, index) => (
            <div class="information_container" key={index}>
              <div class="data_field_container">
              <div class="data_field"><p>{user.first_name}</p></div>
              <div class="data_field"><p>{user.last_name}</p></div>
              <div class="data_field"><p>{user.email}</p></div>
              </div>
              <div class='trash_bin_container' onClick={popupAgreementPanelUsers}></div>
            </div>
          ))}
        </div>
      </div>
    );
  };

  const turnOffAgreementPanel = () => {
    setPopupPanel(
      <div></div>
    )
  }

  //TODO Dodanie usuwania :D

  const popupAgreementPanelUsers = () => {
      setPopupPanel(
      <div class="popup_background">
        <div class="agreement_panel">
          <div class="text_panel">CONFIRM USER DELETION</div>
          <div class="button">YES</div>
          <div class="button" id="red_button" onClick={turnOffAgreementPanel}>NO</div>
        </div>
      </div>
      )
  }

  const popupAgreementPanelCoupon = () => {
    setPopupPanel(
    <div class="popup_background">
      <div class="agreement_panel">
        <div class="text_panel">CONFIRM COUPON DELETION</div>
        <div class="button">YES</div>
        <div class="button" id="red_button" onClick={turnOffAgreementPanel}>NO</div>
      </div>
    </div>
    )
  }

  const popupAgreementPanelEvents = () => {
    setPopupPanel(
    <div class="popup_background">
      <div class="agreement_panel">
        <div class="text_panel">CONFIRM EVENT DELETION</div>
        <div class="button">YES</div>
        <div class="button" id="red_button" onClick={turnOffAgreementPanel}>NO</div>
      </div>
    </div>
    )
  }

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
        mainPage(middlePanel, popupPanel,showOptionsUsers, showChooseMenuEvents,showChooseMenuCoupon, logOutLogin)
      )}
    </div>
  );
}

export default App;
