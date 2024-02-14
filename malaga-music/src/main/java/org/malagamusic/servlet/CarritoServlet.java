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
import org.malagamusic.model.Instrumento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CarritoServlet", value = "/carrito/*")
public class CarritoServlet extends HttpServlet {

    /**
     * HTTP Method: GET
     * Paths:
     * /carrito/
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            //GET
            //	/carrito/

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/carrito.jsp");
        } else {
            System.out.println("Opción POST no soportada.");
            dispatcher = request.getRequestDispatcher("index.jsp");
        }
        dispatcher.forward(request, response);
    }

    /**
     * HTTP Method: POST
     * Paths:
     * /carrito/anadir
     * /carrito/borrar
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;

        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length == 2 && "anadir".equals(pathParts[1])) {
            // Añadir nuevo producto al carrito
            InstrumentoDAO instDAO = new InstrumentoDAOImpl();
            String idInstrumento = request.getParameter("id");
            Optional<Instrumento> optInst = instDAO.find(Integer.parseInt(idInstrumento));
            HttpSession session = request.getSession();

            if (optInst.get().getStock() == 0) {
                String noStock = "¡Lo sentimos! No hay stock disponible ahora mismo para este producto.";
                System.out.println(noStock);
                request.setAttribute("no-stock", noStock);
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/carrito.jsp");
                dispatcher.forward(request, response);
            } else {
                Instrumento newInst = new Instrumento();
                newInst.setIdInstrumento(optInst.get().getIdInstrumento());
                newInst.setNombre(optInst.get().getNombre());
                newInst.setPrecio(optInst.get().getPrecio());
                newInst.setDescripcion(optInst.get().getDescripcion());
                newInst.setIdClase(optInst.get().getIdClase());
                newInst.setIdMarca(optInst.get().getIdMarca());
                newInst.setStock(optInst.get().getStock());

                if (session.getAttribute("carrito") == null) {
                    List<Instrumento> carrito = new ArrayList<>();
                    carrito.add(newInst);

                    double precioTotal = newInst.getPrecio();

                    session.setAttribute("carrito", carrito);
                    session.setAttribute("precioTotal", precioTotal);
                    response.sendRedirect(request.getContextPath() + "/carrito");
                } else {
                    List<Instrumento> carrito = (List<Instrumento>) session.getAttribute("carrito");

                    int contador = 0, stock = 0;
                    boolean hayInstrumento = false;
                    for (Instrumento instrumento : carrito) {
                        if (instrumento.getIdInstrumento() == newInst.getIdInstrumento()) {
                            contador++;
                            stock = instrumento.getStock();
                            hayInstrumento = true;
                        }
                    }
                    contador++;
                    if (hayInstrumento && contador > stock) {
                        String noStock = "¡Lo sentimos! Usted intenta añadir más productos de los que hay en stock.";
                        System.out.println(noStock);
                        request.setAttribute("no-stock", noStock);
                        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/carrito.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        carrito.add(newInst);

                        double precioTotal = (double) session.getAttribute("precioTotal");
                        precioTotal += newInst.getPrecio();

                        session.setAttribute("carrito", carrito);
                        session.setAttribute("precioTotal", precioTotal);
                        response.sendRedirect(request.getContextPath() + "/carrito");
                    }
                }
            }

        } else if (pathParts.length == 2 && "borrar".equals(pathParts[1])) {

            String cadenaId = request.getParameter("codigo");
            int id = Integer.parseInt(cadenaId);
            HttpSession session = request.getSession();
            List<Instrumento> listaInstrumentos = (List<Instrumento>) session.getAttribute("carrito");
            double precioTotal = (Double) session.getAttribute("precioTotal");
            for (Instrumento instrumento : listaInstrumentos) {
                if (id == instrumento.getIdInstrumento()) {
                    listaInstrumentos.remove(instrumento);
                    precioTotal -= instrumento.getPrecio();
                    break;
                }
            }
            if (listaInstrumentos.isEmpty()) {
                session.removeAttribute("precioTotal");
                session.removeAttribute("carrito");
            } else {
                session.setAttribute("precioTotal", precioTotal);
                session.setAttribute("carrito", listaInstrumentos);
            }
            response.sendRedirect(request.getContextPath() + "/carrito");

        } else {
            System.out.println("Opción POST no soportada.");
            response.sendRedirect(request.getContextPath() + "/carrito");
        }
    }
}