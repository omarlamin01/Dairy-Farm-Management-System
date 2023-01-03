-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 25, 2022 at 07:36 PM
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
-- Table structure for table `animals`
--

-- create database dairyfarm;

CREATE DATABASE IF NOT EXISTS `dairyfarm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `animals` (
  `id` varchar(30) NOT NULL,
  `birth_date` date NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `routine` int(11) NOT NULL,
  `race` int(11) NOT NULL,
  `type` enum('cow','bull','calf') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `animals_sales`
--

CREATE TABLE `animals_sales` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `animal_id` varchar(20) DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `email` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `consuming`
--

CREATE TABLE `consuming` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `quantity` float NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `gender` enum('F','M') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` text DEFAULT NULL,
  `salary` float NOT NULL,
  `hire_date` date NOT NULL,
  `contract_type` enum('CDI','CDD','CTT') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`first_name`, `last_name`, `gender`, `cin`, `email`, `phone`, `address`, `salary`, `hire_date`, `contract_type`, `created_at`, `updated_at`) VALUES
('test', 'teeesy', 'M', 'GT3424', 'test@gmail.com', '1556223', 'dfgdfgfdg', 7777, '2023-11-22', 'CDI', '2022-11-23 21:50:45', '2022-11-24 13:59:59'),
('jack', 'dan', 'M', 'JT6345', 'jack@gmail.com', '589855', 'safsfas sdf dsf', 8888, '2023-11-01', 'CDI', '2022-11-24 13:57:49', '2022-11-24 13:58:17'),
('new', 'ttt', 'M', 'JT9923', 'new@gmail.com', '78888', 'adasf as saf as', 5555, '2022-11-23', 'CDI', '2022-11-23 22:55:44', '2022-11-23 22:55:44'),
('Light', 'Laghjaj', 'M', 'JT9982', 'abdesl@gmail.com', '4412221', 'On projection apartments u', 55555, '2023-11-10', 'CDI', '2022-11-22 21:54:15', '2022-11-23 22:53:01'),
('mohamed', 'me', 'F', 'kjsdhfh', 'me@gmail.com', '02184812', 'afdasf8', 99999, '2022-11-21', 'CDI', '2022-11-21 11:48:53', '2022-11-21 11:48:53');

-- --------------------------------------------------------

--
-- Table structure for table `health_status`
--

