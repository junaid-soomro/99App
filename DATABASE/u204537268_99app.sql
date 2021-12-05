-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 13, 2018 at 07:59 PM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u204537268_99app`
--

-- --------------------------------------------------------

--
-- Table structure for table `99admins`
--

CREATE TABLE `99admins` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(250) NOT NULL,
  `admin_email` varchar(250) NOT NULL,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `dept_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `99admins`
--

INSERT INTO `99admins` (`admin_id`, `admin_name`, `admin_email`, `username`, `password`, `dept_id`) VALUES
(1, 'shamsi', 'shamsi@gmail.com', 'shamsi', 'shamsi', 1),
(2, 'abdul', 'abdul@gmail.com', 'abdul', 'abdul', 2);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `dept_id` int(11) NOT NULL,
  `dept_name` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`dept_id`, `dept_name`) VALUES
(1, 'fire'),
(2, 'ambulance');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `e_id` int(11) NOT NULL,
  `e_name` varchar(250) NOT NULL,
  `e_phone` bigint(20) NOT NULL,
  `e_username` varchar(250) NOT NULL,
  `e_password` varchar(250) NOT NULL,
  `admin_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`e_id`, `e_name`, `e_phone`, `e_username`, `e_password`, `admin_id`) VALUES
(2, 'shabir akbar', 123123, 'shabir', 'shabir', 1),
(3, 'shaheed ullah', 123123, 'shaheed', 'shaheed', 2);

-- --------------------------------------------------------

--
-- Table structure for table `medical_history`
--

CREATE TABLE `medical_history` (
  `user_id` int(11) NOT NULL,
  `diseases` varchar(250) NOT NULL,
  `other_details` varchar(250) NOT NULL,
  `blood_group` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `medical_history`
--

INSERT INTO `medical_history` (`user_id`, `diseases`, `other_details`, `blood_group`) VALUES
(15, 'Heart diesease', 'lol', 'A+'),
(16, 'Heart diesease', 'what', 'AB-'),
(16, 'Blood Pressure', 'what', 'AB-'),
(17, 'Heart diesease', 'oi huwy', 'O+'),
(17, 'Blood Pressure', 'oi huwy', 'O+'),
(17, 'Diabetes', 'oi huwy', 'O+');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` bigint(20) NOT NULL,
  `address` varchar(250) NOT NULL,
  `national_id` bigint(20) NOT NULL,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `image` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `name`, `phone`, `address`, `national_id`, `username`, `password`, `image`) VALUES
(3, 'jun', 123, 'asd213', 123123, 'demon', 'demon', 'https://firebasestorage.googleapis.com/v0/b/app-e9bec.appspot.com/o/user_images%2F817d0f19-8cdb-4d98-886a-c43ac411d224?alt=media&token=7e5580c2-df86-4f49-892d-1ef26fbaf842'),
(8, 'jun', 12312312312, 'asdasd3412423', 123123, 'kapoor', 'kapoor', 'https://firebasestorage.googleapis.com/v0/b/app-e9bec.appspot.com/o/user_images%2F45f87174-8c49-4b2b-8157-b1a3c5b4a3fe?alt=media&token=7a99d056-8d00-4a22-871b-2cfdc97b5b5e'),
(14, 'shoaib', 231212, 'fa343', 13123, 'shoaib', 'shoaib', 'https://firebasestorage.googleapis.com/v0/b/app-e9bec.appspot.com/o/user_images%2Fa9f42f02-9d2b-4b5a-b2c6-55069f7e0c90?alt=media&token=b7bb8a12-4623-4bc1-9e1b-b415c80845b8'),
(15, 'khura', 56, 'asd4141', 123, 'khu', 'khu', 'https://firebasestorage.googleapis.com/v0/b/app-e9bec.appspot.com/o/user_images%2F46427c2c-e9bb-458b-bf89-eec1099f1b0c?alt=media&token=3aedb378-ef22-428a-8c11-aa609a05330e'),
(16, 'sal', 453, 'sasd124', 123, 'sal', 'sal', 'https://firebasestorage.googleapis.com/v0/b/app-e9bec.appspot.com/o/user_images%2F53a8768f-5446-490e-ad21-7e034277cfe3?alt=media&token=423a6668-b2ab-4483-be65-326aee6d501b'),
(17, 'shoaib', 65756730, 'dagardena123123', 1231230, 'naran', 'naran', 'https://firebasestorage.googleapis.com/v0/b/app-e9bec.appspot.com/o/user_images%2F12539579-1b9f-44c4-9972-c85b1ef3b165?alt=media&token=ace1a2b5-c643-42bf-9676-bba0fb3791d5');

-- --------------------------------------------------------

--
-- Table structure for table `user_helpme_req`
--

CREATE TABLE `user_helpme_req` (
  `req_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `99emp_id` int(11) NOT NULL,
  `location` double NOT NULL,
  `comment` varchar(250) NOT NULL,
  `voice_comment` varchar(250) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `request_status` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `99admins`
--
ALTER TABLE `99admins`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `dept_id` (`dept_id`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`dept_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`e_id`),
  ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `medical_history`
--
ALTER TABLE `medical_history`
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_helpme_req`
--
ALTER TABLE `user_helpme_req`
  ADD PRIMARY KEY (`req_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `99emp_id` (`99emp_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `99admins`
--
ALTER TABLE `99admins`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `department`
--
ALTER TABLE `department`
  MODIFY `dept_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `e_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `user_helpme_req`
--
ALTER TABLE `user_helpme_req`
  MODIFY `req_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `99admins`
--
ALTER TABLE `99admins`
  ADD CONSTRAINT `99admins_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `department` (`dept_id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `99admins` (`admin_id`);

--
-- Constraints for table `medical_history`
--
ALTER TABLE `medical_history`
  ADD CONSTRAINT `medical_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `user_helpme_req`
--
ALTER TABLE `user_helpme_req`
  ADD CONSTRAINT `user_helpme_req_ibfk_1` FOREIGN KEY (`99emp_id`) REFERENCES `employee` (`e_id`),
  ADD CONSTRAINT `user_helpme_req_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
