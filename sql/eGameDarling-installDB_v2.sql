USE a34;

DROP TABLE IF EXISTS T_GamerHobbys;
DROP TABLE IF EXISTS T_Hobbys;
DROP TABLE IF EXISTS T_Status;
DROP TABLE IF EXISTS T_Zocken;
DROP TABLE IF EXISTS T_SpieleGenres;
DROP TABLE IF EXISTS T_Gamer_hat_sys_log;
DROP TABLE IF EXISTS T_Zahlungseingang;
DROP TABLE IF EXISTS T_Nachricht;
DROP TABLE IF EXISTS T_Nachricht_hat_Preis;
DROP TABLE IF EXISTS T_Premiumgamer;
DROP TABLE IF EXISTS T_Sys_Log;
DROP TABLE IF EXISTS T_Konto;
DROP TABLE IF EXISTS T_Spiele;
DROP TABLE IF EXISTS T_Genres;
DROP TABLE IF EXISTS T_Activity;
DROP TABLE IF EXISTS T_Gamer;
DROP TABLE IF EXISTS T_Accounts;
DROP TABLE IF EXISTS T_Adresse;


CREATE TABLE T_Gamer
(
	p_gamer_id    CHAR(10) NOT NULL PRIMARY KEY,
	f_account_id  INTEGER,
	f_addresse_id INTEGER,
	vname         VARCHAR(50),
	nname         VARCHAR(50)
);

CREATE TABLE T_Hobbys
(
	p_hobby_id CHAR(10) NOT NULL,
	hobbyname  VARCHAR(50)
);

CREATE TABLE T_GamerHobbys
(
	pf_gamer_id CHAR(10) NOT NULL,
	pf_hobby_id CHAR(10) NOT NULL
);

CREATE TABLE T_Accounts
(
	p_account_id INTEGER AUTO_INCREMENT PRIMARY KEY,
	email        VARCHAR(50),
	passwort     VARCHAR(128),
	nickname     VARCHAR(50),
	avatar       BLOB
);

CREATE TABLE T_Status
(
	p_status_id  CHAR(10) NOT NULL,
	f_account_id INTEGER,
	zeitpunkt    TIME,
	text         VARCHAR(100)
);

CREATE TABLE T_Spiele
(
	p_spiel_id CHAR(10) NOT NULL,
	spielname  VARCHAR(50)
);

CREATE TABLE T_Zocken
(
	pf_gamer_id CHAR(10) NOT NULL,
	pf_spiel_id CHAR(10) NOT NULL
);


CREATE TABLE T_Genres
(
	p_genre_id CHAR(10) NOT NULL,
	genrename  VARCHAR(30)
);

CREATE TABLE T_SpieleGenres
(
	pf_spiel_id CHAR(10) NOT NULL,
	pf_genre_id CHAR(10) NOT NULL
);
-- NEUE EINTRÄGE


CREATE TABLE T_Adresse
(
	-- Adresse braucht nicht wissen zu welchem Eintrag Sie gehört.
	p_id       INTEGER AUTO_INCREMENT PRIMARY KEY,
	strasse    VARCHAR(100) NOT NULL,
	plz        VARCHAR(5)   NOT NULL,
	-- ort mit den meisten zeichen hat 85 Zeichen im Namen
	ort        VARCHAR(85)  NOT NULL,
	hausnummer VARCHAR(12)  NOT NULL
);

CREATE TABLE T_Konto
(
	p_iban               CHAR(22)    NOT NULL PRIMARY KEY,
	BIC                  VARCHAR(11) NOT NULL,
	inhaber              VARCHAR(50) NOT NULL,
	einzugsermaechtigung BOOL DEFAULT FALSE,
	f_gamer              CHAR(10)    NOT NULL
);

