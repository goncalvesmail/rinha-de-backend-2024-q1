DROP TABLE transacoes;

DROP TABLE clientes;

CREATE TABLE clientes (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	limite INTEGER NOT NULL,
	saldo INTEGER NOT NULL
);

CREATE TABLE transacoes (
	id SERIAL PRIMARY KEY,
	cliente_id INTEGER NOT NULL,
	valor INTEGER NOT NULL,
	tipo CHAR(1) NOT NULL,
	descricao VARCHAR(10) NOT NULL,
	realizada_em TIMESTAMP NOT NULL DEFAULT NOW(),
	CONSTRAINT fk_clientes_transacoes_id
		FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

INSERT INTO clientes(nome, limite, saldo) VALUES ('o barato sai caro', 1000 * 100, 0);
INSERT INTO clientes(nome, limite, saldo) VALUES ('zan corp ltda', 800 * 100, 0);
INSERT INTO clientes(nome, limite, saldo) VALUES ('les cruders', 10000 * 100, 0);
INSERT INTO clientes(nome, limite, saldo) VALUES ('padaria joia de cocaia', 100000 * 100, 0);
INSERT INTO clientes(nome, limite, saldo) VALUES ('kid mais', 5000 * 100, 0);