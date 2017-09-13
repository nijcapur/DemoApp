CREATE TABLE TrackList(
TrackerName NVARCHAR(MAX),
SourceLongitude FLOAT,
SourceLatitude FLOAT,
DestinationLongitude FLOAT,
DestinationLatitude FLOAT,
UserId INT NOT NULL,
CreateDate DATETIME NOT NULL,
ModifiedDate DATETIME DEFAULT GETDATE(),
DeletedDate DATETIME,
UniqueIdentity UNIQUEIDENTIFIER DEFAULT NEWID(),
ID INT IDENTITY(1,1) NOT NULL,
)


