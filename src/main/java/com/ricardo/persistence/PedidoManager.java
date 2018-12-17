package com.ricardo.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ricardo.models.Pedido;

public class PedidoManager {

	private static PedidoManager instance = null;

	private static SessionFactory sfactory;

	public static PedidoManager getInstance() {
		if (instance == null)
			instance = new PedidoManager();
		return instance;

	}

	private PedidoManager() {
		sfactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public static List<Pedido> getPedidos() {
		Session sess = sfactory.openSession();

		List<Pedido> listaPedidos = sess.createQuery("from Pedido").list();

		sess.close();
		return listaPedidos;
	}

}
