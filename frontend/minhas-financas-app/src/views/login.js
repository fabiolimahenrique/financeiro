import React from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";
import UsuarioService from "../services/usuarioService";

class Login extends React.Component {
  constructor() {
    super();
    this.service = new UsuarioService();
  }

  state = {
    email: "",
    senha: "",
    mensagemErro: "",
  };

  entrar = async () => {
    let user = {
      email: this.state.email,
      senha: this.state.senha,
    };

    await this.service.autenticar(user)
      .then((response) => {
        localStorage.setItem("_usuario_logado", JSON.stringify(response.data));
        this.props.history.push("/home");
      })
      .catch((err) => {
        this.setState({ mensagemErro: err.response.data });
        console.log(err.response);
      });
  };

  preparaCadastro = () => {
    this.props.history.push("/cadastro-usuarios");
  };

  render() {
    return (
      <div className="row">
        <div
          className="col-md-6"
          style={{ position: "relative", left: "300px" }}
        >
          <div className="bs-docs-section">
            <Card title="Login">
              <div className="row">
                <span>{this.state.mensagemErro}</span>
              </div>
              <div class="row">
                <div class="col-lg-12">
                  <div class="bs-component">
                    <fieldset>
                      <FormGroup label="Email: *" htmlFor="identEmail">
                        <input
                          type="email"
                          value={this.state.email}
                          onChange={(e) =>
                            this.setState({ email: e.target.value })
                          }
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
                          onChange={(e) =>
                            this.setState({ senha: e.target.value })
                          }
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
                        type="button"
                        onClick={this.preparaCadastro}
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
    );
  }
}

export default Login;
