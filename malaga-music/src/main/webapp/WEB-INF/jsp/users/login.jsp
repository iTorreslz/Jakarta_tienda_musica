<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Inicia Sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/output.css">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-200">
<div class="h-screen bg-indigo-100 flex justify-center items-center">
    <div class="lg:w-2/5 md:w-1/2 w-2/3">
        <form action="${pageContext.request.contextPath}/users/login" method="post"
              class="bg-white p-10 rounded-lg shadow-lg min-w-full">
            <h1 class="text-center text-2xl mb-6 text-gray-600 font-bold font-sans">Inicia Sesión</h1>
            <div>
                <label class="text-gray-800 font-semibold block my-3 text-md" for="username">Usuario</label>
                <input class="w-full bg-gray-100 px-4 py-2 rounded-lg focus:outline-none" type="text" name="usuario"
                       id="username" placeholder="Usuario"/>
            </div>
            <div>
                <label class="text-gray-800 font-semibold block my-3 text-md" for="password">Contraseña</label>
                <input class="w-full bg-gray-100 px-4 py-2 rounded-lg focus:outline-none" type="password" name="passwd"
                       id="password" placeholder="Contraseña"/>
            </div>
            <div>
                <input type="hidden" name="rol" value="Cliente"/>
            </div>
            <a href="#" class="text-base text-white text-right font-roboto leading-normal hover:underline mb-6">
                ¿No recuerdas tu clave?
            </a>
            <input type="submit" value="Acceder" style="cursor: pointer"
                   class="w-full mt-6 bg-indigo-600 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans"
            />
            <input type="button" value="Atrás" onclick="history.back()" style="cursor: pointer"
                   class="w-full mt-6 bg-indigo-600 rounded-lg px-4 py-2 text-lg text-white tracking-wide font-semibold font-sans"
            />
        </form>
    </div>
</div>
</body>

</html>