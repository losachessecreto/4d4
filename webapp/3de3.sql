--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.24
-- Dumped by pg_dump version 9.4.5
-- Started on 2017-01-12 01:00:58 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1916 (class 1262 OID 16384)
-- Name: test; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE test WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE test OWNER TO postgres;

\connect test

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 165 (class 3079 OID 11679)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1919 (class 0 OID 0)
-- Dependencies: 165
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 163 (class 1259 OID 16434)
-- Name: statements; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE statements (
    id integer NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    folio_number text NOT NULL,
    "user" integer NOT NULL,
    type smallint NOT NULL,
    date timestamp without time zone,
    entity text
);


ALTER TABLE statements OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 16437)
-- Name: statements_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE statements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE statements_id_seq OWNER TO postgres;

--
-- TOC entry 1920 (class 0 OID 0)
-- Dependencies: 164
-- Name: statements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE statements_id_seq OWNED BY statements.id;


--
-- TOC entry 161 (class 1259 OID 16391)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    "user" text NOT NULL,
    password text NOT NULL,
    mail text NOT NULL,
    father_lastname text NOT NULL,
    mother_lastname text NOT NULL,
    given_name text NOT NULL,
    register_date timestamp without time zone,
    last_modification_date timestamp without time zone,
    last_user_modified integer,
    last_access_date timestamp without time zone,
    last_ip inet,
    id integer NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    "position" text NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 16401)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO postgres;

--
-- TOC entry 1921 (class 0 OID 0)
-- Dependencies: 162
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 1795 (class 2604 OID 16439)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY statements ALTER COLUMN id SET DEFAULT nextval('statements_id_seq'::regclass);


--
-- TOC entry 1793 (class 2604 OID 16403)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1910 (class 0 OID 16434)
-- Dependencies: 163
-- Data for Name: statements; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1922 (class 0 OID 0)
-- Dependencies: 164
-- Name: statements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('statements_id_seq', 1, false);


--
-- TOC entry 1908 (class 0 OID 16391)
-- Dependencies: 161
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users ("user", password, mail, father_lastname, mother_lastname, given_name, register_date, last_modification_date, last_user_modified, last_access_date, last_ip, id, status, "position") VALUES ('hlabra', 'hlabra', 'hugo@loshs.com', 'Labra', 'Aguilar', 'Hugo', '2017-01-06 00:27:16.588337', '2017-01-06 00:27:16.588337', 2, '2017-01-06 00:27:16.588337', '192.168.1.3', 2, 0, 'Supervisor');
INSERT INTO users ("user", password, mail, father_lastname, mother_lastname, given_name, register_date, last_modification_date, last_user_modified, last_access_date, last_ip, id, status, "position") VALUES ('kkurata', 'kkurata', 'kimi@loshs.com', 'Kurata', 'Hernandez', 'Kimi', '2017-01-06 00:49:33.296386', '2017-01-06 00:49:33.296386', 3, '2017-01-06 00:49:33.296386', '192.168.1.4', 3, 0, 'Supervisor');
INSERT INTO users ("user", password, mail, father_lastname, mother_lastname, given_name, register_date, last_modification_date, last_user_modified, last_access_date, last_ip, id, status, "position") VALUES ('crenteria', 'crenteria', 'cynthia@loshs.com', 'renteria', 'guevara', 'cynthia mireya', NULL, NULL, NULL, NULL, NULL, 4, 0, 'Supervisor');
INSERT INTO users ("user", password, mail, father_lastname, mother_lastname, given_name, register_date, last_modification_date, last_user_modified, last_access_date, last_ip, id, status, "position") VALUES ('dlabra', 'dlabra', 'diego@loshs.com', 'labra', 'aguilar', 'diego armando', NULL, NULL, NULL, NULL, NULL, 5, 0, 'Supervisor');
INSERT INTO users ("user", password, mail, father_lastname, mother_lastname, given_name, register_date, last_modification_date, last_user_modified, last_access_date, last_ip, id, status, "position") VALUES ('hperez', 'hperez', 'hector@loshs.com', 'Herrera', 'Perez', 'Hector', '2017-01-06 00:26:23.701196', '2017-01-06 00:26:23.701196', 1, '2017-01-06 00:26:23.701196', '192.168.1.2', 1, 0, 'Supervisor');
INSERT INTO users ("user", password, mail, father_lastname, mother_lastname, given_name, register_date, last_modification_date, last_user_modified, last_access_date, last_ip, id, status, "position") VALUES ('dillolabra', 'dlabra', 'dillo@loshs.com', 'labra', 'aguilar', 'diego armando', NULL, NULL, NULL, NULL, NULL, 8, 0, 'Supervisor');


--
-- TOC entry 1923 (class 0 OID 0)
-- Dependencies: 162
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_id_seq', 9, true);


--
-- TOC entry 1804 (class 2606 OID 16460)
-- Name: statements_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY statements
    ADD CONSTRAINT statements_pk PRIMARY KEY (id);


--
-- TOC entry 1806 (class 2606 OID 16462)
-- Name: statements_unique_folio; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY statements
    ADD CONSTRAINT statements_unique_folio UNIQUE (folio_number);


--
-- TOC entry 1798 (class 2606 OID 16454)
-- Name: users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 1800 (class 2606 OID 16456)
-- Name: users_unique_mail; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_unique_mail UNIQUE (mail);


--
-- TOC entry 1802 (class 2606 OID 16458)
-- Name: users_unique_username; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_unique_username UNIQUE ("user");


--
-- TOC entry 1918 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-01-12 01:00:58 CST

--
-- PostgreSQL database dump complete
--

