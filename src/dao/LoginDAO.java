package dao;

import entity.Login;

public class LoginDAO extends GenericDAO<Login>{
    public LoginDAO() {
        super(Login.class);
    }
    
    public Login findByLogin(String nome) {
	String jpql = "from Login l where l.nome like ?";
		
	return findOne(jpql, nome);
    }
    
    public Login findByLogin(String nome, String senha) {
	String jpql = "from Login l where l.nome like ? and l.senha like ?";
		
	return findOne(jpql, nome, senha);
    }
    
}
