-- Adminer 4.8.1 PostgreSQL 10.19 dump

\connect "Hakaton";

DROP TABLE IF EXISTS "car_inspection";
DROP SEQUENCE IF EXISTS car_inspection_id_seq;
CREATE SEQUENCE car_inspection_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."car_inspection" (
    "id" integer DEFAULT nextval('car_inspection_id_seq') NOT NULL,
    "last_inspection" timestamp NOT NULL,
    CONSTRAINT "car_inspection_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "car_inspection" ("id", "last_inspection") VALUES
(1,	'2020-01-22 10:00:00');

DROP TABLE IF EXISTS "car_maininfo";
DROP SEQUENCE IF EXISTS car_maininfo_id_seq;
CREATE SEQUENCE car_maininfo_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."car_maininfo" (
    "id" integer DEFAULT nextval('car_maininfo_id_seq') NOT NULL,
    "brand" character varying NOT NULL,
    "color" character varying NOT NULL,
    "max_passengers" integer NOT NULL,
    "purchase" timestamp NOT NULL,
    "is_left_hand_drive" boolean NOT NULL,
    CONSTRAINT "car_maininfo_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "car_maininfo" ("id", "brand", "color", "max_passengers", "purchase", "is_left_hand_drive") VALUES
(1,	'Audi',	'White',	5,	'2022-01-22 10:00:00',	't');

DROP TABLE IF EXISTS "person_jobplace";
DROP SEQUENCE IF EXISTS person_jobplace_id_seq;
CREATE SEQUENCE person_jobplace_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."person_jobplace" (
    "id" integer DEFAULT nextval('person_jobplace_id_seq') NOT NULL,
    "job" text NOT NULL,
    CONSTRAINT "person_jobplace_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "person_jobplace" ("id", "job") VALUES
(1,	'Программист'),
(2,	'Пианист');

DROP TABLE IF EXISTS "person_maininfo";
DROP SEQUENCE IF EXISTS person_maininfo_id_seq;
CREATE SEQUENCE person_maininfo_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."person_maininfo" (
    "id" integer DEFAULT nextval('person_maininfo_id_seq') NOT NULL,
    "first_name" character varying NOT NULL,
    "last_name" character varying NOT NULL,
    "age" integer NOT NULL,
    CONSTRAINT "person_maininfo_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "person_maininfo" ("id", "first_name", "last_name", "age") VALUES
(1,	'Alex',	'Vox',	25),
(2,	'Nicolay',	'Veronov',	18);

DROP TABLE IF EXISTS "person_money";
DROP SEQUENCE IF EXISTS person_money_id_seq;
CREATE SEQUENCE person_money_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE "public"."person_money" (
    "id" integer DEFAULT nextval('person_money_id_seq') NOT NULL,
    "money" integer NOT NULL,
    CONSTRAINT "person_money_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "person_money" ("id", "money") VALUES
(1,	100),
(2,	50);

-- 2022-02-27 13:40:22.81376+00