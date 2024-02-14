<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Admin-Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="content">
    <div class="main">
        <div class="users">
            <div style="float: left; width: 100%; position: relative">
                <h1 style="font-size: 25px;">Usuarios</h1>
                <div style="position: absolute; right: 0; top : 0;">
                    <form action="${pageContext.request.contextPath}/users/anadir">
                        <input type="submit" value="Añadir nuevo usuario" style="border: solid 1px black">
                    </form>
                </div>
            </div>
            <div class="listaUsuario">
                <div style="float: left; width: 100%">
                    <div style="float: left;width: 20%">Código</div>
                    <div style="float: left;width: 20%">Usuario</div>
                    <div style="float: left;width: 20%">Password</div>
                    <div style="float: left;width: 20%">Rol</div>
                    <div style="float: left;visibility: hidden;width: 20%">Acción</div>
                </div>
                <%
                    if (request.getAttribute("listaUsers") != null) {
                        List<User> listaUsers = (List<User>) request.getAttribute("listaUsers");

                        for (User user : listaUsers) {
                            String shortHash = (user.getPasswd().substring(0, 4));
                %>

                <div class="listaSeparada">
                    <div style="border:solid 1px black;float: left;width: 20%"><%= user.getIdUser()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 20%"><%= user.getUser()%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 20%"><%=shortHash%>
                    </div>
                    <div style="border:solid 1px black;float: left;width: 20%"><%= user.getRol()%>
                    </div>
                    <div style="float: left;width: 20%;">
                        <div class="flex flex-col">
                            <form action="${pageContext.request.contextPath}/users/editar/<%= user.getIdUser()%>"
                                  style="display: inline;">
                                <input style="border: solid 1px black" type="submit" value="Editar usuario"/>
                            </form>
                            <form action="${pageContext.request.contextPath}/users/borrar/" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="__method__" value="delete"/>
                                <input type="hidden" name="codigo" value="<%= user.getIdUser()%>"/>
                                <input style="border: solid 1px black" type="submit" value="Eliminar usuario"/>
                            </form>
                        </div>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                No hay registros de usuario
                <% } %>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>