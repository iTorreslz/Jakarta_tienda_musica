<%@ page import="org.malagamusic.dao.ClaseDAO" %>
<%@ page import="org.malagamusic.dao.ClaseDAOImpl" %>
<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.dao.MarcaDAO" %>
<%@ page import="org.malagamusic.dao.MarcaDAOImpl" %>
<%@ page import="org.malagamusic.model.Marca" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ClaseDAO claseDao = new ClaseDAOImpl();
    List<Clase> listaClases = claseDao.getAll();
    MarcaDAO marcaDao = new MarcaDAOImpl();
    List<Marca> listaMarcas = marcaDao.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Añadir instrumento</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <div class="edit-name">
            <h2>Nuevo Instrumento</h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/productos/anadir/" method="post">
                <label><b>Nombre</b></label>
                <input name="nombre" type="text" placeholder="Nombre" value="">
                <label><b>Precio</b></label>
                <input name="precio" type="text" placeholder="Precio" value="">
                <label><b>Descripción</b></label>
                <input name="descripcion" type="text" placeholder="Descripción" value="">
                <label for="new-clase"><b>Clase</b></label>
                <select name="clase" id="new-clase">
                    <% for (Clase clase : listaClases) { %>
                    <option
                            value="<%= clase.getIdClase() %>"><%= clase.getNombre() %>
                    </option>
                    <% } %>
                </select>
                <label for="new-marca"><b>Marca</b></label>
                <select name="marca" id="new-marca">
                    <% for (Marca marca : listaMarcas) { %>
                    <option
                            value="<%= marca.getIdMarca() %>"><%= marca.getNombre() %>
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