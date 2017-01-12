PGDMP         *                 u            test    9.1.24    9.4.5     j           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            k           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            l           1262    16384    test    DATABASE     v   CREATE DATABASE test WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';
    DROP DATABASE test;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            m           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    5            n           0    0    public    ACL     �   REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
                  postgres    false    5            �            3079    11679    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            o           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    163            �            1259    16391    users    TABLE     �  CREATE TABLE users (
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
    id integer NOT NULL
);
    DROP TABLE public.users;
       public         postgres    false    5            �            1259    16401    users_id_seq    SEQUENCE     n   CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public       postgres    false    161    5            p           0    0    users_id_seq    SEQUENCE OWNED BY     /   ALTER SEQUENCE users_id_seq OWNED BY users.id;
            public       postgres    false    162            �           2604    16403    id    DEFAULT     V   ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public       postgres    false    162    161            f          0    16391    users 
   TABLE DATA                     public       postgres    false    161          q           0    0    users_id_seq    SEQUENCE SET     3   SELECT pg_catalog.setval('users_id_seq', 4, true);
            public       postgres    false    162            �           2606    16412    pk 
   CONSTRAINT     ?   ALTER TABLE ONLY users
    ADD CONSTRAINT pk PRIMARY KEY (id);
 2   ALTER TABLE ONLY public.users DROP CONSTRAINT pk;
       public         postgres    false    161    161            �           2606    16414    unique_mail 
   CONSTRAINT     E   ALTER TABLE ONLY users
    ADD CONSTRAINT unique_mail UNIQUE (mail);
 ;   ALTER TABLE ONLY public.users DROP CONSTRAINT unique_mail;
       public         postgres    false    161    161                        2606    16400    unique_username 
   CONSTRAINT     K   ALTER TABLE ONLY users
    ADD CONSTRAINT unique_username UNIQUE ("user");
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT unique_username;
       public         postgres    false    161    161            f   g  x�ՒOo�0��~�Ƌ� ���o�y0�H�2uW�A�F��-.�ӯE��\8{i�>���/�l�����l�z� \�~W�]�_���1���2%|�a!�e�#�RlN���
��K�ԥM�b����u_xI|�p!�-�7 ��c�'K��{��w� uE"��K�D*̈�ڛ�	�Z�]zӪ��mAwh��� �
l'���Z�Nk
[ǡo���Lhں?x��x�?Ok�LXwx��II3\A����n ���r[S�u�
������J�e�.iN��翙���i������2m�Aޝ��IQ����H��x�I�~����XȔ�&�z!)��z䔓�v�0��)h����     