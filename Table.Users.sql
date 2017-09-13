CREATE TABLE Users(
DeviceID NVARCHAR(MAX),
PhoneNumber NVARCHAR(MAX),
CurrentLongitude FLOAT,
CurrentLatitude FLOAT,
ZoomIn FLOAT,
CreateDate DATETIME NOT NULL,
ModifiedDate DATETIME DEFAULT GETDATE(),
DeletedDate DATETIME,
UniqueIdentity UNIQUEIDENTIFIER DEFAULT NEWID(),
ID INT IDENTITY(1,1) NOT NULL,
)

