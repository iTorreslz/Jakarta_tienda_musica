package org.malagamusic.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.malagamusic.dao.*;
import org.malagamusic.model.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "UsersServlet", value = "/users/*")
public class UsersServlet extends HttpServlet {
    /**
     * HTTP Method: GET
     * Paths:
     * /users/admin
     * /users/anadir
     * /users/borrar
     * /users/editar/{id}
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");
        List<User> listaUsers;

        if (pathParts.length == 2 && "admin".equals(pathParts[1])) {
            // GET
            // /users/admin

            UserDAO userDAO = new UserDAOImpl();
            listaUsers = userDAO.getAll();

            request.setAttribute("listaUsers", listaUsers);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/admin-users.jsp");

        } else if (pathParts.length == 2 && "login".equals(pathParts[1])) {
            // GET
            // /users/login

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/login.jsp");

        } else if (pathParts.length == 2 && "registro".equals(pathParts[1])) {
            // GET
            // /users/registro

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/registro.jsp");

        } else if (pathParts.length == 2 && "anadir".equals(pathParts[1])) {
            // GET
            // /users/anadir

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/anadir-user.jsp");

        } else if (pathParts.length == 2 && "micuenta".equals(pathParts[1])) {
            // GET
            // /users/micuenta

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/mi-cuenta.jsp");

        } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
            // GET
            // /users/editar/{id}

            UserDAO userDAO = new UserDAOImpl();
            try {
                request.setAttribute("user", userDAO.find(Integer.parseInt(pathParts[2])));
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/users/editar-user.jsp");
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

        String __method__ = request.getParameter("__method__");
        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length == 2 && "anadir".equals(pathParts[1])) {
            // Crear uno nuevo
            UserDAO userDAO = new UserDAOImpl();
            List<User> listaUsers = userDAO.getAll();

            String usuario = request.getParameter("usuario");
            String passwd = request.getParameter("passwd");
            String rol = request.getParameter("rol");
            String cliente = request.getParameter("cliente");

            User nuevoUser = new User();
            nuevoUser.setUser(usuario);
            nuevoUser.setPasswd(passwd);
            nuevoUser.setRol(rol);
            boolean disponible = true;

            for (User user : listaUsers) {
                if (user.getUser().equals(nuevoUser.getUser())) {
                    disponible = false;
                }
            }

            if (cliente.equals("yes")) {
                if (disponible) {
                    userDAO.create(nuevoUser);
                    response.sendRedirect(request.getContextPath() + "/");
                } else {
                    response.sendRedirect(request.getContextPath() + "/users/registro");
                }
            } else {
                if (disponible) {
                    userDAO.create(nuevoUser);
                    response.sendRedirect(request.getContextPath() + "/users/admin");
                } else {
                    response.sendRedirect(request.getContextPath() + "/users/anadir");
                }
            }

        } else if (pathParts.length == 2 && "login".equals(pathParts[1])) {
            // Inicio sesión
            UserDAO userDAO = new UserDAOImpl();
            String usuario = request.getParameter("usuario");
            String password = request.getParameter("passwd");
            User userCheck = new User();
            userCheck.setUser(usuario);
            String hashpasswd;
            try {
                hashpasswd = User.hashPassword(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            userCheck.setPasswd(hashpasswd);

            List<User> listaUsers = userDAO.getAll();
            for (User user : listaUsers) {
                if (user.getUser().equals(userCheck.getUser())
                        && user.getPasswd().equals(userCheck.getPasswd())) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userLogin", user);

                    if (user.getRol().equals("Admin")) {
                        session.setAttribute("userAdmin", user);
                    }
                    if (user.getRol().equals("Profesional")) {
                        session.setAttribute("userProf", user);
                    }
                }
            }
            if (request.getSession().getAttribute("userLogin") == null) {
                response.sendRedirect(request.getContextPath() + "/users/login");
            } else {
                HttpSession session = request.getSession();
                if (session.getAttribute("carrito") == null) {
                    response.sendRedirect(request.getContextPath() + "/");
                } else {
                    response.sendRedirect(request.getContextPath() + "/carrito");
                }

            }

        } else if (pathParts.length == 2 && "logout".equals(pathParts[1])) {
            //  /users/logout
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/");

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            // Actualizar uno existente
            //Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
            doPut(request, response);
            response.sendRedirect(request.getContextPath() + "/users/micuenta");
        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar uno existente
            //Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
            doDelete(request, response);
            response.sendRedirect(request.getContextPath() + "/users/admin");
        } else {
            System.out.println("Opción POST no soportada.");
            response.sendRedirect(request.getContextPath() + "/users/micuenta");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = new UserDAOImpl();
        String codigo = request.getParameter("id");
        String usuario = request.getParameter("usuario");
        String passwd = request.getParameter("passwd");

        if (passwd.isEmpty()) {
            int id = Integer.parseInt(codigo);
            Optional <User> optUser = userDAO.find(id);
            optUser.get().getPasswd();
        }

        String rol = request.getParameter("rol");
        User user = new User();

        try {
            int id = Integer.parseInt(codigo);
            user.setIdUser(id);
            user.setUser(usuario);
            user.setPasswd(passwd);
            user.setRol(rol);
            userDAO.update(user);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        UserDAO userDAO = new UserDAOImpl();
        String codigo = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(codigo);

            userDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}