import React from "react";

const LancamentoTable = (props) => {
  const rows = props.lancamentos.map( (lancamento) => {
      return (
        <tr key={lancamento.id}>
            <td>{lancamento.descricao}</td>
            <td>{lancamento.valor}</td>
            <td>{lancamento.tipoLancamento}</td>
            <td>{lancamento.mes}</td>
            <td>{lancamento.statusLancamento}</td>
            <td>

            </td>
       
        </tr> 
      )
  } )

  return (
    <table className="table table-hover">
      <thead>
        <tr>
          <th scope="col">Descrição</th>
          <th scope="col">Valor</th>
          <th scope="col">Tipo</th>
          <th scope="col">Mês</th>
          <th scope="col">Situação</th>
          <th scope="col">Ações</th>
        </tr>
      </thead>
      <tbody>
         {rows} 
      </tbody>
    </table>
  );
};

export default LancamentoTable;
