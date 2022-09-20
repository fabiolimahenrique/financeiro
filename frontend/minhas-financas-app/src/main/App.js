import React from "react";
import Rotas from "../main/rotas";
import Navbar from "../components/navbar";
import "toastr/build/toastr.min.js";

import "bootswatch/dist/flatly/bootstrap.css";
import "../styles/custom.css";
import 'toastr/build/toastr.css';

import "primereact/resources/themes/lara-light-indigo/theme.css";  //theme
import "primereact/resources/primereact.min.css";                  //core css
import "primeicons/primeicons.css";                                //icons
import ProvedorAutenticacao from "./provedorAutenticacao";
 

class App extends React.Component {
  render() {
    return (
      <ProvedorAutenticacao>
        <Navbar />
        <div className="container">
          <Rotas />
        </div>
      </ProvedorAutenticacao>
    );
  }
}

export default  App;
