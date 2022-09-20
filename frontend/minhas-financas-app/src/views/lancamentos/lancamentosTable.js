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
          <button type="button" title="Efetivar" 
                  onClick={ e => props.efetivarAction(lancamento, 'EFETIVADO')} className="btn btn-success">
            <i class="pi pi-check-circle"></i>
          </button>
          <button type="button" title="Cancelar"
                  onClick={ e => props.cancelarAction(lancamento, 'CANCELADO')} className="btn btn-danger">
           <i class="pi pi-times-circle"></i> 
          </button>
          <button type="button" title="Editar"
                  onClick={ e => props.editarAction(lancamento.id)} className="btn btn-primary">
            <i class="pi pi-pencil"></i>  
          </button>
          <button type="button" title="Excluir"
                  onClick={e => props.deletarAction(lancamento)} 
                  className="btn btn-danger">
            <i class="pi pi-trash"></i>  
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
