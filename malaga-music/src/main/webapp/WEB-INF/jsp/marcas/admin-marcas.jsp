<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.model.Marca" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Admin-Marcas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="content">
    <div class="main">
        <div class="marcas">
            <div style="float: left; width: 100%; position: relative">
                <h1 style="font-size: 25px;">Marcas</h1>
                <div style="position: absolute; right: 0%; top : 0%;">
                    <form action="${pageContext.request.contextPath}/marcas/anadir">
                        <input type="submit" value="Añadir nueva marca" style="border: solid 1px black">
                    </form>
                </div>
            </div>
            <div class="listaMarcas">
                <div style="float: left; width: 100%">
                    <div style="float: left;width: 33.33%">Código</div>
                    <div style="float: left;width: 33.33%">Nombre</div>
                    <div style="float: left;visibility: hidden;width: 33.33%">Acción</div>
                </div>
                <%
                    if (request.getAttribute("listaMarcas") != null) {
                        List<Marca> listaMarcas = (List<Marca>) request.getAttribute("listaMarcas");

                        for (Marca marca : listaMarcas) {
                %>

                <div class="listaSeparada">
                    <div style="border:solid 1px black;float: left;width: 33.33%"><%= marca.getIdMarca()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 33.33%"><%= marca.getNombre()%>
                    </div>
                    <div style="float: left;width: 33.33%;">
                        <div class="flex flex-col">
                            <form action="${pageContext.request.contextPath}/marcas/editar/<%= marca.getIdMarca()%>"
                                  style="display: inline;">
                                <input style="border: solid 1px black" type="submit" value="Editar marca"/>
                            </form>
                            <form action="${pageContext.request.contextPath}/marcas/borrar/" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="__method__" value="delete"/>
                                <input type="hidden" name="codigo" value="<%= marca.getIdMarca()%>"/>
                                <input style="border: solid 1px black" type="submit" value="Eliminar marca"/>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                No hay registros de marca
                <% } %>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>