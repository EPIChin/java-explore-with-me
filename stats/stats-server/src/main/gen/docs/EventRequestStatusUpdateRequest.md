

# EventRequestStatusUpdateRequest

Изменение статуса запроса на участие в событии текущего пользователя

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**requestIds** | **List&lt;Long&gt;** | Идентификаторы запросов на участие в событии текущего пользователя |  [optional] |
|**status** | [**StatusEnum**](#StatusEnum) | Новый статус запроса на участие в событии текущего пользователя |  [optional] |



## Enum: StatusEnum

| Name | Value |
|---- | -----|
| CONFIRMED | &quot;CONFIRMED&quot; |
| REJECTED | &quot;REJECTED&quot; |



