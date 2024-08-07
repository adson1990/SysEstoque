-- Inserir Clientes
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('JOSE EUSTAQUIO', '936.909.604-36', 1600.0, TIMESTAMP WITH TIME ZONE '1985-01-10T20:50:07.12345Z', 1);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('AMAURY JR', '101.792.324-87', 2100.0, TIMESTAMP WITH TIME ZONE '1993-02-20T10:25:04.12345Z', 2);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('ANA CASTELLA', '613.173.528-07', 1900.5, TIMESTAMP WITH TIME ZONE '2000-03-15T15:36:00.12345Z', 3);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('ARLETE SOUZA', '962.641.291-78', 2300, TIMESTAMP WITH TIME ZONE '1993-04-30T20:08:35.12345Z', 2);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('KIKO RODRIGUES', '846.970.801-57', 3800, TIMESTAMP WITH TIME ZONE '1990-05-17T20:50:07.12345Z', 5);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('SHELDON BAVARIO', '925.235.732-70', 2540.0, TIMESTAMP WITH TIME ZONE '1999-06-16T07:37:07.12345Z', 5);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('DIMA SHEYLLA', '974.647.305-00', 1980.5, TIMESTAMP WITH TIME ZONE '1989-07-19T10:20:09.12345Z', 1);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('ASTROGILDO MAGALHAES', '986.220.151-73', 2700.0, TIMESTAMP WITH TIME ZONE '1995-08-24T23:55:17.12345Z', 4);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('ROBERTO MOTA', '207.809.597-44', 2980.0, TIMESTAMP WITH TIME ZONE '1988-09-19T21:18:15.12345Z', 2);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('ANACLECIA ALMEIDA', '130.753.592-54', 3000.0, TIMESTAMP WITH TIME ZONE '1994-10-13T17:34:04.12345Z', 2);
INSERT INTO TB_CLIENT (NAME, CPF, INCOME, BIRTH_DATE, CHILDREN) VALUES ('GLANDULA PTUITARIA', '242.601.400-86', 3564.00, TIMESTAMP WITH TIME ZONE '1988-06-19T10:19:08.12345Z', 3);

-- Inserir endereços
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua A', 'Bairro A', 100, 'Estado A', 'País A', 1);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua B', 'Bairro B', 200, 'Estado B', 'País B', 1);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua C', 'Bairro C', 300, 'Estado C', 'País C', 2);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua D', 'Bairro D', 400, 'Estado D', 'País D', 3);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua E', 'Bairro E', 500, 'Estado E', 'País E', 4);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua F', 'Bairro F', 600, 'Estado F', 'País F', 5);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua G', 'Bairro G', 700, 'Estado G', 'País G', 6);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua H', 'Bairro H', 800, 'Estado H', 'País H', 7);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua I', 'Bairro I', 900, 'Estado I', 'País I', 8);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua J', 'Bairro J', 1000, 'Estado J', 'País J', 9);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua K', 'Bairro K', 1100, 'Estado K', 'País K', 10);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua L', 'Bairro L', 1200, 'Estado L', 'País L', 11);
INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CLIENT_ID) VALUES ('Rua M', 'Bairro M', 1300, 'Estado M', 'País M', 11);

-- Inserir Categoria de clientes
INSERT INTO TB_CATEGORY_CLIENT (DESCRIPTION) VALUES ('PAGA EM DIA');
INSERT INTO TB_CATEGORY_CLIENT (DESCRIPTION) VALUES ('ATRASA PAGAMENTO');
INSERT INTO TB_CATEGORY_CLIENT (DESCRIPTION) VALUES ('MAIS DE 1 ANO');
INSERT INTO TB_CATEGORY_CLIENT (DESCRIPTION) VALUES ('MENOS DE 1 ANO');
INSERT INTO TB_CATEGORY_CLIENT (DESCRIPTION) VALUES ('RUIM');
INSERT INTO TB_CATEGORY_CLIENT (DESCRIPTION) VALUES ('BOM');

-- Vincular catregoria a cliente
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (1,2);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (1,3);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (1,5);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (2,1);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (2,4);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (3,1);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (3,3);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (3,6);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (4,2);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (5,6);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (6,1);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (6,3);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (6,5);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (7,1);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (7,4);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (7,5);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (8,5);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (9,4);
INSERT INTO TB_CLIENT_CATEGORY (CLIENT_ID, CATEGORYCLIENT_ID) VALUES (10,2);

