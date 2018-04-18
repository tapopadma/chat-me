-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: chat_me
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

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
-- Table structure for table `account_mst`
--

DROP TABLE IF EXISTS `account_mst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_mst` (
  `account_mst_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`account_mst_id`),
  UNIQUE KEY `uidx_account_mst_01` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_mst`
--

LOCK TABLES `account_mst` WRITE;
/*!40000 ALTER TABLE `account_mst` DISABLE KEYS */;
INSERT INTO `account_mst` VALUES (1,'TAPOPADMA TRIPATHY','tapopadmatripathy1995@gmail.com','84348608','tapopadma','passw0rd'),(2,'ff','fff','444','fff','ewew'),(3,'ggg','ggggg','555','ggg','ggggggg'),(4,'kkk','kkkk','646456','kkk','45456'),(5,'obama','obama','9898','obama','obama'),(6,'trump','trump','32932938','trump','trump'),(7,'xyz','xyz','98349348','xyz','iamaxyz'),(8,'huhu','huhu','9898','huhu','ldskfjlskdjf'),(9,'kk','kkk','4334','k','kkk');
/*!40000 ALTER TABLE `account_mst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_trn`
--

DROP TABLE IF EXISTS `message_trn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_trn` (
  `message_trn_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` varchar(1024) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `message_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`message_trn_id`),
  UNIQUE KEY `uidx_message_trn_01` (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_trn`
--

LOCK TABLES `message_trn` WRITE;
/*!40000 ALTER TABLE `message_trn` DISABLE KEYS */;
INSERT INTO `message_trn` VALUES (8,'Your visa is approved!!!','obama','2018-04-16 11:10:32','31872f6a-a9d9-451a-a7b5-467e0058fbcb'),(9,'lc','k','2018-04-18 01:32:33','5b586b86-717e-40aa-b29f-3e33a922930b'),(10,'lll','k','2018-04-18 01:36:04','517f9413-aa42-4e54-8d36-faf292fade48'),(11,'kjkj','k','2018-04-18 01:38:15','de7b51e3-6779-4461-8b29-908dacfabcd2'),(12,'kkkk','k','2018-04-18 01:39:18','a892e850-9d1a-46a6-8285-e4c57718fd91'),(13,'hey','k','2018-04-18 01:40:20','eaf54794-9e80-431e-b568-707c32e5646e'),(14,'hey','k','2018-04-18 01:41:50','8a098aab-cbe4-4a2e-8ba3-e5d024bf7004'),(15,'bye','k','2018-04-18 01:42:16','5d0b904e-3b97-44ae-bd7d-e51fbde131c0'),(16,'hi','k','2018-04-18 01:46:01','b5c16df7-42c6-41d1-bc2b-146095611309'),(17,'what\'s up?','k','2018-04-18 01:46:13','c2ff0867-3931-4ae1-b2cd-bc07bcc7403a'),(18,'How are you\nI was a bit busy\nso didn\'t make a call.','k','2018-04-18 01:46:50','12e9fa19-8879-4553-9112-79a84dfcae35'),(19,'oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo','k','2018-04-18 01:47:41','cc148171-5896-403a-8ee6-dda37b5cb8f0'),(20,'sorry for spamming','k','2018-04-18 01:48:18','ed807772-29ce-4a38-a791-a2a89e7d423f');
/*!40000 ALTER TABLE `message_trn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_users_trn`
--

DROP TABLE IF EXISTS `message_users_trn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_users_trn` (
  `message_users_trn_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(256) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`message_users_trn_id`),
  UNIQUE KEY `uidx_message_users_trn_01` (`message_id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_users_trn`
--

LOCK TABLES `message_users_trn` WRITE;
/*!40000 ALTER TABLE `message_users_trn` DISABLE KEYS */;
INSERT INTO `message_users_trn` VALUES (28,'12e9fa19-8879-4553-9112-79a84dfcae35','k'),(29,'12e9fa19-8879-4553-9112-79a84dfcae35','tapopadma'),(8,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','obama'),(9,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','tapopadma'),(12,'517f9413-aa42-4e54-8d36-faf292fade48','k'),(13,'517f9413-aa42-4e54-8d36-faf292fade48','tapopadma'),(10,'5b586b86-717e-40aa-b29f-3e33a922930b','k'),(11,'5b586b86-717e-40aa-b29f-3e33a922930b','tapopadma'),(22,'5d0b904e-3b97-44ae-bd7d-e51fbde131c0','k'),(23,'5d0b904e-3b97-44ae-bd7d-e51fbde131c0','tapopadma'),(20,'8a098aab-cbe4-4a2e-8ba3-e5d024bf7004','k'),(21,'8a098aab-cbe4-4a2e-8ba3-e5d024bf7004','tapopadma'),(16,'a892e850-9d1a-46a6-8285-e4c57718fd91','k'),(17,'a892e850-9d1a-46a6-8285-e4c57718fd91','tapopadma'),(24,'b5c16df7-42c6-41d1-bc2b-146095611309','k'),(25,'b5c16df7-42c6-41d1-bc2b-146095611309','tapopadma'),(26,'c2ff0867-3931-4ae1-b2cd-bc07bcc7403a','k'),(27,'c2ff0867-3931-4ae1-b2cd-bc07bcc7403a','tapopadma'),(30,'cc148171-5896-403a-8ee6-dda37b5cb8f0','k'),(31,'cc148171-5896-403a-8ee6-dda37b5cb8f0','tapopadma'),(14,'de7b51e3-6779-4461-8b29-908dacfabcd2','k'),(15,'de7b51e3-6779-4461-8b29-908dacfabcd2','tapopadma'),(18,'eaf54794-9e80-431e-b568-707c32e5646e','k'),(19,'eaf54794-9e80-431e-b568-707c32e5646e','tapopadma'),(32,'ed807772-29ce-4a38-a791-a2a89e7d423f','k'),(33,'ed807772-29ce-4a38-a791-a2a89e7d423f','tapopadma');
/*!40000 ALTER TABLE `message_users_trn` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-18  9:50:45
