IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND OBJECT_ID = OBJECT_ID('dbo.DEMO_IMPORT_CUSTOMER_DATA'))
   exec('CREATE PROCEDURE [dbo].[DEMO_IMPORT_CUSTOMER_DATA] AS BEGIN SET NOCOUNT ON; END')
GO
ALTER PROCEDURE [dbo].[DEMO_IMPORT_CUSTOMER_DATA] (
    @ImportFile VARCHAR(256)
	,@Count Int OUTPUT
)
AS 


BEGIN
	SET NOCOUNT ON;
	DECLARE
            @LocalError     INT,
            @ErrorMessage   VARCHAR(4000),
            @SQL_BULK VARCHAR(MAX)
	BEGIN TRY
		IF OBJECT_ID('DEMO_CUSTOMER_TEMP') IS NOT NULL
		    DROP TABLE DEMO_CUSTOMER_TEMP;

		CREATE TABLE DEMO_CUSTOMER_TEMP (
			ID varchar(10),
			BIRTHDAY varchar(8),
		);

		DECLARE
		@ErrorFile as varchar(150) = 'D:\data\PCL_Import_DM\DB_Import_logs_DEMO_',
		@TodayDate as varchar(40) = CONVERT(varchar(10), GETDATE(), 112),
		@TodayHour as varchar(40) = DATEPART(hh, GETDATE()),
		@TodayMinu as varchar(40) = DATEPART(mi, GETDATE()),
		@TodaySecd as varchar(40) = DATEPART(second, GETDATE());
		
		SET @ErrorFile += @TodayDate + '_' + @TodayHour + @TodayMinu + '_' +@TodaySecd + '.txt';
		SET @SQL_BULK = 'BULK INSERT DBO.DEMO_CUSTOMER_TEMP FROM ''' + @ImportFile + ''' WITH (FIELDTERMINATOR = ''|'', ROWTERMINATOR = ''\n''
					, MAXERRORS = 100, BATCHSIZE = 10000, TABLOCK ,CODEPAGE = ''950'', ERRORFILE = ''' + @ErrorFile + ''')';
		
		EXEC(@SQL_BULK);

		IF OBJECT_ID('DEMO_CUSTOMER') IS NOT NULL
		    DROP TABLE DEMO_CUSTOMER;

		SELECT
			 CAST(REPLACE(NEWID(), '-', '') AS VARCHAR(32)) AS OID
			,CAST(LTRIM(RTRIM(ID)) AS VARCHAR(10)) AS ID
			,CAST(LTRIM(RTRIM(BIRTHDAY)) AS VARCHAR(8)) AS BIRTHDAY
		INTO DEMO_CUSTOMER
		FROM DEMO_CUSTOMER_TEMP;

		ALTER TABLE DEMO_CUSTOMER ALTER COLUMN OID VARCHAR(32) NOT NULL;

		ALTER TABLE DEMO_CUSTOMER
		ADD CONSTRAINT P_DEMO_CUSTOMER_TEMP PRIMARY KEY CLUSTERED 
		(
			OID ASC
		)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY];

		SELECT COUNT(*) FROM DEMO_CUSTOMER;
		SELECT @Count = COUNT(*) FROM DEMO_CUSTOMER;
	END TRY
	BEGIN CATCH
        SELECT  @LocalError     =   ERROR_NUMBER(),
                @ErrorMessage   =   ERROR_MESSAGE()
        RAISERROR ('IMPORT_DEMO_CUSTOMER_DATA: %d: %s', 16, 1, @LocalError, @ErrorMessage) ;
        RETURN(0)
	END CATCH
END