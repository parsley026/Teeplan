import React from 'react';

export function mainPage(middlePanel, showOptionsUsers, showOptionsEvents, showOptionsCoupon, logOutLogin) {
    return (
        <div id="middle_container">{
            <div id="screen_after_login">
                <div id="left_inner_container">
                    <div class="option_panel" id="users_panel" onClick={showOptionsUsers}>
                        <div class="icon" id="users_icon"></div>
                        <div class="text_panel"><p>USERS</p></div>
                    </div>
                    <div class="option_panel" id="events_panel" onClick={showOptionsEvents}>
                        <div class="icon" id="events_icon"></div>
                        <div class="text_panel"><p>EVENTS</p></div>
                    </div>
                    <div class="option_panel" id="coupons_panel" onClick={showOptionsCoupon}>
                        <div class="icon" id="coupons_icon"></div>
                        <div class="text_panel"><p>COUPONS</p></div>
                    </div>
                </div>
                <div id="middle_inner_container">
                    {middlePanel}
                </div>
                <div id="right_inner_container">
                    <div class="option_panel" id="logOut_panel" onClick={logOutLogin}>
                        <div class="text_panel_right"><p>LOG OUT</p></div>
                        <div class="icon" id="logOut_icon"></div>
                    </div>
                </div>
            </div>
        }</div>
    )
}