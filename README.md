﻿LEARNING MANAGEMENT SYSTEM (CLI)
Система управления обучением

Роли
Проект предусматривает следующие роли: студент, тренер.

Студент
Студент учится на курсе и входит в состав определенной группы. Задача студента - выполнение задач, полученных в рамках курса. Студент может обучаться одновременно на нескольких курсах.

У каждого студента должны быть: уникальный идентификатор, имя, фамилия, список курсов на которых он сейчас учится, список задач полученных в рамках каждого курса.

Тренер
Тренер читает один и более курсов. Каждый тренер должен иметь: уникальный идентификатор, имя, фамилию, список курсов которые он читает.

Курс
Каждый курс должен содержать: уникальный идентификатор, название, краткое описание, список студентов записанных на данный курс, тренера который читает курс, дату начала, дату окончания, список дней недели в которые проходят занятия, а также журнал успеваемости. Курс имеет ограничение, на один курс не может быть записано более чем 12 студентов. Студент должен иметь возможность перейти с одного курса на другой.

Журнал успеваемости
Журнал успеваемости хранит количество полученных баллов каждым студентом по каждой задаче в рамках курса. 

Система управления обучением
Система должна позволять при помощи command line interface (CLI) выполнять управление курсами, а именно:
1.	Создание курса
2.	Вывод подробной информации о курсе по его идентификатору
3.	Вывод списка названий всех курсов
4.	Создание студента в рамках определенного курса(ов)
5.	Перевод студента из одного курса на другой
6.	Вывод информации о студенте по его идентификатору
7.	Создание тренера в рамках определенного курса(ов)
8.	Вывод информации о тренере по его идентификатору
9.	Создание задач в рамках определенного курса
10.	Вывод имен и фамилий всех студентов по идентификатору курса
11.	Вывод журнала успеваемости определенного курса
12.	Сохранение журнала успеваемости в файл
13.	Выход из программы

ЗАДАНИЕ
Реализовать проект “Cистему управления обучением” согласно описанным выше требованиям. При реализации выбирать и использовать наиболее подходящие коллекции и структуры данных. Предусмотреть возможность сборки проекта при помощи сборщика Maven. И последующего запуска приложения из полученного jar-артефакта. Где есть необходимость применить изученные паттерны проектирования. На произвольную часть функционала написать 7 unit-тестов.


СОЗДАНИЕ КУРСА
Создает новый курс. Идентификатор курса должен формироваться автоматически. При попытке создать курс с названием которое уже есть, выводить сообщение: Course name should be unique. Please, enter another name.

Пример команды:
Please, enter the command:
create course

Course name:
Java for Beginners
Course description:
Course for people that want learn Java programming language
Start date:
01.01.2015
End date: 01.04.2015
Days:
Tue, Wed, Sat

New course has been successfully created:
Course ID: 1
Course name: Java for Beginners
Course description: Course for people that want learn Java programming language
Start date: 01.01.2015
End date: 01.04.2015
Days: Tue, Wed, Sat


ВЫВОД ПОДРОБНОЙ ИНФОРМАЦИИ О КУРСЕ ПО ЕГО ИДЕНТИФИКАТОРУ
Выводит описание курса по его идентификатору. Если курса с указанным идентификатором нет, необходимо вывести сообщение: Course with id xx doesn’t exist.

Пример команды:
Please, enter the command:
show course 1

Course ID: 1
Course name: Java for Beginners
Course description: Course for people that want learn Java programming language
Start date: 01.01.2015
End date: 01.04.2015
Days: Tue, Wed, Sat


ВЫВОД СПИСКА НАЗВАНИЙ ВСЕХ КУРСОВ
Выводит список идентификаторов и названий всех курсов. Если ни один курс еще не добавлен, вывести сообщение: No courses available yet

Пример команды:
Please, enter the command:
show courses

1: Java for beginners
2: .NET for beginners
3. Automated testing


СОЗДАНИЕ СТУДЕНТА В РАМКАХ ОПРЕДЕЛЕННОГО КУРСА(ОВ)
Добавляет нового студента и записывает его на курс. Идентификатор студента должен формироваться автоматически. При попытке создать студента с именем и фамилией, которые уже есть, выводить сообщение: Student name should be unique. Please, enter another name. Список курсов задается идентификаторами курса, разделенными запятой. В случае, если одного или нескольких курсов не существует, выводить сообщения вида: Course with id xx doesn’t exist (для каждого идентификатора) – при этом на существующие курсы зачисление все равно производится.

Пример команды:
Please, enter the command:
create student

First name: Ivan
Last name: Ivanov
Age: 24
Course(s): 1,2,5

Course with id 5 doesn’t exist
New student has been successfully created:

Student ID: 1
Name: Ivan Ivanov
Age: 24
Courses:
1: Java for beginners
2: .NET for beginners


ПЕРЕВОД СТУДЕНТА ИЗ ОДНОГО КУРСА НА ДРУГОЙ
Производится в рамках двух команд. Первая зачисляет студента с указанным идентификатором на курс с указанным идентификатором. Вторая отчисляет указанного студента с указанного курса. Если студента с указанным идентификатором нет, необходимо вывести сообщение: Student with id xx doesn’t exist. Если курса с указанным идентификатором нет, необходимо вывести сообщение: Course with id xx doesn’t exist. 

Пример команды:
Please, enter the command:
transfer student 1 add 3

Student Ivan Ivanov was successfully enrolled for course Automated testing

Please, enter the command:
transfer student 1 remove 2

Student Ivan Ivanov was expelled from course : .NET for beginners


ВЫВОД ИНФОРМАЦИИ О СТУДЕНТЕ ПО ЕГО ИДЕНТИФИКАТОРУ
Выводит информацию о студенте по его идентификатору. Если студента с указанным идентификатором нет, необходимо вывести сообщение: Student with id xx doesn’t exist.

Пример команды:
Please, enter the command:
show student 1

Student ID: 1
Name: Ivan Ivanov
Age: 24
Courses:
1: Java for beginners
3: Automated testing


СОЗДАНИЕ ТРЕНЕРА В РАМКАХ ОПРЕДЕЛЕННОГО КУРСА(ОВ)
Добавляет нового тренера на курс. Идентификатор тренера должен формироваться автоматически. При попытке создать тренера с именем и фамилией, которые уже есть, выводить сообщение: Trainer name should be unique. Please, enter another name. Список курсов задается идентификаторами курса, разделенными запятой. В случае, если одного или нескольких курсов не существует, выводить сообщения вида: Course with id xx doesn’t exist (для каждого идентификатора).

Пример команды:
Please, enter the command:
create trainer

First name: Petr
Last name: Petrov
Course(s): 1

New trainer has been successfully created:

Trainer ID: 1
Name: Petr Petrov
Courses:
1: Java for beginners


ВЫВОД ИНФОРМАЦИИ О ТРЕНЕРЕ ПО ЕГО ИДЕНТИФИКАТОРУ
Выводит информацию о тренере по его идентификатору. Если тренера с указанным идентификатором нет, необходимо вывести сообщение: Trainer with id xx doesn’t exist.

Пример команды:
Please, enter the command:
show trainer 1

Trainer ID: 1
Name: Petr Petrov
Courses:
1: Java for beginners


Создание задач в рамках определенного курса
Вывод имен и фамилий всех студентов по идентификатору курса
Вывод журнала успеваемости определенного курса
Сохранение журнала успеваемости в файл


ВЫХОД ИЗ ПРОГРАММЫ
Завершает работу с приложением

Пример команды:
Please, enter the command:
exit

Goodbye!