<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Opciones de administrador</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="content">
    <div class="main">
        <div class="hiAdmin">
            <h3>Hola ${sessionScope.userLogin.user}.</h3>
            <h3>¿Qué necesitas gestionar?</h3>
        </div>
        <div class="options">
            <div class="opt">
                <a href="<%=application.getContextPath()%>/productos/admin">PRODUCTOS</a>
            </div>
            <div class="opt">
                <a href="<%=application.getContextPath()%>/clases/admin">CLASES</a>
            </div>
            <div class="opt">
                <a href="<%=application.getContextPath()%>/marcas/admin">MARCAS</a>
            </div>
            <div class="opt">
                <a href="<%=application.getContextPath()%>/users/admin">USUARIOS</a>
            </div>
            <div class="opt">
                <a href="<%=application.getContextPath()%>/pedidos/admin">PEDIDOS</a>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>
</html>