

# UpdateEventUserRequest

Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**annotation** | **String** | Новая аннотация |  [optional] |
|**category** | **Long** | Новая категория |  [optional] |
|**description** | **String** | Новое описание |  [optional] |
|**eventDate** | **String** | Новые дата и время на которые намечено событие. Дата и время указываются в формате \&quot;yyyy-MM-dd HH:mm:ss\&quot; |  [optional] |
|**location** | [**Location**](Location.md) |  |  [optional] |
|**paid** | **Boolean** | Новое значение флага о платности мероприятия |  [optional] |
|**participantLimit** | **Integer** | Новый лимит пользователей |  [optional] |
|**requestModeration** | **Boolean** | Нужна ли пре-модерация заявок на участие |  [optional] |
|**stateAction** | [**StateActionEnum**](#StateActionEnum) | Изменение сотояния события |  [optional] |
|**title** | **String** | Новый заголовок |  [optional] |



## Enum: StateActionEnum

| Name | Value |
|---- | -----|
| SEND_TO_REVIEW | &quot;SEND_TO_REVIEW&quot; |
| CANCEL_REVIEW | &quot;CANCEL_REVIEW&quot; |



