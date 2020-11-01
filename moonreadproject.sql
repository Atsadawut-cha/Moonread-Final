-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 01, 2020 at 07:10 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `moonreadproject`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL,
  `book_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `book_price` int(11) NOT NULL,
  `img_book` varchar(500) CHARACTER SET utf8 NOT NULL,
  `book_type` varchar(100) CHARACTER SET utf8 NOT NULL,
  `book_detail` varchar(500) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`book_id`, `book_name`, `book_price`, `img_book`, `book_type`, `book_detail`) VALUES
(1, 'หัวขโมยแห่งบารามอส1', 200, 'https://www.satapornbooks.co.th/imgadmins/product_large/000453_2.jpg', 'Fiction', 'afsafpowap'),
(2, 'หัวขโมยแห่งบารามอส เล่ม2', 250, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1328632000l/13456471.jpg', 'Fiction', 'หกดหกเกดเกด้เ่ด้่เ้่เ้่้เ่'),
(3, 'My Hero Academia เล่ม 1', 59, 'https://cdn-local.mebmarket.com/meb/server1/103174/Thumbnail/book_detail_large.gif?3', 'Comic', 'Plus ULTRA !'),
(4, 'Return of Wolverine Mavel Comic', 50, 'https://images-na.ssl-images-amazon.com/images/I/71oSXu0cGHL.jpg', 'EBook', 'X-men');

-- --------------------------------------------------------

--
-- Table structure for table `booktype`
--

CREATE TABLE `booktype` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cus_id` int(11) NOT NULL,
  `cus_username` varchar(50) CHARACTER SET utf8 NOT NULL,
  `cus_password` varchar(10) CHARACTER SET utf8 NOT NULL,
  `cus_tel` varchar(10) CHARACTER SET utf8 NOT NULL,
  `type` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cus_id`, `cus_username`, `cus_password`, `cus_tel`, `type`) VALUES
(1, 'aaa', '1234', '111111111', 2),
(2, 'bbbb', '1234', '123445', 1),
(3, 'ccc', '1234', '22222222', 1),
(4, 'rrr', '1234', '11111111', 1),
(5, 'wwww', '1234', '11111', 1),
(6, 'fah@gmail.com', '12345', '0640497471', 1),
(7, 'abc', '1234', '123456', 1),
(8, 'ddd', '1234', '1234', 1),
(9, 'zzz', '1234', '1234', 1),
(10, 'xxx', '1234', '1234', 1),
(11, 'yyy', '1234', '1234', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `order_date` varchar(10) CHARACTER SET utf8 NOT NULL,
  `address` varchar(200) CHARACTER SET utf8 NOT NULL,
  `status` varchar(20) CHARACTER SET utf8 NOT NULL,
  `pay` varchar(100) NOT NULL,
  `delivery` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `customer_id`, `total_price`, `order_date`, `address`, `status`, `pay`, `delivery`) VALUES
(2, 1, 900, '11102020', 'ewewqeqwe', 'good', '', ''),
(3, 1, 450, '', '', '', '', ''),
(4, 2, 100, '11102020', 'ewewqeqwe', 'delivery', '', ''),
(5, 3, 0, 'h', 'k', 'done', '', ''),
(6, 3, 200, '', '', 'ordered', '', ''),
(14, 7, 0, '', '', 'order', '', ''),
(16, 8, 1100, '1/11/2563', 'kku', 'ordered', 'บัตรเครดิต', 'EMS'),
(17, 9, 727, '31/10/25', 'kku', 'done', 'เก็บเงินปลายทาง', 'EMS'),
(602, 4, 450, '01/11/2563', 'kku', 'ordered', 'โอนผ่านธนาคาร', 'EMS'),
(603, 4, 0, '', '', 'order', '', ''),
(604, 10, 230, '1234', 'kku', 'ordered', 'บัตรเครดิต', 'ลงทะเบียน'),
(605, 10, 0, '', '', 'order', '', ''),
(606, 8, 289, '1/11/2563', 'KKU', 'ordered', 'บัตรเครดิต', 'ลงทะเบียน'),
(607, 3, 189, '1/11/2563', 'KKU', 'ordered', 'เก็บเงินปลายทาง', 'ลงทะเบียน'),
(608, 8, 0, '', '', 'order', '', ''),
(609, 3, 0, '', '', 'order', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `detail_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`detail_id`, `order_id`, `book_id`, `qty`, `price`) VALUES
(1, 2, 1, 2, 400),
(3, 3, 1, 1, 200),
(4, 3, 1, 1, 250),
(5, 2, 2, 1, 200),
(6, 2, 3, 3, 177),
(8, 14, 1, 1, 200),
(28, 17, 2, 2, 500),
(30, 602, 1, 2, 400),
(32, 17, 3, 3, 177),
(33, 603, 3, 1, 59),
(34, 604, 1, 1, 200),
(37, 16, 1, 4, 800),
(38, 16, 2, 1, 250),
(40, 607, 3, 1, 59),
(41, 606, 1, 1, 200),
(42, 606, 3, 1, 59),
(43, 607, 4, 2, 100);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `booktype`
--
ALTER TABLE `booktype`
  ADD PRIMARY KEY (`type_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cus_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`detail_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `booktype`
--
ALTER TABLE `booktype`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `cus_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=610;

--
-- AUTO_INCREMENT for table `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
