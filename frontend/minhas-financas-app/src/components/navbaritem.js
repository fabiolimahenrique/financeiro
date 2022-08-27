import React from "react";

function Navbaritem(props){
    return(
        <li class="nav-item">
            <a class="nav-link" 
            href={props.href}>{props.label}</a>
          </li>
    )
}

export default Navbaritem;