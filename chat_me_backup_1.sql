-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: chat_me
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `chat_me`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `chat_me` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `chat_me`;

--
-- Table structure for table `channel_mst`
--

DROP TABLE IF EXISTS `channel_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channel_mst` (
  `channel_id` char(36) NOT NULL,
  `channel_name` varchar(64) DEFAULT NULL,
  `channel_creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channel_mst`
--

LOCK TABLES `channel_mst` WRITE;
/*!40000 ALTER TABLE `channel_mst` DISABLE KEYS */;
INSERT INTO `channel_mst` VALUES ('01bd8e79-a8d3-4207-885d-45782e5829dd','geek_bros','2018-05-29 11:12:47'),('3af74b20-1db5-47a1-ba6b-7e7567c6af36','taposoma','2018-05-29 11:17:53'),('6d30ad80-94fe-4047-bef3-4fe3aa0c5845','tapochandu','2018-05-29 11:17:38'),('f0c5280a-c41e-441e-9539-12c3cba0c0f9','hh','2018-05-29 11:59:37');
/*!40000 ALTER TABLE `channel_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `channel_user_mst`
--

DROP TABLE IF EXISTS `channel_user_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channel_user_mst` (
  `channel_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `user_type` varchar(8) DEFAULT NULL COMMENT 'admin/user',
  PRIMARY KEY (`channel_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channel_user_mst`
--

LOCK TABLES `channel_user_mst` WRITE;
/*!40000 ALTER TABLE `channel_user_mst` DISABLE KEYS */;
INSERT INTO `channel_user_mst` VALUES ('01bd8e79-a8d3-4207-885d-45782e5829dd','6e414fd2-e1c3-4d52-90c0-7445d56256b6','user'),('01bd8e79-a8d3-4207-885d-45782e5829dd','8bb64cdd-c8c0-4219-b364-690142e80fc4','admin'),('01bd8e79-a8d3-4207-885d-45782e5829dd','b7053ebe-e5ed-433d-a02b-4a4b4354598a','user'),('3af74b20-1db5-47a1-ba6b-7e7567c6af36','8bb64cdd-c8c0-4219-b364-690142e80fc4','admin'),('3af74b20-1db5-47a1-ba6b-7e7567c6af36','b7053ebe-e5ed-433d-a02b-4a4b4354598a','user'),('6d30ad80-94fe-4047-bef3-4fe3aa0c5845','8bb64cdd-c8c0-4219-b364-690142e80fc4','admin'),('f0c5280a-c41e-441e-9539-12c3cba0c0f9','6e414fd2-e1c3-4d52-90c0-7445d56256b6','user'),('f0c5280a-c41e-441e-9539-12c3cba0c0f9','8bb64cdd-c8c0-4219-b364-690142e80fc4','admin');
/*!40000 ALTER TABLE `channel_user_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_delivery_status_trn`
--

DROP TABLE IF EXISTS `message_delivery_status_trn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_delivery_status_trn` (
  `message_id` char(36) NOT NULL COMMENT 'message id',
  `user_id` char(36) NOT NULL COMMENT 'user id',
  `message_delivery_status` varchar(8) NOT NULL DEFAULT 'SENT' COMMENT 'SENT, UNREAD or READ',
  PRIMARY KEY (`message_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_delivery_status_trn`
--

LOCK TABLES `message_delivery_status_trn` WRITE;
/*!40000 ALTER TABLE `message_delivery_status_trn` DISABLE KEYS */;
INSERT INTO `message_delivery_status_trn` VALUES ('29059c3f-b7b4-4da3-af00-81df5f220af5','6e414fd2-e1c3-4d52-90c0-7445d56256b6','UNREAD'),('29059c3f-b7b4-4da3-af00-81df5f220af5','8bb64cdd-c8c0-4219-b364-690142e80fc4','READ'),('2bd933b5-1b66-4444-907a-b3c9d4d97d4d','6e414fd2-e1c3-4d52-90c0-7445d56256b6','READ'),('2bd933b5-1b66-4444-907a-b3c9d4d97d4d','8bb64cdd-c8c0-4219-b364-690142e80fc4','UNREAD'),('56bf939d-bae2-4132-959e-1268825d1a07','6e414fd2-e1c3-4d52-90c0-7445d56256b6','UNREAD'),('56bf939d-bae2-4132-959e-1268825d1a07','8bb64cdd-c8c0-4219-b364-690142e80fc4','READ'),('5a3808a4-d3a5-4a4f-a1ba-dea55946546d','6e414fd2-e1c3-4d52-90c0-7445d56256b6','UNREAD'),('5a3808a4-d3a5-4a4f-a1ba-dea55946546d','8bb64cdd-c8c0-4219-b364-690142e80fc4','READ'),('5a3808a4-d3a5-4a4f-a1ba-dea55946546d','b7053ebe-e5ed-433d-a02b-4a4b4354598a','SENT'),('5f78602f-8cfd-4a0c-b4d9-68afdfa6c5bb','6e414fd2-e1c3-4d52-90c0-7445d56256b6','READ'),('5f78602f-8cfd-4a0c-b4d9-68afdfa6c5bb','8bb64cdd-c8c0-4219-b364-690142e80fc4','SENT'),('5f78602f-8cfd-4a0c-b4d9-68afdfa6c5bb','b7053ebe-e5ed-433d-a02b-4a4b4354598a','SENT'),('ea7a340e-fdae-409e-abfd-4e31f90cae90','6e414fd2-e1c3-4d52-90c0-7445d56256b6','UNREAD'),('ea7a340e-fdae-409e-abfd-4e31f90cae90','8bb64cdd-c8c0-4219-b364-690142e80fc4','READ');
/*!40000 ALTER TABLE `message_delivery_status_trn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_trn`
--

DROP TABLE IF EXISTS `message_trn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_trn` (
  `message_id` char(36) NOT NULL COMMENT 'unique message id',
  `message` varchar(4096) DEFAULT NULL COMMENT 'message content',
  `message_mode` varchar(8) DEFAULT NULL COMMENT 'DIRECT or  CHANNEL',
  `message_creation_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `message_operation_status` varchar(8) DEFAULT NULL COMMENT 'CREATE, UPDATE, DELETE',
  `source_id` char(36) DEFAULT NULL COMMENT 'Sender (User)',
  `destination_id` char(36) DEFAULT NULL COMMENT 'Receiver (User or Channel)',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_trn`
--

LOCK TABLES `message_trn` WRITE;
/*!40000 ALTER TABLE `message_trn` DISABLE KEYS */;
INSERT INTO `message_trn` VALUES ('29059c3f-b7b4-4da3-af00-81df5f220af5','how are you','DIRECT','2018-05-29 17:55:11','CREATE','8bb64cdd-c8c0-4219-b364-690142e80fc4','6e414fd2-e1c3-4d52-90c0-7445d56256b6'),('2bd933b5-1b66-4444-907a-b3c9d4d97d4d','I\'m fine','DIRECT','2018-05-29 18:11:30','CREATE','6e414fd2-e1c3-4d52-90c0-7445d56256b6','8bb64cdd-c8c0-4219-b364-690142e80fc4'),('56bf939d-bae2-4132-959e-1268825d1a07','hello','DIRECT','2018-05-29 17:55:05','CREATE','8bb64cdd-c8c0-4219-b364-690142e80fc4','6e414fd2-e1c3-4d52-90c0-7445d56256b6'),('5a3808a4-d3a5-4a4f-a1ba-dea55946546d','hey guys','CHANNEL','2018-05-29 18:12:06','CREATE','8bb64cdd-c8c0-4219-b364-690142e80fc4','01bd8e79-a8d3-4207-885d-45782e5829dd'),('5f78602f-8cfd-4a0c-b4d9-68afdfa6c5bb','Hmmok','CHANNEL','2018-05-29 18:13:05','CREATE','6e414fd2-e1c3-4d52-90c0-7445d56256b6','01bd8e79-a8d3-4207-885d-45782e5829dd'),('ea7a340e-fdae-409e-abfd-4e31f90cae90','hi','DIRECT','2018-05-29 16:54:54','CREATE','8bb64cdd-c8c0-4219-b364-690142e80fc4','6e414fd2-e1c3-4d52-90c0-7445d56256b6');
/*!40000 ALTER TABLE `message_trn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_mst`
--

DROP TABLE IF EXISTS `user_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_mst` (
  `user_id` char(36) NOT NULL,
  `user_name` varchar(64) NOT NULL,
  `full_name` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `phone` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_mst`
--

LOCK TABLES `user_mst` WRITE;
/*!40000 ALTER TABLE `user_mst` DISABLE KEYS */;
INSERT INTO `user_mst` VALUES ('6e414fd2-e1c3-4d52-90c0-7445d56256b6','chandu','chandu','chandu','chandu','09230293'),('8bb64cdd-c8c0-4219-b364-690142e80fc4','tapopadma','TAPOPADMA TRIPATHY','tapopadmatripathy1995@gmail.com','tapopadma','84348608'),('b7053ebe-e5ed-433d-a02b-4a4b4354598a','somanaik','soma','soma','somanaik','23424');
/*!40000 ALTER TABLE `user_mst` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-30  2:14:41
