import React from "react";
import currencyFormatter from "currency-formatter";

const LancamentoTable = (props) => {
  const rows = props.lancamentos.map((lancamento) => {
    return (
      <tr key={lancamento.id}>
        <td>{String(lancamento.descricao).toUpperCase()}</td>
        <td>
          {currencyFormatter.format(lancamento.valor, { locale: "pt-BR" })}
        </td>
        <td>{lancamento.tipoLancamento}</td>
        <td>{lancamento.mes}</td>
        <td>{lancamento.statusLancamento}</td>
        <td>
          <button type="button" 
                  onClick={ e => props.editarAction(lancamento.id)} className="btn btn-primary">
            Editar
          </button>
          <button type="button"
                  onClick={e => props.deletarAction(lancamento)} 
                  className="btn btn-danger">
            Deletar
          </button>
        </td>
      </tr>
    );
  });

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
      <tbody>{rows}</tbody>
    </table>
  );
};

export default LancamentoTable;