CREATE TABLE T_Premiumgamer
(
	pf_gamer_id CHAR(10) NOT NULL PRIMARY KEY,
	f_konto     CHAR(22) NOT NULL,
	status      BOOL DEFAULT FALSE,
	seit        DATE     NOT NULL
);
CREATE TABLE T_Zahlungseingang
(
	p_zbeitrags_id   INTEGER AUTO_INCREMENT PRIMARY KEY,
	betrag           DECIMAL(10, 2) NOT NULL,
	verwendungszweck VARCHAR(50)    NOT NULL,
	f_gamer_id       CHAR(10)       NOT NULL,
	datetime         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE T_Nachricht
(
	p_nachricht_id INTEGER PRIMARY KEY AUTO_INCREMENT,
	empfangen_am   DATETIME DEFAULT now(),
	gesendet_am    DATETIME DEFAULT now(),
	inhalt         TEXT        NOT NULL,
	f_gamer_id     CHAR(10)    NOT NULL,
	status         VARCHAR(10) NOT NULL, -- senden oder empfangen
	FOREIGN KEY (f_gamer_id) REFERENCES T_Gamer (p_gamer_id)
);

CREATE TABLE T_Nachricht_hat_Preis
(
	p_nachricht_id  INTEGER NOT NULL PRIMARY KEY,
	preis_nachricht DECIMAL(5, 2)
);

CREATE TABLE T_Activity
(
	p_activity_id INTEGER AUTO_INCREMENT PRIMARY KEY,
	bezeichnung   text NOT NULL
);

CREATE TABLE T_Sys_Log
(
	p_log_id    INTEGER AUTO_INCREMENT PRIMARY KEY,
	zeitstempel TIMESTAMP DEFAULT NOW(),
	-- ipv4 is made of 4 byte there fore we can save it in a 32 Binary
	ipv4        Binary(4) NOT NULL,
	f_activity  INTEGER   NOT NULL,
	FOREIGN KEY (f_activity) REFERENCES T_Activity (p_activity_id)
);

Create TABLE T_Gamer_hat_sys_log
(
	pf_gamer_id CHAR(10) NOT NULL,
	pf_log_id   INTEGER  NOT NULL,
	FOREIGN KEY (pf_gamer_id) REFERENCES T_Gamer (p_gamer_id),
	FOREIGN KEY (pf_log_id) REFERENCES T_Sys_Log (p_log_id)
);


-- ENDE NEUE EINTRÄGE


# ALTER TABLE T_Gamer
# 	ADD CONSTRAINT PRIMARY KEY (p_gamer_id);

ALTER TABLE T_Hobbys
	ADD CONSTRAINT PRIMARY KEY (p_hobby_id);

# ALTER TABLE T_Accounts
# 	ADD CONSTRAINT PRIMARY KEY (p_account_id);

ALTER TABLE T_Status
	ADD CONSTRAINT PRIMARY KEY (p_status_id);

ALTER TABLE T_Genres
	ADD CONSTRAINT PRIMARY KEY (p_genre_id);

ALTER TABLE T_Spiele
	ADD CONSTRAINT PRIMARY KEY (p_spiel_id);

ALTER TABLE T_SpieleGenres
	ADD CONSTRAINT PRIMARY KEY (pf_spiel_id, pf_genre_id);

ALTER TABLE T_Zocken
	ADD CONSTRAINT PRIMARY KEY (pf_gamer_id, pf_spiel_id);

ALTER TABLE T_GamerHobbys
	ADD CONSTRAINT PRIMARY KEY (pf_gamer_id, pf_hobby_id);


ALTER TABLE T_Gamer
	ADD Constraint FOREIGN KEY (f_account_id) REFERENCES T_Accounts (p_account_id) ON UPDATE CASCADE ON DELETE CASCADE,
	ADD Constraint FOREIGN KEY (f_addresse_id) REFERENCES T_Adresse (p_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE T_Status
	ADD Constraint FOREIGN KEY (f_account_id) REFERENCES T_Accounts (p_account_id);

ALTER TABLE T_GamerHobbys
	ADD CONSTRAINT FOREIGN KEY (pf_gamer_id) REFERENCES T_Gamer (p_gamer_id) ON UPDATE CASCADE ON DELETE CASCADE,
	ADD CONSTRAINT FOREIGN KEY (pf_hobby_id) REFERENCES T_Hobbys (p_hobby_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE T_Zocken
	ADD CONSTRAINT FOREIGN KEY (pf_gamer_id) REFERENCES T_Gamer (p_gamer_id) ON UPDATE CASCADE ON DELETE CASCADE,
	ADD CONSTRAINT FOREIGN KEY (pf_spiel_id) REFERENCES T_Spiele (p_spiel_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE T_SpieleGenres
	ADD CONSTRAINT FOREIGN KEY (pf_spiel_id) REFERENCES T_Spiele (p_spiel_id),
	ADD CONSTRAINT FOREIGN KEY (pf_genre_id) REFERENCES T_Genres (p_genre_id);


ALTER TABLE T_Premiumgamer
	ADD CONSTRAINT FOREIGN KEY (f_konto) REFERENCES T_Konto (p_iban) ON UPDATE CASCADE,
	ADD CONSTRAINT FOREIGN KEY (pf_gamer_id) REFERENCES T_Gamer (p_gamer_id) ON DELETE CASCADE;
ALTER TABLE T_Konto
	-- Konto löschen wenn der Gamer Account gelöscht wird!!!
	ADD CONSTRAINT FOREIGN KEY (f_gamer) REFERENCES T_Gamer (p_gamer_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- Zahlungs eingänge bleiben erhalten auch wenn der Account gelöscht wird.
ALTER TABLE T_Zahlungseingang
	ADD CONSTRAINT FOREIGN KEY (f_gamer_id) REFERENCES T_Gamer (p_gamer_id) ON UPDATE CASCADE;


ALTER TABLE T_Gamer_hat_sys_log
	ADD PRIMARY KEY (pf_gamer_id, pf_log_id);


