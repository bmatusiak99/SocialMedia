<#macro sidebar>
    <div class="sidebar">
        <div class="spec"><img class="emotka" src="http://localhost:8081/emotka.png" id="titleImg">Reddit</div>
        <div class="menu">
            <form action="/">
                <button type="submit" class="button1">Strona główna</button>
            </form>
            <form action="/main">
                <button type="submit" class="button1">Posty</button>
            </form>
            <#if admin??>
                <form action="/user">
                    <button type="submit" class="button1">Lista użytkowników</button>
                </form>
            </#if>
            <#if name!="unknown">
                <form action="/profile">
                    <button type="submit" class="button1">Profil</button>
                </form>
            </#if>
        </div>
        <div class="specb"><div class="user">${name}</div>
            <#if name!="unknown">
                <form action="/logout" method="post">
                    <button type="submit" class="button1">Wyloguj się</button>
                </form>
            </#if>
        </div>
    </div>
</#macro>

