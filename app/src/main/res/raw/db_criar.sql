CREATE TABLE IF NOT EXISTS usuarios (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        usuario varchar PRIMARY KEY(255),
        senha   varchar(30)
);
