package org.malagamusic.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.malagamusic.dao.*;
import org.malagamusic.model.Clase;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClasesServlet", value = "/clases/*")
public class ClasesServlet extends HttpServlet {

    /**
     * HTTP Method: GET
     * Paths:
     * /clases/{idFamilia}
     * /clases/admin
     * /clases/anadir
     * /clases/borrar
     * /clases/editar/{id}
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");
        List<Clase> listaClases;

        if (pathParts.length == 2 && pathParts[1].matches("[0-9]+")) {
            // GET
            // /clases/{idFamilia}

            ClaseDAO claseDAO = new ClaseDAOImpl();
            try {
                int familia = Integer.parseInt(pathParts[1]);
                listaClases = claseDAO.getByFamily(familia);
                request.setAttribute("listaClases", listaClases);
                request.setAttribute("familia", pathParts[1]);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/clases/clases.jsp");

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                dispatcher = request.getRequestDispatcher("/index.jsp");
            }

        } else if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
            // GET
            // /clases/admin

            ClaseDAO claseDAO = new ClaseDAOImpl();

            listaClases = claseDAO.getAll();

            request.setAttribute("listaClases", listaClases);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/clases/admin-clases.jsp");

        } else if (pathParts.length == 2 && "anadir".equals(pathParts[1])) {
            // GET
            // /clases/anadir

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/clases/anadir-clase.jsp");

        } else if (pathParts.length == 2 && "borrar".equals(pathParts[1])) {
            // GET
            // /clases/borrar


        } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
            // GET
            // /clases/editar/{id}

            ClaseDAO claseDAO = new ClaseDAOImpl();
            try {
                request.setAttribute("clase", claseDAO.find(Integer.parseInt(pathParts[2])));
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/clases/editar-clase.jsp");
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/clases/admin-clases.jsp");
            }

        } else {
            System.out.println("Opción POST no soportada.");
            dispatcher = request.getRequestDispatcher("/index.jsp");
        }
        dispatcher.forward(request, response);
    }

    /**
     * HTTP Method: POST
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear uno nuevo
            ClaseDAO claseDAO = new ClaseDAOImpl();
            String nombre = request.getParameter("nombre");
            String cadenaFamilia = request.getParameter("familia");
            Clase nuevaClase = new Clase();

            try {
                int familia = Integer.parseInt(cadenaFamilia);
                nuevaClase.setNombre(nombre);
                nuevaClase.setIdFamilia(familia);
                claseDAO.create(nuevaClase);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            // Actualizar uno existente
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar uno existente
            doDelete(request, response);
        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/clases/admin");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ClaseDAO claseDAO = new ClaseDAOImpl();
        String cadenaId = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String cadenaFamilia = request.getParameter("familia");
        Clase clase = new Clase();

        try {
            int id = Integer.parseInt(cadenaId);
            int familia = Integer.parseInt(cadenaFamilia);
            clase.setIdClase(id);
            clase.setNombre(nombre);
            clase.setIdFamilia(familia);
            claseDAO.update(clase);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        ClaseDAO claseDAO = new ClaseDAOImpl();
        String cadenaId = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(cadenaId);
            claseDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}