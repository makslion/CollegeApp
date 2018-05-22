CREATE DATABASE  IF NOT EXISTS `maksym_vavilov_16856_provisional_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `maksym_vavilov_16856_provisional_project`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: programming_db
-- ------------------------------------------------------
-- Server version	5.7.19-log

-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856
-- AUTHOR Maksym Vavilov. Student ID - 16856


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
-- Table structure for table `assignment_grades`
--

DROP TABLE IF EXISTS `assignment_grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignment_grades` (
  `Aid` varchar(10) NOT NULL,
  `Sid` varchar(10) NOT NULL,
  `Grade` double DEFAULT NULL,
  PRIMARY KEY (`Aid`,`Sid`),
  KEY `Sid6_idx` (`Sid`),
  CONSTRAINT `Aid` FOREIGN KEY (`Aid`) REFERENCES `assignments` (`Aid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Sid6` FOREIGN KEY (`Sid`) REFERENCES `student` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignment_grades`
--

LOCK TABLES `assignment_grades` WRITE;
/*!40000 ALTER TABLE `assignment_grades` DISABLE KEYS */;
INSERT INTO `assignment_grades` VALUES ('A01','S01',100),('A01','S03',90),('A01','S04',70),('A02','S01',NULL),('A02','S03',NULL),('A02','S04',NULL);
/*!40000 ALTER TABLE `assignment_grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assignments`
--

DROP TABLE IF EXISTS `assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assignments` (
  `Cid` varchar(10) NOT NULL,
  `Aid` varchar(10) NOT NULL,
  `Subject` varchar(10) NOT NULL,
  `Visible` varchar(3) NOT NULL,
  `DueDate` date NOT NULL,
  PRIMARY KEY (`Aid`),
  KEY `SBid2_idx` (`Subject`),
  CONSTRAINT `SBid2` FOREIGN KEY (`Subject`) REFERENCES `subjects` (`SBid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignments`
--

LOCK TABLES `assignments` WRITE;
/*!40000 ALTER TABLE `assignments` DISABLE KEYS */;
INSERT INTO `assignments` VALUES ('C2','A01','SB02','yes','2018-05-26'),('C2','A02','SB02','no','2018-05-28');
/*!40000 ALTER TABLE `assignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `Sid` varchar(10) NOT NULL,
  `Lessons` int(11) DEFAULT NULL,
  `Attended` int(11) DEFAULT NULL,
  PRIMARY KEY (`Sid`),
  CONSTRAINT `Sid1` FOREIGN KEY (`Sid`) REFERENCES `student` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES ('S01',50,50),('S02',45,44),('S03',50,49),('S04',50,50),('S05',45,45),('S06',62,61),('S07',54,26),('S08',62,58),('S09',62,58),('S10',62,60),('S11',48,48),('S12',48,39),('S13',48,46);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch_details`
--

DROP TABLE IF EXISTS `branch_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branch_details` (
  `Bid` varchar(10) NOT NULL,
  `Bname` varchar(45) NOT NULL,
  `Baddress` varchar(45) NOT NULL,
  PRIMARY KEY (`Bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_details`
--

LOCK TABLES `branch_details` WRITE;
/*!40000 ALTER TABLE `branch_details` DISABLE KEYS */;
INSERT INTO `branch_details` VALUES ('B01','Computing','8 Belvedare Palace'),('B02','Business','156 Stephan Road'),('B03','Graphics','58 Martin Square');
/*!40000 ALTER TABLE `branch_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branches`
--

DROP TABLE IF EXISTS `branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branches` (
  `Bid` varchar(10) NOT NULL,
  `Courses` varchar(10) NOT NULL,
  PRIMARY KEY (`Courses`,`Bid`),
  KEY `Bid_idx` (`Bid`),
  CONSTRAINT `Bid` FOREIGN KEY (`Bid`) REFERENCES `branch_details` (`Bid`) ON DELETE CASCADE,
  CONSTRAINT `Cid` FOREIGN KEY (`Courses`) REFERENCES `courses` (`Cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branches`
--

LOCK TABLES `branches` WRITE;
/*!40000 ALTER TABLE `branches` DISABLE KEYS */;
INSERT INTO `branches` VALUES ('B01','C1'),('B01','C2'),('B01','C3'),('B02','B1'),('B02','B2'),('B03','A1'),('B03','A3'),('B03','D1'),('B03','D2'),('B03','S2');
/*!40000 ALTER TABLE `branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `Cid` varchar(10) NOT NULL,
  `Course` varchar(45) NOT NULL,
  `Price` int(11) NOT NULL,
  PRIMARY KEY (`Cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('A1','Animation 1 year',2100),('A2','Animation 2 year',1700),('A3','Animation 3 year',1700),('B1','Business 1 year',2000),('B2','Business 2 year',1500),('C1','Computing 1 year',2000),('C2','Computing 2 year',1500),('C3','Computing 3 year',1500),('D1','Design 1 year',2100),('D2','Design 2 year',1700),('S1','Security',2000),('S2','Security 2 year',1500);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty_members`
--

DROP TABLE IF EXISTS `faculty_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty_members` (
  `Fid` varchar(10) NOT NULL,
  `Fname` varchar(45) NOT NULL,
  `Lname` varchar(45) NOT NULL,
  PRIMARY KEY (`Fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_members`
--

LOCK TABLES `faculty_members` WRITE;
/*!40000 ALTER TABLE `faculty_members` DISABLE KEYS */;
INSERT INTO `faculty_members` VALUES ('F01','Sable','Marion'),('F02','Deacon','Randolph'),('F03','Veronica','Mikhaila'),('F04','Jessie','Hamilton'),('F05','Nicole','Chelle'),('F06','Wilbur','Melesina'),('F07','Caryn','Ivor');
/*!40000 ALTER TABLE `faculty_members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grades` (
  `Sid` varchar(10) NOT NULL,
  `SBid` varchar(10) NOT NULL,
  `Grade` double DEFAULT NULL,
  `ExamGrade` double DEFAULT NULL,
  PRIMARY KEY (`Sid`,`SBid`),
  KEY `SBid1_idx` (`SBid`),
  CONSTRAINT `SBid1` FOREIGN KEY (`SBid`) REFERENCES `subjects` (`SBid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Sid5` FOREIGN KEY (`Sid`) REFERENCES `student` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES ('S01','SB02',100,NULL),('S01','SB03',100,NULL),('S03','SB02',89,NULL),('S03','SB03',90,NULL),('S04','SB02',70,NULL),('S04','SB03',68,NULL);
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_details`
--

DROP TABLE IF EXISTS `group_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_details` (
  `Gid` varchar(10) NOT NULL,
  `Cid` varchar(10) NOT NULL,
  `Superviser` varchar(45) NOT NULL,
  PRIMARY KEY (`Gid`),
  KEY `Cid2_idx` (`Cid`),
  KEY `Fid_idx` (`Superviser`),
  CONSTRAINT `Cid2` FOREIGN KEY (`Cid`) REFERENCES `courses` (`Cid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Fid` FOREIGN KEY (`Superviser`) REFERENCES `faculty_members` (`Fid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_details`
--

LOCK TABLES `group_details` WRITE;
/*!40000 ALTER TABLE `group_details` DISABLE KEYS */;
INSERT INTO `group_details` VALUES ('G01','A1','F01'),('G02','B1','F03'),('G03','C1','F02'),('G04','C2','F02'),('G05','S2','F07');
/*!40000 ALTER TABLE `group_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `Sid` varchar(10) NOT NULL,
  `Gid` varchar(10) NOT NULL,
  PRIMARY KEY (`Sid`,`Gid`),
  KEY `Gid2_idx` (`Gid`),
  CONSTRAINT `Gid2` FOREIGN KEY (`Gid`) REFERENCES `group_details` (`Gid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Sid3` FOREIGN KEY (`Sid`) REFERENCES `student` (`Sid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES ('S06','G01'),('S09','G01'),('S10','G01'),('S02','G02'),('S05','G02'),('S11','G03'),('S12','G03'),('S13','G03'),('S01','G04'),('S03','G04'),('S04','G04'),('S07','G05'),('S08','G05');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `Username` varchar(10) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Role` varchar(7) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('admin','admin','admin'),('F01','panadol','faculty'),('F02','panadol','faculty'),('S01','panadol','student');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payments` (
  `Sid` varchar(10) NOT NULL,
  `Required` int(11) NOT NULL,
  `Payed` int(11) NOT NULL,
  PRIMARY KEY (`Sid`),
  CONSTRAINT `Sid2` FOREIGN KEY (`Sid`) REFERENCES `student` (`Sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES ('S01',1500,1500),('S02',2000,1000),('S03',1500,1500),('S04',1500,1500),('S05',2000,0),('S06',2100,2100),('S07',1500,1500),('S08',1500,1500),('S09',2100,2100),('S10',2100,2100),('S11',2000,2000),('S12',2000,0),('S13',2000,2000);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `Sid` varchar(10) NOT NULL,
  `Fname` varchar(45) NOT NULL,
  `Lname` varchar(45) NOT NULL,
  `Course` varchar(45) DEFAULT NULL,
  `Phone` int(15) DEFAULT NULL,
  `Address` varchar(45) NOT NULL,
  PRIMARY KEY (`Sid`),
  KEY `Course_idx` (`Course`),
  CONSTRAINT `Course` FOREIGN KEY (`Course`) REFERENCES `courses` (`Cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('S01','Maksym','Vavilov','C2',899460799,'27 Dorset Lane, Dublin'),('S02','John','Smith','B1',429334407,'93 Park st Dundalk'),('S03','Habib','Carver','C2',579351574,'3 Harbour st Tullamore'),('S04','Jamaal','Jamie','C2',878621483,'21 Market sq Portalaosie'),('S05','Bastian','Willoughby','B2',872357235,'122 Stillorgan Wood'),('S06','Morty','Kendra','A1',526123509,'29 Queen'),('S07','Rifat','Ronnete','S2',14133862,'Unit L2 Baldonnell Enterprise Park'),('S08','Dollie','Sajjad','S2',214305327,'21 Church st Cork'),('S09','Addyson','Quinton','A1',656842357,'9 Lr Mkt St Ennis'),('S10','Sieger','Rafiq','A1',872321391,'34 College Square'),('S11','Ohiyesa','Elke','C1',579321060,'3 Cormac St Tullamore'),('S12','Kelly','Breanna','C1',12841998,'73 George'),('S13','Royale','Khloe','C1',214508888,'8a McCurtain st Cork');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjects`
--

DROP TABLE IF EXISTS `subjects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subjects` (
  `SBid` varchar(10) NOT NULL,
  `Lecturer` varchar(10) DEFAULT NULL,
  `Subject` varchar(45) NOT NULL,
  PRIMARY KEY (`SBid`),
  KEY `Fid1_idx` (`Lecturer`),
  CONSTRAINT `Fid1` FOREIGN KEY (`Lecturer`) REFERENCES `faculty_members` (`Fid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjects`
--

LOCK TABLES `subjects` WRITE;
/*!40000 ALTER TABLE `subjects` DISABLE KEYS */;
INSERT INTO `subjects` VALUES ('SB01','F01','Animation'),('SB02','F02','Programming'),('SB03','F03','Math'),('SB04','F04','Databases'),('SB05','F05','Design'),('SB06','F07','Networks');
/*!40000 ALTER TABLE `subjects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `Gid` varchar(10) NOT NULL,
  `Day` varchar(10) NOT NULL,
  `Subject` varchar(45) NOT NULL,
  `Start` time NOT NULL,
  `End` time NOT NULL,
  PRIMARY KEY (`Gid`,`Day`),
  CONSTRAINT `Gid1` FOREIGN KEY (`Gid`) REFERENCES `group_details` (`Gid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES ('G01','FRI','Design','09:00:00','12:00:00'),('G01','MON','Animation','09:00:00','12:00:00'),('G01','THU','Design','09:00:00','12:00:00'),('G01','TUE','Animation','09:00:00','12:00:00'),('G01','WED','Animation','09:00:00','12:00:00'),('G02','FRI','Databases','09:00:00','12:00:00'),('G02','MON','Math','09:00:00','12:00:00'),('G02','THU','Math','09:00:00','12:00:00'),('G02','TUE','Databases','09:00:00','12:00:00'),('G02','WED','Math','09:00:00','12:00:00'),('G03','FRI','Math','09:00:00','12:00:00'),('G03','MON','Programming','09:00:00','12:00:00'),('G03','THU','Math','13:00:00','16:00:00'),('G03','TUE','Programming','09:00:00','12:00:00'),('G03','WED','Math','13:00:00','16:00:00'),('G04','FRI','Programming','09:00:00','12:00:00'),('G04','MON','Math','13:00:00','16:00:00'),('G04','THU','Programming','09:00:00','12:00:00'),('G04','TUE','Math','09:00:00','12:00:00'),('G04','WED','Programming','09:00:00','12:00:00'),('G05','FRI','Programming','13:00:00','16:00:00'),('G05','MON','Networks','13:00:00','16:00:00'),('G05','THU','Networks','09:00:00','12:00:00'),('G05','TUE','Networks','09:00:00','12:00:00'),('G05','WED','Networks','09:00:00','12:00:00');
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-20 20:46:05
