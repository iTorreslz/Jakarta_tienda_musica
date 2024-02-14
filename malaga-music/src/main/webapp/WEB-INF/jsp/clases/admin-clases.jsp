<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.model.Clase" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Admin-Clases</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="content">
    <div class="main">
        <div class="clases">
            <div style="float: left; width: 100%; position: relative">
                <h1 style="font-size: 25px;">Clases</h1>
                <div style="position: absolute; right: 0; top : 0;">
                    <form action="${pageContext.request.contextPath}/clases/anadir">
                        <input type="submit" value="Añadir nueva clase" style="border: solid 1px black">
                    </form>
                </div>
            </div>
            <div class="listaClases">
                <div style="float: left; width: 100%">
                    <div style="float: left;width: 33.33%">Código</div>
                    <div style="float: left;width: 33.33%">Nombre</div>
                    <div style="float: left;visibility: hidden;width: 33.33%">Acción</div>
                </div>
                <%
                    if (request.getAttribute("listaClases") != null) {
                        List<Clase> listaClases = (List<Clase>) request.getAttribute("listaClases");

                        for (Clase clase : listaClases) {
                %>

                <div class="listaSeparada">
                    <div style="border:solid 1px black;float: left;width: 33.33%"><%= clase.getIdClase()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 33.33%"><%= clase.getNombre()%>
                    </div>
                    <div style="float: left;width: 33.33%;">
                        <div class="flex flex-col">
                            <form action="${pageContext.request.contextPath}/clases/editar/<%= clase.getIdClase()%>"
                                  style="display: inline;">
                                <input style="border: solid 1px black" type="submit" value="Editar clase"/>
                            </form>
                            <form action="${pageContext.request.contextPath}/clases/borrar/" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="__method__" value="delete"/>
                                <input type="hidden" name="codigo" value="<%= clase.getIdClase()%>"/>
                                <input style="border: solid 1px black" type="submit" value="Eliminar clase"/>
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