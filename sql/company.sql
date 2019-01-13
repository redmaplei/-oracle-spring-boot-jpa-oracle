--------------------------------------------------------
--  文件已创建 - 星期日-一月-13-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table COMPANY
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."COMPANY" 
   (	"ID" NUMBER, 
	"COMPANY_NAME" VARCHAR2(255 BYTE), 
	"PHONE_NUMBER" VARCHAR2(255 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
 

   COMMENT ON COLUMN "SYSTEM"."COMPANY"."COMPANY_NAME" IS '租赁公司名';
 
   COMMENT ON COLUMN "SYSTEM"."COMPANY"."PHONE_NUMBER" IS '电话';
REM INSERTING into SYSTEM.COMPANY
SET DEFINE OFF;
Insert into SYSTEM.COMPANY (ID,COMPANY_NAME,PHONE_NUMBER) values (1,'科大租车','12345678');
--------------------------------------------------------
--  DDL for Index COMPANY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."COMPANY_PK" ON "SYSTEM"."COMPANY" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger COMPANY_ID_AUDO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."COMPANY_ID_AUDO" 
   before insert on "SYSTEM"."COMPANY" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select ID_AUTO.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "SYSTEM"."COMPANY_ID_AUDO" ENABLE;
--------------------------------------------------------
--  Constraints for Table COMPANY
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."COMPANY" ADD CONSTRAINT "COMPANY_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
 
  ALTER TABLE "SYSTEM"."COMPANY" MODIFY ("COMPANY_NAME" NOT NULL ENABLE);
