<#import "common.ftl" as c>

<@c.page>
    <#if isCurrentUser>
        <#include "messageEdit.ftl" />
    </#if>
    <#include "messageList.ftl" />

</@c.page>