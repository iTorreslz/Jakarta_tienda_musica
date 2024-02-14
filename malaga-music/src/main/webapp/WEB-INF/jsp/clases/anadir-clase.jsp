<%@ page import="org.malagamusic.dao.FamiliaDAO" %>
<%@ page import="org.malagamusic.dao.FamiliaDAOImpl" %>
<%@ page import="org.malagamusic.model.Familia" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    FamiliaDAO famDao = new FamiliaDAOImpl();
    List<Familia> listaFamilias = famDao.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Añadir clase</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <div class="edit-name">
            <h2>Nueva Clase</h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/clases/anadir/" method="post">
                <label><b>Nombre</b></label>
                <input name="nombre" type="text" placeholder="Nombre" value="">
                <label for="new-familia"><b>Familia</b></label>
                <select name="familia" id="new-familia">
                    <% for (Familia familia : listaFamilias) { %>
                    <option
                            value="<%= familia.getIdFamilia() %>"><%= familia.getNombre() %>
                    </option>
                    <% } %>
                </select>
                <div class="edit-boton">
                    <input type="submit" value="Añadir">
                </div>
            </form>
        </div>
    </div>
</div>
</body>

</html>