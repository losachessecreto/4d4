--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.24
-- Dumped by pg_dump version 9.5.5

-- Started on 2017-01-14 17:06:27 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 11679)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1915 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 163 (class 1259 OID 16387)
-- Name: statements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE statements (
    id integer NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    folio_number text NOT NULL,
    "user" integer NOT NULL,
    type smallint NOT NULL,
    date timestamp without time zone
);


ALTER TABLE statements OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 16385)
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
-- TOC entry 1916 (class 0 OID 0)
-- Dependencies: 162
-- Name: statements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE statements_id_seq OWNED BY statements.id;


--
-- TOC entry 165 (class 1259 OID 16397)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    id integer NOT NULL,
    status smallint DEFAULT 0 NOT NULL,
    rfc text NOT NULL,
    "user" text NOT NULL,
    password text NOT NULL,
    mail text NOT NULL,
    father_lastname text NOT NULL,
    mother_lastname text NOT NULL,
    given_name text NOT NULL,
    city text,
    entity text NOT NULL,
    "position" text NOT NULL,
    register_date timestamp without time zone,
    last_modification_date timestamp without time zone,
    last_user_modified integer,
    last_access_date timestamp without time zone,
    last_ip inet
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 16395)
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
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 164
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 1793 (class 2604 OID 16390)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY statements ALTER COLUMN id SET DEFAULT nextval('statements_id_seq'::regclass);


--
-- TOC entry 1795 (class 2604 OID 16400)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1798 (class 2606 OID 16406)
-- Name: statements_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY statements
    ADD CONSTRAINT statements_pk PRIMARY KEY (id);


--
-- TOC entry 1800 (class 2606 OID 16408)
-- Name: statements_unique_folio; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY statements
    ADD CONSTRAINT statements_unique_folio UNIQUE (folio_number);


--
-- TOC entry 1802 (class 2606 OID 16410)
-- Name: users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- TOC entry 1804 (class 2606 OID 16412)
-- Name: users_unique_mail; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_unique_mail UNIQUE (mail);


--
-- TOC entry 1806 (class 2606 OID 16414)
-- Name: users_unique_username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_unique_username UNIQUE ("user");


--
-- TOC entry 1914 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-01-14 17:06:27 CST

--
-- PostgreSQL database dump complete
--

