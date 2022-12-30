DROP DATABASE IF EXISTS timesheetdb;
CREATE DATABASE timesheetdb;

CREATE USER IF NOT EXISTS 'stock'@'localhost' IDENTIFIED BY 'check';
CREATE USER IF NOT EXISTS 'stock'@'%' IDENTIFIED BY 'check';
GRANT ALL ON timesheetdb.* TO 'stock'@'localhost';
GRANT ALL ON timesheetdb.* TO 'stock'@'%';

USE timesheetdb;

-- Create table Employees --
DROP TABLE IF EXISTS Employees;
CREATE TABLE Employees (
    `EmployeeNumber` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `FirstName` VARCHAR(50) NOT NULL,
    `LastName` VARCHAR(50) NOT NULL,
    `UserName` VARCHAR(50) NOT NULL UNIQUE,
    `Password` VARCHAR(50) NOT NULL,
    `IsAdmin` BIT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (EmployeeNumber)
);
-- Insert data in Employee table --
INSERT INTO Employees VALUES
    (1, 'Dongchan', 'Koh', 'kohdc', '1723', 1),
    (2, 'Juyeong', 'Park', 'parkjy', '1234', 0);

-- Create table Timesheet --
DROP TABLE IF EXISTS Timesheets;
CREATE TABLE Timesheets (
    `TimesheetID` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `EmployeeNumber` TINYINT UNSIGNED NOT NULL,
    `WeekEndDate` DATE NOT NULL,
    PRIMARY KEY (TimesheetID),
    FOREIGN KEY (EmployeeNumber) REFERENCES Employees (EmployeeNumber) ON DELETE CASCADE,
    UNIQUE (EmployeeNumber, WeekEndDate)
);
-- Insert data in Timesheet table --
INSERT INTO Timesheets VALUES
    (1, 1, '2022-12-24'),
    (2, 1, '2022-12-31'),
    (3, 2, '2022-12-24'),
    (4, 2, '2022-12-31');

-- Create table TimesheetRow --
DROP TABLE IF EXISTS TimesheetRows;
CREATE TABLE TimesheetRows (
    `TimesheetRowID` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `TimesheetID` TINYINT UNSIGNED NOT NULL,
    `ProjectTitle` VARCHAR(50) NOT NULL,
    `SatHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `SunHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `MonHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `TueHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `WedHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `ThuHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `FriHours` FLOAT(3,1) UNSIGNED NOT NULL DEFAULT 0,
    `Notes` VARCHAR(100),
    PRIMARY KEY (TimesheetRowID),
    FOREIGN KEY (TimesheetID) REFERENCES Timesheets (TimesheetID) ON DELETE CASCADE,
    UNIQUE (TimesheetID, ProjectTitle),
    CONSTRAINT Check_SatHours CHECK (SatHours BETWEEN 0.0 AND 24.0),
    CONSTRAINT Check_SunHours CHECK (SunHours BETWEEN 0.0 AND 24.0),
    CONSTRAINT Check_MonHours CHECK (MonHours BETWEEN 0.0 AND 24.0),
    CONSTRAINT Check_TueHours CHECK (TueHours BETWEEN 0.0 AND 24.0),
    CONSTRAINT Check_WedHours CHECK (WedHours BETWEEN 0.0 AND 24.0),
    CONSTRAINT Check_ThuHours CHECK (ThuHours BETWEEN 0.0 AND 24.0),
    CONSTRAINT Check_FriHours CHECK (FriHours BETWEEN 0.0 AND 24.0)
);
-- Insert data in TimesheetRows table --
INSERT INTO TimesheetRows VALUES
    (1, 1, 'comp-3522', 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 'CPP'),
    (2, 1, 'comp-3042', 1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 'STAT'),
    (3, 2, 'comp-3760', 1.0, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 'DATACOMM'),
    (4, 2, 'comp-3910', 3.3, 3.3, 3.3, 3.3, 3.3, 3.3, 3.3, 'FULLSTACK');

-- Create table Token --
DROP TABLE IF EXISTS Tokens;
CREATE TABLE Tokens (
    `TokenID` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `Token` VARCHAR(50) NOT NULL UNIQUE,
    `EmployeeNumber` TINYINT UNSIGNED NOT NULL,
    `ExpiryDateTime` DATETIME NOT NULL,
    PRIMARY KEY (TokenID),
    FOREIGN KEY (EmployeeNumber) REFERENCES Employees (EmployeeNumber) ON DELETE CASCADE
);