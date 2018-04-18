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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_mst`
--

LOCK TABLES `account_mst` WRITE;
/*!40000 ALTER TABLE `account_mst` DISABLE KEYS */;
INSERT INTO `account_mst` VALUES (1,'TAPOPADMA TRIPATHY','tapopadmatripathy1995@gmail.com','84348608','tapopadma','passw0rd'),(2,'ff','fff','444','fff','ewew'),(3,'ggg','ggggg','555','ggg','ggggggg'),(4,'kkk','kkkk','646456','kkk','45456'),(5,'obama','obama','9898','obama','obama'),(6,'trump','trump','32932938','trump','trump'),(7,'xyz','xyz','98349348','xyz','iamaxyz'),(8,'huhu','huhu','9898','huhu','ldskfjlskdjf'),(9,'kk','kkk','4334','k','kkk'),(10,'B Soma Naik','testing123@email.com','123123123','somanaik','somanaik');
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
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_trn`
--

LOCK TABLES `message_trn` WRITE;
/*!40000 ALTER TABLE `message_trn` DISABLE KEYS */;
INSERT INTO `message_trn` VALUES (8,'Your visa is approved!!!','obama','2018-04-16 11:10:32','31872f6a-a9d9-451a-a7b5-467e0058fbcb'),(9,'lc','k','2018-04-18 01:32:33','5b586b86-717e-40aa-b29f-3e33a922930b'),(10,'lll','k','2018-04-18 01:36:04','517f9413-aa42-4e54-8d36-faf292fade48'),(11,'kjkj','k','2018-04-18 01:38:15','de7b51e3-6779-4461-8b29-908dacfabcd2'),(12,'kkkk','k','2018-04-18 01:39:18','a892e850-9d1a-46a6-8285-e4c57718fd91'),(13,'hey','k','2018-04-18 01:40:20','eaf54794-9e80-431e-b568-707c32e5646e'),(14,'hey','k','2018-04-18 01:41:50','8a098aab-cbe4-4a2e-8ba3-e5d024bf7004'),(15,'bye','k','2018-04-18 01:42:16','5d0b904e-3b97-44ae-bd7d-e51fbde131c0'),(16,'hi','k','2018-04-18 01:46:01','b5c16df7-42c6-41d1-bc2b-146095611309'),(17,'what\'s up?','k','2018-04-18 01:46:13','c2ff0867-3931-4ae1-b2cd-bc07bcc7403a'),(18,'How are you\nI was a bit busy\nso didn\'t make a call.','k','2018-04-18 01:46:50','12e9fa19-8879-4553-9112-79a84dfcae35'),(19,'oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo','k','2018-04-18 01:47:41','cc148171-5896-403a-8ee6-dda37b5cb8f0'),(20,'sorry for spamming','k','2018-04-18 01:48:18','ed807772-29ce-4a38-a791-a2a89e7d423f'),(21,'I am a good.','obama','2018-04-18 04:38:10','8d36d1c6-f4ba-47d2-889b-bf1473a6807c'),(22,'how are you','obama','2018-04-18 04:38:16','19d9cfff-ce10-4ff1-befc-0155f9f9d915'),(23,'hey obama ! how you doing. sorry for the late reply.','tapopadma','2018-04-18 04:40:50','fc17cca5-2dfa-410d-91ae-5689ba670d7b'),(24,'hey i changed the entity.','tapopadma','2018-04-18 04:53:37','e988d803-c12c-4efb-9777-928f0927817f'),(25,'sdd','tapopadma','2018-04-18 07:10:19','b2069334-2af2-4efa-85ce-f69914002deb'),(26,'cc','tapopadma','2018-04-18 07:13:16','adee685f-d58d-49a4-af7f-0e8f21920b48'),(27,'Hi','tapopadma','2018-04-18 08:14:45','a98b7b13-c0c7-4c13-be0a-6b780e98fc0a'),(28,'It\'s fine :)','tapopadma','2018-04-18 08:15:11','8fd1ad7f-6acf-4ad6-98d2-d8b3a9ceebc0'),(29,'So r u free?','tapopadma','2018-04-18 08:17:18','fbd5621f-b165-4606-8338-2700340f1e42'),(30,'Yah sort of xD','k','2018-04-18 08:17:46','9fef8b0b-7229-42f3-b926-3b36dd963258'),(31,'hmm, i\'m kinda busy','k','2018-04-18 08:21:39','8306d700-f798-4b70-8ac0-4dce379714c1'),(32,'how about you','k','2018-04-18 08:21:56','b4b5e8f8-0727-4bd1-a0f5-8f8b719b9cb0'),(33,'fuck man','k','2018-04-18 08:23:46','ee98b8d3-e5a0-40bf-93c7-50efc869fcd0'),(34,'what >>','k','2018-04-18 08:25:01','1bbdc6f7-b89e-4eba-a956-8cb331fa1ff8'),(35,'what the hell','k','2018-04-18 08:25:28','75fec15e-6179-4c5f-82c6-a821834594c1'),(36,'ok','k','2018-04-18 08:57:38','1aa9d4e7-ef47-487e-9b94-00dc3aa093f5'),(37,'hi','k','2018-04-18 08:58:35','23c84267-9b63-4fa7-9828-e996e0e89e7e'),(38,'hey','k','2018-04-18 08:58:38','de27d25c-5659-4066-b36a-a599461beb32'),(39,'fyyy','k','2018-04-18 09:24:59','f2186bad-8c38-463a-aaf5-f0a6f84b974f'),(40,'dont angry me','tapopadma','2018-04-18 09:26:13','b7a2f337-aa8a-41e3-a9e9-8a2b6e067a3f'),(41,'sorry','tapopadma','2018-04-18 09:26:32','2959e814-ec89-4bb6-ab28-6b2a041ac9ca'),(42,'what','k','2018-04-18 09:26:36','9124a750-31cb-4fc0-addd-efa8abd8ca47'),(43,'hey dude!!','tapopadma','2018-04-18 09:32:16','2be3e5cd-4c8a-4f95-82c6-db31930a0da8'),(44,'hey hey dude!!!!!!!!!!!!!!!!','somanaik','2018-04-18 09:32:59','85afee20-5c3e-4343-89ba-8fcaf8af6b2d'),(45,'hmm','tapopadma','2018-04-18 09:33:06','66387876-b887-4ca2-a54d-c7f3b9c78f8e'),(46,'journal testing ka kya hua','tapopadma','2018-04-18 09:33:23','cfe3bd12-7dea-4dc0-8c34-0870c16bd96d'),(47,'lot of things are happening. goom raha hai','somanaik','2018-04-18 09:33:52','97637332-c3b9-4fd8-9163-02da4d96412c'),(48,'thanks , now you can logout . my testing done for today','tapopadma','2018-04-18 09:34:22','66613a51-6d9e-472b-8be5-0da898a4b0cb'),(49,'kaunsa db use kiya iskeliye','somanaik','2018-04-18 09:34:28','4279ea2e-c923-47e4-9359-610a9b623384'),(50,':)','tapopadma','2018-04-18 09:34:33','9642b5a3-76bd-4ac1-be1c-4f816cf2a0a6'),(51,'mysql','tapopadma','2018-04-18 09:34:42','28b07114-7699-4c88-9a0f-4bff4a183ca4'),(52,'u can\'t the user logout','somanaik','2018-04-18 09:34:47','d84c5219-cf46-452e-b9b2-62f0f16c49d8'),(53,':)','somanaik','2018-04-18 09:35:14','90be9a6e-27e6-45e5-94dc-ec1a09eeb510'),(54,'what do u mean','tapopadma','2018-04-18 09:35:18','6a4aa61f-9b51-428e-ad8e-5d330d75f3cf'),(55,'just kidding','somanaik','2018-04-18 09:35:43','8e679f87-1a36-48ba-88c4-f091262dbe43'),(56,'logging out','somanaik','2018-04-18 09:35:56','baaceaff-0ec7-437e-b09c-b1ca67a5c390'),(57,'thanks','tapopadma','2018-04-18 09:36:37','3a09a436-f19a-41a3-96c2-d7c419abe8c9'),(58,'logged in','somanaik','2018-04-18 09:43:10','326d1c47-0b4d-47c9-b49e-1e2bfc1103ed'),(59,'messaging myself','somanaik','2018-04-18 09:43:52','34d817ee-1120-49ce-a494-3d8595ccd8c1'),(60,'messaging myself','somanaik','2018-04-18 09:43:53','5ceea8a2-b2a9-4857-baeb-78557b5c31e1'),(61,'messaging myself','somanaik','2018-04-18 09:43:53','7264c1ae-7c8f-4384-a8fe-08d58e732e3a'),(62,'messaging myself','somanaik','2018-04-18 09:43:53','e7b271cc-6340-4f24-9f9c-4dec5261ba33'),(63,'fixed the issue','tapopadma','2018-04-18 09:46:05','374c42f1-0a8b-4061-91dd-ef67a8438f89'),(64,'do you see it ?','tapopadma','2018-04-18 09:46:17','0b6f4c13-2dd1-4aad-b66a-c4ca120b2312'),(65,'yup','somanaik','2018-04-18 09:46:50','b0b9713a-c124-4251-9ad4-2e3e58fe3b71'),(66,'it is fixed','somanaik','2018-04-18 09:46:56','77b191c3-5372-4815-86e4-bf0dea15d480'),(67,'msg myself','somanaik','2018-04-18 09:47:18','6108332e-b076-42b7-9702-56f840a9e110'),(68,'msg myself','somanaik','2018-04-18 09:47:19','f065dfc6-918d-4d24-94d5-1983202c4518'),(69,'msg myself','somanaik','2018-04-18 09:47:19','31fac919-221d-4272-a182-62a43cda36e0'),(70,'msg myself','somanaik','2018-04-18 09:47:56','7365f7a2-5055-4233-9927-f93bb5373fd5'),(71,'msg myself','somanaik','2018-04-18 09:47:56','3a731ccc-7d86-47cb-8365-775547f054da'),(72,'ok','tapopadma','2018-04-18 09:48:22','5cc4b342-c89b-48ff-b6b1-bc50aa50dec1'),(73,'hello','k','2018-04-18 09:48:57','a26a3170-918f-4f42-a3ed-c603e2252b4a'),(74,'hi','tapopadma','2018-04-18 09:49:08','bc168b64-914b-4526-a915-f69a7e5c0f64'),(75,'i can select my self from the user list\nbut when i try to message it does not send the msg.\nin future, either remove the current user from the list or they should be able to msg themselves.','somanaik','2018-04-18 09:49:31','d939eea2-db65-4378-a9ba-b9de64a58de8'),(76,'hye','tapopadma','2018-04-18 09:56:00','40a4ca0f-b80f-4855-b388-9e0ede8e9b47'),(77,'testing self msg','somanaik','2018-04-18 09:56:19','4333965e-597a-46b6-8893-ca38aabdd2f6'),(78,'testing self msg','somanaik','2018-04-18 09:56:57','a56e6952-3376-49a0-9375-b63d1ede914e'),(79,'hi','k','2018-04-18 09:57:10','7a5ef66e-e3ec-420a-9fa3-d30a768ab565'),(80,'hmm','tapopadma','2018-04-18 09:57:15','34716db7-cd8c-46e9-8de7-065a62aa75fa');
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
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_users_trn`
--

LOCK TABLES `message_users_trn` WRITE;
/*!40000 ALTER TABLE `message_users_trn` DISABLE KEYS */;
INSERT INTO `message_users_trn` VALUES (121,'0b6f4c13-2dd1-4aad-b66a-c4ca120b2312','somanaik'),(120,'0b6f4c13-2dd1-4aad-b66a-c4ca120b2312','tapopadma'),(28,'12e9fa19-8879-4553-9112-79a84dfcae35','k'),(29,'12e9fa19-8879-4553-9112-79a84dfcae35','tapopadma'),(36,'19d9cfff-ce10-4ff1-befc-0155f9f9d915','obama'),(37,'19d9cfff-ce10-4ff1-befc-0155f9f9d915','tapopadma'),(64,'1aa9d4e7-ef47-487e-9b94-00dc3aa093f5','k'),(65,'1aa9d4e7-ef47-487e-9b94-00dc3aa093f5','tapopadma'),(60,'1bbdc6f7-b89e-4eba-a956-8cb331fa1ff8','k'),(61,'1bbdc6f7-b89e-4eba-a956-8cb331fa1ff8','tapopadma'),(66,'23c84267-9b63-4fa7-9828-e996e0e89e7e','k'),(67,'23c84267-9b63-4fa7-9828-e996e0e89e7e','tapopadma'),(95,'28b07114-7699-4c88-9a0f-4bff4a183ca4','somanaik'),(94,'28b07114-7699-4c88-9a0f-4bff4a183ca4','tapopadma'),(75,'2959e814-ec89-4bb6-ab28-6b2a041ac9ca','k'),(74,'2959e814-ec89-4bb6-ab28-6b2a041ac9ca','tapopadma'),(79,'2be3e5cd-4c8a-4f95-82c6-db31930a0da8','somanaik'),(78,'2be3e5cd-4c8a-4f95-82c6-db31930a0da8','tapopadma'),(8,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','obama'),(9,'31872f6a-a9d9-451a-a7b5-467e0058fbcb','tapopadma'),(130,'31fac919-221d-4272-a182-62a43cda36e0','somanaik'),(108,'326d1c47-0b4d-47c9-b49e-1e2bfc1103ed','somanaik'),(109,'326d1c47-0b4d-47c9-b49e-1e2bfc1103ed','tapopadma'),(153,'34716db7-cd8c-46e9-8de7-065a62aa75fa','k'),(152,'34716db7-cd8c-46e9-8de7-065a62aa75fa','tapopadma'),(110,'34d817ee-1120-49ce-a494-3d8595ccd8c1','somanaik'),(119,'374c42f1-0a8b-4061-91dd-ef67a8438f89','somanaik'),(118,'374c42f1-0a8b-4061-91dd-ef67a8438f89','tapopadma'),(107,'3a09a436-f19a-41a3-96c2-d7c419abe8c9','somanaik'),(106,'3a09a436-f19a-41a3-96c2-d7c419abe8c9','tapopadma'),(134,'3a731ccc-7d86-47cb-8365-775547f054da','somanaik'),(145,'40a4ca0f-b80f-4855-b388-9e0ede8e9b47','k'),(144,'40a4ca0f-b80f-4855-b388-9e0ede8e9b47','tapopadma'),(90,'4279ea2e-c923-47e4-9359-610a9b623384','somanaik'),(91,'4279ea2e-c923-47e4-9359-610a9b623384','tapopadma'),(146,'4333965e-597a-46b6-8893-ca38aabdd2f6','somanaik'),(12,'517f9413-aa42-4e54-8d36-faf292fade48','k'),(13,'517f9413-aa42-4e54-8d36-faf292fade48','tapopadma'),(10,'5b586b86-717e-40aa-b29f-3e33a922930b','k'),(11,'5b586b86-717e-40aa-b29f-3e33a922930b','tapopadma'),(137,'5cc4b342-c89b-48ff-b6b1-bc50aa50dec1','somanaik'),(136,'5cc4b342-c89b-48ff-b6b1-bc50aa50dec1','tapopadma'),(112,'5ceea8a2-b2a9-4857-baeb-78557b5c31e1','somanaik'),(22,'5d0b904e-3b97-44ae-bd7d-e51fbde131c0','k'),(23,'5d0b904e-3b97-44ae-bd7d-e51fbde131c0','tapopadma'),(126,'6108332e-b076-42b7-9702-56f840a9e110','somanaik'),(83,'66387876-b887-4ca2-a54d-c7f3b9c78f8e','somanaik'),(82,'66387876-b887-4ca2-a54d-c7f3b9c78f8e','tapopadma'),(89,'66613a51-6d9e-472b-8be5-0da898a4b0cb','somanaik'),(88,'66613a51-6d9e-472b-8be5-0da898a4b0cb','tapopadma'),(101,'6a4aa61f-9b51-428e-ad8e-5d330d75f3cf','somanaik'),(100,'6a4aa61f-9b51-428e-ad8e-5d330d75f3cf','tapopadma'),(114,'7264c1ae-7c8f-4384-a8fe-08d58e732e3a','somanaik'),(132,'7365f7a2-5055-4233-9927-f93bb5373fd5','somanaik'),(62,'75fec15e-6179-4c5f-82c6-a821834594c1','k'),(63,'75fec15e-6179-4c5f-82c6-a821834594c1','tapopadma'),(124,'77b191c3-5372-4815-86e4-bf0dea15d480','somanaik'),(125,'77b191c3-5372-4815-86e4-bf0dea15d480','tapopadma'),(150,'7a5ef66e-e3ec-420a-9fa3-d30a768ab565','k'),(151,'7a5ef66e-e3ec-420a-9fa3-d30a768ab565','tapopadma'),(54,'8306d700-f798-4b70-8ac0-4dce379714c1','k'),(55,'8306d700-f798-4b70-8ac0-4dce379714c1','tapopadma'),(80,'85afee20-5c3e-4343-89ba-8fcaf8af6b2d','somanaik'),(81,'85afee20-5c3e-4343-89ba-8fcaf8af6b2d','tapopadma'),(20,'8a098aab-cbe4-4a2e-8ba3-e5d024bf7004','k'),(21,'8a098aab-cbe4-4a2e-8ba3-e5d024bf7004','tapopadma'),(34,'8d36d1c6-f4ba-47d2-889b-bf1473a6807c','obama'),(35,'8d36d1c6-f4ba-47d2-889b-bf1473a6807c','tapopadma'),(102,'8e679f87-1a36-48ba-88c4-f091262dbe43','somanaik'),(103,'8e679f87-1a36-48ba-88c4-f091262dbe43','tapopadma'),(49,'8fd1ad7f-6acf-4ad6-98d2-d8b3a9ceebc0','k'),(48,'8fd1ad7f-6acf-4ad6-98d2-d8b3a9ceebc0','tapopadma'),(98,'90be9a6e-27e6-45e5-94dc-ec1a09eeb510','somanaik'),(99,'90be9a6e-27e6-45e5-94dc-ec1a09eeb510','tapopadma'),(76,'9124a750-31cb-4fc0-addd-efa8abd8ca47','k'),(77,'9124a750-31cb-4fc0-addd-efa8abd8ca47','tapopadma'),(93,'9642b5a3-76bd-4ac1-be1c-4f816cf2a0a6','somanaik'),(92,'9642b5a3-76bd-4ac1-be1c-4f816cf2a0a6','tapopadma'),(86,'97637332-c3b9-4fd8-9163-02da4d96412c','somanaik'),(87,'97637332-c3b9-4fd8-9163-02da4d96412c','tapopadma'),(52,'9fef8b0b-7229-42f3-b926-3b36dd963258','k'),(53,'9fef8b0b-7229-42f3-b926-3b36dd963258','tapopadma'),(138,'a26a3170-918f-4f42-a3ed-c603e2252b4a','k'),(139,'a26a3170-918f-4f42-a3ed-c603e2252b4a','tapopadma'),(148,'a56e6952-3376-49a0-9375-b63d1ede914e','somanaik'),(16,'a892e850-9d1a-46a6-8285-e4c57718fd91','k'),(17,'a892e850-9d1a-46a6-8285-e4c57718fd91','tapopadma'),(47,'a98b7b13-c0c7-4c13-be0a-6b780e98fc0a','k'),(46,'a98b7b13-c0c7-4c13-be0a-6b780e98fc0a','tapopadma'),(45,'adee685f-d58d-49a4-af7f-0e8f21920b48','obama'),(44,'adee685f-d58d-49a4-af7f-0e8f21920b48','tapopadma'),(122,'b0b9713a-c124-4251-9ad4-2e3e58fe3b71','somanaik'),(123,'b0b9713a-c124-4251-9ad4-2e3e58fe3b71','tapopadma'),(43,'b2069334-2af2-4efa-85ce-f69914002deb','obama'),(42,'b2069334-2af2-4efa-85ce-f69914002deb','tapopadma'),(56,'b4b5e8f8-0727-4bd1-a0f5-8f8b719b9cb0','k'),(57,'b4b5e8f8-0727-4bd1-a0f5-8f8b719b9cb0','tapopadma'),(24,'b5c16df7-42c6-41d1-bc2b-146095611309','k'),(25,'b5c16df7-42c6-41d1-bc2b-146095611309','tapopadma'),(73,'b7a2f337-aa8a-41e3-a9e9-8a2b6e067a3f','k'),(72,'b7a2f337-aa8a-41e3-a9e9-8a2b6e067a3f','tapopadma'),(104,'baaceaff-0ec7-437e-b09c-b1ca67a5c390','somanaik'),(105,'baaceaff-0ec7-437e-b09c-b1ca67a5c390','tapopadma'),(141,'bc168b64-914b-4526-a915-f69a7e5c0f64','k'),(140,'bc168b64-914b-4526-a915-f69a7e5c0f64','tapopadma'),(26,'c2ff0867-3931-4ae1-b2cd-bc07bcc7403a','k'),(27,'c2ff0867-3931-4ae1-b2cd-bc07bcc7403a','tapopadma'),(30,'cc148171-5896-403a-8ee6-dda37b5cb8f0','k'),(31,'cc148171-5896-403a-8ee6-dda37b5cb8f0','tapopadma'),(85,'cfe3bd12-7dea-4dc0-8c34-0870c16bd96d','somanaik'),(84,'cfe3bd12-7dea-4dc0-8c34-0870c16bd96d','tapopadma'),(96,'d84c5219-cf46-452e-b9b2-62f0f16c49d8','somanaik'),(97,'d84c5219-cf46-452e-b9b2-62f0f16c49d8','tapopadma'),(142,'d939eea2-db65-4378-a9ba-b9de64a58de8','somanaik'),(143,'d939eea2-db65-4378-a9ba-b9de64a58de8','tapopadma'),(68,'de27d25c-5659-4066-b36a-a599461beb32','k'),(69,'de27d25c-5659-4066-b36a-a599461beb32','tapopadma'),(14,'de7b51e3-6779-4461-8b29-908dacfabcd2','k'),(15,'de7b51e3-6779-4461-8b29-908dacfabcd2','tapopadma'),(116,'e7b271cc-6340-4f24-9f9c-4dec5261ba33','somanaik'),(41,'e988d803-c12c-4efb-9777-928f0927817f','fff'),(40,'e988d803-c12c-4efb-9777-928f0927817f','tapopadma'),(18,'eaf54794-9e80-431e-b568-707c32e5646e','k'),(19,'eaf54794-9e80-431e-b568-707c32e5646e','tapopadma'),(32,'ed807772-29ce-4a38-a791-a2a89e7d423f','k'),(33,'ed807772-29ce-4a38-a791-a2a89e7d423f','tapopadma'),(58,'ee98b8d3-e5a0-40bf-93c7-50efc869fcd0','k'),(59,'ee98b8d3-e5a0-40bf-93c7-50efc869fcd0','tapopadma'),(128,'f065dfc6-918d-4d24-94d5-1983202c4518','somanaik'),(70,'f2186bad-8c38-463a-aaf5-f0a6f84b974f','k'),(71,'f2186bad-8c38-463a-aaf5-f0a6f84b974f','tapopadma'),(51,'fbd5621f-b165-4606-8338-2700340f1e42','k'),(50,'fbd5621f-b165-4606-8338-2700340f1e42','tapopadma'),(39,'fc17cca5-2dfa-410d-91ae-5689ba670d7b','obama'),(38,'fc17cca5-2dfa-410d-91ae-5689ba670d7b','tapopadma');
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

-- Dump completed on 2018-04-18 17:58:29
