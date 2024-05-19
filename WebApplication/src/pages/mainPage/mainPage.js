import React from 'react';
import '../../pages/mainPage/mainPage.css';

export function mainPage(middlePanel, showOptionsUsers, showOptionsEvents, showOptionsCoupon, logOutLogin) {
    return (
        <div id="app_container">
            <div id="left_inner_container">
                <div className="option_panel" id="users_panel" onClick={showOptionsUsers}>
                    <div className="icon" id="users_icon"></div>
                    <div className="text_panel"><p>USERSM</p></div>
                </div>
                <div className="option_panel" id="events_panel" onClick={showOptionsEvents}>
                    <div className="icon" id="events_icon"></div>
                    <div className="text_panel"><p>EVENTS</p></div>
                </div>
                <div className="option_panel" id="coupons_panel" onClick={showOptionsCoupon}>
                    <div className="icon" id="coupons_icon"></div>
                    <div className="text_panel"><p>COUPONS</p></div>
                </div>
            </div>
            <div id="middle_inner_container">
                {middlePanel}
            </div>
            <div id="right_inner_container">
                <div className="option_panel" id="logOut_panel" onClick={logOutLogin}>
                    <div className="text_panel"><p>LOG OUT</p></div>
                    <div className="icon" id="logOut_icon"></div>
                </div>
            </div>
        </div>
    );
}