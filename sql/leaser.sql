--------------------------------------------------------
--  文件已创建 - 星期六-一月-12-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table LEASER
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."LEASER" 
   (	"ID" NUMBER(11,0), 
	"AID" NUMBER(11,0), 
	"ID_NUMBER" VARCHAR2(255 BYTE), 
	"NAME" VARCHAR2(255 BYTE), 
	"PHONE_NUMBER" VARCHAR2(255 BYTE), 
	"GENDER" VARCHAR2(255 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
 

   COMMENT ON COLUMN "SYSTEM"."LEASER"."AID" IS '账号id';
 
   COMMENT ON COLUMN "SYSTEM"."LEASER"."ID_NUMBER" IS '身份证号码';
 
   COMMENT ON COLUMN "SYSTEM"."LEASER"."NAME" IS '姓名';
 
   COMMENT ON COLUMN "SYSTEM"."LEASER"."PHONE_NUMBER" IS '电话';
 
   COMMENT ON COLUMN "SYSTEM"."LEASER"."GENDER" IS '性别';
REM INSERTING into SYSTEM.LEASER
SET DEFINE OFF;
Insert into SYSTEM.LEASER (ID,AID,ID_NUMBER,NAME,PHONE_NUMBER,GENDER) values (1,1,'45093323234234234','赵石头','15577780888','男');
Insert into SYSTEM.LEASER (ID,AID,ID_NUMBER,NAME,PHONE_NUMBER,GENDER) values (21,6,'45068888888333332','zhao','9999999','女');
--------------------------------------------------------
--  DDL for Index LEASER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."LEASER_PK" ON "SYSTEM"."LEASER" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger LEASER_ID_AUTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."LEASER_ID_AUTO" 
   before insert on "SYSTEM"."LEASER" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select ID_AUTO.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "SYSTEM"."LEASER_ID_AUTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger LEASER
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."LEASER" 
   before insert on "SYSTEM"."LEASER" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select ID_AUTO.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "SYSTEM"."LEASER" ENABLE;
--------------------------------------------------------
--  Constraints for Table LEASER
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."LEASER" ADD CONSTRAINT "LEASER_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
 
  ALTER TABLE "SYSTEM"."LEASER" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "SYSTEM"."LEASER" MODIFY ("AID" NOT NULL ENABLE);
