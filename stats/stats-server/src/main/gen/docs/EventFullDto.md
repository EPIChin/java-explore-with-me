

# EventFullDto


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**annotation** | **String** | Краткое описание |  |
|**category** | [**CategoryDto**](CategoryDto.md) |  |  |
|**confirmedRequests** | **Long** | Количество одобренных заявок на участие в данном событии |  [optional] |
|**createdOn** | **String** | Дата и время создания события (в формате \&quot;yyyy-MM-dd HH:mm:ss\&quot;) |  [optional] |
|**description** | **String** | Полное описание события |  [optional] |
|**eventDate** | **String** | Дата и время на которые намечено событие (в формате \&quot;yyyy-MM-dd HH:mm:ss\&quot;) |  |
|**id** | **Long** | Идентификатор |  [optional] |
|**initiator** | [**UserShortDto**](UserShortDto.md) |  |  |
|**location** | [**Location**](Location.md) |  |  |
|**paid** | **Boolean** | Нужно ли оплачивать участие |  |
|**participantLimit** | **Integer** | Ограничение на количество участников. Значение 0 - означает отсутствие ограничения |  [optional] |
|**publishedOn** | **String** | Дата и время публикации события (в формате \&quot;yyyy-MM-dd HH:mm:ss\&quot;) |  [optional] |
|**requestModeration** | **Boolean** | Нужна ли пре-модерация заявок на участие |  [optional] |
|**state** | [**StateEnum**](#StateEnum) | Список состояний жизненного цикла события |  [optional] |
|**title** | **String** | Заголовок |  |
|**views** | **Long** | Количество просмотрев события |  [optional] |



## Enum: StateEnum

| Name | Value |
|---- | -----|
| PENDING | &quot;PENDING&quot; |
| PUBLISHED | &quot;PUBLISHED&quot; |
| CANCELED | &quot;CANCELED&quot; |



