<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.dao.ClaseDAO" %>
<%@ page import="org.malagamusic.dao.ClaseDAOImpl" %>
<%@ page import="org.malagamusic.model.Instrumento" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ClaseDAO claseDao = new ClaseDAOImpl();
    List<Clase> listaClases = claseDao.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>
        <% for (Clase clase : listaClases) {
            String idClase = (String) request.getAttribute("clase");
            if (clase.getIdClase() == Integer.parseInt(idClase)) {
        %>
        <%=clase.getNombre()%>
        <%
                }
            }%>
    </title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
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
                <h1>
                    <% for (Clase clase : listaClases) {
                        String idClase = (String) request.getAttribute("clase");
                        if (clase.getIdClase() == Integer.parseInt(idClase)) {
                    %>
                    <%=clase.getNombre()%>
                    <%
                            }
                        }%>
                </h1>
            </div>
            <div class="listaClases">
                <%
                    if (request.getAttribute("listaInst") != null) {
                        List<Instrumento> listaInstrumentos = (List<Instrumento>) request.getAttribute("listaInst");

                        for (Instrumento instrumento : listaInstrumentos) {
                %>
                <a href="<%=application.getContextPath()%>/productos/detalle/<%=instrumento.getIdInstrumento()%>">
                    <div class="clase" id="<%=instrumento.getNombre()%>">
                        <div class="<%=instrumento.getNombre()%>" style="font-size: 14px; text-align: center">
                            <h2><%=instrumento.getNombre().toUpperCase()%>
                            </h2>
                        </div>
                        <% for (Clase clase : listaClases) {
                            if (clase.getIdClase() == instrumento.getIdClase()) {
                        %>
                        <img src="${pageContext.request.contextPath}/img/productos/<%=clase.getNombre().toLowerCase()%>/<%=instrumento.getNombre().replace('/','-')%>.jpg">
                        <%
                                }
                            }%>
                    </div>
                </a>
                <%
                    }
                } else {
                %>
                No hay registros de instrumentos
                <% } %>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>