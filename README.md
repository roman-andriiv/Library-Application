# Library Application
In this repository I created a small CRUD Spring web-application.
<br />

It will help you if you have a small library and give books to your friends😂😂😂😂

Application features:
- add, modify and remove persons from the database and user list;
- add, modify and remove books from the database and book list;
- control of person taken books, attach and detach books from people;
- correct input data validation from the user;
- the index() method in the BooksController can accept the key 'sort_by_year' in the address bar. If it is 'true' then 
the books will be sorted by year. If this key is not passed in the address bar, the books are returned to the normal order;
- Book Expiration Check. If a person took a book more than 10 days ago and still hasn't returned it, this book will be 
highlighted in red on that person's page;
- Books searching. We enter in the field the initial letters of the title of the book, we get the full title of the book 
and the name of the author. Also, if the book is now with someone, we get the name of this person.



Technology stack used during development: ✅Java ✅Spring (Core,MVC, Data JPA)
 ✅PostgreSQL ✅JdbcTemplate ✅Hibernate ORM ✅Hibernate-validator ✅Thymeleaf ✅HTML/CSS  ✅Maven
<br />

Web-application schema:
![Library App](https://user-images.githubusercontent.com/63511356/187675178-8dc1a171-c056-46a2-ac42-fabdf93c2a6c.png)

Screenshots:

![Знімок екрана 2022-08-31 141225](https://user-images.githubusercontent.com/63511356/187675554-7701a883-383d-47a2-b463-714a6beff160.png)
![Знімок екрана 2022-08-31 141225](https://user-images.githubusercontent.com/63511356/187676031-740b997e-71fd-4ad8-bfd9-b8366df33e7d.png)
![Знімок екрана 2022-08-31 141225](https://user-images.githubusercontent.com/63511356/187676700-fbb9e504-84e4-4ece-93b6-01c74c519d8a.png)
![Знімок екрана 2022-08-31 141225](https://user-images.githubusercontent.com/63511356/187676952-a65e27d6-6962-4cb2-b323-c9c6f6a1d712.png)
![Знімок екрана 2022-08-31 141225](https://user-images.githubusercontent.com/63511356/187677326-43c267e8-e27f-4d0f-8d7b-ea82693c18de.png)
![Знімок екрана 2022-08-31 141225](https://user-images.githubusercontent.com/63511356/187678101-aba6624e-62d9-48b6-b6cb-c787ab4c6b6f.png)