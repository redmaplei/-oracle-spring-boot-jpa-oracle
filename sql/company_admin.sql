--------------------------------------------------------
--  �ļ��Ѵ��� - ������-һ��-12-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table COMPANY_ADMIN
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."COMPANY_ADMIN" 
   (	"ID" NUMBER(19,0), 
	"AID" NUMBER(19,0), 
	"COMPANY_NAME" VARCHAR2(255 CHAR), 
	"SALESMAN_NAME" VARCHAR2(255 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
 

   COMMENT ON COLUMN "SYSTEM"."COMPANY_ADMIN"."AID" IS '�˺�id';
 
   COMMENT ON COLUMN "SYSTEM"."COMPANY_ADMIN"."COMPANY_NAME" IS '���޹�˾��';
 
   COMMENT ON COLUMN "SYSTEM"."COMPANY_ADMIN"."SALESMAN_NAME" IS '����Ա';
REM INSERTING into SYSTEM.COMPANY_ADMIN
SET DEFINE OFF;
Insert into SYSTEM.COMPANY_ADMIN (ID,AID,COMPANY_NAME,SALESMAN_NAME) values (1,3,'�ƴ��⳵','�ֶ�');
--------------------------------------------------------
--  DDL for Index SYS_C0010833
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."SYS_C0010833" ON "SYSTEM"."COMPANY_ADMIN" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger COMPANY_ADMIN
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "SYSTEM"."COMPANY_ADMIN" 
   before insert on "SYSTEM"."COMPANY_ADMIN" 
   for each row 
begin  
   if inserting then 
      if :NEW."ID" is null then 
         select ID_AUTO.nextval into :NEW."ID" from dual; 
      end if; 
   end if; 
end;

/
ALTER TRIGGER "SYSTEM"."COMPANY_ADMIN" ENABLE;
--------------------------------------------------------
--  Constraints for Table COMPANY_ADMIN
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."COMPANY_ADMIN" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "SYSTEM"."COMPANY_ADMIN" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
