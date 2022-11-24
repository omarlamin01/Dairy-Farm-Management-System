-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 17, 2022 at 10:45 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dairyfarm`
--

-- --------------------------------------------------------

--
-- Table structure for table `animal`
--

CREATE TABLE `animal` (
  `id` varchar(30) NOT NULL,
  `birth_date` date NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `routine_id` int(11) NOT NULL,
  `race_id` int(11) NOT NULL,
  `type` enum('cow','bull','calf') NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `animal`
--

INSERT INTO `animal` (`id`, `birth_date`, `purchase_date`, `routine_id`, `race_id`, `type`, `created_at`, `updated_at`) VALUES
('1285635', '2022-11-22', '2022-11-20', 1, 1, 'cow', '2022-11-10', '2022-11-10'),
('Cow--129958159', '2022-11-23', '2022-12-05', 1, 1, 'cow', '2022-11-12', '2022-11-12'),
('Cow-534634684', '2022-11-30', '2022-11-23', 1, 1, 'bull', '2022-11-12', '2022-11-12');

-- --------------------------------------------------------

--
-- Table structure for table `animal_sale`
--

CREATE TABLE `animal_sale` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `animal_id` varchar(30) DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `animal_sale`
--

INSERT INTO `animal_sale` (`id`, `client_id`, `animal_id`, `price`, `sale_date`, `created_at`, `updated_at`) VALUES
(3, 1, 'Cow-534634684', 33333, '2022-11-07', '2022-11-17', '2022-11-17');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `name`, `type`, `phone`, `email`, `created_at`, `updated_at`) VALUES
(1, 'ADARDORNaima', 'person', 788900, 'adardournaima@gmail.com', '2022-11-17', '2022-11-17');

-- --------------------------------------------------------

--
-- Table structure for table `consuming`
--

CREATE TABLE `consuming` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `quantity` float NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `gender` enum('F','M') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` varchar(30) NOT NULL,
  `salary` float NOT NULL,
  `recruitment_date` date NOT NULL,
  `contract_type` enum('CDI','CDD','CTT') NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `first_name`, `last_name`, `gender`, `cin`, `email`, `phone`, `address`, `salary`, `hire_date`, `contract_type`, `created_at`, `updated_at`) VALUES
(1, 'Naima', 'Adardor', 'F', 'KJDKDIRT789', 'adardournaima@gmail.com', '098477895', 'jfjfu', 859595000, '2022-11-21', 'CDD', '2022-11-12', '2022-11-12');

-- --------------------------------------------------------

--
-- Table structure for table `health_status`
--

