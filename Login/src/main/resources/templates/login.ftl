<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/static/style.css">
  <link rel="stylesheet" href="/static/logReg.css">
  <title>Login</title>
</head>
<body>
<#import "sidebar.ftl" as s>
<@s.sidebar>
</@s.sidebar>
<div class="ribbon"></div>
<div class="login">
  <h1>Reddit</h1>
  <p>Postuj wiadomości tutaj!</p>
  <form action="/login" method="post">
    <div class="input">
      <div class="blockinput">
        <i class="icon-envelope-alt"></i><input type="text" name="username" placeholder="Nazwa użytkownika:" required oninvalid="this.setCustomValidity('Proszę podać login')" oninput="this.setCustomValidity('')">
      </div>
      <div class="blockinput">
        <i class="icon-unlock"></i><input type="password" name="password" placeholder="Hasło:" required oninvalid="this.setCustomValidity('Proszę podać hasło')" oninput="this.setCustomValidity('')">
      </div>
    </div>
    <button class="button2" type="submit">Zaloguj się</button>
  </form>
</div>
<form action="/registration">
  <button class="button3">Utwórz nowe konto</button>
</form>
<#if message2??>
    <div class="success">${message2}</div>
</#if>
<#if message??>
  <div class="error">${message}</div>
</#if>
</body>
</html>