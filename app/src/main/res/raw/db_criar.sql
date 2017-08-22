CREATE TABLE IF NOT EXISTS usuarios (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        usuario varchar(255),
        senha   varchar(30)
);
CREATE TABLE IF NOT EXISTS favoritos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(255),
    postalcode varchar(200),
    telefone varchar(40),
    latitute varchar(200),
    longitude varchar(200)
);