<%@ page import="java.util.Optional" %>
<%@ page import="org.malagamusic.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar user</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <% Optional<User> optUser = (Optional<User>) request.getAttribute("user");
            if (optUser.isPresent()) {
        %>
        <div class="edit-name">
            <h2><%= optUser.get().getUser() %>
            </h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/users/editar/" method="post">
                <input type="hidden" name="__method__" value="put"/>
                <label><b>Código</b></label>
                <input name="id" type="text" value="<%= optUser.get().getIdUser() %>" readonly>
                <label><b>Usuario</b></label>
                <input name="usuario" type="text" value="<%= optUser.get().getUser() %>">
                <label><b>Contraseña</b></label>
                <input name="passwd" type="password" value="">
                <%
                    User usuario = (User) request.getSession().getAttribute("userLogin");
                    if (!usuario.getRol().equals("Cliente")) {
                %>
                <label><b>Rol</b></label>
                <select name="rol">
                    <option value="Cliente" <% if (optUser.get().getRol().equals("Cliente")) { %>
                            selected <% } %>>Cliente
                    </option>
                    <option value="Profesional" <% if (optUser.get().getRol().equals("Profesional")) { %>
                            selected <% } %>>Profesional
                    </option>
                    <option value="Admin" <% if (optUser.get().getRol().equals("Admin")) { %>
                            selected <% } %>>Admin
                    </option>
                </select>
                <% } %>

                <div class="edit-boton">
                    <input type="submit" value="Guardar">
                </div>

                <% } else { %>
                request.sendRedirect("admin-users/");

                <% } %>
            </form>
        </div>
    </div>
</div>
</body>

</html>