CREATE TABLE `health_status` (
  `id` int(11) NOT NULL,
  `animal_id` varchar(30) DEFAULT NULL,
  `vaccine_id` int(11) NOT NULL,
  `weight` float DEFAULT NULL,
  `breading` float DEFAULT NULL,
  `age` float DEFAULT NULL,
  `control_date` date NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `milk_collection`
--

CREATE TABLE `milk_collection` (
  `id` int(11) NOT NULL,
  `cow_id` varchar(30) NOT NULL,
  `quantity` float NOT NULL,
  `period` enum('morning','evening') NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `milk_collection`
--

INSERT INTO `milk_collection` (`id`, `cow_id`, `quantity`, `period`, `created_at`, `updated_at`) VALUES
(2, 'Cow--129958159', 678, 'evening', '2022-11-12', '2022-11-12'),
(3, '1285635', 235689, 'morning', '2022-11-17', '2022-11-17');

-- --------------------------------------------------------

--
-- Table structure for table `milk_sale`
--

CREATE TABLE `milk_sale` (
  `id` int(11) NOT NULL DEFAULT 0,
  `client_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pregnancy`
--

CREATE TABLE `pregnancy` (
  `id` int(11) NOT NULL,
  `cow_id` varchar(30) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `pregnancy_type` enum('Natural Service','By Collecting Semen') DEFAULT NULL,
  `pregnancy_status` enum('pending','finished','failed') NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `price` float NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `race`
--

CREATE TABLE `race` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `race`
--

INSERT INTO `race` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'holandiya', '2022-11-09', '2022-11-09');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `created_at`, `updated_at`) VALUES
(1, 'admin', '2022-11-12 10:54:20', '2022-11-12 10:54:20'),
(2, 'Admin', '2022-11-12 11:07:58', '2022-11-12 11:07:58'),
(3, 'HR', '2022-11-12 11:07:58', '2022-11-12 11:07:58'),
(4, 'Sales agent', '2022-11-12 11:07:58', '2022-11-12 11:07:58'),
(5, 'Production manager', '2022-11-12 11:07:59', '2022-11-12 11:07:59'),
(6, 'Veterinary', '2022-11-12 11:07:59', '2022-11-12 11:07:59');

-- --------------------------------------------------------

--
-- Table structure for table `routine`
--

CREATE TABLE `routine` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `routine`
--

INSERT INTO `routine` (`id`, `name`, `note`, `created_at`, `updated_at`) VALUES
(1, 'routine1', 'hhhhh', '2022-11-09', '2022-11-09');

-- --------------------------------------------------------

--
-- Table structure for table `routine_has_feeds`
--

CREATE TABLE `routine_has_feeds` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `routine_id` int(11) NOT NULL,
  `feeding_time` date NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` enum('Machine','Vaccine','feed','Drug') DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `added_date` date DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `email` int(11) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `password` varchar(20) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp(),
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `gender` enum('M','F') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `salary` float NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `role_id`, `employee_id`, `password`, `created_at`, `updated_at`, `first_name`, `last_name`, `gender`, `cin`, `phone`, `salary`, `email`, `address`) VALUES
(1, 1, 1, '123123', '2022-11-12', '2022-11-12', 'Naima', 'Adardor', 'F', 'KJDKDIRT789', '788900', 1000000000, 'adardournaima@gmail.com', 'naima');

-- --------------------------------------------------------

--
-- Table structure for table `vaccine`
--

CREATE TABLE `vaccine` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `dose` float NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp(),
  `updated_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `animal`
--
ALTER TABLE `animal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_id_r` (`race_id`),
  ADD KEY `fk_animal_id_routine` (`routine_id`);

--
-- Indexes for table `animal_sale`
--
ALTER TABLE `animal_sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_sale_id_client` (`client_id`),
  ADD KEY `fk_animal_sale_id_animal` (`animal_id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `consuming`
--
ALTER TABLE `consuming`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_consuming_id_stock` (`stock_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `health_status`
--
ALTER TABLE `health_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_health_status_id_animal` (`animal_id`),
  ADD KEY `fk_health_status_id_vaccine` (`vaccine_id`);

--
-- Indexes for table `milk_collection`
--
ALTER TABLE `milk_collection`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milk_collection_id_cow` (`cow_id`);

--
-- Indexes for table `milk_sale`
--
ALTER TABLE `milk_sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milk_sale_id_client` (`client_id`);

--
-- Indexes for table `pregnancy`
--
ALTER TABLE `pregnancy`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pregnancy_id_cow` (`cow_id`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_purchase_id_stock` (`stock_id`),
  ADD KEY `fk_purchase_id_supplier` (`supplier_id`);

--
-- Indexes for table `race`
--
ALTER TABLE `race`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `routine`
--
ALTER TABLE `routine`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_routine_has_feeds_id_routine` (`routine_id`),
  ADD KEY `fk_routine_has_feeds_id_stock` (`stock_id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_id_role` (`role_id`),
  ADD KEY `fk_user_id_employee` (`employee_id`);

--
-- Indexes for table `vaccine`
--
ALTER TABLE `vaccine`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `animal_sale`
--
ALTER TABLE `animal_sale`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `consuming`
--
ALTER TABLE `consuming`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `health_status`
--
ALTER TABLE `health_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `milk_collection`
--
ALTER TABLE `milk_collection`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pregnancy`
--
ALTER TABLE `pregnancy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `race`
--
ALTER TABLE `race`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `routine`
--
ALTER TABLE `routine`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stock`
--
ALTER TABLE `stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `vaccine`
--
ALTER TABLE `vaccine`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `animal`
--
ALTER TABLE `animal`
  ADD CONSTRAINT `fk_animal_id_r` FOREIGN KEY (`race_id`) REFERENCES `race` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_animal_id_routine` FOREIGN KEY (`routine_id`) REFERENCES `routine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `animal_sale`
--
ALTER TABLE `animal_sale`
  ADD CONSTRAINT `fk_animal_sale_id_animal` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_animal_sale_id_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `consuming`
--
ALTER TABLE `consuming`
  ADD CONSTRAINT `fk_consuming_id_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `health_status`
--
ALTER TABLE `health_status`
  ADD CONSTRAINT `fk_health_status_id_vaccine` FOREIGN KEY (`vaccine_id`) REFERENCES `vaccine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `milk_sale`
--
ALTER TABLE `milk_sale`
  ADD CONSTRAINT `fk_milk_sale_id_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_purchase_id_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_purchase_id_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  ADD CONSTRAINT `fk_routine_has_feeds_id_routine` FOREIGN KEY (`routine_id`) REFERENCES `routine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_routine_has_feeds_id_stock` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_id_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_id_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
