DROP TABLE comm_code;
CREATE TABLE comm_code (
   comm_cd CHAR(4) NOT NULL, 
   comm_nm VARCHAR2(100) NOT NULL, 
   comm_parent CHAR(4), 
   comm_ord NUMBER,
   CONSTRAINTS pk_comm_code PRIMARY KEY comm_code (comm_cd)
);

COMMENT ON TABLE comm_code  IS '공통코드 테이블';
COMMENT ON COLUMN comm_code.comm_cd IS '코드';
COMMENT ON COLUMN comm_code.comm_nm IS '코드명';
COMMENT ON COLUMN comm_code.comm_parent IS '부모 코드';
COMMENT ON COLUMN comm_code.comm_ord IS '순번';

INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB00','직업',null,0);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB01','주부','JB00',1);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB02','은행원','JB00',2);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB03','공무원','JB00',3);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB04','축산업','JB00',4);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB05','회사원','JB00',5);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB06','농업','JB00',6);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB07','자영업','JB00',7);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB08','학생','JB00',8);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('JB09','교사','JB00',9);

INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB00','취미',null,0);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB01','서예','HB00',1);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB02','장기','HB00',2);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB03','수영','HB00',3);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB04','독서','HB00',4);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB05','당구','HB00',5);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB06','바둑','HB00',6);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB07','볼링','HB00',7);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB08','스키','HB00',8);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB09','만화','HB00',9);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB10','낚시','HB00',10);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB11','영화감상','HB00',11);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB12','등산','HB00',12);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB13','개그','HB00',13);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('HB14','카레이싱','HB00',14);

INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('BC00','글분류',null,0);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('BC01','프로그램','BC00',1);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('BC02','웹','BC00',2);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('BC03','사는 이야기','BC00',3);
INSERT INTO comm_code (comm_cd,comm_nm,comm_parent,comm_ord) values ('BC04','취업','BC00',4);


