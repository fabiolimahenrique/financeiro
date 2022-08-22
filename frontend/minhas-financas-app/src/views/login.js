import React from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";

class Login extends React.Component {

state = {
    email: '',
    senha: ''
}

entrar = () => {
    console.log("Email: ", this.state.email)
    console.log("Senha: ", this.state.senha)
}

  render() {
    return (
      <div className="container">
        <div className="row">
          <div
            className="col-md-6"
            style={{ position: "relative", left: "300px" }}
          >
            <div className="bs-docs-section">
              <Card title="Login">
                <div class="row">
                  <div class="col-lg-12">
                    <div class="bs-component">
                      <fieldset>
                        <FormGroup label="Email: *" htmlFor="identEmail">
                          <input
                            type="email"
                            value={this.state.email}
                            onChange={ e => this.setState({email: e.target.value}) }
                            className="form-control"
                            id="identEmail"
                            aria-describedby="emailHelp"
                            placeholder="Digite o Email"
                          />
                        </FormGroup>
                        <FormGroup label="Senha: *" htmlFor="identPass">
                          <input
                            type="password"
                            value={this.state.senha}
                            onChange={ e => this.setState({senha: e.target.value}) }
                            className="form-control"
                            id="identPass"
                            placeholder="Password"
                          />
                        </FormGroup>
                        <button
                          onClick={this.entrar}
                          type="button"
                          className="btn btn-success"
                        >
                          Entrar
                        </button>
                        <button
                          onClick=""
                          type="button"
                          className="btn btn-danger"
                        >
                          Cadastrar
                        </button>
                      </fieldset>
                    </div>
                  </div>
                </div>
              </Card>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;
