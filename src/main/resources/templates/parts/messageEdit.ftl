<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Редактор сообщений
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" action="/main" enctype="multipart/form-data">
            <div class="form-group">
                <label>
                    <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                           placeholder="Введите сообщение"
                           value="<#if message??>${message.text}</#if>"/>
                </label>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label>
                    <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
                           placeholder="Тэг"
                           value="<#if message??>${message.tag}</#if>"/>
                </label>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="custom-file">
                <input type="file" name="file" id="customFile"/>
                <label class="custom-file-label" for="customFile">Выберите файл</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group mt-3">
                <button type="submit" class="btn btn-primary">Сохранить сообщение</button>
            </div>
        </form>
    </div>
</div>