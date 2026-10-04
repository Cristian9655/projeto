-- ============================================================
-- DDL - Projeto Java / Azure SQL
-- ============================================================

-- ============================================================
-- TABELA: CLIENTE
-- ============================================================

CREATE TABLE cliente (
                         id_cliente BIGINT IDENTITY(1,1) NOT NULL,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL,

                         CONSTRAINT pk_cliente
                             PRIMARY KEY (id_cliente)
);

-- ============================================================
-- TABELA: PEDIDO
-- ============================================================

CREATE TABLE pedido (
                        id_pedido BIGINT IDENTITY(1,1) NOT NULL,
                        data_pedido DATE NOT NULL,
                        valor_total DECIMAL(10,2) NOT NULL,
                        id_cliente BIGINT NOT NULL,

                        CONSTRAINT pk_pedido
                            PRIMARY KEY (id_pedido),

                        CONSTRAINT fk_pedido_cliente
                            FOREIGN KEY (id_cliente)
                                REFERENCES cliente(id_cliente)
);

-- ============================================================
-- CONSULTA PARA VALIDAR O RELACIONAMENTO
-- ============================================================

SELECT
    p.id_pedido,
    p.data_pedido,
    p.valor_total,
    c.id_cliente,
    c.nome AS cliente,
    c.email
FROM pedido p
         INNER JOIN cliente c
                    ON p.id_cliente = c.id_cliente;