-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 29, 2021 at 12:05 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id16882321_cafex`
--

-- --------------------------------------------------------

--
-- Table structure for table `cabang`
--

CREATE TABLE `cabang` (
  `id_cabang` int(11) NOT NULL,
  `nama_cabang` varchar(30) NOT NULL,
  `notelp_cabang` varchar(30) NOT NULL,
  `alamat_cabang` varchar(30) NOT NULL,
  `tanggal_cabang` date NOT NULL,
  `update_cabang` date DEFAULT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cabang`
--

INSERT INTO `cabang` (`id_cabang`, `nama_cabang`, `notelp_cabang`, `alamat_cabang`, `tanggal_cabang`, `update_cabang`, `status`) VALUES
(1, 'Ambarukmo', '12434566', 'Amplas', '2021-05-02', '2021-05-17', 2),
(2, 'Sleman', '333', 'Babarsari', '2021-05-07', '2021-05-17', 2),
(3, 'Jogja', '55555', 'jogja', '2021-05-15', '2021-05-18', 1),
(4, 'Ambarukmo', '12434566', 'Amplas', '2021-05-18', NULL, 1),
(5, 'Mars', '51010405', 'jln mars mars', '2021-05-18', NULL, 1),
(6, 'Paris', '123', 'Paris', '2021-05-18', NULL, 1),
(7, 'Bandung', '23545', 'Bandung', '2021-05-18', '2021-05-19', 1),
(8, 'Jakarta', '123', 'Jakarta', '2021-05-18', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `detailinventori`
--

CREATE TABLE `detailinventori` (
  `id_detailinventori` int(11) NOT NULL,
  `id_inventori` int(11) NOT NULL,
  `id_cabang` int(11) NOT NULL,
  `nama_bahanbaku` varchar(30) NOT NULL,
  `jumlah_bahanbaku` int(11) NOT NULL,
  `harga_bahanbaku` int(11) NOT NULL,
  `exp_bahanbaku` date NOT NULL,
  `tanggal_masuk` date NOT NULL,
  `tanggal_keluar` date DEFAULT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detailinventori`
--

INSERT INTO `detailinventori` (`id_detailinventori`, `id_inventori`, `id_cabang`, `nama_bahanbaku`, `jumlah_bahanbaku`, `harga_bahanbaku`, `exp_bahanbaku`, `tanggal_masuk`, `tanggal_keluar`, `status`) VALUES
(10, 10, 2, 'kopi', 20, 30000, '2021-11-11', '2021-05-25', '0000-00-00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaksi`
--

CREATE TABLE `detailtransaksi` (
  `id_detailtransaksi` int(11) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `id_produk` int(10) NOT NULL,
  `id_cabang` int(10) NOT NULL,
  `nama_user` varchar(30) NOT NULL,
  `jumlah_item` int(10) NOT NULL,
  `harga_subtotal` int(10) NOT NULL,
  `tanggal_buat` date NOT NULL,
  `nama_pembeli` varchar(30) NOT NULL,
  `status` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detailtransaksi`
--

INSERT INTO `detailtransaksi` (`id_detailtransaksi`, `id_transaksi`, `id_produk`, `id_cabang`, `nama_user`, `jumlah_item`, `harga_subtotal`, `tanggal_buat`, `nama_pembeli`, `status`) VALUES
(132, 113, 9, 2, 'owner', 2, 30000, '2021-05-25', 'Niel', 0);

-- --------------------------------------------------------

--
-- Table structure for table `diskon`
--

CREATE TABLE `diskon` (
  `id_diskon` int(11) NOT NULL,
  `nama_diskon` varchar(30) NOT NULL,
  `min_bayar` int(11) NOT NULL,
  `persen_diskon` double NOT NULL,
  `harga_diskon` int(11) NOT NULL,
  `max_diskon` int(11) NOT NULL,
  `exp_diskon` date NOT NULL,
  `tgl_diskon` date NOT NULL,
  `status` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `diskon`
--

INSERT INTO `diskon` (`id_diskon`, `nama_diskon`, `min_bayar`, `persen_diskon`, `harga_diskon`, `max_diskon`, `exp_diskon`, `tgl_diskon`, `status`) VALUES
(1, 'lebaransale', 10000, 0, 1000, 10000, '2021-05-21', '2021-05-13', 2),
(3, 'salmon', 10000, 0.2, 0, 10000, '2021-06-01', '2021-05-15', 2),
(5, 'xx', 1000, 0, 5000, 3000, '2021-11-11', '2021-05-17', 1),
(6, 'salmonkun', 10000, 0.2, 0, 10000, '2021-06-01', '2021-05-16', 1),
(7, 'Niel', 1000, 0, 25000, 20000, '2021-11-11', '2021-05-24', 1),
(8, 'Lein', 10000, 0.2, 0, 20000, '2021-10-12', '2021-05-16', 1),
(9, 'OnSale', 1000, 0.1, 0, 10000, '2021-11-12', '2021-05-16', 1),
(10, 'Hen', 10000, 0.2, 0, 10000, '2021-10-08', '2021-05-21', 1);

-- --------------------------------------------------------

--
-- Table structure for table `inventori`
--

CREATE TABLE `inventori` (
  `id_inventori` int(11) NOT NULL,
  `id_cabang` int(11) NOT NULL,
  `nama_bahanbaku` varchar(30) NOT NULL,
  `tanggal_buat` date NOT NULL,
  `status` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inventori`
--

INSERT INTO `inventori` (`id_inventori`, `id_cabang`, `nama_bahanbaku`, `tanggal_buat`, `status`) VALUES
(10, 2, 'Kopi', '2021-05-25', 1),
(11, 2, 'Gula', '2021-05-25', 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id_produk` int(4) NOT NULL,
  `nama_produk` varchar(250) NOT NULL,
  `harga_produk` int(10) NOT NULL,
  `biaya_produk` int(11) NOT NULL,
  `tanggal_produk` date NOT NULL,
  `kategori_produk` varchar(45) NOT NULL,
  `foto_produk` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id_produk`, `nama_produk`, `harga_produk`, `biaya_produk`, `tanggal_produk`, `kategori_produk`, `foto_produk`) VALUES
(7, 'Test', 17000, 10000, '2021-05-13', 'Ice', 'IMG-20210513-WA0006.jpg'),
(9, 'Paris Praline', 15000, 10000, '2021-05-05', 'Hot', 'nespresso-recipes-Variations-Paris-Madeleine-with-Lemonade.jpg'),
(11, 'Due Bianco', 30000, 25000, '2021-05-05', 'Hot', 'nespresso-recipes-Due-Bianco.jpg'),
(14, 'Mocca Latte', 40000, 20000, '2021-05-06', 'Hot', 'nespresso-recipes-Mocca-Latte-by-Nespresso.jpg'),
(16, 'Nespresso', 20000, 10000, '2021-05-06', 'Hot', 'UCC-AU-Il-Caffe-Negroni.jpg'),
(17, 'Latte', 10000, 7000, '2021-05-25', 'EC Signature Tea', '86340426b35dd2965053dede09259e46.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `id_cabang` int(11) NOT NULL,
  `id_diskon` int(11) NOT NULL,
  `nama_pembeli` varchar(30) NOT NULL,
  `tanggal` date NOT NULL,
  `total_bayar` varchar(30) NOT NULL,
  `nama_user` varchar(30) NOT NULL,
  `status` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_cabang`, `id_diskon`, `nama_pembeli`, `tanggal`, `total_bayar`, `nama_user`, `status`) VALUES
(113, 2, 6, 'Niel', '2021-05-25', '24000', 'owner', 1);

--
-- Triggers `transaksi`
--
DELIMITER $$
CREATE TRIGGER `update_detailtransaksi` AFTER INSERT ON `transaksi` FOR EACH ROW BEGIN
	UPDATE detailtransaksi set nama_pembeli = NEW.nama_pembeli, status = 0
    WHERE nama_pembeli = '' AND status = 1;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `id_cabang` int(30) NOT NULL,
  `nama_user` varchar(30) NOT NULL,
  `nohp_user` varchar(30) NOT NULL,
  `noktp_user` varchar(30) NOT NULL,
  `email_user` varchar(30) NOT NULL,
  `password_user` varchar(32) NOT NULL,
  `tanggal_user` date DEFAULT NULL,
  `jabatan_user` int(3) NOT NULL,
  `status_user` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `id_cabang`, `nama_user`, `nohp_user`, `noktp_user`, `email_user`, `password_user`, `tanggal_user`, `jabatan_user`, `status_user`) VALUES
(1, 2, 'Goldian', '08234568234', '12334465775', 'goldian.pakpahan5@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 3, 0),
(2, 1, 'owner', '082268237070', '12334465775', 'owner@gmail.com', '3899dcbab79f92af727c2190bbd8abc5', NULL, 3, 1),
(3, 2, 'Andrex', '082268237070', '12334465775', 'andre.damanik0@gmail.com', '1a100d2c0dab19c4430e7d73762b3423', NULL, 3, 0),
(4, 2, 'Andre', '082268237070', '12334465775', 'andre.damanik0@gmail.com', '1a100d2c0dab19c4430e7d73762b3423', NULL, 3, 1),
(5, 2, 'Iwan', '085288404', '54040845', 'iwanjajaj', 'e10adc3949ba59abbe56e057f20f883e', NULL, 3, 1),
(6, 2, 'Iwan', '085288404', '54040845', 'iwanjajaj', 'e10adc3949ba59abbe56e057f20f883e', NULL, 3, 1),
(7, 2, 'granger', '88401040', '840404', 'granger', '827ccb0eea8a706c4c34a16891f84e7b', NULL, 2, 1),
(8, 1, 'Niel', '999', '1234567', 'ylex110@gmail.com', '73882ab1fa529d7273da0db6b49cc4f3', NULL, 2, 1),
(9, 3, 'Doni', '082268237070', '12334465775', 'Doni.Doni@gmail.com', '5b1b68a9abf4d2cd155c81a9225fd158', NULL, 2, 3),
(10, 3, 'Doni', '082268237070', '12334465775', 'Doni.Doni@gmail.com', '5b1b68a9abf4d2cd155c81a9225fd158', NULL, 2, 3),
(11, 4, 'Doni', '082268237070', '12334465775', 'Doni.Doni@gmail.com', '5b1b68a9abf4d2cd155c81a9225fd158', NULL, 2, 3),
(12, 3, 'Doni', '082268237070', '12334465775', 'Doni.Doni@gmail.com', '5b1b68a9abf4d2cd155c81a9225fd158', NULL, 2, 3),
(13, 1, 'Doni', '082268237070', '12334465775', 'Doni.Doni@gmail.com', '5b1b68a9abf4d2cd155c81a9225fd158', NULL, 2, 3),
(14, 5, 'dono', '3131', '3131', 'niel', '670b14728ad9902aecba32e22fa4f6bd', NULL, 3, 1),
(15, 1, 'owner', '082268237070', '12334465775', 'owner@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 1, 1),
(16, 2, 'owner', '082268237070', '12334465775', 'owner@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 1, 1),
(17, 3, 'owner', '082268237070', '12334465775', 'owner@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 1, 1),
(18, 1, 'Ada', '082268237070', '12334465775', 'ada@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 2, 3),
(19, 2, 'Liu', '082268237070', '12334465775', 'liu@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 3, 2),
(20, 5, 'xxxxx', '111111', '111111', 'yyyy@gmail.com', 'bcbe3365e6ac95ea2c0343a2395834dd', NULL, 3, 0),
(21, 5, 'owner', '1234435656', '49574560968', 'ownerowner@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 1, 1),
(22, 5, 'owner', '1234435656', '49574560968', 'ownerowner@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 1, 1),
(23, 8, 'owner', '1234435656', '49574560968', 'ownerowner@gmail.com', '96e79218965eb72c92a549dd5a330112', NULL, 1, 1),
(24, 5, 'sjsjs', '9797', '9794', 'jdjd', '21c42fccecd856a413c0f5a8b699b69d', '2021-05-23', 2, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cabang`
--
ALTER TABLE `cabang`
  ADD PRIMARY KEY (`id_cabang`);

--
-- Indexes for table `detailinventori`
--
ALTER TABLE `detailinventori`
  ADD PRIMARY KEY (`id_detailinventori`);

--
-- Indexes for table `detailtransaksi`
--
ALTER TABLE `detailtransaksi`
  ADD PRIMARY KEY (`id_detailtransaksi`);

--
-- Indexes for table `diskon`
--
ALTER TABLE `diskon`
  ADD PRIMARY KEY (`id_diskon`),
  ADD UNIQUE KEY `nama_diskon` (`nama_diskon`);

--
-- Indexes for table `inventori`
--
ALTER TABLE `inventori`
  ADD PRIMARY KEY (`id_inventori`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cabang`
--
ALTER TABLE `cabang`
  MODIFY `id_cabang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `detailinventori`
--
ALTER TABLE `detailinventori`
  MODIFY `id_detailinventori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `detailtransaksi`
--
ALTER TABLE `detailtransaksi`
  MODIFY `id_detailtransaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=133;

--
-- AUTO_INCREMENT for table `diskon`
--
ALTER TABLE `diskon`
  MODIFY `id_diskon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `inventori`
--
ALTER TABLE `inventori`
  MODIFY `id_inventori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id_produk` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=114;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
