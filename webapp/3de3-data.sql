--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.24
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-01-15 20:14:35 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 1909 (class 0 OID 16387)
-- Dependencies: 163
-- Data for Name: statements; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO statements (id, status, folio_number, "user", type, date) VALUES (1, 1, '1', 1, 1, '2017-01-14 17:02:26.144558');
INSERT INTO statements (id, status, folio_number, "user", type, date) VALUES (3, 1, '2', 1, 2, '2017-01-14 17:02:40.803186');
INSERT INTO statements (id, status, folio_number, "user", type, date) VALUES (4, 1, '3', 2, 1, '2017-01-14 17:02:49.267957');
INSERT INTO statements (id, status, folio_number, "user", type, date) VALUES (5, 1, '4', 3, 3, '2017-01-14 17:03:00.093101');


--
-- TOC entry 1916 (class 0 OID 0)
-- Dependencies: 162
-- Name: statements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('statements_id_seq', 5, true);


--
-- TOC entry 1911 (class 0 OID 16397)
-- Dependencies: 165
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (5, 0, 'XXXX123456XXX', 'fgutierrez', 'TIVz1M5Bb6zbB9oXdBk2MLwaJGc2DAJ0dvI4J23lvhI=$aDDwaUkhiUE7NnODFk2iDBDkAJMe9zQ7HxeCvMzwO7w=', 'francisco@loshs.com', 'Gutiérrez', 'Franco', 'Francisco', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-15 20:09:22.100913', NULL, NULL, NULL, NULL);
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (6, 0, 'XXXX123456XXX', 'dillo', '66g95dqd90E0PHwc1fmg9CK/NYpzLdXFJbcO8XyEwto=$MrG7g67j2r0vTaTEtJTrnNJur1HLQW0s3Wcj60nkq1w=', 'daniel@loshs.com', 'Illo', 'Buenrostro', 'Daniel', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-15 20:10:45.477208', NULL, NULL, NULL, NULL);
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (4, 0, 'XXXX123456XXX', 'mhernandez', 'Jz5/fKYPCmO05ePvcBxrQNjpBiT5vzxl7f3P6a8xwVA=$OetqiWBLkQpiMQIoAEaCCHEZzQ6g54N2PbRfZb6JiW0=', 'mariana@loshs.com', 'Hernández', 'Sánchez', 'Mariana', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-15 20:08:09.202464', NULL, NULL, NULL, NULL);
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (2, 0, 'XXXX123456XXX', 'hlabra', '8R7mSGfpHA1JvhTrWKDW5pZpdzX4u7QXuiiwzUwbgYw=$4nMgoI8H3RsNU1PRzHaGF0MWRFUZ8dB5bgajLagm31w=', 'hugo@loshs.com', 'Labra', 'Aguilar', 'Hugo', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-06 00:27:16.588337', '2017-01-06 00:27:16.588337', 2, '2017-01-06 00:27:16.588337', '192.168.1.3');
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (3, 0, 'XXXX123456XXX', 'kkurata', 'Ercs/yU7RclrihMe/vSNeeT0Y8+vV5YvII6fgmZZ9qQ=$MybUdTUCAN9+yheKsb9BWrMq18xh6ExTCzbE8fobFAQ=', 'kimi@loshs.com', 'Kurata', 'Hernandez', 'Kimi', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-06 00:49:33.296386', '2017-01-06 00:49:33.296386', 3, '2017-01-06 00:49:33.296386', '192.168.1.4');
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (1, 0, 'XXXX123456XXX', 'hperez', 'hJ4FJAjqQWf87FAUCvd50QMFFzuX5Fd1sgUaBfh08dE=$zvwX+/jjBl48bqQDMyHtjKf/fiyorS7PlcLO0ng2LlE=', 'hector@loshs.com', 'Herrera', 'Perez', 'Hector', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-06 00:26:23.701196', '2017-01-06 00:26:23.701196', 1, '2017-01-06 00:26:23.701196', '192.168.1.2');


--
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 164
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


-- Completed on 2017-01-15 20:14:35 CST

--
-- PostgreSQL database dump complete
--

