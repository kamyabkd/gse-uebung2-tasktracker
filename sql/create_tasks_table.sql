-- Optional: Datenbank auswählen/erstellen
-- CREATE DATABASE TaskTrackerDB;
-- GO
-- USE TaskTrackerDB;
-- GO

-- Falls Tabelle schon existiert, zuerst löschen
IF OBJECT_ID('dbo.Tasks', 'U') IS NOT NULL
    DROP TABLE dbo.Tasks;
GO

-- Tabelle für Aufgaben
CREATE TABLE dbo.Tasks (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Title NVARCHAR(200) NOT NULL,
    Description NVARCHAR(1000) NULL,
    Status NVARCHAR(20) NOT NULL
);
GO
