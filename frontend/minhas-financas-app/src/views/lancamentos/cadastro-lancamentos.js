import React from "react";
import { withRouter } from "react-router-dom";
import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/selectMenu";
import LancamentoService from "../../services/lancamentoService";
import * as messages from "../../components/toastr";
import LocalStorageService from "../../services/localStorageService";
import { ConfirmDialog } from 'primereact/confirmdialog';

class CadastroLancamentos extends React.Component {
  state = {
    ano: "",
    mes: "",
    descricao: "",
    valor: null,
    tipoLancamento: "",
    usuario: null,
    showConfirmDialog: false
  };

  constructor() {
    super();
    this.service = new LancamentoService();
  }

  validar() {
    const msgs = [];

    if (!this.state.descricao) {
      msgs.push("Informe a descrição do lancamento.");
    }

    if (!this.state.ano) {
        msgs.push("Informe o ano do lancamento.");
    }

    if (!this.state.mes) {
        msgs.push("Informe o mes do lancamento.");
    }  

    if (!this.state.valor) {
        msgs.push("Informe o valor do lancamento.");
    }  

    return msgs;
  }
  
  limparCampos = () => {
    this.setState({
        ano: "",
        mes: "",
        descricao: "",
        valor: "",
        tipoLancamento: "",
        usuario: null,
        showConfirmDialog: false
      })
  }


  obterLancamento = () => {
    const usuarioLogado  = LocalStorageService.obterItem("_usuario_logado");
    return {
        descricao: this.state.descricao,
        ano: this.state.ano,
        mes: this.state.mes,
        valor: this.state.valor,
        tipoLancamento: this.state.tipoLancamento ,
        usuario: usuarioLogado.id
    }
  }

  cadastrar = () => {
    const msgs = this.validar();
    if (msgs && msgs.length > 0 ){
       msgs.forEach( (msg, index) => {
          messages.mensagemErro(msg)
       })
       return false;
    }

      const lancamento = this.obterLancamento();
      this.service.cadastrar(lancamento).then( response => {
        this.limparCampos();
        messages.mensagemSucesso('Lançamento cadastrado com sucesso.') 
      }).catch ( error => {
        messages.mensagemErro(error.data);
      } )
  }

  confirmaCancelar = () => {

    if (this.state.descricao ||  this.state.ano || this.state.mes || this.state.valor ) {
        this.setState({showConfirmDialog : true})
    } else {
        this.cancelar();
    }
  }

  cancelar = () => {
    this.props.history.push("/home");
  };

  render() {
    const meses = this.service.obterListaMeses();
    const tipos = this.service.obterListaTipos();
    return (
      <Card title="Cadastro de lançamentos">
        <div className="row">
          <FormGroup htmlfor="inputDescricao" label="Descrição *">
            <input
              type="text"
              className="form-control"
              value={this.state.descricao}
              onChange={ e => this.setState({ descricao: e.target.value })}
              id="inputDescricao"
            />
          </FormGroup>
        </div>
        <div className="row">
          <div className="col-md-6">
            <FormGroup htmlFor="inputAno" label="Ano: *">
              <input
                type="text"
                className="form-control"
                value={this.state.ano}
                onChange={(e) => this.setState({ ano: e.target.value })}
                id="inputAno"
              />
            </FormGroup>

            <FormGroup htmlfor="inputValor" label="Valor">
              <input
                type="text"
                className="form-control"
                value={this.state.valor}
                onChange={ e => this.setState({ valor: e.target.value })}
                id="inputValor"
              />
            </FormGroup>
          </div>
          <div className="col-md-6">
            <FormGroup htmlFor="inputMes" label="Mês: ">
              <SelectMenu
                id="inputMes"
                value={this.state.mes}
                onChange={(e) => this.setState({ mes: e.target.value })}
                className="form-control"
                lista={meses}
              />
            </FormGroup>
            <FormGroup htmlFor="inputTipo" label="Tipo: ">
              <SelectMenu
                id="inputTipo"
                value={this.state.tipoLancamento}
                onChange={(e) => this.setState({ tipoLancamento: e.target.value })}
                className="form-control"
                lista={tipos}
              />
            </FormGroup>
          </div>
          <div>
            < br/> 
            <button
              type="button"
              onClick={this.cadastrar}
              className="btn btn-success"
            >
              Salvar
            </button>
            <button
              type="button"
              onClick={this.confirmaCancelar}
              className="btn btn-danger"
            >
              Cancelar
            </button>
          </div>
        </div>
        <div>
        <ConfirmDialog visible={this.state.showConfirmDialog} 
                       onHide={() => this.setState({showConfirmDialog: false})} 
                       message="Existe um lancamento em edição. Deseja descartar?"
                       header="Confirmação" 
                       icon="pi pi-exclamation-triangle" 
                       acceptLabel="Sim"
                       rejectLabel="Não"
                       accept={this.cancelar} 
                       //reject={reject} 
                       />
        </div>
      </Card>
    );
  }
}

export default withRouter(CadastroLancamentos);
