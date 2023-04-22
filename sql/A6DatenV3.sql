USE a34;

INSERT INTO `T_Accounts` (`p_account_id`, `email`, `passwort`, `nickname`) 
VALUES
  (1,"ut.molestie.in@protonmail.org","risus","Lenore"),
  (2,"dolor@hotmail.couk","turpis","MacKenzie"),
  (3,"et.magnis@protonmail.org","vitae,","Harrison"),
  (4,"magna.malesuada@outlook.net","vel sapien imperdiet","Cadman"),
  (5,"purus.ac.tellus@protonmail.org","cursus in,","Ivan"),
  (6,"bot@egamedarling.de","sodales","Serena"),
  (7,"lacus@icloud.net","pellentesque eget,","Ferdinand"),
  (8,"lectus.sit.amet@outlook.couk","sed tortor.","Britanney"),
  (9,"elementum.lorem@google.org","ligula. Nullam","Camden"),
  (10,"bot2@egamedarling.de","Nunc ac sem","Ariel"),
  (11,"lacus@icloud.net","pellentesque eget,","Ferdinand"),
  (12,"lectus.sit.amet@outlook.couk","sed tortor.","Britaney"),
  (13,"elementum.lorem@google.org","ligula. Nullam","Sasha"),
  (14,"bot3@egamedarling.de","Nunc ac sem","Arielle"),
  (15,"bot4@egamedarling.de","sodales","Serena"),
  (16,"risus@outlook.ca","Nunc pulvinar","Jamal"),
  (17,"tempus.risus@hotmail.org","ligula. Aliquam erat","Shay"),
  (18,"consectetuer.ipsum@aol.net","aliquet","Lesley"),
  (19,"integer@hotmail.net","tortor, dictum","Audra"),
  (20,"magna@hotmail.couk","justo eu arcu.","Hiram");


INSERT INTO `T_Gamer` (`p_gamer_id`,`f_account_id`,`vname`,`nname`)
VALUES
  ("1.6763264",1,"Lenore","Hebert"),
  ("8.8887936",2,"MacKenzie","Mullen"),
  ("46.3471744",3,"Harrison","Cohen"),
  ("55.4392832",4,"Cadman","Greer"),
  ("87.7499264",5,"Ivan","Rice"),
  ("67.4451456",6,"Serena","Serena"),
  ("58.6192512",7,"Ferdinand","Rios"),
  ("25.7920384",8,"Britanney","Buckner"),
  ("15.5652736",9,"Camden","Kirby"),
  ("54.7247629",10,"Ariel","Ariel"),
  ("44.4552543",11,"Ferdinand","Herman"),
  ("79.4030952",12,"Britaney","Hardin"),
  ("54.0637466",13,"Sasha","Cross"),
  ("59.4884357",14,"Arielle","Arielle"),
  ("32.6294776",15,"Serena","Serena"),
  ("56.5263251",16,"Jamal","Webb"),
  ("21.4336307",17,"Shay","Cox"),
  ("23.1445740",18,"Lesley","Pickett"),
  ("62.2993075",19,"Audra","Carter"),
  ("49.6902787",20,"Hiram","Adams");

CREATE TABLE `T_Ueberweisungen` (
  `p_id` int(11) NOT NULL PRIMARY KEY,
  `ziel_iban` char(34) NOT NULL,
  `kontosaldo` decimal(10,2) NOT NULL,
  `ueberweisungsbetrag` decimal(10,2) NOT NULL
);

INSERT INTO `T_Ueberweisungen` (`p_id`,`ziel_iban`,`kontosaldo`,`ueberweisungsbetrag`)
VALUES
  (365972,"MC3967965555127532741491326",5323162,376),
  (365973,"DO38227790374766241716220359",5323538,409),
  (365974,"BE53816577637344",5323947,467),
  (365975,"KW7323857631765163841500529932",5324414,264),
  (365976,"TR544547225486856387957589",5324678,491),
  (365977,"SM7973565734559678646239067",5325169,494),
  (365978,"AD2278315588843237975618",5325663,146),
  (365979,"FO2387666278748048",5325809,439),
  (365980,"TN8011312558035380190663",5326248,64),
  (365981,"IS268628548830207748186286",5326312,294),  
  (365983,"KZ803583861272424185",5326606,-16),
  (365984,"CR5055856627262067743",5326590,1.24),
  (365985,"DE64391457815604919856",5326591.24,-5326591.24),
  (365986,"PK6995221278541882385029",0,313),
  (365987,"GR0959389163678021588834558",313,394);