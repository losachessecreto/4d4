--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.24
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-01-14 17:08:21 CST

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

INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (2, 0, 'XXXX123456XXX', 'hlabra', 'hlabra', 'hugo@loshs.com', 'Labra', 'Aguilar', 'Hugo', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-06 00:27:16.588337', '2017-01-06 00:27:16.588337', 2, '2017-01-06 00:27:16.588337', '192.168.1.3');
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (3, 0, 'XXXX123456XXX', 'kkurata', 'kkurata', 'kimi@loshs.com', 'Kurata', 'Hernandez', 'Kimi', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-06 00:49:33.296386', '2017-01-06 00:49:33.296386', 3, '2017-01-06 00:49:33.296386', '192.168.1.4');
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (4, 0, 'XXXX123456XXX', 'crenteria', 'crenteria', 'cynthia@loshs.com', 'renteria', 'guevara', 'cynthia mireya', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (5, 0, 'XXXX123456XXX', 'dlabra', 'dlabra', 'diego@loshs.com', 'labra', 'aguilar', 'diego armando', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', NULL, NULL, NULL, NULL, NULL);
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (1, 0, 'XXXX123456XXX', 'hperez', 'hperez', 'hector@loshs.com', 'Herrera', 'Perez', 'Hector', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', '2017-01-06 00:26:23.701196', '2017-01-06 00:26:23.701196', 1, '2017-01-06 00:26:23.701196', '192.168.1.2');
INSERT INTO users (id, status, rfc, "user", password, mail, father_lastname, mother_lastname, given_name, city, entity, "position", register_date, last_modification_date, last_user_modified, last_access_date, last_ip) VALUES (6, 0, 'XXXX123456XXX', 'dillolabra', 'dlabra', 'dillo@loshs.com', 'labra', 'aguilar', 'diego armando', 'Guadalajara, Jalisco', 'Contraloría del Estado', 'Contralor', NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 164
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


-- Completed on 2017-01-14 17:08:21 CST

--
-- PostgreSQL database dump complete
--

