DROP TABLE APP.MENU;

CREATE TABLE APP.MENU(
	MENU_ID 	INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	DISP_NO		INT NOT NULL,
	NAME    	VARCHAR(200),
	URL     	VARCHAR(200),
	CONTENT 	VARCHAR(2000)
);

INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(1,'REST �Ł@XML ���擾����T���v��','/sample_rest_service/city/all','WEB�T�[�r�X(REST)���Ăяo���AXML���擾���܂�');
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(2,'Viewer(�{��)��ʂ̃T���v��','/sample_spring_mvc/viewer.html','WEB�T�[�r�X(REST)���Ăяo���A�擾����XML�ŉ�ʂ��\�z���܂�');
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(3,'Retriever(����)��ʂ̃T���v��','/sample_spring_mvc/retriever.html','');
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(4,'���O�C����ʂ̃T���v��','/sample_spring_mvc/login.html','');
