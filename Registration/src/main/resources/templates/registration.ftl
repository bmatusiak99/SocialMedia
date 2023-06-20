<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/static/logReg.css">
  <link rel="stylesheet" href="/static/style.css">
  <title>Registration</title>
</head>
<body>
<#import "sidebar.ftl" as s>
<@s.sidebar>
</@s.sidebar>
<div class="ribbon"></div>
<div class="login">
  <h1>Reddit</h1>
  <p>Zarejestruj się tutaj</p>
  <form action="/registration" method="post">
    <div class="input">
      <div class="blockinput">
        <i class="icon-envelope-alt"></i><input type="text" name="username" placeholder="Nazwa użytkownika:" required oninvalid="this.setCustomValidity('Proszę podać login')" oninput="this.setCustomValidity('')">
      </div>
      <div class="blockinput">
        <i class="icon-unlock"></i><input type="password" name="password" placeholder="Hasło:" required oninvalid="this.setCustomValidity('Proszę podać hasło')" oninput="this.setCustomValidity('')">
      </div>
      <div class="blockinput">
        <i class="icon-unlock"></i><input type="password" name="password2" placeholder="Potwierdź hasło:" required oninvalid="this.setCustomValidity('Proszę potwierdzić hasło')" oninput="this.setCustomValidity('')">
      </div>
      <div class="blockinput">
        <i class="icon-unlock"></i><input type="email" name="email" placeholder="Email:" required oninvalid="this.setCustomValidity('Proszę podać email')" oninput="this.setCustomValidity('')">
      </div>
    </div>
    <button class="button2" type="submit">Zarejestruj się</button>
  </form>
</div>
<h2>${message?ifExists}</h2>
</body>
</html>