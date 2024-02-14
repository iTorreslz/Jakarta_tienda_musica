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
        urlPatterns = {"/admin","/productos/admin","/clases/admin",
                "/marcas/admin", "/users/admin", "/pedidos/admin",
                "/productos/borrar", "/clases/borrar", "/marcas/borrar",
                "/users/borrar", "/pedidos/borrar",},
        initParams = @WebInitParam(name = "acceso-concedido-a-rol", value = "Admin")
)
public class AdminFilter implements Filter {

    private String rolAcceso;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.rolAcceso = filterConfig.getInitParameter("acceso-concedido-a-rol");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //Accediendo al objeto de sesión
        HttpSession session = httpRequest.getSession();

        //Obteniendo la url
        String url = httpRequest.getRequestURL().toString();

        User user = null;

        if (session != null  //Seteo inline de usuario
                && (user = (User) session.getAttribute("userLogin")) != null
                && rolAcceso.equals(user.getRol())) {

            //Si eres administrador acceso a cualquier página del filtro
            chain.doFilter(request, response);

        } else if (url.endsWith("/admin")
                || url.contains("/users/admin")
                || url.contains("/clases/admin")
                || url.contains("/marcas/admin")
                || url.contains("/productos/admin")
                || url.contains("/pedidos/admin")
                || url.contains("/users/borrar")
                || url.contains("/clases/borrar")
                || url.contains("/marcas/borrar")
                || url.contains("/productos/borrar")
                || url.contains("/pedidos/borrar")) {

            // Usuario no administrador trata de acceder, y el filtro lo redirige a login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/users/login");

        } else {
            // Otras rutas se dan paso a cualquier rol
            chain.doFilter(request, response);
        }
    }
    public void destroy() { }
}