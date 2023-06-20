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
        <form method="get" action="/main">
            <input class="in" type="text" name="filter" placeholder="Szukaj tag" value="${filter?ifExists}"/>
            <button class="buttonMain" type="submit">Szukaj</button>
        </form>
        <form method="post" enctype="multipart/form-data">
            <input class="in1" type="text" name="text" placeholder="Wprowadz wiadomosc" />
            <input class="in1" type="text" name="tag" placeholder="Tag" />
            <input class="in1" type="file" name="file"/>
            <br><button class="buttonMain" type="submit">Dodać nowy post</button>
        </form>
    </div>
    <br>
    <div>&nbsp; &nbsp;Lista wiadomości</div>
    <div class="card-columns m-3">
        <#list messages as message>
            <div class="card my-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span><br>
                    <i>#${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    <a href="/user-messages/${message.author.id}"><strong>${message.authorName}</strong></a>
                    <#if message.author.id == currentUserId>
                        <a class="buttonEdit" href="/user-messages/${message.author.id}?message=${message.id}">Zmień swój post</a>
                    </#if>
                </div>
                <div>

                </div>
            </div>
        <#else>
            Brak postów do wyświetlenia.
        </#list>
    </div>
</div>
</body>
</html>