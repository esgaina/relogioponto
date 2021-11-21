package bo;

import dao.LoginDAO;
import entity.Login;
import exception.NegocioException;
import exception.ValidacaoException;
import util.MensagensUtil;
import java.util.List;
import javax.swing.JOptionPane;

public class LoginBO {
    
    public boolean logar(Login login) {
        boolean resultado = false;
        try {
            if (login.getNome() == null || "".equals(login.getNome())) {
                MensagensUtil.addMsg(null, "Usuário obrigatório");
            } else if (login.getSenha() == null || "".equals(login.getSenha())) {
                MensagensUtil.addMsg(null, "Senha obrigatória");
            } else if (login.getNome().length() > 50) {
                MensagensUtil.addMsg(null, "Usuário excede os 50 caracteres");
            } else if (login.getSenha().length() > 20) {
                MensagensUtil.addMsg(null, "Senha excede os 50 caracteres");
            } else {
                new LoginDAO().findByLogin(login.getNome(), login.getSenha());
                resultado = true;
            }
        } catch (Exception e) {
            MensagensUtil.addMsg(null, "Não foram encontrados valores na base de dados\nLog de Erro: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public boolean criarLogin(Login login) {
        boolean resultado = false;
        try {
            if (login.getNome() == null || "".equals(login.getNome())) {
                MensagensUtil.addMsg(null, "Login obrigatório");
            } else if (login.getSenha() == null || "".equals(login.getSenha())) {
                MensagensUtil.addMsg(null, "Senha obrigatória");
            } else if (login.getNome().length() > 50) {
                MensagensUtil.addMsg(null, "Login excede os 50 caracteres");
            } else if (login.getSenha().length() > 20) {
                MensagensUtil.addMsg(null, "Senha excede os 50 caracteres");
            } else {
                new LoginDAO().save(login);
                MensagensUtil.addMsg(null, "Login criado com sucesso");
                resultado = true;
            }
        } catch (Exception e) {
            MensagensUtil.addMsg(null, "Erro ao criar login\nLog de Erro: " + e.getMessage());
        }
        return resultado;
    }
    
    public boolean validaUtilizador(String utilizador, String login,
            String senha) throws ValidacaoException {
        boolean ehValido = true;
         if (utilizador.length() > 30) {
            ehValido = false;
            throw new ValidacaoException("Campo Utilizador excede 30 caracteres.");
        } else if (login == null || login.equals("")) {
            ehValido = false;
            throw new ValidacaoException("Campo Login não pode ser nulo.");
        } else if (login.length() > 50) {
            ehValido = false;
            throw new ValidacaoException("Campo Login excede 50 caracteres.");
        } else if (senha == null || senha.equals("")) {
            ehValido = false;
            throw new ValidacaoException("Campo Senha não pode ser nulo.");
        } else if (senha.length() > 20) {
            ehValido = false;
            throw new ValidacaoException("Campo Senha excede 20 caracteres.");
        } 
        return ehValido;
    }
    
    public void addUtilizador(Login login) throws NegocioException {
        try {
            new LoginDAO().save(login);
            JOptionPane.showMessageDialog(null, "Novo Utilizador salvo com sucesso.", "Salvar Utilizador", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            throw new NegocioException(exception.getMessage());            
        }
    }
    
    public void update(Login login) throws NegocioException {
        try {
            new LoginDAO().update(login);
            JOptionPane.showMessageDialog(null, "Dados de Utilizador alterados com sucesso.", "Alterar Utilizador", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            throw new NegocioException(exception.getMessage());            
        }
    }
    
    public String[][] listagem() throws NegocioException {
        int numCols = 3;
        String[][] listaRetorno = null;
        try {
            LoginDAO lDAO = new LoginDAO();
            List<Login> lista = lDAO.findAll();
            listaRetorno = new String[lista.size()][numCols];

            for (int i = 0; i < lista.size(); i++) {
                Login login = lista.get(i);
                listaRetorno[i][0] = login.getUtilizador();
                listaRetorno[i][1] = login.getNivel();
                listaRetorno[i][2] = login.getNome();
            }
        } catch (Exception exception) {
            throw new NegocioException(exception.getMessage());
        }
        return listaRetorno;
    }
}
