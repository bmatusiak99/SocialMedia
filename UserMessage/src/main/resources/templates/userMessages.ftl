<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/static/main.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/style.css">
    <title>Reddit</title>
</head>
<body>
<#import "sidebar.ftl" as s>
<@s.sidebar>
</@s.sidebar>
<div class="main">
    <div class="head">
        <form method="get" action="/user-messages/${currentUserId}">
            <input class="in" type="text" name="filter" value="${filter?ifExists}"/>
            <button class="buttonMain" type="submit">Szukaj</button>
        </form>
        <#if isCurrentUser>
            <form method="post" enctype="multipart/form-data">
            <input class="in1" type="text" name="text" value="<#if message??>${message.text}</#if>" placeholder="Wprowadz wiadomosc" />
            <input class="in1" type="text" name="tag" value="<#if message??>${message.tag}</#if>" placeholder="Tag" />
            <input class="in1" type="file" name="file"/>
                <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"  />
            <br><button class="buttonMain" type="submit">Zapisz wiadomość</button>
        </form>
        </#if>
    </div>
    <div>Lista wiadomosci</div>
    <div class="card-columns m-3">
        <#list messages as message>
                <div class="card my-3">
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div class="m-2">
                        <span>${message.text}</span>
                        <i>${message.tag}</i>
                    </div>
                    <div class="card-footer text-muted">
                        <strong>${message.authorName}</strong>
                    </div>
                    <div>

                    </div>
                </div>
        <#else>
    </div>
            <div class="info">Brak postów do wyświetlenia</div>
        </#list>
</div>
</body>
</html>