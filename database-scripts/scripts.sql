CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE usuario
(
	usuario_id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
	codigo numeric NOT NULL ,
	nome varchar(100) NOT NULL,
	nascimento date NOT NULL,
	cpf varchar(11) NOT NULL,
	email varchar(100) NOT NULL,
	senha varchar(10) NOT NULL,
	situacao varchar(10) NOT NULL
);