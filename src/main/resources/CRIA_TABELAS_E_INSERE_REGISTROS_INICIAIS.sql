-- Remover tabelas se existirem
DROP TABLE IF EXISTS compromisso;
DROP TABLE IF EXISTS agenda;
DROP TABLE IF EXISTS funcionario;

-- Criar tabela funcionario
CREATE TABLE funcionario (
    rowid IDENTITY PRIMARY KEY,
    nm_funcionario VARCHAR(255) NOT NULL
);

-- Criar tabela agenda
CREATE TABLE agenda (
    rowid IDENTITY PRIMARY KEY,
    nm_agenda VARCHAR(255) NOT NULL,
    cd_periodo_disponivel TINYINT -- VAZIO, MANHÃ, TARDE ou AMBOS
);

-- Criar tabela compromisso
CREATE TABLE compromisso (
    rowid IDENTITY PRIMARY KEY,
    id_funcionario BIGINT NOT NULL,
    id_agenda BIGINT NOT NULL,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    CONSTRAINT fk_funcionario FOREIGN KEY (id_funcionario) REFERENCES funcionario(rowid) ON DELETE CASCADE,
    CONSTRAINT fk_agenda FOREIGN KEY (id_agenda) REFERENCES agenda(rowid)
);

-- Inserindo dados em funcionario
INSERT INTO funcionario (nm_funcionario) VALUES ('Ana Paula');
INSERT INTO funcionario (nm_funcionario) VALUES ('Carlos Silva');
INSERT INTO funcionario (nm_funcionario) VALUES ('Mariana Souza');

-- Inserindo dados em agenda
INSERT INTO agenda (nm_agenda, cd_periodo_disponivel) VALUES ('Consultas Médicas', 1);
INSERT INTO agenda (nm_agenda, cd_periodo_disponivel) VALUES ('Reuniões Internas', 2);
INSERT INTO agenda (nm_agenda, cd_periodo_disponivel) VALUES ('Atendimentos Externos', 3);

-- Inserindo dados em compromisso
-- Assumindo que os IDs são incrementais a partir de 1
INSERT INTO compromisso (id_funcionario, id_agenda, data, hora) VALUES (1, 1, DATE '2025-09-01', TIME '09:00:00');
INSERT INTO compromisso (id_funcionario, id_agenda, data, hora) VALUES (2, 2, DATE '2025-09-01', TIME '14:30:00');
INSERT INTO compromisso (id_funcionario, id_agenda, data, hora) VALUES (3, 3, DATE '2025-09-02', TIME '10:15:00');
INSERT INTO compromisso (id_funcionario, id_agenda, data, hora) VALUES (1, 3, DATE '2025-09-03', TIME '13:00:00');

