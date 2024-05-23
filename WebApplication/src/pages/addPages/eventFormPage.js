import React from 'react';
import '../../pages/mainPage/mainPage.css';

export function eventFormPage(name ,handleNameChange, description, handleDescriptionChange,date,handleDateChange,addNewEvent,showOptionsEvent){
    return (
        <div id='middle_inner_container'>
        <div className="add_form">
        <div className="text_panel">ADD NEW EVENT</div>
        <div className="outline">
            <input 
                className="input_field"
                type="text" 
                name="name" 
                placeholder="EVENT NAME" 
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
            placeholder="EVENT DESCRIPTION" 
            value={description}
            required onChange={handleDescriptionChange}/>
        </div>
        <div className="outline">
            <input 
            className="input_field"
            type="date" 
            name="code"
            placeholder="COUPON CODE" 
            required 
            value={date}
            onChange={handleDateChange}/>
        </div>
        <div className="buttons_container">
        <div className="button" id='red_button' onClick={showOptionsEvent} >back</div>
        <div className="button" onClick={addNewEvent} >add</div>
        </div>
    </div>
    </div>
    )
}