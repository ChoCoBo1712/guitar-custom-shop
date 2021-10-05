CREATE DATABASE  IF NOT EXISTS `epam` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `epam`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: epam
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bodies`
--

DROP TABLE IF EXISTS `bodies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bodies` (
  `body_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `id_wood` int unsigned NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`body_id`),
  KEY `bodies_woods_idx` (`id_wood`),
  CONSTRAINT `bodies_woods` FOREIGN KEY (`id_wood`) REFERENCES `woods` (`wood_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guitars`
--

DROP TABLE IF EXISTS `guitars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guitars` (
  `guitar_id` int unsigned NOT NULL AUTO_INCREMENT,
  `picture_path` varchar(100) NOT NULL,
  `id_body` int unsigned NOT NULL,
  `id_neck` int unsigned NOT NULL,
  `id_pickup` int unsigned NOT NULL,
  `color` varchar(30) NOT NULL,
  `id_user` int unsigned NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  `neck_joint` enum('BOLT_ON','SET_NECK','NECK_THROUGH') NOT NULL,
  `name` varchar(30) NOT NULL,
  `order_status` enum('ORDERED','IN_PROGRESS','COMPLETED') NOT NULL,
  PRIMARY KEY (`guitar_id`),
  KEY `guitars_bodies_idx` (`id_body`),
  KEY `guitars_necks_idx` (`id_neck`),
  KEY `guitars_pickups_idx` (`id_pickup`),
  KEY `guitars_users_idx` (`id_user`),
  CONSTRAINT `guitars_bodies` FOREIGN KEY (`id_body`) REFERENCES `bodies` (`body_id`),
  CONSTRAINT `guitars_necks` FOREIGN KEY (`id_neck`) REFERENCES `necks` (`neck_id`),
  CONSTRAINT `guitars_pickups` FOREIGN KEY (`id_pickup`) REFERENCES `pickups` (`pickup_id`),
  CONSTRAINT `guitars_users` FOREIGN KEY (`id_user`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `necks`
--

DROP TABLE IF EXISTS `necks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `necks` (
  `neck_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `id_neck_wood` int unsigned NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  `id_fretboard_wood` int unsigned NOT NULL,
  PRIMARY KEY (`neck_id`),
  KEY `necks_woods_idx` (`id_neck_wood`),
  KEY `fretboards_woods_idx` (`id_fretboard_wood`),
  CONSTRAINT `fretboards_woods` FOREIGN KEY (`id_fretboard_wood`) REFERENCES `woods` (`wood_id`),
  CONSTRAINT `necks_woods` FOREIGN KEY (`id_neck_wood`) REFERENCES `woods` (`wood_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pickups`
--

DROP TABLE IF EXISTS `pickups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pickups` (
  `pickup_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`pickup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `login` varchar(20) NOT NULL,
  `password_hash` binary(64) NOT NULL,
  `salt` binary(16) NOT NULL,
  `role` enum('CLIENT','MAKER','ADMIN') NOT NULL,
  `status` enum('NOT_CONFIRMED','CONFIRMED','DELETED') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `woods`
--

DROP TABLE IF EXISTS `woods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `woods` (
  `wood_id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`wood_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-05 12:59:02
