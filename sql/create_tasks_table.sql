-- Optional: Datenbank auswählen/erstellen
-- CREATE DATABASE TaskTrackerDB;
-- GO
-- USE TaskTrackerDB;
-- GO

IF OBJECT_ID('dbo.Tasks', 'U') IS NOT NULL
    DROP TABLE dbo.Tasks;
GO

CREATE TABLE dbo.Tasks (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Title NVARCHAR(200) NOT NULL,
    Description NVARCHAR(1000) NULL,
    Status NVARCHAR(20) NOT NULL
);
GO
