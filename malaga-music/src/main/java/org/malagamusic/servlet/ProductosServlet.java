package org.malagamusic.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.malagamusic.dao.*;
import org.malagamusic.model.Clase;
import org.malagamusic.model.Instrumento;
import org.malagamusic.model.Marca;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductosServlet", value = "/productos/*")
public class ProductosServlet extends HttpServlet {
    /**
     * HTTP Method: GET
     * Paths:
     * /productos/
     * /productos/admin
     * /productos/anadir
     * /productos/borrar
     * /productos/editar/{id}
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");
        List<Instrumento> listaInstrumentos;
        List<Clase> listaClases;
        List<Marca> listaMarcas;

        if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
            // GET
            // /productos/admin

            InstrumentoDAO instDAO = new InstrumentoDAOImpl();
            ClaseDAO claseDAO = new ClaseDAOImpl();
            MarcaDAO marcaDAO = new MarcaDAOImpl();

            listaClases = claseDAO.getAll();
            listaMarcas = marcaDAO.getAll();
            listaInstrumentos = instDAO.getAll();

            request.setAttribute("listaClases", listaClases);
            request.setAttribute("listaMarcas", listaMarcas);
            request.setAttribute("listaProductos", listaInstrumentos);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/admin-productos.jsp");

        } else if (pathParts.length == 2 && "anadir".equals(pathParts[1])) {
            // GET
            // /productos/anadir

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/anadir-instrumento.jsp");

        } else if (pathParts.length == 2) {
            // GET
            // /productos/{clase}
            InstrumentoDAO instDAO = new InstrumentoDAOImpl();
            try {
                int clase = Integer.parseInt(pathParts[1]);
                listaInstrumentos = instDAO.getByClass(clase);
                request.setAttribute("listaInst", listaInstrumentos);
                request.setAttribute("clase", pathParts[1]);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/productos.jsp");

            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                dispatcher = request.getRequestDispatcher("/index.jsp");
            }

        } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
            // GET
            // /productos/editar/{id}

            InstrumentoDAO instDAO = new InstrumentoDAOImpl();
            try {
                request.setAttribute("producto", instDAO.find(Integer.parseInt(pathParts[2])));
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/editar-producto.jsp");
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/admin-productos.jsp");
            }

        } else if (pathParts.length == 3 && "detalle".equals(pathParts[1])) {
            // GET
            // /productos/detalle/{id}

            InstrumentoDAO instDAO = new InstrumentoDAOImpl();
            try {
                request.setAttribute("producto", instDAO.find(Integer.parseInt(pathParts[2])));
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/detalle-producto.jsp");
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                dispatcher = request.getRequestDispatcher("/index.jsp");
            }

        } else {
            System.out.println("Opción POST no soportada.");
            dispatcher = request.getRequestDispatcher("/index.jsp");
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");

        if (__method__ == null) {
            // Crear uno nuevo
            InstrumentoDAO instDAO = new InstrumentoDAOImpl();
            String nombre = request.getParameter("nombre");
            String cadenaPrecio = request.getParameter("precio");
            String descripcion = request.getParameter("descripcion");
            String cadenaClase = request.getParameter("clase");
            String cadenaMarca = request.getParameter("marca");
            Instrumento nuevoInst = new Instrumento();

            try {
                double precio = Double.parseDouble(cadenaPrecio);
                int clase = Integer.parseInt(cadenaClase);
                int marca = Integer.parseInt(cadenaMarca);
                nuevoInst.setNombre(nombre);
                nuevoInst.setPrecio(precio);
                nuevoInst.setDescripcion(descripcion);
                nuevoInst.setIdClase(clase);
                nuevoInst.setIdMarca(marca);
                instDAO.create(nuevoInst);
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

        response.sendRedirect(request.getContextPath() + "/productos/admin");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InstrumentoDAO instDAO = new InstrumentoDAOImpl();
        String cadenaId = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String cadenaPrecio = request.getParameter("precio");
        String descripcion = request.getParameter("descripcion");
        String cadenaClase = request.getParameter("clase");
        String cadenaMarca = request.getParameter("marca");
        Instrumento inst = new Instrumento();

        try {
            int id = Integer.parseInt(cadenaId);
            double precio = Double.parseDouble(cadenaPrecio);
            int clase = Integer.parseInt(cadenaClase);
            int marca = Integer.parseInt(cadenaMarca);
            inst.setIdInstrumento(id);
            inst.setNombre(nombre);
            inst.setPrecio(precio);
            inst.setDescripcion(descripcion);
            inst.setIdClase(clase);
            inst.setIdMarca(marca);
            instDAO.update(inst);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        InstrumentoDAO instDAO = new InstrumentoDAOImpl();
        String cadenaId = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(cadenaId);
            instDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}