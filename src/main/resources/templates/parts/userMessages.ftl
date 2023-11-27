<#import "common.ftl" as c>

<@c.page>
    <h3>${userChannel.username}</h3>
    <#if !isCurrentUser>
        <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Подписаться</a>
        <#if isCurrentUser>
            <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Отписаться</a>
        </#if>
    </#if>
    <div class="container my-3">
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Подписки</div>
                        <h3 class="card-text">
                            <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <div class="card-title">Подписчики</div>
                        <h3 class="card-text">
                            <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if isCurrentUser>
        <#include "messageEdit.ftl" />
    </#if>
    <#include "messageList.ftl" />

</@c.page>