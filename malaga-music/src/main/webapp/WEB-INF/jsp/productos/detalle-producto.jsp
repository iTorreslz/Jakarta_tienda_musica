<%@ page import="org.malagamusic.model.Instrumento" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="org.malagamusic.dao.MarcaDAO" %>
<%@ page import="org.malagamusic.dao.MarcaDAOImpl" %>
<%@ page import="org.malagamusic.model.Marca" %>
<%@ page import="org.malagamusic.model.Clase" %>
<%@ page import="org.malagamusic.dao.ClaseDAO" %>
<%@ page import="org.malagamusic.dao.ClaseDAOImpl" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    MarcaDAO marcaDao = new MarcaDAOImpl();
    List<Marca> listaMarcas = marcaDao.getAll();
    ClaseDAO claseDao = new ClaseDAOImpl();
    List<Clase> listaClases = claseDao.getAll();
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>
        <% Optional<Instrumento> optInst = (Optional<Instrumento>) request.getAttribute("producto");
            if (optInst.isPresent()) {
        %>
        <%= optInst.get().getNombre() %>
    </title>
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
<section class="text-gray-700 body-font bg-white">
    <div class="container px-5 py-12 mx-auto">
        <div class="lg:w-4/5 mx-auto flex flex-wrap">
            <% for (Clase clase : listaClases) {
                if (clase.getIdClase() == optInst.get().getIdClase()) {
            %>
            <img alt="ecommerce" class="lg:w-1/2 w-full object-cover object-center rounded border border-gray-200"
                 src="${pageContext.request.contextPath}/img/productos/<%=clase.getNombre().toLowerCase()%>/<%=optInst.get().getNombre().replace('/','-')%>.jpg">
            <%
                    }
                }%>
            <form action="${pageContext.request.contextPath}/carrito/anadir/" method="post"
                  class="lg:w-1/2 w-full lg:pl-10 lg:py-6 mt-6 lg:mt-0">
                <div>
                    <h2 class="text-sm title-font text-gray-500 tracking-widest">
                        <b>
                            <% for (Marca marca : listaMarcas) {
                                if (marca.getIdMarca() == optInst.get().getIdMarca()) { %>
                            <%= marca.getNombre().toUpperCase() %>
                            <% }
                            } %>
                        </b>
                    </h2>
                    <h1 class="text-gray-900 text-3xl title-font font-medium mb-1">
                        <b><%= optInst.get().getNombre().toUpperCase() %>
                        </b>
                    </h1>
                    <div class="flex mb-4"></div>
                    <p class="leading-relaxed">Te presentamos nuestro <%= optInst.get().getDescripcion() %>, al mejor
                        precio.</p>
                    <div class="flex items-center pb-5 border-b-2 border-gray-200 mb-5"></div>
                    <div class="flex">
                        <span class="title-font font-medium text-2xl text-gray-900"><b><%= optInst.get().getPrecio() %>€</b></span>
                        <button type="submit" name="id" value="<%= optInst.get().getIdInstrumento() %>"
                                class="flex ml-auto text-white bg-red-500 border-0 py-2 px-6 focus:outline-none hover:bg-red-600 rounded">
                            Añadir al carrito
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<% } else { %>
No hay datos sobre este instrumento

<% } %>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/scripts.jspf" %>
</body>

</html>