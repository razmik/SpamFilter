-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 25, 2012 at 04:55 PM
-- Server version: 5.1.36
-- PHP Version: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `spamfilterdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `nonspambox`
--

CREATE TABLE IF NOT EXISTS `nonspambox` (
  `sf_id` int(11) NOT NULL AUTO_INCREMENT,
  `sf_from` char(50) DEFAULT NULL,
  `sf_to` char(50) DEFAULT NULL,
  `sf_cc` char(50) DEFAULT NULL,
  `sf_bcc` char(50) DEFAULT NULL,
  `sf_message` text,
  `sf_point` float DEFAULT NULL,
  PRIMARY KEY (`sf_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `nonspambox`
--

INSERT INTO `nonspambox` (`sf_id`, `sf_from`, `sf_to`, `sf_cc`, `sf_bcc`, `sf_message`, `sf_point`) VALUES
(1, 'aer', 'faf', NULL, 'fasd', 'asfasd', 0.4),
(2, 'rashmika', 'sahan', 'dileepa', 'shchini', 'Kajaa jasdflasldmclsd mata pissu', 0.4),
(3, 'rasgnuja', 'pappa', 'dakka', 'laakaa', 'i am sri lanka', 0.4);

-- --------------------------------------------------------

--
-- Table structure for table `spambox`
--

CREATE TABLE IF NOT EXISTS `spambox` (
  `sf_id` int(11) NOT NULL AUTO_INCREMENT,
  `sf_from` char(50) DEFAULT NULL,
  `sf_to` char(50) DEFAULT NULL,
  `sf_cc` char(50) DEFAULT NULL,
  `sf_bcc` char(50) DEFAULT NULL,
  `sf_message` text,
  `sf_point` float DEFAULT NULL,
  PRIMARY KEY (`sf_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `spambox`
--

INSERT INTO `spambox` (`sf_id`, `sf_from`, `sf_to`, `sf_cc`, `sf_bcc`, `sf_message`, `sf_point`) VALUES
(1, 'razmik89@gmail.com', 'kalana@yahoo.com', 'binu@gamil.com', 'tisha@hotmail.com', 'Hi Friend, \n\nMy name is Mark Zuckerberg, Ceo of Facebook. We have recently joined up with Apple company regarding a one-time promotional event today, we are giving away no cost Apple iPhones and iPads to randomly selected individuals who have been fortunate to be selected as one of our newest winners for today. We randomly selected users from our systems database and you have matched with our latest drawing. \n\nWe have partnered up with Apple to advertise their hottest product yet, the Apple iPhone and iPad. Once again, we are running this campaign for one-day only. All you need to do is CLICK HERE to go to our website made for this promotion and fill out the short survey to get yours for free. Simply make sure you enter your email so we can locate our records to ensure that we have reserved one for you. That''s it! \n\nCongrats on winning a free Apple iPhone4 and iPad2. If you have any question or concerns, feel free to e-mail me back. However, you need to claim your free iPhone and iPad first to ensure one will be reserved for you before the deadline ends. We do understand that you may possibly not receive this e-mail until after the deadline, however, we advise you check the site and enter your email to see if we still have yours on hold, which we often-times do because others have not claimed theirs in time.', 1);
