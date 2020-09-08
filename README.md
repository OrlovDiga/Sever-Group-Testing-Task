[![Build Status](https://travis-ci.com/OrlovDiga/Sever-Group-Testing-Task.svg?branch=master)](https://travis-ci.com/OrlovDiga/Sever-Group-Testing-Task)

# Sever-Group-Testing-Task

Rest api on Spring Boot.
Testing task to Java Developer position for SeverGroup Company.

<details><summary>Technical assignment:</summary>


Создать REST-сервис книжного магазина по поиску, добавлению и удалению книг из реестра



Хранилище магазина представляет собой набор стеллажей, с тремя уровнями, на каждом уровне выставлены книги в случайном порядке

Требуется написать следующий API:

- Получение книги по ее id

- Получение списка книг по id cтеллажа, либо по номеру уровня, либо по обоим параметрам одновременно

- Добавление книги на определенный стеллаж и уровень

- Удаление книги по id

- Обновление информации по id книги

- Поиск книги в хранилище по ее названию



Требуется покрыть тестами API, и компоненты сервиса.



Используемые технологии: Java 8+, Spring Boot, Maven/Gradle (на выбор кандидата), JUnit

Хранилище - in memory

* Бонус: использовать реальную БД для реализации хранилища

</details>
## Installation

To use this repository, you need to download it. Next, you need to create an application.yml in src/main/resource/ and add the following configuration there:

Application.yml
```
spring:
  jpa:
    database: POSTGRESQL
    show-sql: true
    hibernate:
      ddl-auto: create-drop
      #ddl-auto: update

  datasource:
    platform: postgres
    url: jdbc:postgresql://localhost:5432/DATABASE_NAME
    username: USERNAME
    password: PASSWORD
    driver-class-name: org.postgresql.Driver
```
__P.S.__ Fully uppercase values ​​are not allowed, you need to replace them with existing values.


## Usage

This application has multiple entry points:
* GET */bookStore/books/{bookId}* - returns book by id.
* GET */bookStore/books/rack/{rackId}* - returns books by rackId..
* GET */bookStore/books/level/{number}* - returns books by shelf level.
* GET */bookStore/books/rack/{rackId}/level/{number}* - returns books by rackId and shelf level.
* POST */bookStore/books* - add book.
* DELETE */bookStore/books/{bookId}* - delete book by id.
* PUT */bookStore/books/{bookId}/update* - update book by id.
* POST */bookStore/books/sarch* - search book by name.

<details><summary>Request examples:</summary>

#### */bookStore/books/4*
 `GET`
```
  response
{
"id": "2"
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "SECOND",
}
 ```
#### */bookStore/books/rack/2*
 `GET`
```
  response
[
{
"id": "2"
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "SECOND",
},
{
"id": "1",
"name": "The Master and Margarita",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "SECOND",
}
]
 ```
#### */bookStore/books/level/FIRST*
 `GET`
```
  response
[
{
"id": "4"
"name": "Count of Monte Cristo",
"author": "Alexandre Dumas"
"rackId": "1",
"levelNumber": "FIRST",
},
{
"id": "5"
"name": "Time doesn't wait",
"author": "Jack London"
"rackId": "1",
"levelNumber": "FIRST",
},
{
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "FIRST",
}
]
 ```
#### */bookStore/books/rack/2/level/FIRST*
 `GET`
```
  response
[
{
"id": "4"
"name": "Count of Monte Cristo",
"author": "Alexandre Dumas"
"rackId": "1",
"levelNumber": "FIRST",
},
{
"id": "5"
"name": "Time doesn't wait",
"author": "Jack London"
"rackId": "1",
"levelNumber": "FIRST",
}
]
 ```
#### */bookStore/books *
 `POST`
```
  request
{
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "FIRST",
}

  response
{
"id": "2"
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "SECOND",
}

 ```
#### */bookStore/books/3*
 `DELETE`

```
This request and response haven't body.
 ```
#### */bookStore/books/2/update*
 `PUT`
```
  request
{
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "THIRD",
}

  response
{
"id": "2"
"name": "Morphine",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "THIRD",
}
 ```

#### */bookStore/books/sarch*
 `POST`
```
  request
{
"name": "The Master and Margarita"
}

  response
{
"id": "1",
"name": "The Master and Margarita",
"author": "Bulgakov Mikhail"
"rackId": "2",
"levelNumber": "SECOND",
}

 ```
</details>

## License
[MIT](https://github.com/OrlovDiga/Sever-Group-Testing-Task/blob/master/LICENSE)
