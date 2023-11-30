<a id="anchor"> </a>

# SpringBootLibrary
### 1. Использованные технологии
* Spring Framework
  * Spring Boot
  * Spring MVC
  * Spring Data JPA
* Веб-представление
  * HTML
  * Thymeleaf

### 2. Структура проекта
#### Controllers:
* PersonController 
* BookController
  
#### __Методы контроллеров:__ </a>



|                 Метод                 |                                          Mapping                                           |                                    Используемые методы                                     |                                                                         Работа метода                                                                          |                                       Возвращаемая страница                                       |
|:-------------------------------------:|:------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------:|
|               showAll()               |                                    /people <br/> /books                                    |                    personService.findAll() <br/> bookService.findAll()                     |                                                               Ищет в БД всех клиентов/все книги                                                                |                    Страница со всеми [клиентами](#/people)/[книгами](#/books)                     |
|              showUnit()               |                                        /people/{id}                                        |                  1. personService.findOne(id) <br/> 2. person.getBooks()                   |                                                    Ищет в БД клиента по {id} (1) и все взятые им книги (2)                                                     |                                  [Страница клиента](#/people/id)                                  |
|              showUnit()               |                                        /books/{id}                                         | 1. bookService.findOne(id) <br/> 2. book.getOwner().get() <br/> 3. personService.findAll() | Ищет в БД книгу по {id} (1) и её владельца (2). Если у книги отсутствует владелец - ищет в БД всех клиентов (3) и добавляет соответствующий список на страницу |                                   [Страница книги](#/books/id)                                    |
|         newPerson()/newBook()         |                               [/people/new <br/> /books/new                                |                                                                                            |                                                                                                                                                                |         Страница с формой добавления нового [клиента](#/people/new)/[книги](#/books/new)          |
|      newPersonPost()/newBookPost      |                          [PostMapping] <br/> /people <br/> /books                          |               personService.save(newPerson) <br/> bookService.save(newBook)                |                                                             Сохраняет клиента/книгу из формы в БД                                                              |                    Страница со всеми [клиентами](#/people)/[книгами](#/books)                     |
|      updatePerson()/updateBook()      |            /people/{id}/edit <br/> /books/{id}/edit <a id="/controllers"> </a>             |                  personService.findOne(id) <br/> bookService.findOne(id)                   |                                            Ищет в БД клиента/книгу по {id} и добавляет объект на страницу(в форму)                                             | Страница с формой изменения существующего(ей) [клиента](#/people/id/edit)/[книги](#books/id/edit) |
| updatePersonPatch()/updateBookPatch() |                    [PatchMapping] <br/> /people/{id} <br/> /books/{id}                     |            personService.update(id, person) <br/> bookService.update(id, book)             |                                                                  Обновляет клиента/книгу в БД                                                                  |                        Страница [клиента](#/people/id)/[книги](#/books/id)                        |
|      deletePerson()/deleteBook()      |                    [DeleteMapping] <br/> /people/{id} <br/> /books/{id}                    |                   personService.delete(id) <br/> bookService.delete(id)                    |                                                                  Удаляет клиента/книгу из БД                                                                   |                    Страница со всеми [клиентами](#/people)/[книгами](#/books)                     |
|      releaseBook()/assignBook()       |             [PatchMapping] <br/> /books/{id}/release <br/> /books/{id}/assign              |                           bookService.updateOwner(id, newOwner)                            |                                          Освобождает книгу из владения(newOwner = null) / назначает нового владельца                                           |                                   Страница [книги](#/books/id)                                    |
|             searchPage()              |                                       /books/search                                        |                                                                                            |                                                                                                                                                                |                              [Страница поиска книг](#/books/search)                               |
|           makeSearchPage()            |                             [PostMapping] <br/> /books/search                              |                               bookService.findByTitle(query)                               |                                                        Ищет в БД книги по вхождению {query} в названии                                                         |                    [Страница поиска книг с результами поиска](#/books/search1)                    |

#### Models:
* Person
  * int id
  * String fullName(ФИО)
  * int birthYear(Дата рождения)
  * List(Book) books(Книги, находящиеся во владении)
* Book
  * int id
  * String title(Название)
  * String author(Автор)
  * int year(Год публикации)
  * Person owner(Текущий владелец)
  * Date dateOfTaken(Дата и время назначения книги клиенту(через 10 дней книга станет просроченной))

#### Repositories:
* PersonRepository
* BookRepository

#### Services:
* PersonService
* BookService

Дополнительные методы:

|                                Название метода                                 |                    Метод из Repository                     |                     Работа метода                      |
|:------------------------------------------------------------------------------:|:----------------------------------------------------------:|:------------------------------------------------------:|
|                       PersonService.nameIsUsed(fullName)                       |              existsPersonByFullName(fullName)              |            Существует ли имя в базе данных             |
|                     BookService.findByTitle(String query)                      |           findByTitleContainingIgnoreCase(query)           |     List с книгами содержащими {query} в названии      |
|                   updateOwner(int id, Person selectedPerson)                   |      findOne(id) <br/> findOne(selectedPerson.getId()      | Обновляет владельца у книги с {id} на {selectedPerson} |

#### Util:
* PersonValidator
  * validate() - производит валидацию имени на уникальность
  * validateUpdate() - та же валидация, но учитывающая имя обновляемого клиента  

### 3. Веб-представление:
#### (Нажмите на картинку, чтобы перейти обратно к таблице)
### <an id="/people"> /people </a>
[![/people](imagesForReadme/.people.png)](#/controllers)

### <an id="/people/id"> /people/{id} </a>
[![/people/{id}](imagesForReadme/.people.%7Bid%7D.png)](#/controllers)

### <an id="/people/id/edit"> /people/{id}/edit </a>
[![.people.{id}.edit.png](imagesForReadme/.people.%7Bid%7D.edit.png)](#/controllers)

### <an id="/people/new"> /people/new </a>
[![.people.new.png](imagesForReadme/.people.new.png)](#/controllers)

### <an id="/books"> /books </a>
[![.books.png](imagesForReadme/.books.png)](#/controllers)

### <an id="/books/id"> /books/{id} </a>
[![.books.{id}.png](imagesForReadme/.books.%7Bid%7D.png)](#/controllers)

### <an id="/books/id/edit"> /books/{id}/edit </a>
[![.books.{id}.edit.png](imagesForReadme/.books.%7Bid%7D.edit.png)](#/controllers)

### <an id="/books/new"> /books/new </a>
[![.books.new.png](imagesForReadme/.books.new.png)](#/controllers)

### <an id="/books/search"> /books/search </a>
[![.books.search.png](imagesForReadme/.books.search.png)](#/controllers)

### <an id="/books/search1"> /books/search с результатами </a>
[![.books.search1.png](imagesForReadme/.books.search1.png)](#/controllers)
[Вверх](#anchor)

