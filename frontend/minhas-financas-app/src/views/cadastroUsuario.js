import React from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";
import { withRouter } from "react-router-dom";
import UsuarioService from "../services/usuarioService";
import { mensagemErro, mensagemSucesso } from "../components/toastr";

class CadastroUsuario extends React.Component {
  state = {
    nome: "",
    email: "",
    senha: "",
    senhaRepeticao: "",
  };

  constructor() {
    super();
    this.usuarioService = new UsuarioService();
  }

  validar() {
    const msgs = [];

    if (!this.state.nome) {
      msgs.push("Informe o nome do usuário.");
    }

    if (!this.state.email) {
      msgs.push("Informe o email do usuário.");
    } else if (
      !this.state.email.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)
    ) {
      msgs.push("Informe um email válido.");
    }

    if (!this.state.senha) {
      msgs.push("Informe a senha do usuário.");
    } else if (this.state.senha !== this.state.senhaRepeticao) {
      msgs.push("Senha não confere.");
    }

    return msgs;
  }

  cadastrar = () => {

    const msgs = this.validar();
    if (msgs && msgs.length > 0 ){
       msgs.forEach( (msg, index) => {
          mensagemErro(msg)
       })
       return false;
    }

    let user = {
      nome: this.state.nome,
      email: this.state.email,
      senha: this.state.senha,
    };
    this.usuarioService
      .cadastrarUsuario(user)
      .then((response) => {
        mensagemSucesso(
          `Usuário ${response.data.nome} cadastrado com sucesso.`
        );
        this.props.history.push("/login");
      })
      .catch((erro) => mensagemErro(erro.response.data));
  };

  cancelar = () => {
    this.props.history.push("/login");
  };

  render() {
    return (
      <Card title="Cadastro de Usuário">
        <div className="row">
          <div className="col-lg-12">
            <div className="bs-component">
              <FormGroup label="Nome: *" htmlFor="inputNome">
                <input
                  type="text"
                  className="form-control"
                  id="inputNome"
                  name="nome"
                  value={this.state.nome}
                  onChange={(e) => this.setState({ nome: e.target.value })}
                />
              </FormGroup>
              <FormGroup label="Email: *" htmlFor="inputEmail">
                <input
                  type="Email"
                  className="form-control"
                  id="inputEmail"
                  name="email"
                  value={this.state.email}
                  onChange={(e) => this.setState({ email: e.target.value })}
                />
              </FormGroup>
              <FormGroup label="Senha: *" htmlFor="inputSenha">
                <input
                  type="password"
                  className="form-control"
                  id="inputSenha"
                  name="senha"
                  value={this.state.senha}
                  onChange={(e) => this.setState({ senha: e.target.value })}
                />
              </FormGroup>
              <FormGroup label="Repita a senha: *" htmlFor="inputRepitaSenha">
                <input
                  type="password"
                  className="form-control"
                  id="inputRepitaSenha"
                  name="senha"
                  value={this.state.senhaRepeticao}
                  onChange={(e) =>
                    this.setState({ senhaRepeticao: e.target.value })
                  }
                />
              </FormGroup>

              <button
                type="button"
                onClick={this.cadastrar}
                className="btn btn-success"
              >
                Salvar
              </button>
              <button
                type="button"
                onClick={this.cancelar}
                className="btn btn-danger"
              >
                Cancelar
              </button>
            </div>
          </div>
        </div>
      </Card>
    );
  }
}

export default withRouter(CadastroUsuario);
