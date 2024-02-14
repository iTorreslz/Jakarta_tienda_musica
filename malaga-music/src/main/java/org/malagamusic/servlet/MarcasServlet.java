package org.malagamusic.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.malagamusic.dao.MarcaDAO;
import org.malagamusic.dao.MarcaDAOImpl;
import org.malagamusic.model.Marca;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MarcasServlet", value = "/marcas/*")
public class MarcasServlet extends HttpServlet {

    /**
     * HTTP Method: GET
     * Paths:
     * /marcas/admin
     * /marcas/anadir
     * /marcas/borrar
     * /marcas/editar/{id}
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");
        List<Marca> listaMarcas;

        if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
            // GET
            // /marcas/admin

            MarcaDAO marcaDAO = new MarcaDAOImpl();

            listaMarcas = marcaDAO.getAll();

            request.setAttribute("listaMarcas", listaMarcas);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/marcas/admin-marcas.jsp");

        } else if (pathParts.length == 2 && "anadir".equals(pathParts[1])) {
            // GET
            // /marcas/anadir

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/marcas/anadir-marca.jsp");

        } else if (pathParts.length == 2 && "borrar".equals(pathParts[1])) {
            // GET
            // /marcas/borrar


        } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
            // GET
            // /marcas/editar/{id}

            MarcaDAO marcaDAO = new MarcaDAOImpl();
            try {
                request.setAttribute("marca", marcaDAO.find(Integer.parseInt(pathParts[2])));
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/marcas/editar-marca.jsp");
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/marcas/admin-marcas.jsp");
            }

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

        if (__method__ == null) {
            // Crear uno nuevo
            MarcaDAO marcaDAO = new MarcaDAOImpl();
            String nombre = request.getParameter("nombre");
            Marca nuevaMarca = new Marca();
            nuevaMarca.setNombre(nombre);
            marcaDAO.create(nuevaMarca);

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            // Actualizar uno existente
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar uno existente
            doDelete(request, response);
        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/marcas/admin");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        MarcaDAO marcaDAO = new MarcaDAOImpl();
        String cadenaId = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        System.out.println(nombre);
        Marca marca = new Marca();
        try {
            int id = Integer.parseInt(cadenaId);
            marca.setIdMarca(id);
            marca.setNombre(nombre);
            marcaDAO.update(marca);
            System.out.println(marca.getNombre());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        MarcaDAO marcaDAO = new MarcaDAOImpl();
        String cadenaId = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(cadenaId);
            marcaDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}