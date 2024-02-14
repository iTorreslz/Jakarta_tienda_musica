<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Amadeus</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Pacifico&display=swap');
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
        <div id="home">
            <h5>BIENVENIDO</h5>
        </div>
        <div id="new">
            <h2>NOVEDADES</h2>
            <h3>--Te presentamos nuestra nueva sección de instrumentos de Arco--</h3>
            <div id="news" class="flex flex-row">
                <form class="formOut" action="<%=application.getContextPath()%>/productos/detalle/7">
                    <button type="submit" style="visibility: hidden"></button>
                    <a class="logOuta">
                        <img class="imagesHome" src="${pageContext.request.contextPath}/img/productos/violines/Stentor%20Master%204-4.jpg" style="height: 250px">
                    </a>
                </form>
                <form class="formOut" action="<%=application.getContextPath()%>/productos/detalle/8">
                    <button type="submit" style="visibility: hidden"></button>
                    <a class="logOuta">
                        <img class="imagesHome" src="${pageContext.request.contextPath}/img/productos/violonchelos/Eastman%20VC150.jpg" style="height: 250px">
                    </a>
                </form>
                <form class="formOut" action="<%=application.getContextPath()%>/productos/detalle/9">
                    <button type="submit" style="visibility: hidden"></button>
                    <a class="logOuta">
                        <img class="imagesHome" src="${pageContext.request.contextPath}/img/productos/violonchelos/Höfner%20Serie%20AS-190-C%203-4.jpg" style="height: 250px">
                    </a>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>