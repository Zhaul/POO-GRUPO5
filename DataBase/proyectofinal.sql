-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 22-11-2022 a las 23:41:40
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectofinal`
--
CREATE DATABASE IF NOT EXISTS `proyectofinal` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `proyectofinal`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `areas`
--

DROP TABLE IF EXISTS `areas`;
CREATE TABLE IF NOT EXISTS `areas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `status` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NameUnique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargosPersonal`
--

DROP TABLE IF EXISTS `cargosPersonal`;
CREATE TABLE IF NOT EXISTS `cargosPersonal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idArea` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FK_CargoAreas` (`idArea`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `names` varchar(60) NOT NULL,
  `lastNames` varchar(60) NOT NULL,
  `typeDocument` varchar(1) NOT NULL,
  `numberDocument` varchar(15) NOT NULL,
  `idCargo` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,
  `status` char(1) NOT NULL DEFAULT '1',
  `email` varchar(100) DEFAULT NULL,
  `sex` char(1) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `dateBirth` date DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `dateStart` date DEFAULT NULL,
  `password` varchar(150) DEFAULT NULL,
  `flagAccess` char(1) DEFAULT NULL,
  `codeEmployee` varchar(45) NOT NULL,
  `user` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codeEmployee` (`codeEmployee`),
  KEY `FKEmpleadoCargo` (`idCargo`),
  KEY `FKEmpleadoRol` (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `justifications`
--

DROP TABLE IF EXISTS `justifications`;
CREATE TABLE IF NOT EXISTS `justifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `file` varchar(150) DEFAULT NULL,
  `reason` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `idShedule` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKJustificationShedule` (`idShedule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permisoRol`
--

DROP TABLE IF EXISTS `permisoRol`;
CREATE TABLE IF NOT EXISTS `permisoRol` (
  `idPermiso` char(3) NOT NULL,
  `idRol` int(11) NOT NULL,
  KEY `FK_PermisoRol` (`idPermiso`),
  KEY `FK_RolPermiso` (`idRol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permisos`
--

DROP TABLE IF EXISTS `permisos`;
CREATE TABLE IF NOT EXISTS `permisos` (
  `id` char(3) NOT NULL,
  `name` varchar(100) NOT NULL,
  `position` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `schedules`
--

DROP TABLE IF EXISTS `schedules`;
CREATE TABLE IF NOT EXISTS `schedules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hourStart` time DEFAULT NULL,
  `dateStart` date DEFAULT NULL,
  `idEmploye` int(11) NOT NULL,
  `hourEntry` time DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FKEmployeSchedule` (`idEmploye`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cargosPersonal`
--
ALTER TABLE `cargosPersonal`
  ADD CONSTRAINT `FK_CargoAreas` FOREIGN KEY (`idArea`) REFERENCES `areas` (`id`);

--
-- Filtros para la tabla `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `FKEmpleadoCargo` FOREIGN KEY (`idCargo`) REFERENCES `cargosPersonal` (`id`),
  ADD CONSTRAINT `FKEmpleadoRol` FOREIGN KEY (`idRol`) REFERENCES `roles` (`id`);

--
-- Filtros para la tabla `justifications`
--
ALTER TABLE `justifications`
  ADD CONSTRAINT `FKJustificationShedule` FOREIGN KEY (`idShedule`) REFERENCES `schedules` (`id`);

--
-- Filtros para la tabla `permisoRol`
--
ALTER TABLE `permisoRol`
  ADD CONSTRAINT `FK_PermisoRol` FOREIGN KEY (`idPermiso`) REFERENCES `permisos` (`id`),
  ADD CONSTRAINT `FK_RolPermiso` FOREIGN KEY (`idRol`) REFERENCES `roles` (`id`);

--
-- Filtros para la tabla `schedules`
--
ALTER TABLE `schedules`
  ADD CONSTRAINT `FKEmployeSchedule` FOREIGN KEY (`idEmploye`) REFERENCES `employees` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
