<%@ page import="org.malagamusic.model.Marca" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar marca</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <% Optional<Marca> optMarca = (Optional<Marca>) request.getAttribute("marca");
            if (optMarca.isPresent()) {
        %>
        <div class="edit-name">
            <h2><%= optMarca.get().getNombre() %>
            </h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/marcas/editar/" method="post">
                <input type="hidden" name="__method__" value="put"/>
                <label><b>ID</b></label>
                <input name="id" type="text" value="<%= optMarca.get().getIdMarca() %>" readonly>
                <label><b>Nombre</b></label>
                <input name="nombre" type="text" value="<%= optMarca.get().getNombre() %>">
                <div class="edit-boton">
                    <input type="submit" value="Guardar">
                </div>

                <% } else { %>

                request.sendRedirect("admin-marcas/");

                <% } %>
            </form>
        </div>
    </div>
</div>
</body>

</html>