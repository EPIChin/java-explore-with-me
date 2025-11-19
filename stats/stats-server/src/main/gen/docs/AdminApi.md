# AdminApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addCategory**](AdminApi.md#addCategory) | **POST** /admin/categories | Добавление новой категории |
| [**delete**](AdminApi.md#delete) | **DELETE** /admin/users/{userId} | Удаление пользователя |
| [**deleteCategory**](AdminApi.md#deleteCategory) | **DELETE** /admin/categories/{catId} | Удаление категории |
| [**deleteCompilation**](AdminApi.md#deleteCompilation) | **DELETE** /admin/compilations/{compId} | Удаление подборки |
| [**getEvents2**](AdminApi.md#getEvents2) | **GET** /admin/events | Поиск событий |
| [**getUsers**](AdminApi.md#getUsers) | **GET** /admin/users | Получение информации о пользователях |
| [**registerUser**](AdminApi.md#registerUser) | **POST** /admin/users | Добавление нового пользователя |
| [**saveCompilation**](AdminApi.md#saveCompilation) | **POST** /admin/compilations | Добавление новой подборки (подборка может не содержать событий) |
| [**updateCategory**](AdminApi.md#updateCategory) | **PATCH** /admin/categories/{catId} | Изменение категории |
| [**updateCompilation**](AdminApi.md#updateCompilation) | **PATCH** /admin/compilations/{compId} | Обновить информацию о подборке |
| [**updateEvent1**](AdminApi.md#updateEvent1) | **PATCH** /admin/events/{eventId} | Редактирование данных события и его статуса (отклонение/публикация). |


<a id="addCategory"></a>
# **addCategory**
> CategoryDto addCategory(newCategoryDto)

Добавление новой категории

Обратите внимание: имя категории должно быть уникальным

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    NewCategoryDto newCategoryDto = new NewCategoryDto(); // NewCategoryDto | данные добавляемой категории
    try {
      CategoryDto result = apiInstance.addCategory(newCategoryDto);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#addCategory");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **newCategoryDto** | [**NewCategoryDto**](NewCategoryDto.md)| данные добавляемой категории | |

### Return type

[**CategoryDto**](CategoryDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Категория добавлена |  -  |
| **400** | Запрос составлен некорректно |  -  |
| **409** | Нарушение целостности данных |  -  |

<a id="delete"></a>
# **delete**
> delete(userId)

Удаление пользователя

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    Long userId = 56L; // Long | id пользователя
    try {
      apiInstance.delete(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#delete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **userId** | **Long**| id пользователя | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Пользователь удален |  -  |
| **404** | Пользователь не найден или недоступен |  -  |

<a id="deleteCategory"></a>
# **deleteCategory**
> deleteCategory(catId)

Удаление категории

Обратите внимание: с категорией не должно быть связано ни одного события.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    Long catId = 56L; // Long | id категории
    try {
      apiInstance.deleteCategory(catId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#deleteCategory");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **catId** | **Long**| id категории | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Категория удалена |  -  |
| **404** | Категория не найдена или недоступна |  -  |
| **409** | Существуют события, связанные с категорией |  -  |

<a id="deleteCompilation"></a>
# **deleteCompilation**
> deleteCompilation(compId)

Удаление подборки

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    Long compId = 56L; // Long | id подборки
    try {
      apiInstance.deleteCompilation(compId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#deleteCompilation");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **compId** | **Long**| id подборки | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Подборка удалена |  -  |
| **404** | Подборка не найдена или недоступна |  -  |

<a id="getEvents2"></a>
# **getEvents2**
> List&lt;EventFullDto&gt; getEvents2(users, states, categories, rangeStart, rangeEnd, from, size)

Поиск событий

Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия  В случае, если по заданным фильтрам не найдено ни одного события, возвращает пустой список

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    List<Long> users = Arrays.asList(); // List<Long> | список id пользователей, чьи события нужно найти
    List<String> states = Arrays.asList(); // List<String> | список состояний в которых находятся искомые события
    List<Long> categories = Arrays.asList(); // List<Long> | список id категорий в которых будет вестись поиск
    String rangeStart = "rangeStart_example"; // String | дата и время не раньше которых должно произойти событие
    String rangeEnd = "rangeEnd_example"; // String | дата и время не позже которых должно произойти событие
    Integer from = 0; // Integer | количество событий, которые нужно пропустить для формирования текущего набора
    Integer size = 10; // Integer | количество событий в наборе
    try {
      List<EventFullDto> result = apiInstance.getEvents2(users, states, categories, rangeStart, rangeEnd, from, size);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#getEvents2");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **users** | [**List&lt;Long&gt;**](Long.md)| список id пользователей, чьи события нужно найти | [optional] |
| **states** | [**List&lt;String&gt;**](String.md)| список состояний в которых находятся искомые события | [optional] |
| **categories** | [**List&lt;Long&gt;**](Long.md)| список id категорий в которых будет вестись поиск | [optional] |
| **rangeStart** | **String**| дата и время не раньше которых должно произойти событие | [optional] |
| **rangeEnd** | **String**| дата и время не позже которых должно произойти событие | [optional] |
| **from** | **Integer**| количество событий, которые нужно пропустить для формирования текущего набора | [optional] [default to 0] |
| **size** | **Integer**| количество событий в наборе | [optional] [default to 10] |

### Return type

[**List&lt;EventFullDto&gt;**](EventFullDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | События найдены |  -  |
| **400** | Запрос составлен некорректно |  -  |

<a id="getUsers"></a>
# **getUsers**
> List&lt;UserDto&gt; getUsers(ids, from, size)

Получение информации о пользователях

Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки), либо о конкретных (учитываются указанные идентификаторы)  В случае, если по заданным фильтрам не найдено ни одного пользователя, возвращает пустой список

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    List<Long> ids = Arrays.asList(); // List<Long> | id пользователей
    Integer from = 0; // Integer | количество элементов, которые нужно пропустить для формирования текущего набора
    Integer size = 10; // Integer | количество элементов в наборе
    try {
      List<UserDto> result = apiInstance.getUsers(ids, from, size);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#getUsers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ids** | [**List&lt;Long&gt;**](Long.md)| id пользователей | [optional] |
| **from** | **Integer**| количество элементов, которые нужно пропустить для формирования текущего набора | [optional] [default to 0] |
| **size** | **Integer**| количество элементов в наборе | [optional] [default to 10] |

### Return type

[**List&lt;UserDto&gt;**](UserDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Пользователи найдены |  -  |
| **400** | Запрос составлен некорректно |  -  |

<a id="registerUser"></a>
# **registerUser**
> UserDto registerUser(newUserRequest)

Добавление нового пользователя

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    NewUserRequest newUserRequest = new NewUserRequest(); // NewUserRequest | Данные добавляемого пользователя
    try {
      UserDto result = apiInstance.registerUser(newUserRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#registerUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **newUserRequest** | [**NewUserRequest**](NewUserRequest.md)| Данные добавляемого пользователя | |

### Return type

[**UserDto**](UserDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Пользователь зарегистрирован |  -  |
| **400** | Запрос составлен некорректно |  -  |
| **409** | Нарушение целостности данных |  -  |

<a id="saveCompilation"></a>
# **saveCompilation**
> CompilationDto saveCompilation(newCompilationDto)

Добавление новой подборки (подборка может не содержать событий)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    NewCompilationDto newCompilationDto = new NewCompilationDto(); // NewCompilationDto | данные новой подборки
    try {
      CompilationDto result = apiInstance.saveCompilation(newCompilationDto);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#saveCompilation");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **newCompilationDto** | [**NewCompilationDto**](NewCompilationDto.md)| данные новой подборки | |

### Return type

[**CompilationDto**](CompilationDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Подборка добавлена |  -  |
| **400** | Запрос составлен некорректно |  -  |
| **409** | Нарушение целостности данных |  -  |

<a id="updateCategory"></a>
# **updateCategory**
> CategoryDto updateCategory(catId, categoryDto)

Изменение категории

Обратите внимание: имя категории должно быть уникальным

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    Long catId = 56L; // Long | id категории
    CategoryDto categoryDto = new CategoryDto(); // CategoryDto | Данные категории для изменения
    try {
      CategoryDto result = apiInstance.updateCategory(catId, categoryDto);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#updateCategory");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **catId** | **Long**| id категории | |
| **categoryDto** | [**CategoryDto**](CategoryDto.md)| Данные категории для изменения | |

### Return type

[**CategoryDto**](CategoryDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Данные категории изменены |  -  |
| **404** | Категория не найдена или недоступна |  -  |
| **409** | Нарушение целостности данных |  -  |

<a id="updateCompilation"></a>
# **updateCompilation**
> CompilationDto updateCompilation(compId, updateCompilationRequest)

Обновить информацию о подборке

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    Long compId = 56L; // Long | id подборки
    UpdateCompilationRequest updateCompilationRequest = new UpdateCompilationRequest(); // UpdateCompilationRequest | данные для обновления подборки
    try {
      CompilationDto result = apiInstance.updateCompilation(compId, updateCompilationRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#updateCompilation");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **compId** | **Long**| id подборки | |
| **updateCompilationRequest** | [**UpdateCompilationRequest**](UpdateCompilationRequest.md)| данные для обновления подборки | |

### Return type

[**CompilationDto**](CompilationDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Подборка обновлена |  -  |
| **404** | Подборка не найдена или недоступна |  -  |

<a id="updateEvent1"></a>
# **updateEvent1**
> EventFullDto updateEvent1(eventId, updateEventAdminRequest)

Редактирование данных события и его статуса (отклонение/публикация).

Редактирование данных любого события администратором. Валидация данных не требуется. Обратите внимание:  - дата начала изменяемого события должна быть не ранее чем за час от даты публикации. (Ожидается код ошибки 409) - событие можно публиковать, только если оно в состоянии ожидания публикации (Ожидается код ошибки 409) - событие можно отклонить, только если оно еще не опубликовано (Ожидается код ошибки 409)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AdminApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    AdminApi apiInstance = new AdminApi(defaultClient);
    Long eventId = 56L; // Long | id события
    UpdateEventAdminRequest updateEventAdminRequest = new UpdateEventAdminRequest(); // UpdateEventAdminRequest | Данные для изменения информации о событии
    try {
      EventFullDto result = apiInstance.updateEvent1(eventId, updateEventAdminRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdminApi#updateEvent1");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **eventId** | **Long**| id события | |
| **updateEventAdminRequest** | [**UpdateEventAdminRequest**](UpdateEventAdminRequest.md)| Данные для изменения информации о событии | |

### Return type

[**EventFullDto**](EventFullDto.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Событие отредактировано |  -  |
| **404** | Событие не найдено или недоступно |  -  |
| **409** | Событие не удовлетворяет правилам редактирования |  -  |