-- Inserir usuarios
INSERT INTO TB_USER (NOME, SOBRENOME, SENHA, EMAIL, IDADE, FOTO, DT_NASCIMENTO, CREATE_AT, UPDATE_AT, ACCOUNT_BLOK)
VALUES ('ADSON', 'FARIAS', '$2a$10$on5XZrC87ppA/cozWviTe.aKMK2bXDPLnozamMBxolXOdmdhbk7uC', 'ADSON@EXEMPLO.COM.BR', 33, '', '1990-11-12', '2024-05-16 17:50:00', NULL, FALSE);
INSERT INTO TB_USER (NOME, SOBRENOME, SENHA, EMAIL, IDADE, FOTO, DT_NASCIMENTO, CREATE_AT, UPDATE_AT, ACCOUNT_BLOK)
VALUES ('LUIZ','JANUARIO','$2a$10$ChDa4UckO/93mN.PpVjv3.UCt7dsNl7nxmqqWm8AaSRgf2RTVcWY6','LUIZ@EXEMPLO.COM.BR',44,'','1980-05-10','2024-05-16 17:50:00', NULL, TRUE);
INSERT INTO TB_USER (NOME, SOBRENOME, SENHA, EMAIL, IDADE, FOTO, DT_NASCIMENTO, CREATE_AT, UPDATE_AT, ACCOUNT_BLOK)
VALUES ('VALERIA','SILVA','$2a$10$xhz7uIMiy.rroa9Gc5uPq.J3EzBEIrSTS8jrMsDPfXLx0G97YDG8O','VALERIA@EXEMPLO.COM.BR',34,'','1988-07-15','2024-05-16 17:50:00',NULL, FALSE);

-- inserir roles
INSERT INTO TB_ROLES (AUTHORITY) VALUES ('GERENTE');
INSERT INTO TB_ROLES (AUTHORITY) VALUES ('SUPERVISOR');
INSERT INTO TB_ROLES (AUTHORITY) VALUES ('PEÃO');
INSERT INTO TB_ROLES (AUTHORITY) VALUES ('BASIC');

-- vincular roles a usuarios
INSERT INTO TB_USER_ROLE (USER_ID, ROLE_ID) VALUES (1,1);
INSERT INTO TB_USER_ROLE (USER_ID, ROLE_ID) VALUES (2,3);
INSERT INTO TB_USER_ROLE (USER_ID, ROLE_ID) VALUES (3,2);

-- Inserir produtos
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('PS4', 4250.00, '','PLAY STATION GEN4', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('NOTBOOK LEN', 3850.80, '','NOTBOOK LENOVO GEN5', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('XIAOMI 13T', 4500.00, '','CELULAR XIAOMI 13T MIUI 14', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('XIAOMI NOTE12', 1500.00, '','CELULAR XIAOMI NOTE12 256GB 8GB', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('SAMSUNG A30', 1870.00, '','CELL SAMSUNG A30 PRETO', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('MOTO G5', 2640.40, '','CELULAR MOTOROLA G5', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('XBOX', 4900.00, '','X-BOX ONE', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('NOTE GAMER ACER', 5150.89, '','NOTEBOOK ACER NITRO 5 GAMER', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('LIVRO JSE SARAMAGO', 45.00, '','ENSAIO SOBRE A CEGUEIRA', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('LIVRO JSE SARAMAGO', 47.00, '','ENSAIO SOBRE A LUCIDEZ', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('LIVRO 2WW', 39.00, '','STALIN, OS NAZISTAS E O OCIDENTE', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('JHONNY WALKER', 89.00, '','RED LABEL J. WALKER 750ML', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('JHONNY WALKER', 129.00, '','BLACK LABEL J. WALKER 1L', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('JHONNY WALKER', 600.00, '','BLUE LABEL J. WALKER 1L', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('JHONNY WALKER', 479.00, '','GREEN LABEL J. WALKER 750ML', '2024-05-17 20:44:00');
INSERT INTO TB_PRODUCT (NAME,PRICE,IMG_URL,DESCRIPTION,DT_INCLUDED) 
VALUES ('WHITE HORSE', 109.00, '','WHISKY WHITE HORSE 1L', '2024-05-17 20:44:00');

-- Inserir categoria de produtos
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('ELETRONICOS');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('LIVROS');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('BEBIDAS');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('CELULAR');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('NOTEBOOK');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('GAMER');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('AUTO AJUDA');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('HISTÓRIA');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('1L');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('750ML');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('FAMILIA JHONNY WALKER');
INSERT INTO TB_CATEGORY_PRODUCT (DESCRIPTION) VALUES ('CONSOLE');

-- Vincular categoria a produto
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (1,12);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (2,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (2,5);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (3,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (3,4);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (3,6);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (4,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (4,4);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (5,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (5,4);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (6,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (6,4);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (7,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (7,6);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (7,12);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (8,1);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (8,5);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (8,6);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (9,2);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (9,7);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (10,2);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (10,7);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (11,2);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (11,8);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (12,3);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (12,10);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (12,11);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (13,3);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (13,9);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (13,11);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (14,3);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (14,9);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (14,11);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (15,3);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (15,10);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (15,11);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (16,3);
INSERT INTO TB_PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_PRODUCT_ID) VALUES (16,9);


