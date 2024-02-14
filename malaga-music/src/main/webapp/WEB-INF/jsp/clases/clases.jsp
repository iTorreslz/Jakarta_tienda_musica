<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>
        Instrumentos de
        <% switch ((String) request.getAttribute("familia")) {
            case "1":
        %>
        Viento-Madera
        <%
                break;
            case "2":
        %>
        Viento-Metal
        <%
                break;
            case "3":
        %>
        Arco
        <%
                break;
            }
        %>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap"
          rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="content">
    <div class="main">
        <div class="productos">
            <div>
                <h1>Instrumentos de
                    <% switch ((String) request.getAttribute("familia")) {
                        case "1":
                    %>
                    Viento-Madera
                    <%
                            break;
                        case "2":
                    %>
                    Viento-Metal
                    <%
                            break;
                        case "3":
                    %>
                    Arco
                    <%
                                break;
                        }
                    %></h1>
            </div>
            <div class="listaClases">
                <%
                    if (request.getAttribute("listaClases") != null) {
                        List<Clase> listaClases = (List<Clase>) request.getAttribute("listaClases");

                        for (Clase clase : listaClases) {
                %>
                <a href="<%=application.getContextPath()%>/productos/<%=clase.getIdClase()%>">
                    <div class="clase" id="<%=clase.getNombre()%>">
                        <div class="<%=clase.getNombre()%>" style="text-align: center">
                            <h2><%=clase.getNombre().toUpperCase()%></h2>
                        </div>
                        <img src="${pageContext.request.contextPath}/img/<%=clase.getNombre()%>.jpg">
                    </div>
                </a>
                <%
                    }
                } else {
                %>
                No hay registros de clases
                <% } %>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>