package com.ricardo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ricardo.models.Pedido;
import com.ricardo.models.StatusMessage;
import com.ricardo.persistence.PedidoManager;

@Path("/pedidos")
public class PedidosService extends JSONService {

	private static final List<Pedido> pedidos = new ArrayList<Pedido>();

	static {
		pedidos.add(new Pedido(1, "Producto 1", 23));
		pedidos.add(new Pedido(2, "Producto 2", 24));
		pedidos.add(new Pedido(3, "Producto 3", 25));
		pedidos.add(new Pedido(4, "Producto 4", 26));
		pedidos.add(new Pedido(5, "Producto 5", 27));
	}

	private static Logger logger = Logger.getLogger("PedidosService");

	@GET
	@Produces("application/json")
	public Response getPedidos() {

		PedidoManager pm= PedidoManager.getInstance();
		List<Pedido> pedidosadevolver= pm.getPedidos();
		return Response.status(200).entity(pedidosadevolver).build();
	}

	@Path("/{id}")
	@GET
	@Produces("application/json")
	public Response getPedido(@PathParam("id") int pid) {

		Pedido pedidoRet = null;

		for (Pedido pedido : pedidos) {
			if (pedido.getPid() == pid) {
				pedidoRet = pedido;
				break;
			}
		}

		if (pedidoRet == null) {
			return Response.status(404).entity(new StatusMessage(404, "El pedido no existe")).build();
		} else {
			return Response.status(200).entity(pedidoRet).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedido(Pedido pedidoNuevo) {
		Response resp = null;
		if (pedidoNuevo.validate()) {
			pedidoNuevo.setPid(pedidos.size() + 1);
			pedidos.add(pedidoNuevo);
			resp = Response.status(200).entity(pedidoNuevo).build();
		} else {
			resp = Response.status(400).entity(new StatusMessage(400, "Pedido incompleto")).build();
		}

		return resp;
	}

	@Path("/{id}")
	@DELETE
	@Produces("application/json")
	public boolean deletePedido(@PathParam("id") int pid) {
		boolean OK = false;

		for (Pedido pedido : pedidos) {
			if (pedido.getPid() == pid) {
				pedidos.remove(pedido);
				OK = true;
				break;
			}
		}

		return OK;
	}

	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean actualizarPedido(@PathParam("id") int pid, Pedido unPedido) {
		boolean OK = false;

		for (Pedido pedido : pedidos) {
			if (pedido.getPid() == pid) {
				pedidos.remove(pedido);
				pedidos.add(unPedido);
				OK = true;
				break;
			}
		}

		return OK;
	}

}
