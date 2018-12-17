package com.netmind.DAO;


public abstract class UsuarioDAO extends DAO {
	public abstract com.ricardo.models.Usuario getUsuario(String username, String password) throws Exception;
	public abstract com.ricardo.models.Usuario getUsuarioByMail(String email) throws Exception;
}
