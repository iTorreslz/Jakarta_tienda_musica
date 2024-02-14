<%@ page import="org.malagamusic.model.Instrumento" %>
<%@ page import="java.util.List" %>
<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="org.malagamusic.dao.ClaseDAO" %>
<%@ page import="org.malagamusic.dao.ClaseDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    ClaseDAO claseDao = new ClaseDAOImpl();
    List<Clase> listaClases = claseDao.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Carrito de Compra</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Oswald&display=swap');
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstat ic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Kalnia:wght@600&family=Roboto:wght@300&display=swap"
          rel="stylesheet">
    <link href="https://unpkg.com/tailwindcss@1.4.6/dist/tailwind.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<% if (request.getAttribute("no-stock") != null) { %>
<div class="mensaje-error" style="background: red; max-width: 1100px; margin: 0 auto">
    <%= request.getAttribute("no-stock") %>
</div>
<% } %>
<% if (request.getAttribute("completado") != null) { %>
<div class="mensaje-error" style="background: limegreen; max-width: 1100px; margin: 0 auto">
    <%= request.getAttribute("completado") %>
</div>
<% } %>
<div class="bg-gray-100 pt-5" style="position: relative">
    <h1 class="mb-10 text-center text-2xl font-bold">Carrito de Compra</h1>
    <% if (request.getSession().getAttribute("carrito") != null) { %>
    <div class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
        <div style="flex-direction: column" class="mx-auto max-w-5xl justify-center px-6 md:flex xl:px-0">
            <% List<Instrumento> listaInstrumentos = (List<Instrumento>) session.getAttribute("carrito");
                for (Instrumento instrumento : listaInstrumentos) {
            %>
            <div class="rounded-lg md:w-2/3 flex" style="width: fit-content;">
                <div class="justify-between mb-3 rounded-lg bg-white p-6 shadow-md sm:flex sm:justify-start">
                    <% for (Clase clase : listaClases) {
                        if (clase.getIdClase() == instrumento.getIdClase()) {
                    %>
                    <img alt="product-image" class="w-full rounded-lg sm:w-40"
                         src="${pageContext.request.contextPath}/img/productos/<%=clase.getNombre().toLowerCase()%>/<%=instrumento.getNombre().replace('/','-')%>.jpg">
                    <%
                            }
                        }%>
                    <div class="sm:ml-4 sm:flex sm:w-full sm:justify-between" style="width: 131px">
                        <div class="mt-5 sm:mt-0">
                            <h2 class="text-lg font-bold text-gray-900"><%= instrumento.getNombre() %>
                            </h2>
                            <p class="mt-1 text-xs text-gray-700"><%= instrumento.getPrecio() %>€</p>
                        </div>
                    </div>
                    <div class="sm:ml-4 sm:flex sm:w-full sm:justify-between" style="width: 131px">
                        <form action="${pageContext.request.contextPath}/carrito/borrar/" method="post"
                              style="display: inline;">
                            <input type="hidden" name="codigo" value="<%= instrumento.getIdInstrumento()%>"/>
                            <input class="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600" type="submit" value="Eliminar del carrito"/>
                        </form>
                    </div>
                </div>
            </div>

            <% }
            } else { %>
            <h1 style="text-align: center">Ningún producto en el carrito :(</h1>
            <% } %>
        </div>
        <div class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
            <!-- Sub total -->
            <form action="${pageContext.request.contextPath}/pedidos/anadir/" method="post"
                  class="mt-6 h-full rounded-lg border bg-white p-6 shadow-md md:mt-0 md:w-1/3"
                  style="width: fit-content; height: fit-content">
                <div style="width: 300px; height: fit-content">
                    <div class="mb-2 flex justify-between" style="flex-direction: column">
                        <% if (request.getSession().getAttribute("carrito") != null) {
                            List<Instrumento> listaInstrumentos = (List<Instrumento>) session.getAttribute("carrito");

                            for (Instrumento instrumento : listaInstrumentos) {
                        %>
                        <div class="mb-2 flex justify-between" style="width: 100%;">
                            <p class="text-gray-700" style="width: fit-content">
                                <% for (Clase clase : listaClases) {
                                    if (clase.getIdClase() == instrumento.getIdClase()) {
                                %>
                                <%= clase.getNombre().substring(0, clase.getNombre().length() - 2) %>
                                <%
                                        }
                                    }%>
                            </p>
                            <p class="text-gray-700" style="width: fit-content">
                                <%= instrumento.getPrecio() %>€
                            </p>
                        </div>
                        <% } %>
                    </div>
                    <div class="flex justify-between">
                        <p class="text-gray-700">Envío</p>
                        <p class="text-gray-700">4.99€</p>
                    </div>
                    <hr class="my-4"/>
                    <div class="flex justify-between">
                        <p class="text-lg font-bold">Total</p>
                        <div class="">
                            <p class="mb-1 text-lg font-bold">
                                <% if (request.getSession().getAttribute("precioTotal") != null) { %>

                                <%= Math.floor(((double) session.getAttribute("precioTotal") + 4.99) * 100) / 100 %>€

                                <% } %>
                            </p>
                            <p class="text-sm text-gray-700">IVA incluido</p>
                        </div>
                    </div>
                    <button type="submit" style="height: 40px"
                            class="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">
                        <b>Tramitar pedido</b>
                    </button>
                </div>
            </form>
        </div>
    </div>

</div>
<% } %>

<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>
<% List<Instrumento> listaInstrumentos = (List<Instrumento>) session.getAttribute("carrito");
    if (session.getAttribute("carrito") != null && listaInstrumentos.size() > 1) { %>
<footer class="p-4 bg-white shadow md:flex md:items-center md:justify-between md:p-6" style="background: #7CB9E8;position: relative; bottom: 0;">
    <span class="text-sm text-black-500 sm:text-center dark:text-black-400">© 2024 <a href="${pageContext.request.contextPath}/index.jsp"
                                                                                      class="hover:underline"
                                                                                      target="_blank">AMADEUS INSTRUMENTOS, S.L. </a>. Todos los derechos reservados.
    </span>
    <ul class="flex flex-wrap items-center mt-3 sm:mt-0">
        <li>
            <a href="#" class="mr-4 text-sm text-black-500 hover:underline md:mr-6">Sobre nosotros</a>
        </li>
        <li>
            <a href="#" class="mr-4 text-sm text-black-500 hover:underline md:mr-6">Aviso de
                privacidad</a>
        </li>
        <li>
            <a href="#" class="mr-4 text-sm text-black-500 hover:underline md:mr-6">Condiciones de compra</a>
        </li>
        <li>
            <a href="#" class="text-sm text-black-500 hover:underline">Contacto</a>
        </li>
    </ul>
</footer>
<% } else { %>
<footer class="p-4 bg-white shadow md:flex md:items-center md:justify-between md:p-6" style="background: #7CB9E8;position: fixed; bottom: 0;left: 0">
    <span class="text-sm text-black-500 sm:text-center dark:text-black-400">© 2024 <a href="${pageContext.request.contextPath}/index.jsp"
                                                                                      class="hover:underline"
                                                                                      target="_blank">AMADEUS INSTRUMENTOS, S.L. </a>. Todos los derechos reservados.
    </span>
    <ul class="flex flex-wrap items-center mt-3 sm:mt-0">
        <li>
            <a href="#" class="mr-4 text-sm text-black-500 hover:underline md:mr-6">Sobre nosotros</a>
        </li>
        <li>
            <a href="#" class="mr-4 text-sm text-black-500 hover:underline md:mr-6">Aviso de
                privacidad</a>
        </li>
        <li>
            <a href="#" class="mr-4 text-sm text-black-500 hover:underline md:mr-6">Condiciones de compra</a>
        </li>
        <li>
            <a href="#" class="text-sm text-black-500 hover:underline">Contacto</a>
        </li>
    </ul>
</footer>
<% } %>
</html>