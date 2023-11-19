<#import "parts/common.ftl" as c>

<@c.page>
    ${message!}
    <h5>${username}</h5>
    <form method="post">
        <div class="form-group">
            <label> Пароль:
                <input type="password" name="password" class="form-control" placeholder="Введите пароль"/>
            </label>
        </div>
        <div class="form-group">
            <label> Электронная почта:
                <input type="email" name="email" class="form-control" placeholder="Введите email" value="${email!''}"/>
            </label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Сохранить</button>
    </form>
</@c.page>