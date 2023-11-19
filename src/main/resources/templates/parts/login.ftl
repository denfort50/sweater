<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1"> Имя пользователя:
                <input type="text" name="username" class="form-control" placeholder="Введите имя">
            </label>
        </div>
        <div class="form-group">
            <label> Пароль:
                <input type="password" name="password" class="form-control" placeholder="Введите пароль"/>
            </label>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label> Электронная почта:
                    <input type="email" name="email" class="form-control" placeholder="Введите email"/>
                </label>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <a href="/registration">Зарегистрироваться</a>
        </#if>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Зарегистрироваться<#else>Войти</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Выйти</button>
    </form>
</#macro>