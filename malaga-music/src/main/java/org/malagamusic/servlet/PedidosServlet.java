package org.malagamusic.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.malagamusic.dao.InstrumentoDAO;
import org.malagamusic.dao.InstrumentoDAOImpl;
import org.malagamusic.dao.PedidoDAO;
import org.malagamusic.dao.PedidoDAOImpl;
import org.malagamusic.model.Clase;
import org.malagamusic.model.Instrumento;
import org.malagamusic.model.Pedido;
import org.malagamusic.model.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "PedidosServlet", value = "/pedidos/*")
public class PedidosServlet extends HttpServlet {

    /**
     * HTTP Method: GET
     * Paths:
     * /pedidos/admin
     * /pedidos/user/{id}
     * /pedidos/borrar
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");
        PedidoDAO pedDao = new PedidoDAOImpl();
        List<Pedido> listaPedidos = pedDao.getAll();

        if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
            // GET
            // /pedidos/admin

            request.setAttribute("listaPedidos", listaPedidos);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/admin-pedidos.jsp");

        } else if (pathParts.length == 3 && "user".equals(pathParts[1])) {
            // GET
            // /pedidos/user/{id}

            PedidoDAO pedidoDAO = new PedidoDAOImpl();
            listaPedidos = pedidoDAO.getAll();

            List<Pedido> listaFiltrada = listaPedidos.stream()
                    .filter(pedido -> pedido.getIdUsuario() == Integer.parseInt(pathParts[2]))
                    .toList();

            request.setAttribute("listaFiltrada", listaFiltrada);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/mis-pedidos.jsp");

        } else if (pathParts.length == 2 && "borrar".equals(pathParts[1])) {
            // GET
            // /pedidos/borrar
            System.out.println("Opción POST no soportada.");
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");

        } else {
            System.out.println("Opción POST no soportada.");
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");
        HttpSession session = request.getSession();

        if (__method__ == null) {
            // Completar pedido
            PedidoDAO pedidoDAO = new PedidoDAOImpl();
            User user = (User) session.getAttribute("userLogin");

            // Guardar ID del usuario
            int idUsuario = user.getIdUser();

            // Guardar lista como String
            List<Instrumento> carrito = (List<Instrumento>) session.getAttribute("carrito");
            String listaCompra = carrito.stream().map(Instrumento::getNombre).collect(Collectors.joining(", "));

            // Guardar fecha de pedido en String con formato
            Date fechaSinFormato = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formato.format(fechaSinFormato);

            // Creación del pedido y asignación de propiedades
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setIdUsuario(idUsuario);
            nuevoPedido.setListaCompra(listaCompra);
            nuevoPedido.setFecha(fecha);
            pedidoDAO.create(nuevoPedido);

            // Restar stock
            carrito.forEach(instrumento -> {
                InstrumentoDAO instDAO = new InstrumentoDAOImpl();
                Optional<Instrumento> optInst = instDAO.find(instrumento.getIdInstrumento());
                Instrumento actualizado = new Instrumento();
                actualizado.setIdInstrumento(optInst.get().getIdInstrumento());
                actualizado.setNombre(optInst.get().getNombre());
                actualizado.setPrecio(optInst.get().getPrecio());
                actualizado.setDescripcion(optInst.get().getDescripcion());
                actualizado.setIdClase(optInst.get().getIdClase());
                actualizado.setIdMarca(optInst.get().getIdMarca());
                actualizado.setStock(optInst.get().getStock() - 1);
                instDAO.update(actualizado);
            });

            // Limpiado de carrito
            carrito.clear();
            session.removeAttribute("carrito");
            session.removeAttribute("precioTotal");

            // Mensaje de pedido completado
            String completado = "Enhorabuena, ¡pedido realizado! Lo recibirá en un máximo de 48 horas.";
            System.out.println(completado);
            request.setAttribute("completado", completado);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/carrito.jsp");
            dispatcher.forward(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar uno existente
            doDelete(request, response);
            response.sendRedirect(request.getContextPath() + "/pedidos/admin");
        } else {
            System.out.println("Opción POST no soportada.");
            response.sendRedirect(request.getContextPath() + "/carrito");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        String cadenaId = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(cadenaId);
            pedidoDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}