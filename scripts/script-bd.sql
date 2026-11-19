-- =========================================
-- 1. CRIACAO DAS TABELAS (DDL)
-- =========================================

CREATE TABLE cidadao (
                         id_cidadao BIGINT IDENTITY(1,1) PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL
);

CREATE TABLE categoria (
                           id_categoria BIGINT IDENTITY(1,1) PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           descricao VARCHAR(255)
);

CREATE TABLE ocorrencia (
                            id_ocorrencia BIGINT IDENTITY(1,1) PRIMARY KEY,
                            titulo VARCHAR(150) NOT NULL,
                            descricao VARCHAR(500) NOT NULL,
                            id_cidadao BIGINT NOT NULL,
                            id_categoria BIGINT NOT NULL,

                            FOREIGN KEY (id_cidadao) REFERENCES cidadao(id_cidadao),
                            FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);


-- =========================================
-- 2. INSERCAO DE DADOS (INSERT)
-- =========================================

INSERT INTO cidadao (nome, email)
VALUES ('Joao Silva', 'joao@email.com');

INSERT INTO categoria (nome, descricao)
VALUES ('Iluminacao Publica', 'Problemas com postes e iluminacao');

INSERT INTO ocorrencia (titulo, descricao, id_cidadao, id_categoria)
VALUES ('Poste apagado', 'Poste sem iluminacao na rua', 1, 1);


-- =========================================
-- 3. CONSULTA DE DADOS (SELECT)
-- =========================================

SELECT * FROM cidadao;

SELECT * FROM categoria;

SELECT * FROM ocorrencia;


-- =========================================
-- 4. ATUALIZACAO DE DADOS (UPDATE)
-- =========================================

UPDATE cidadao
SET nome = 'Joao Oliveira'
WHERE id_cidadao = 1;

UPDATE categoria
SET descricao = 'Manutencao da iluminacao publica'
WHERE id_categoria = 1;

UPDATE ocorrencia
SET titulo = 'Poste com lampada queimada'
WHERE id_ocorrencia = 1;


-- =========================================
-- 5. EXCLUSAO DE DADOS (DELETE)
-- =========================================

DELETE FROM ocorrencia
WHERE id_ocorrencia = 1;

DELETE FROM categoria
WHERE id_categoria = 1;

DELETE FROM cidadao
WHERE id_cidadao = 1;