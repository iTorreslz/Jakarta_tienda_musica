<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Añadir user</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
</head>

<body>
<div class="content">
    <div class="edit-main">
        <div class="edit-name">
            <h2>Nuevo Usuario</h2>
        </div>
        <div class="edit-form">
            <form action="${pageContext.request.contextPath}/users/anadir/" method="post">
                <label><b>Usuario</b></label>
                <input name="usuario" type="text" placeholder="Usuario" value="">
                <label><b>Contraseña</b></label>
                <input name="passwd" type="password" placeholder="Contraseña" value="">
                <label><b>Rol</b></label>
                <select name="rol">
                    <option value="Cliente">Cliente</option>
                    <option value="Profesional">Profesional</option>
                    <option value="Admin">Admin</option>
                </select>
                <div>
                    <input type="hidden" name="cliente" value="no"/>
                </div>
                <div class="edit-boton">
                    <input type="submit" value="Añadir">
                </div>
            </form>
        </div>
    </div>
</div>
</body>

</html>