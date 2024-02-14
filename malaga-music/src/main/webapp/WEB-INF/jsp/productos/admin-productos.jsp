<%@ page import="org.malagamusic.model.Instrumento" %>
<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="org.malagamusic.model.Marca" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Admin-Productos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="content">
    <div class="main">
        <div class="productos">
            <div style="float: left; width: 100%; position: relative">
                <h1 style="font-size: 25px;">Productos</h1>
                <div style="position: absolute; right: 0; top : 0;">
                    <form action="${pageContext.request.contextPath}/productos/anadir">
                        <input type="submit" value="Añadir nuevo instrumento" style="border: solid 1px black">
                    </form>
                </div>
            </div>
            <div class="listaInstrumentos">
                <div style="float: left; width: 100%">
                    <div style="float: left;width: 14.28%">Código</div>
                    <div style="float: left;width: 14.28%">Nombre</div>
                    <div style="float: left;width: 14.28%">Precio</div>
                    <div style="float: left;width: 14.28%">Descripción</div>
                    <div style="float: left;width: 14.28%">Clase</div>
                    <div style="float: left;width: 14.28%">Marca</div>
                    <div style="float: left;visibility: hidden;width: 14.28%">Acción</div>
                </div>
                <%
                    if (request.getAttribute("listaProductos") != null) {
                        List<Instrumento> listaInstrumentos = (List<Instrumento>) request.getAttribute("listaProductos");
                        List<Clase> listaClases = (List<Clase>) request.getAttribute("listaClases");
                        List<Marca> listaMarcas = (List<Marca>) request.getAttribute("listaMarcas");

                        for (Instrumento instrumento : listaInstrumentos) {
                %>

                <div class="listaSeparada">
                    <div style="border:solid 1px black;float: left;width: 14.28%"><%= instrumento.getIdInstrumento()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 14.28%"><%= instrumento.getNombre()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 14.28%"><%= instrumento.getPrecio()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 14.28%"><%= instrumento.getDescripcion()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 14.28%"><%= listaClases.get(instrumento.getIdClase() - 1).getNombre()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 14.28%"><%= listaMarcas.get(instrumento.getIdMarca() - 1).getNombre()%>
                    </div>
                    <div style="float: left;width: 14.28%;">
                        <div class="flex flex-col">
                            <form action="${pageContext.request.contextPath}/productos/editar/<%= instrumento.getIdInstrumento()%>"
                                  style="display: inline;">
                                <input style="border: solid 1px black" type="submit" value="Editar producto"/>
                            </form>
                            <form action="${pageContext.request.contextPath}/productos/borrar/" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="__method__" value="delete"/>
                                <input type="hidden" name="codigo" value="<%= instrumento.getIdInstrumento()%>"/>
                                <input style="border: solid 1px black" type="submit" value="Eliminar producto"/>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                No hay registros de producto
                <% } %>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>