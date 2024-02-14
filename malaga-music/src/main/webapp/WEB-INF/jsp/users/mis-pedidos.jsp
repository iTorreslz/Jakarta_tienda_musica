<%@ page import="org.malagamusic.model.User" %>
<%@ page import="org.malagamusic.model.Pedido" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
<div class="bg-gray-200">
    <div class="bg-white p-8 rounded-md w-full">
        <div class=" flex items-center justify-between pb-6">
            <div>
                <h2 class="text-gray-600 font-semibold">Mis pedidos</h2>
                <span class="text-xs"><%= usuario.getUser().toUpperCase() %></span>
            </div>
            <div class="flex items-center justify-between">
                <div class="flex bg-gray-50 items-center p-2 rounded-md">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-400" viewBox="0 0 20 20"
                         fill="currentColor">
                        <path fill-rule="evenodd"
                              d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                              clip-rule="evenodd"></path>
                    </svg>
                    <input class="bg-gray-50 outline-none ml-1 block " type="text" name="" id=""
                           placeholder="Buscar...">
                </div>
            </div>
        </div>
        <div>
            <div class="-mx-4 sm:-mx-8 px-4 sm:px-8 py-4 overflow-x-auto">
                <div class="inline-block min-w-full shadow rounded-lg overflow-hidden">
                    <table class="min-w-full leading-normal">
                        <thead>
                        <tr>
                            <th
                                    class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                Pedido realizado
                            </th>
                            <th
                                    class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                Productos
                            </th>
                            <th
                                    class="px-5 py-3 border-b-2 border-gray-200 bg-gray-100 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                                Estado
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (request.getAttribute("listaFiltrada") != null) {
                                List<Pedido> listaPedidos = (List<Pedido>) request.getAttribute("listaFiltrada");

                                for (Pedido pedido : listaPedidos) {
                        %>
                        <tr>
                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                <p class="text-gray-900 whitespace-no-wrap">
                                    <%
                                        LocalDate fecha = LocalDate.parse(pedido.getFecha());
                                        String fechaFormato = fecha.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy"));
                                    %>
                                    <%= fechaFormato %>
                                </p>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                <p class="text-gray-900 whitespace-no-wrap">
                                    <%= pedido.getListaCompra() %>
                                </p>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 bg-white text-sm">
                                    <span
                                            class="relative inline-block px-3 py-1 font-semibold text-green-900 leading-tight">
                                        <span aria-hidden
                                              class="absolute inset-0 bg-green-200 opacity-50 rounded-full"></span>
                                        <span class="relative">Activo</span>
                                    </span>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        Aún no hay registro de pedidos :(
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>