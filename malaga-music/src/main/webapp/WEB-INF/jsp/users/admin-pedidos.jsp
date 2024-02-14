<%@ page import="org.malagamusic.model.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Admin-Pedidos</title>
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
            <div style="float: left; width: 50%">
                <h1 style="font-size: 25px;">Pedidos</h1>
            </div>
            <div class="listaInstrumentos">
                <div style="float: left; width: 100%">
                    <div style="float: left;width: 20%">Código</div>
                    <div style="float: left;width: 20%">Usuario</div>
                    <div style="float: left;width: 20%">Lista Compra</div>
                    <div style="float: left;width: 20%">Fecha</div>
                    <div style="float: left;visibility: hidden;width: 20%">Acción</div>
                </div>
                <%
                    if (request.getAttribute("listaPedidos") != null) {
                        List<Pedido> listaPedidos = (List<Pedido>) request.getAttribute("listaPedidos");

                        for (Pedido pedido : listaPedidos) {
                %>

                <div class="listaSeparada">
                    <div style="border:solid 1px black;float: left;width: 20%"><%= pedido.getIdPedido()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 20%"><%= pedido.getIdUsuario()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 20%"><%= pedido.getListaCompra()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 20%"><%= pedido.getFecha()%>
                    </div>
                    <div style="float: left;width: 20%;">
                        <div class="flex flex-col">
                            <form action="${pageContext.request.contextPath}/pedidos/borrar/" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="__method__" value="delete"/>
                                <input type="hidden" name="codigo" value="<%= pedido.getIdPedido()%>"/>
                                <input style="border: solid 1px black" type="submit" value="Eliminar pedido"/>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                No hay registros de pedidos
                <% } %>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>