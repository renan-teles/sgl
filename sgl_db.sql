CREATE DATABASE sgl;

ALTER DATABASE sgl CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE book_categories(
	id INT auto_increment not null,
    category VARCHAR(60) NOT NULL,
    primary key(id)
)Engine = InnoDB;

CREATE TABLE books(
	id INT auto_increment not null,
    id_book_category INT NOT NULL,
    title Varchar(60) not null,
    author varchar(60) not null,
	description varchar(255) not null,
    total_quantity int not null,
    primary key(id),
    foreign key(id_book_category) references book_categories(id) ON DELETE CASCADE
)Engine = InnoDB;