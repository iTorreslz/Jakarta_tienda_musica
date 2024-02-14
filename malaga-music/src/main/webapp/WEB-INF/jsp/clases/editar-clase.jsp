<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="java.util.Optional" %>
<%@ page import="org.malagamusic.dao.FamiliaDAO" %>
<%@ page import="org.malagamusic.dao.FamiliaDAOImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.model.Familia" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    FamiliaDAO familiaDao = new FamiliaDAOImpl();
    List<Familia> listaFamilias = familiaDao.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar clase</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <% Optional<Clase> optClase = (Optional<Clase>) request.getAttribute("clase");
            if (optClase.isPresent()) {
        %>
        <div class="edit-name">
            <h2><%= optClase.get().getNombre() %>
            </h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/clases/editar/" method="post">
                <input type="hidden" name="__method__" value="put"/>
                <label><b>ID</b></label>
                <input name="id" type="text" value="<%= optClase.get().getIdClase() %>" readonly>
                <label><b>Nombre</b></label>
                <input name="nombre" type="text" value="<%= optClase.get().getNombre() %>">
                <label><b>Familia</b></label>
                <select name="familia">
                    <% for (Familia familia : listaFamilias) { %>
                    <option
                            value="<%= familia.getIdFamilia() %>" <% if (familia.getIdFamilia() == optClase.get().getIdFamilia()) { %>
                            selected <% } %>><%= familia.getNombre() %>
                    </option>
                    <% } %>
                </select>
                <div class="edit-boton">
                    <input type="submit" value="Guardar">
                </div>

                <% } else { %>

                request.sendRedirect("admin-clases/");

                <% } %>
            </form>
        </div>
    </div>
</div>
</body>

</html>