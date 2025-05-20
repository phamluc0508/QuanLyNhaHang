CREATE TABLE IF NOT EXISTS `tbluser` (
   `id` int NOT NULL AUTO_INCREMENT,
   `username` varchar(45) NOT NULL,
   `password` varchar(45) NOT NULL,
   `name` varchar(45) NOT NULL,
   `position` varchar(45) NOT NULL,
   `note` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `id_UNIQUE` (`id`),
   UNIQUE KEY `username_UNIQUE` (`username`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tblclient` (
   `id` int NOT NULL AUTO_INCREMENT,
   `name` varchar(45) NOT NULL,
   `address` varchar(45) NOT NULL,
   `tel` varchar(45) NOT NULL,
   `email` varchar(45) NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `id_UNIQUE` (`id`),
   UNIQUE KEY `email_UNIQUE` (`email`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tbltable` (
   `id` int NOT NULL AUTO_INCREMENT,
   `name` varchar(45) NOT NULL,
   `maxnumber` int NOT NULL,
   `des` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `idtbltable_UNIQUE` (`id`),
   UNIQUE KEY `name_UNIQUE` (`name`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tblbooking` (
   `id` int NOT NULL AUTO_INCREMENT,
   `creatorid` int NOT NULL,
   `clientid` int NOT NULL,
   `bookingdate` datetime NOT NULL,
   `totalamount` float DEFAULT NULL,
   `note` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `id_UNIQUE` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tblbookedtable` (
   `id` int NOT NULL AUTO_INCREMENT,
   `bookingid` int NOT NULL,
   `tableid` int NOT NULL,
   `checkin` datetime NOT NULL,
   `checkout` datetime NOT NULL,
   `price` float DEFAULT NULL,
   `ischeckedin` tinyint DEFAULT NULL,
   `note` varchar(45) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `idtblbookedtable_UNIQUE` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

