<%@ page import="org.malagamusic.model.Instrumento" %>
<%@ page import="java.util.Optional" %>
<%@ page import="org.malagamusic.dao.MarcaDAO" %>
<%@ page import="org.malagamusic.dao.MarcaDAOImpl" %>
<%@ page import="org.malagamusic.model.Marca" %>
<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.dao.ClaseDAO" %>
<%@ page import="org.malagamusic.dao.ClaseDAOImpl" %>
<%@ page import="org.malagamusic.model.Clase" %>
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
    <title>Editar producto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <% Optional<Instrumento> optInst = (Optional<Instrumento>) request.getAttribute("producto");
            if (optInst.isPresent()) {
        %>
        <div class="edit-name">
            <h2><%= optInst.get().getNombre() %>
            </h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/productos/editar/" method="post">
                <input type="hidden" name="__method__" value="put" />
                <label><b>ID</b></label>
                <input name="id" type="text" value="<%= optInst.get().getIdInstrumento() %>" readonly>
                <label><b>Nombre</b></label>
                <input name="nombre" type="text" value="<%= optInst.get().getNombre() %>">
                <label><b>Precio</b></label>
                <input name="precio" type="text" value="<%= optInst.get().getPrecio() %>">
                <label><b>Descripción</b></label>
                <input name="descripcion" type="text" value="<%= optInst.get().getDescripcion() %>">
                <label><b>Clase</b></label>
                <select name="clase">
                    <% for (Clase clase : listaClases) { %>
                    <option
                            value="<%= clase.getIdClase() %>" <% if (clase.getIdClase() == optInst.get().getIdClase()) { %>
                            selected <% } %>><%= clase.getNombre() %>
                    </option>
                    <% } %>
                </select>
                <label><b>Marca</b></label>
                <select name="marca">
                    <% for (Marca marca : listaMarcas) { %>
                    <option
                            value="<%= marca.getIdMarca() %>" <% if (marca.getIdMarca() == optInst.get().getIdMarca()) { %>
                            selected <% } %>><%= marca.getNombre() %>
                    </option>
                    <% } %>
                </select>
                <div class="edit-boton">
                    <input type="submit" value="Guardar">
                </div>

                <% } else { %>
                request.sendRedirect("admin-productos/");

                <% } %>
            </form>
        </div>
    </div>
</div>
</body>

</html>