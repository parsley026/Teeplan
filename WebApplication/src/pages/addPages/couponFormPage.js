import React from 'react';
import '../../pages/mainPage/mainPage.css';

export function couponFormPage(name ,handleNameChange, description, handleDescriptionChange,code,handleCodeChange,addNewCoupon,showOptionsCoupon){
    return (
        <div id='middle_inner_container'>
        <div className="add_form">
        <div className="text_panel">ADD NEW COUPON</div>
        <div className="outline">
            <input 
                className="input_field"
                type="text" 
                name="name" 
                placeholder="COUPON NAME" 
                required 
                value={name}
                onChange={handleNameChange}
            />
        </div>
        <div className="outline">
            <textarea 
            className="input_field" 
            type="text" 
            name="description" 
            placeholder="COUPON DESCRIPTION" 
            value={description}
            required onChange={handleDescriptionChange}/>
        </div>
        <div className="outline">
            <input 
            className="input_field"
            type="text" 
            name="code"
            placeholder="COUPON CODE" 
            required 
            value={code}
            onChange={handleCodeChange}/>
        </div>
        <div className="buttons_container">
        <div className="button" id='red_button' onClick={showOptionsCoupon} >back</div>
        <div className="button" onClick={addNewCoupon} >add</div>
        </div>
    </div>
    </div>
    )
}