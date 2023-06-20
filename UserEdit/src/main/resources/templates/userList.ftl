<!DOCTYPE HTML>
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
Lista użytkowników
<table>
    <thead>
    <tr>
        <th>Imie</th>
        <th>Roła</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td><#list user.roles as role>${role}<#sep>; </#list></td>
            <td><a href="/user/${user.id}">Edit</a></td>
        </tr>
    </#list>
    </tbody>
</table>
<button><a href = "/main">Strona główna</a></button>
</body>
</html>