#  CRUD-приложение на Spring MVC с валидацией и PostgreSQL

## Описание проекта
Веб-приложение для управления списком людей с полным набором CRUD-операций, валидацией данных и использованием Spring MVC, Thymeleaf и PostgreSQL.

## Технологический стек

###  Основные технологии:
- **Java** + **Spring Framework 5.2.1**
- **Spring MVC** (Model-View-Controller)
- **Spring JDBC** для работы с базой данных
- **Thymeleaf 3.0** (шаблонизатор)
- **HTML5**
- **PostgreSQL 42.2** (база данных)
- **Hibernate Validator 6.1** (валидация)
- **Apache Tomcat 9+** (сервер)
- **Servlet API 4.0**

###  Архитектура:
MVC (Model-View-Controller) + DAO (Data Access Object)

## Структура проекта

### Java классы:
```
org.example/
├── config/
│   ├── MySpringMvcDispatcherServletInitializer.java
│   └── SpringConfig.java
├── controllers/
│   └── PeopleController.java
├── dao/
│   ├── PersonDAO.java
│   └── PersonMapper.java
└── models/
    └── Person.java
```
### Thymeleaf шаблоны:
```
WEB-INF/views/people/
├── index.html      # Список всех людей
├── show.html       # Детальная информация
├── new.html        # Форма создания
└── edit.html       # Форма редактирования
```
## База данных

### Таблица PostgreSQL:
```
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    email VARCHAR(100)
);
```

### Настройки подключения:

**URL:** jdbc:postgresql://localhost:5432/PersonCRUD_db

**Пользователь:** postgres

**Пароль:** div3

**Драйвер:** org.postgresql.Driver

## Функциональность
### CRUD операции:
| Операция | HTTP Метод | URL | Описание |
|----------|------------|-----|----------|
|  **Просмотр всех** | `GET` | `/people` | Получить список всех людей |
|  **Просмотр одного** | `GET` | `/people/{id}` | Получить детальную информацию о конкретном человеке |
|  **Форма создания** | `GET` | `/people/new` | Отобразить форму для создания нового человека |
|  **Создание** | `POST` | `/people` | Сохранить нового человека в базе данных |
|  **Форма редактирования** | `GET` | `/people/{id}/edit` | Отобразить форму для редактирования существующего человека |
|  **Обновление** | `PATCH` | `/people/{id}` | Обновить данные человека в базе данных |
|  **Удаление** | `DELETE` | `/people/{id}` | Удалить человека из базы данных |



- <img width="289" height="214" alt="image" src="https://github.com/user-attachments/assets/09641e10-4626-40a4-9136-884d166e6483" />



- <img width="378" height="130" alt="image" src="https://github.com/user-attachments/assets/15eb3b03-635d-48ad-9be6-f23a48e86417" />


### Валидация данных:
**Имя:** не пустое, 2-30 символов

**Возраст:** не отрицательный

**Email:** не пустой, валидный формат

<img width="393" height="310" alt="image" src="https://github.com/user-attachments/assets/c3044387-cdd3-47e5-9a39-1607f7712712" />

## Конфигурация
#### 1. SpringConfig.java
```
@Configuration
@ComponentScan("org.example")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    // Конфигурация Thymeleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() { ... }
    
    @Bean
    public SpringTemplateEngine templateEngine() { ... }
    
    // Конфигурация базы данных
    @Bean
    public DataSource dataSource() { ... }
    
    @Bean
    public JdbcTemplate jdbcTemplate() { ... }
}
```

#### 2. MySpringMvcDispatcherServletInitializer
- **Настраивает DispatcherServlet** - центральный сервлет Spring MVC
- **Регистрирует HiddenHttpMethodFilter** для поддержки PATCH/DELETE методов в HTML формах
- **Без использования web.xml** - вся конфигурация через Java классы (Servlet 3.0+ API)

##### Модель данных
Класс Person:
```
public class Person {
    private int id;
    
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 chapters")
    private String name;
    
    @Min(value = 0, message = "Age shoud be greater then 0")
    private int age;
    
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
}
```
## Data Access Layer
### PersonDAO с JdbcTemplate:
```
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // Все методы используют JdbcTemplate
    public List<Person> index() { ... }
    public Person show(int id) { ... }
    public void save(Person person) { ... }
    public void update(int id, Person person) { ... }
    public void delete(int id) { ... }
}
```

### Используемые методы JdbcTemplate:
query() - SELECT с маппингом в объекты

update() - INSERT, UPDATE, DELETE

BeanPropertyRowMapper - автоматический маппинг полей

## Thymeleaf шаблоны
#### Особенности:
- **Динамические URL:** @{/people/{id}(id=${person.getId()})}
- **Привязка форм:** th:object, th:field
- **Валидация:** th:errors, th:if="${#fields.hasErrors('name')}"
- **Итерация:** th:each="person : ${people}"

**Пример формы:**
```
html
<form th:method="POST" th:action="@{/people}" th:object="${person}">
    <input type="text" th:field="*{name}"/>
    <div th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></div>
</form>
```

## Установка и запуск
#### Предварительные требования:
-**Java 8+**
-**Apache Maven 3.6+**
-**PostgreSQL 12+**
-**Apache Tomcat 9+**

