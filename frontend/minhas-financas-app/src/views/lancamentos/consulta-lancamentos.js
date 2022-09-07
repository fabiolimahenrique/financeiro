import React from "react";
import { withRouter } from "react-router-dom";
import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/selectMenu";
import LancamentoService from "../../services/lancamentoService";
import LancamentoTable from "./lancamentosTable";
import LocalStorageService from "../../services/localStorageService";

class ConsultaLancamentos extends React.Component {

  state = {
    ano: '',
    mes: '',
    tipoLancamento: '',
    statusLancamento: '',
    lancamentos: []
  }

  constructor(){
    super();
    this.service = new LancamentoService(); 
  }

  buscar = () => {
    const usuarioLogado  = LocalStorageService.obterItem("_usuario_logado");

     const lancamentoFiltro = {
         ano: this.state.ano,
         mes: this.state.mes,
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

  render() {
    const meses = [
      { label: "Selecione...", value: "" },
      { label: "Janeiro", value: 1 },
      { label: "Fevereiro", value: 2 },
      { label: "Março", value: 3 },
      { label: "Abril", value: 4 },
      { label: "Maio", value: 5 },
      { label: "Junho", value: 6 },
      { label: "Julho", value: 7 },
      { label: "Agosto", value: 8 },
      { label: "Setembro", value: 9 },
      { label: "Outubro", value: 10 },
      { label: "Novembro", value: 11 },
      { label: "Dezembro", value: 12 },
    ];

    const tipos = [
      { label: "Selecione...", value: "" },
      { label: "Receita", value: "RECEITA" },
      { label: "Despesa", value: "DESPESA" },
    ];

    

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
                 <LancamentoTable  lancamentos={this.state.lancamentos}/>
              </div>
                
            </div>
        </div>
      </Card>
    );
  }
}

export default withRouter(ConsultaLancamentos);
