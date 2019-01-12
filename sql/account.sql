--------------------------------------------------------
--  文件已创建 - 星期六-一月-12-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ACCOUNT
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."ACCOUNT" 
   (	"AID" NUMBER(11,0), 
	"ROLE" VARCHAR2(20 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
 

   COMMENT ON COLUMN "SYSTEM"."ACCOUNT"."AID" IS '账号';
 
   COMMENT ON COLUMN "SYSTEM"."ACCOUNT"."ROLE" IS '角色';
REM INSERTING into SYSTEM.ACCOUNT
SET DEFINE OFF;
Insert into SYSTEM.ACCOUNT (AID,ROLE) values (1,'Leaser');
Insert into SYSTEM.ACCOUNT (AID,ROLE) values (2,'Customers');
Insert into SYSTEM.ACCOUNT (AID,ROLE) values (3,'CompanyAdmin');
Insert into SYSTEM.ACCOUNT (AID,ROLE) values (4,'CompanySalesman');
Insert into SYSTEM.ACCOUNT (AID,ROLE) values (22,'Customers');
Insert into SYSTEM.ACCOUNT (AID,ROLE) values (6,'Leaser');
--------------------------------------------------------
--  DDL for Index ACCOUNT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."ACCOUNT_PK" ON "SYSTEM"."ACCOUNT" ("AID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger TRIG_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."TRIG_ID_AUTO" 
   before insert on "SYSTEM"."ACCOUNT" 
   for each row 
begin  
   if inserting then 
      if :NEW."AID" is null then 
         select ID_AUTO.nextval into :NEW."AID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "SYSTEM"."TRIG_ID_AUTO" ENABLE;
--------------------------------------------------------
--  Constraints for Table ACCOUNT
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."ACCOUNT" ADD CONSTRAINT "ACCOUNT_PK" PRIMARY KEY ("AID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
 
  ALTER TABLE "SYSTEM"."ACCOUNT" MODIFY ("ROLE" NOT NULL ENABLE);
