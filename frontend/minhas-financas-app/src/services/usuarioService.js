import ApiService from "./apiservice";


class UsuarioService extends ApiService {

    constructor(){
        super('/usuarios')
    }

    autenticar(credenciais) {
        return this.post('/autenticar', credenciais);
    }

    obterSaldoPorUsuario(id){
        return this.get(`/${id}/saldo`)
    }

    cadastrarUsuario(usuario) {
        return this.post('/', usuario);
    }

}

export default UsuarioService;
