drop table if exists jogo;
drop table if exists jogador;

create table jogador (
    codigo int auto_increment not null,
    apelido varchar(30) not null,
    nome varchar(100) not null,
    email varchar(200) not null,
    data_nasc date not null,
    unique(apelido),
    unique(email),
    primary key(codigo)
);

create table jogo (
    cod_jogador int not null,
    data_hora timestamp not null,
    pontuacao int not null,
    primary key(cod_jogador, data_hora),
    foreign key(cod_jogador) references jogador(codigo)
);

-- alguns registros para teste
insert into jogador values (1, 'admin', 'Administrador Geral', 'admin@email.com', '1970-01-01');
insert into jogador values (2, 'jsilva', 'João da Silva', 'jsilva@email.com', '2002-02-02');
insert into jogador values (3, 'msilva', 'Maria da Silva', 'msilva@email.com', '2003-03-03');
insert into jogador values (4, 'jsantos', 'José dos Santos', 'jsantos@email.com', '2004-04-04');

insert into jogo values (2, '2021-11-15 10:10:32', 3);
insert into jogo values (2, '2021-11-16 19:43:57', 7);

insert into jogo values (3, '2021-11-15 14:34:12', 8);
insert into jogo values (3, '2021-11-17 20:25:22', 2);

insert into jogo values (4, '2021-11-10 16:35:16', 6);
insert into jogo values (4, '2021-11-12 23:15:35', 5);
insert into jogo values (4, '2021-11-23 11:38:18', 7);