import React from "react";
import { withRouter } from "react-router-dom";
import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/selectMenu";
import LancamentoService from "../../services/lancamentoService";
import LancamentoTable from "./lancamentosTable";
import LocalStorageService from "../../services/localStorageService";
import * as messages from "../../components/toastr";
import { ConfirmDialog } from 'primereact/confirmdialog';

class ConsultaLancamentos extends React.Component {

  state = {
    ano: '',
    mes: '',
    descricao: '',
    tipoLancamento: '',
    statusLancamento: '',
    showConfirmDialog: false,
    lancamentoDeletar: {},
    lancamentos: []
  }

  constructor(){
    super();
    this.service = new LancamentoService(); 
  }

  buscar = () => {

    if(!this.state.ano) {
      messages.mensagemErro('Informe o ano para busca.')
      return false;
    }

    const usuarioLogado  = LocalStorageService.obterItem("_usuario_logado");

     const lancamentoFiltro = {
         ano: this.state.ano,
         mes: this.state.mes,
         descricao: this.state.descricao,
         tipoLancamento: this.state.tipoLancamento,
         statusLancamento: this.state.statusLancamento,
         usuario: usuarioLogado.id
     }

     this.service.consutar(lancamentoFiltro)
          .then( response => {
             this.setState({ lancamentos: response.data })  
          }).catch ( error =>  {
            console.log(error.data)
          }  )  

  }


  confirmaExclusao = (lancamento) => {
    this.setState({showConfirmDialog : true, lancamentoDeletar: lancamento})
  }

  deletar = () => {
    this.service.deletar(this.state.lancamentoDeletar.id).then( response => {
       const lancamentos = this.state.lancamentos;
       const index = lancamentos.indexOf(this.state.lancamentoDeletar);
       lancamentos.splice(index, 1);
       this.setState(lancamentos);
       messages.mensagemAviso('Lancamento excluido com sucesso.')
    }).catch (error => {
       messages.mensagemErro(error.data)
    })

  }

  render() {
    const meses = this.service.obterListaMeses();

    const tipos = this.service.obterListaTipos();

   
    return (
      <Card title="Consulta Lançamentos">
        <div className="row">
          <div className="col-md-6">
            <div className="bs-component">
              <FormGroup htmlFor="inputAno" label="Ano: *">
                <input type="text" 
                       className="form-control"
                       value={this.state.ano} 
                       onChange={ e => this.setState({ ano: e.target.value })}
                       id="inputAno" />
              </FormGroup>
              <FormGroup htmlFor="inputMes" label="Mês: ">
                <SelectMenu id="inputMes" 
                            value={this.state.mes}
                            onChange={e => this.setState({ mes: e.target.value})}
                            className="form-control" 
                            lista={meses} />
              </FormGroup>
              <FormGroup htmlFor="inputDescricao" label="Descrição: ">
                <input id="inputDescricao" 
                            value={this.state.descricao}
                            onChange={e => this.setState({ descricao: e.target.value})}
                            className="form-control" 
                            />
              </FormGroup>
              <FormGroup htmlFor="inputTipo" label="Tipo: ">
                <SelectMenu
                  id="inputTipo"
                  value={this.state.tipo}
                  onChange={ e => this.setState({ tipo: e.target.value }) }
                  className="form-control"
                  lista={tipos}
                />
              </FormGroup>
              <br /> 
              <button type="button"  onClick={this.buscar} className="btn btn-success">
                Buscar
              </button>
              <button type="button" className="btn btn-danger">
                Cadastrar
              </button>
            </div>
          </div>
        </div>
        <br />
        <div className="row">
            <div className="col-md-12">
              <div className="bs-component">
                 <LancamentoTable  lancamentos={this.state.lancamentos}  deletarAction={this.confirmaExclusao} />
              </div>
            </div>
        </div>
        <ConfirmDialog visible={this.state.showConfirmDialog} 
                       onHide={() => this.setState({showConfirmDialog: false})} 
                       message="Deseja excluir o lancamento?"
                       header="Confirmação" 
                       icon="pi pi-exclamation-triangle" 
                       acceptLabel="Sim"
                       rejectLabel="Não"
                       accept={this.deletar} 
                       //reject={reject} 
                       />
        <div>

        </div>
      </Card>
    );
  }
}

export default withRouter(ConsultaLancamentos);
