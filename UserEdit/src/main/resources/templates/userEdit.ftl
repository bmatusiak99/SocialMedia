<!DOCTYPE HTML>
<head>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
User Editor
<form action="/user" method="post">
    <input type="text" name="username" value="${user.username}"/>
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("cheked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" name="userId" value="${user.id}"/>
    <button type="submit">Save</button>
</form>
</body>
</html>