import React, {useState, useEffect} from 'react';

import {loginPage} from './pages/loginPage/loginPage.js';
import {mainPage} from './pages/mainPage/mainPage.js';

import {login, getUsers, getCoupons, getEvents, addCoupon, addEvent} from './services/firebase.js';

function App() {
    // State variables
    const [loggedIn, setLoggedIn] = useState(true);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const [search, setSearch] = useState('');

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');

    const [code, setCode] = useState('');
    const [date, setDate] = useState('');

    const [users, setUsers] = useState([]);
    const [coupons, setCoupons] = useState([]);
    const [events, setEvents] = useState([]);

    const [middlePanel, setMiddlePanel] = useState(null);
    const [popupPanel, setPopupPanel] = useState(null);

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handleSearchChange = (event) => {
        setSearch(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleCodeChange = (event) => {
        setCode(event.target.value);
    };

    const handleDateChange = (event) => {
        setDate(event.target.value);
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

    const logOutUser = () => {
        setLoggedIn(false);
        setEmail('');
        setPassword('');
        setMiddlePanel(<div/>);
        setPopupPanel(<div/>)
    };

    const showChooseMenuEvents = () => {
        setMiddlePanel(
            <div className="add_search_menu">
                <div className="text_panel">EVENTS</div>
                <div className="choose_menu">
                    <div className="small_panel" onClick={showOptionsEvents}>
                        <div className="small_panel_text">SEARCH</div>
                        <div className="icon" id="search_icon"></div>
                    </div>
                    <div className="small_panel" onClick={addOptionsEvents}>
                        <div className="small_panel_text">ADD</div>
                        <div className="icon" id="add_icon"></div>
                    </div>
                </div>
            </div>
        );
    };

    const showChooseMenuCoupon = () => {
        setMiddlePanel(
            <div className="add_search_menu">
                <div className="text_panel">COUPONS</div>
                <div className="choose_menu">
                    <div className="small_panel" onClick={showOptionsCoupon}>
                        <div className="small_panel_text">SEARCH</div>
                        <div className="icon" id="search_icon"></div>
                    </div>
                    <div className="small_panel" onClick={addOptionsCoupon}>
                        <div className="small_panel_text">ADD</div>
                        <div className="icon" id="add_icon"></div>
                    </div>
                </div>
            </div>
        );
    };

    const addOptionsEvents = () => {
        setMiddlePanel(
            <div className="add_form">
                <div className="text_panel">ADD NEW EVENT</div>
                <div className="outline">
                    <input className="input_field" type="text" name="name" placeholder="EVENT NAME" required value={name} onChange={handleNameChange}/>
                </div>
                <div className="outline">
                    <textarea className="input_field" type="text" name="description" placeholder="EVENT DESCRIPTION" required value={description} onChange={handleDescriptionChange}/>
                </div>
                <div className="outline">
                    <input className="input_field" type="date" name="date" required value={date} onChange={handleDateChange}/>
                </div>
                <div className="button" onClick={addEvent(name, description, date)} >accept</div>
            </div>
        );
    };

    const addOptionsCoupon = () => {
        setMiddlePanel(
            <div className="add_form">
                <div className="text_panel">ADD NEW COUPON</div>
                <div className="outline">
                    <input className="input_field" type="text" name="name" placeholder="COUPON NAME" required value={name} onChange={handleNameChange}/>
                </div>
                <div className="outline">
                    <textarea className="input_field" type="text" name="description" placeholder="COUPON DESCRIPTION" required value={description} onChange={handleDescriptionChange}/>
                </div>
                <div className="outline">
                    <input className="input_field" type="text" name="code" placeholder="COUPON CODE" required value={code} onChange={handleCodeChange}/>
                </div>
                <div className="button" onClick={addCoupon(name, description, code)} >accept</div>
            </div>
        );
    };

    const showOptionsEvents = () => {
        setMiddlePanel(
            <div id="section_panel">
                <div className="action_panel">
                    <div className="search_bar">
                        <input className="searchbar_input" type="text" name="search" placeholder='search' value={search} onChange={handleSearchChange} />
                        <div className="icon" id="search_icon"></div>
                    </div>
                </div>
                <div className="data_container">
                    {events.map((event, index) => (
                        <div className="information_container" key={index}>
                            <div className="data_field_container">
                                <div className="data_field">{event.name}</div>
                                <div className="data_field">{event.description}</div>
                                <div className="data_field">{event.date}</div>
                            </div>
                            <div className='trash_bin_container' onClick={popupAgreementPanelEvents}></div>
                        </div>
                    ))}
                </div>
            </div>
        );
    };

    const showOptionsCoupon = () => {
        setMiddlePanel(
            <div id="section_panel">
                <div className="action_panel">
                    <div className="search_bar">
                        <input className="searchbar_input" placeholder='search'></input>
                        <div className="icon" id="search_icon"></div>
                    </div>
                </div>
                <div className="data_container">
                    {coupons.map((coupon, index) => (
                        <div className="information_container" key={index}>
                            <div className="data_field_container">
                                <div className="data_field"><p>{coupon.name}</p></div>
                                <div className="data_field"><p>{coupon.description}</p></div>
                                <div className="data_field"><p>{coupon.code}</p></div>
                            </div>
                            <div className='trash_bin_container' onClick={popupAgreementPanelCoupon}></div>
                        </div>
                    ))}
                </div>
            </div>
        );
    };

    const showOptionsUsers = () => {
        setMiddlePanel(
            <div id="section_panel">
                <div className="action_panel">
                    <div className="search_bar">
                        <input className="searchbar_input" placeholder='search'></input>
                        <div className="icon" id="search_icon"></div>
                    </div>
                </div>
                <div className="data_container">
                    {users.map((user, index) => (
                        <div className="information_container" key={index}>
                            <div className="data_field_container">
                                <div className="data_field"><p>{user.first_name}</p></div>
                                <div className="data_field"><p>{user.last_name}</p></div>
                                <div className="data_field"><p>{user.email}</p></div>
                            </div>
                            <div className='trash_bin_container' onClick={popupAgreementPanelUsers}></div>
                        </div>
                    ))}
                </div>
            </div>
        );
    };

    const popupAgreementPanelUsers = () => {
        setPopupPanel(
            <div className="popup_background">
                <div className="agreement_panel">
                    <div className="text_panel">CONFIRM USER DELETION</div>
                    <div className="button">YES</div>
                    <div className="button" id="red_button" onClick={turnOffAgreementPanel}>NO</div>
                </div>
            </div>
        );
    };

    const popupAgreementPanelCoupon = () => {
        setPopupPanel(
            <div className="popup_background">
                <div className="agreement_panel">
                    <div className="text_panel">CONFIRM COUPON DELETION</div>
                    <div className="button">YES</div>
                    <div className="button" id="red_button" onClick={turnOffAgreementPanel}>NO</div>
                </div>
            </div>
        );
    };

    const popupAgreementPanelEvents = () => {
        setPopupPanel(
            <div className="popup_background">
                <div className="agreement_panel">
                    <div className="text_panel">CONFIRM EVENT DELETION</div>
                    <div className="button">YES</div>
                    <div className="button" id="red_button" onClick={turnOffAgreementPanel}>NO</div>
                </div>
            </div>
        );
    };

    const turnOffAgreementPanel = () => {
        setPopupPanel(<div/>)
    };

    const fetchUsers = async () => {
        try {
            const fetchedUsers = await getUsers(search);
            setUsers(fetchedUsers);
        } catch (error) {
            const errorCode = error.code;
            const errorMessage = error.message;

            console.error("fetchUsers failed")
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

            console.error("fetchCoupons failed")
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

            console.error("fetchEvents failed")
            console.error(errorCode + errorMessage);
        }
        ;
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
        <div clas="App">
            {!loggedIn ? (
                loginPage(email, password, handleEmailChange, handlePasswordChange, logInUser)
            ) : (
                mainPage(middlePanel, popupPanel, showOptionsUsers, showChooseMenuEvents, showChooseMenuCoupon, logOutUser)
            )}
        </div>
    );
};

export default App;
