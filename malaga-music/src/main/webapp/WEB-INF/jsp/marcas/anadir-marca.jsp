<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Añadir marca</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <div class="edit-name">
            <h2>Nueva Marca</h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/marcas/anadir/" method="post">
                <label><b>Nombre</b></label>
                <input name="nombre" type="text" placeholder="Nombre" value="">
                <div class="edit-boton">
                    <input type="submit" value="Añadir">
                </div>
            </form>
        </div>
    </div>
</div>
</body>

</html>