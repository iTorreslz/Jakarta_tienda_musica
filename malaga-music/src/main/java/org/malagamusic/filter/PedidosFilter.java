package org.malagamusic.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.malagamusic.model.User;
import java.io.IOException;

@WebFilter(
        urlPatterns = {"/pedidos/anadir/"}
)
public class PedidosFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //Accediendo al objeto de sesión
        HttpSession session = httpRequest.getSession();

        //Obteniendo la url
        String url = httpRequest.getRequestURL().toString();

        if (session != null && session.getAttribute("userLogin") != null) {

            //Si has iniciado sesión tendrás acceso a la compra
            chain.doFilter(request, response);

        } else if (url.endsWith("/pedidos/anadir/")) {

            // Usuario no registrado trata de acceder, y el filtro lo redirige a login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/users/login");

        } else {
            // Otras rutas se dan paso a cualquier rol
            chain.doFilter(request, response);
        }
    }
    public void destroy() { }
}