CREATE TABLE `health_status` (
  `id` int(11) NOT NULL,
  `animal_id` varchar(20) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `breathing` int(11) DEFAULT NULL,
  `health_score` enum('excellent','good','not bad','bad') NOT NULL,
  `control_date` date NOT NULL,
  `notes` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `milk_collections`
--

CREATE TABLE `milk_collections` (
  `id` int(11) NOT NULL,
  `cow_id` varchar(20) NOT NULL,
  `quantity` float NOT NULL,
  `period` enum('morning','evening') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `milk_sales`
--

CREATE TABLE `milk_sales` (
  `id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float NOT NULL,
  `sale_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `pregnancies`
--

CREATE TABLE `pregnancies` (
  `id` int(11) NOT NULL,
  `cow_id` varchar(20) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `pregnancy_type` enum('Natural Service','By Collecting Semen') DEFAULT NULL,
  `pregnancy_status` enum('pending','finished','failed') NOT NULL,
  `notes` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `price` float NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `races`
--

CREATE TABLE `races` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'admin'),
(2, 'hr');

-- --------------------------------------------------------

--
-- Table structure for table `routines`
--

CREATE TABLE `routines` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `note` varchar(50) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `routine_has_feeds`
--

CREATE TABLE `routine_has_feeds` (
  `id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `routine_id` int(11) NOT NULL,
  `feeding_time` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `stocks`
--

CREATE TABLE `stocks` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` enum('Machine','Vaccine','Feed','Drug') NOT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `quantity` int(11) NOT NULL,
  `availability` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stocks`
--

INSERT INTO `stocks` (`id`, `name`, `type`, `unit`, `created_at`, `updated_at`, `quantity`, `availability`) VALUES
(5, 'Tbaan', 'Feed', 'Kg', '2022-11-23 17:26:33', '2022-11-23 17:26:33', 3, 1),
(6, 'makla', 'Feed', 'Kg', '2022-11-23 17:26:54', '2022-11-23 17:26:54', 4, 1),
(7, 'Makla 2', 'Vaccine', 'Kg', '2022-11-23 17:45:02', '2022-11-23 17:45:02', 1, 1),
(8, 'Makla 3', 'Machine', 'Kg', '2022-11-23 17:45:10', '2022-11-23 17:45:10', 2, 1),
(9, 'Zra3', 'Machine', 'Kg', '2022-11-23 18:08:05', '2022-11-23 18:08:05', 2, 1),
(10, 'updated', 'Vaccine', 'Kg', '2022-11-23 18:09:09', '2022-11-23 19:25:28', 3, 1),
(12, 'rrrr', 'Machine', 'Kg', '2022-11-23 18:15:33', '2022-11-23 19:24:24', 8, 1),
(14, 'UUUU', 'Feed', 'Litre', '2022-11-23 22:05:22', '2022-11-23 22:05:22', 3, 1),
(15, 'PPPP', 'Machine', 'Kg', '2022-11-23 22:06:36', '2022-11-23 22:06:36', 5, 1),
(16, 'wew', 'Machine', 'Kg', '2022-11-25 18:33:09', '2022-11-25 18:33:09', 2, 1),
(17, 'rrr', 'Machine', 'Kg', '2022-11-25 18:33:19', '2022-11-25 18:33:19', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `type` enum('person','company') DEFAULT NULL,
  `phone` int(11) NOT NULL,
  `email` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `gender` enum('M','F') NOT NULL,
  `cin` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `salary` float NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `role_id`, `password`, `created_at`, `updated_at`, `first_name`, `last_name`, `gender`, `cin`, `phone`, `salary`, `email`, `address`) VALUES
(1, 1, '1234', '2022-11-21 11:08:00', '2022-11-21 11:08:03', 'abdellatif', 'laghjaj', 'M', 'jt00000', '0000000', 9999, 'abc@gmail.com', 'ouled ali');

-- --------------------------------------------------------

--
-- Table structure for table `vaccination`
--

CREATE TABLE `vaccination` (
  `id` int(11) NOT NULL,
  `animal_id` varchar(30) NOT NULL,
  `responsible_id` int(11) NOT NULL,
  `vaccine_id` int(11) NOT NULL,
  `vaccination_date` date NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `animals`
--
ALTER TABLE `animals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_id_r` (`race`),
  ADD KEY `fk_animal_id_routine` (`routine`);

--
-- Indexes for table `animals_sales`
--
ALTER TABLE `animals_sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_sale_id_client` (`client_id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`phone`,`email`);

--
-- Indexes for table `consuming`
--
ALTER TABLE `consuming`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_consuming_id_stock` (`stock_id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`cin`),
  ADD UNIQUE KEY `cin` (`cin`,`email`,`phone`);

--
-- Indexes for table `health_status`
--
ALTER TABLE `health_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_health_status_id_animal` (`animal_id`);

--
-- Indexes for table `milk_collections`
--
ALTER TABLE `milk_collections`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milk_collection_id_cow` (`cow_id`);

--
-- Indexes for table `milk_sales`
--
ALTER TABLE `milk_sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_milk_sale_id_client` (`client_id`);

--
-- Indexes for table `pregnancies`
--
ALTER TABLE `pregnancies`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pregnancy_id_cow` (`cow_id`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_purchase_id_stock` (`stock_id`),
  ADD KEY `fk_purchase_id_supplier` (`supplier_id`);

--
-- Indexes for table `races`
--
ALTER TABLE `races`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `routines`
--
ALTER TABLE `routines`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_routine_has_feeds_id_routine` (`routine_id`),
  ADD KEY `fk_routine_has_feeds_id_stock` (`stock_id`);

--
-- Indexes for table `stocks`
--
ALTER TABLE `stocks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`phone`,`email`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cin` (`cin`,`phone`,`email`),
  ADD KEY `fk_user_id_role` (`role_id`);

--
-- Indexes for table `vaccination`
--
ALTER TABLE `vaccination`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_vacc_has_animal` (`animal_id`),
  ADD KEY `fk_vacc_has_respo` (`responsible_id`),
  ADD KEY `fk_vacc_has_stock` (`vaccine_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `animals_sales`
--
ALTER TABLE `animals_sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `consuming`
--
ALTER TABLE `consuming`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `health_status`
--
ALTER TABLE `health_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `milk_collections`
--
ALTER TABLE `milk_collections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `milk_sales`
--
ALTER TABLE `milk_sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pregnancies`
--
ALTER TABLE `pregnancies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `races`
--
ALTER TABLE `races`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `routines`
--
ALTER TABLE `routines`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `routine_has_feeds`
--
ALTER TABLE `routine_has_feeds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stocks`
--
ALTER TABLE `stocks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `vaccination`
--
ALTER TABLE `vaccination`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `vaccination`
--
ALTER TABLE `vaccination`
  ADD CONSTRAINT `fk_vacc_has_animal` FOREIGN KEY (`animal_id`) REFERENCES `animals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vacc_has_respo` FOREIGN KEY (`responsible_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_vacc_has_stock` FOREIGN KEY (`vaccine_id`) REFERENCES `stocks` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
