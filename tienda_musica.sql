SET sql_notes = 0;
-- Volcando estructura de base de datos para malagamusic
DROP DATABASE IF EXISTS `malagamusic`;
CREATE DATABASE IF NOT EXISTS `malagamusic`;
USE `malagamusic`;

-- Volcando estructura para tabla malagamusic.fabricantes
DROP TABLE IF EXISTS `marca`;
CREATE TABLE IF NOT EXISTS `marca` (
  `idMarca` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idMarca`)
) AUTO_INCREMENT=10;

-- Volcando estructura para tabla malagamusic.familia
DROP TABLE IF EXISTS `familia`;
CREATE TABLE IF NOT EXISTS `familia` (
  `idFamilia` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFamilia`)
) AUTO_INCREMENT=4;

-- Volcando estructura para tabla malagamusic.clase
DROP TABLE IF EXISTS `clase`;
CREATE TABLE IF NOT EXISTS `clase` (
  `idClase` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `idFamilia` int NOT NULL,
  PRIMARY KEY (`idClase`),
  FOREIGN KEY (`idFamilia`) REFERENCES `familia`(`idFamilia`)
);

-- Volcando estructura para tabla malagamusic.instrumentos
DROP TABLE IF EXISTS `instrumentos`;
CREATE TABLE IF NOT EXISTS `instrumentos` (
  `idInstrumento` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `precio` double NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `idClase` int NOT NULL,
  `idMarca` int NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`idInstrumento`),
  FOREIGN KEY (`idClase`) REFERENCES `clase` (`idClase`),
  FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`)
) AUTO_INCREMENT=9;

-- Trigger para asegurar el stock en 0
DELIMITER //
CREATE TRIGGER `asegurar_stock_no_menos_cero` BEFORE UPDATE ON `instrumentos`
FOR EACH ROW
BEGIN
    IF NEW.`stock` < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El stock no puede ser menor que 0.';
    END IF;
END;
//
DELIMITER ;

-- Volcando datos para la tabla malagamusic.marca:
DELETE FROM `marca`;
INSERT INTO `marca` (`idMarca`, `nombre`) VALUES
	(1, 'Buffet Crampon'),
	(2, 'Bach'),
	(3, 'Consolat de Mar'),
	(4, 'Yamaha'),
	(5, 'Pearl'),
	(6, 'Stentor'),
	(7, 'Eastman Strings'),
	(8, 'Höfner'),
	(9, 'Selmer Paris');
    
-- Volcando datos para la tabla malagamusic.familia:
DELETE FROM `familia`;
INSERT INTO `familia` (`idFamilia`, `nombre`) VALUES
	(1, 'Viento-Madera'),
	(2, 'Viento-Metal'),
	(3, 'Arco');
    
-- Volcando datos para la tabla malagamusic.marca:
DELETE FROM `clase`;
INSERT INTO `clase` (`idClase`, `idFamilia`, `nombre`) VALUES
	(1, 1, 'Saxofones'),
	(2, 1, 'Clarinetes'),
	(3, 2, 'Trombones'),
	(4, 2, 'Trompetas'),
	(5, 3, 'Violines'),
	(6, 3, 'Violonchelos'),
	(7, 1, 'Oboes'),
	(8, 2, 'Trompas'),
	(9, 3, 'Contrabajos');

-- Volcando datos para la tabla malagamusic.instrumentos:
DELETE FROM `instrumentos`;
INSERT INTO `instrumentos` (`idInstrumento`, `nombre`, `precio`, `descripcion`, `idClase`, `idMarca`, `stock`) VALUES
	(1, 'Buffet Tradition Sib', 5205.00, 'Clarinete en Sib Buffet Tradition', 2, 1, 12),
	(2, 'Buffet RC La', 3739.00, 'Clarinete en La Buffet RC', 2, 1, 0),
	(3, 'Bach TR450 Sib', 929.90, 'Trompeta en Sib Bach TR450 Lacada', 4, 4, 11),
	(4, 'Consolat de Mar TV-700', 229.90, 'Trombon en Sib Consolat de Mar TV-700', 3, 3, 20),
	(5, 'Yamaha YTR-5335GSII Sib', 1379.00, 'Trompeta en Sib Yamaha YTR-5335GSII', 4, 4, 4),
	(6, 'Yamaha YAS-480', 1309.00, 'Saxo Alto Yamaha YAS-480', 1, 1, 2),
	(7, 'Stentor Master 4/4', 829.90, 'Violin Stentor Master 4/4', 5, 6, 1),
	(8, 'Eastman VC150', 1389.00, 'Violonchelo Eastman VC150', 6, 7, 14),
	(9, 'Höfner Serie AS-190-C 3/4', 1489.00, 'Violonchelo Alfred Stingl-Höfner Serie AS-190-C 3/4', 6, 8, 28);

--
-- Table structure for table `usuarios`
--
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(45) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `rol` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`)
) AUTO_INCREMENT=2;

-- Tabla usuarios (datos)
DELETE FROM `usuarios`;
INSERT INTO `usuarios` (`idUsuario`, `usuario`, `password`, `rol`) VALUES
	('1', 'itorres', 'd398b29d3dbbb9bf201d4c7e1c19ff9d43c15fd45a0cec46fbe9885ec3f6e97f', 'Admin'
);

-- Tabla pedidos (estructura)
DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE IF NOT EXISTS `pedidos` (
    `idPedido` int PRIMARY KEY AUTO_INCREMENT,
    `idUsuario` int,
    `listaCompra` varchar(300) NOT NULL,
    `fecha` date,
    FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`idUsuario`)
);