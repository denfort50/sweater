<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1"> Имя пользователя:
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       placeholder="Введите имя">
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </label>
        </div>
        <div class="form-group">
            <label> Пароль:
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Введите пароль"/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </label>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label> Пароль:
                    <input type="password" name="password2"
                           class="form-control ${(password2Error??)?string('is-invalid', '')}"
                           placeholder="Подтвердите пароль"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </label>
            </div>
            <div class="form-group">
                <label> Электронная почта:
                    <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           placeholder="Введите email"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </label>
            </div>
        </#if>
        <div class="form-group">
            <div class="g-recaptcha" data-sitekey="6Lez1hUpAAAAACUliJMjFe9_2GuEME0tor8BWumS"></div>
            <#if captchaError??>
                <div class="alert alert-danger" role="alert">
                    ${captchaError}
                </div>
            </#if>
        </div>
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