-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 11 Nov 2025 pada 10.51
-- Versi Server: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbo_2310010362`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_pemesanan`
--

CREATE TABLE `detail_pemesanan` (
  `ID_Detail_Pemesanan` varchar(11) NOT NULL,
  `Harga_Produk` decimal(15,2) NOT NULL,
  `Jumlah_Pemesanan` int(11) NOT NULL,
  `Status_Detail_Pemesanan` varchar(50) DEFAULT NULL COMMENT 'e.g., Diproses, Dikirim, Selesai',
  `ID_Pemesanan` varchar(10) NOT NULL,
  `ID_Produk` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detail_pemesanan`
--

INSERT INTO `detail_pemesanan` (`ID_Detail_Pemesanan`, `Harga_Produk`, `Jumlah_Pemesanan`, `Status_Detail_Pemesanan`, `ID_Pemesanan`, `ID_Produk`) VALUES
('d01', '10000000.00', 2, 'Processing', 'K01', 'B01'),
('d02', '10000000.00', 7, 'Processing', 'K01', 'B01'),
('d03', '10000000.00', 9, 'Pending', 'K01', 'B01');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_produk`
--

CREATE TABLE `jenis_produk` (
  `ID_Produk` varchar(10) NOT NULL,
  `Jenis_Produk` varchar(100) NOT NULL,
  `Ukuran` varchar(50) DEFAULT NULL COMMENT 'e.g., Ukuran Mesh, Kalori',
  `Harga` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jenis_produk`
--

INSERT INTO `jenis_produk` (`ID_Produk`, `Jenis_Produk`, `Ukuran`, `Harga`) VALUES
('B01', 'Batu Alam', '1 Ton', '10000000.00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `ID_Karyawan` varchar(10) NOT NULL,
  `Nama_Karyawan` varchar(100) NOT NULL,
  `Jabatan` varchar(50) DEFAULT NULL,
  `Alamat_Karyawan` text,
  `No_telp_Karyawan` varchar(15) DEFAULT NULL,
  `Password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`ID_Karyawan`, `Nama_Karyawan`, `Jabatan`, `Alamat_Karyawan`, `No_telp_Karyawan`, `Password`) VALUES
('K01', 'Riska Hidayah', 'Staf', 'Sutoyo ', '0824689', '12345');

-- --------------------------------------------------------

--
-- Struktur dari tabel `konsumen`
--

CREATE TABLE `konsumen` (
  `ID_Konsumen` varchar(10) NOT NULL,
  `Nama_Perusahaan` varchar(150) NOT NULL,
  `Alamat_Perusahaan` text,
  `No_telp_Perusahaan` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `konsumen`
--

INSERT INTO `konsumen` (`ID_Konsumen`, `Nama_Perusahaan`, `Alamat_Perusahaan`, `No_telp_Perusahaan`) VALUES
('K1', 'PT. Catur Sentosa Adiprana', 'JL Gubernur Subarjo', '052345678');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemesanan`
--

CREATE TABLE `pemesanan` (
  `ID_Pemesanan` varchar(10) NOT NULL,
  `Tanggal_Pemesanan` date NOT NULL,
  `Notes` text,
  `ID_Konsumen` varchar(10) NOT NULL,
  `ID_Karyawan` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pemesanan`
--

INSERT INTO `pemesanan` (`ID_Pemesanan`, `Tanggal_Pemesanan`, `Notes`, `ID_Konsumen`, `ID_Karyawan`) VALUES
('K01', '2025-11-10', 'g7eruguug', 'K1', 'K01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_pemesanan`
--
ALTER TABLE `detail_pemesanan`
  ADD PRIMARY KEY (`ID_Detail_Pemesanan`),
  ADD KEY `ID_Pemesanan` (`ID_Pemesanan`),
  ADD KEY `ID_Produk` (`ID_Produk`);

--
-- Indexes for table `jenis_produk`
--
ALTER TABLE `jenis_produk`
  ADD PRIMARY KEY (`ID_Produk`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`ID_Karyawan`);

--
-- Indexes for table `konsumen`
--
ALTER TABLE `konsumen`
  ADD PRIMARY KEY (`ID_Konsumen`);

--
-- Indexes for table `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`ID_Pemesanan`),
  ADD KEY `ID_Konsumen` (`ID_Konsumen`),
  ADD KEY `ID_Karyawan` (`ID_Karyawan`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_pemesanan`
--
ALTER TABLE `detail_pemesanan`
  ADD CONSTRAINT `detail_pemesanan_ibfk_1` FOREIGN KEY (`ID_Pemesanan`) REFERENCES `pemesanan` (`ID_Pemesanan`),
  ADD CONSTRAINT `detail_pemesanan_ibfk_2` FOREIGN KEY (`ID_Produk`) REFERENCES `jenis_produk` (`ID_Produk`);

--
-- Ketidakleluasaan untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD CONSTRAINT `pemesanan_ibfk_1` FOREIGN KEY (`ID_Konsumen`) REFERENCES `konsumen` (`ID_Konsumen`),
  ADD CONSTRAINT `pemesanan_ibfk_2` FOREIGN KEY (`ID_Karyawan`) REFERENCES `karyawan` (`ID_Karyawan`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
