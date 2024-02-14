<%@ page import="org.malagamusic.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User usuario = (User) request.getSession().getAttribute("userLogin");
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Mi cuenta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap"
          rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<div class="bg-gray-200 relative flex justify-center">
    <div
            class="absolute max-w-md mx-auto md:max-w-2xl mt-28 min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded-xl mt-16 top-16">
        <div class="px-6">
            <div class="flex flex-wrap justify-center">
                <div class="w-full flex justify-center">
                    <div class="relative flex justify-center">
                        <img src="${pageContext.request.contextPath}/img/user.png"
                             class="shadow-xl rounded-full border-none -m-36 max-w-[150px]"
                             style="position: absolute;left: 71px;"/>
                    </div>
                </div>
            </div>
            <div class="text-center mt-2">
                <h3 class="text-2xl text-slate-700 font-bold leading-normal mb-1">
                    <%= usuario.getUser() %>
                </h3>
                <div class="text-xs mt-0 mb-2 text-slate-400 font-bold uppercase">
                    <%= usuario.getRol() %>
                </div>
            </div>
            <div class="mt-6 py-6 border-t border-slate-200 text-center">
                <div class="flex flex-wrap justify-center">
                    <div class="w-full px-4">
                        <p class="font-light leading-relaxed text-slate-600 mb-4">Este es tu perfil de usuario, donde
                            podrás modificar la información de tu cuenta y ver tus pedidos.</p>
                        <div class="w-full px-4 flex flex-row justify-center">
                            <form action="${pageContext.request.contextPath}/pedidos/user/<%= usuario.getIdUser()%>">
                                <button type="submit" class="font-normal text-slate-700 hover:text-slate-400 mr-10">
                                    Ver pedidos realizados
                                </button>
                            </form>
                            <form action="${pageContext.request.contextPath}/users/editar/<%= usuario.getIdUser()%>">
                                <button type="submit" class="font-normal text-slate-700 hover:text-slate-400">
                                    Editar perfil
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>