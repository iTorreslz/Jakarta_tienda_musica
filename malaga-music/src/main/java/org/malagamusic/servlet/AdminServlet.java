package org.malagamusic.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminServlet", value = "/admin/*")
public class AdminServlet extends HttpServlet {
    /**
     * HTTP Method: GET
     * Paths:
     * /admin/
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            //GET
            //	/admin/

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        } else {
            System.out.println("Opción POST no soportada.");
            dispatcher = request.getRequestDispatcher("index.jsp");
        }
        dispatcher.forward(request, response);
    }
}