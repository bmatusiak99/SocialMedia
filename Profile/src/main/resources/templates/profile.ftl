<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/static/main.css">
  <link rel="stylesheet" href="/static/style.css">
  <title>Login</title>
</head>
<body>
<#import "sidebar.ftl" as s>
<@s.sidebar>
</@s.sidebar>
<div class="main">
  <h1 class="lab">Twoje dane</h1>
  <form method="post">
    <div class="input">
      <label class="lab" for="pass">Hasło:</label><br>
      <input class="in1" type="password" name="password" id="pass" placeholder="Password:" value="${password}"><br>
      <label class="lab" for="email">Email:</label><br>
      <input class="in1" type="email" name="email" id="email" placeholder="Email:" value="${email!''}">
    </div>
    <button class="buttonReg" type="submit">Zapisz zmiany</button>
  </form>
</div>
</body>
</html>