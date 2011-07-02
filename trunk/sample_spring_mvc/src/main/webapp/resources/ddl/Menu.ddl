DROP TABLE APP.MENU;

CREATE TABLE APP.MENU(
	MENU_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	NAME    VARCHAR(200),
	URL     VARCHAR(200),
	CONTENT VARCHAR(2000)
);

INSERT INTO APP.MENU (NAME,URL,CONTENT) VALUES('REST �Ł@XML ���擾����T���v��','/sample_rest_service/city/all','WEB�T�[�r�X(REST)���Ăяo���AXML���擾���܂�');
INSERT INTO APP.MENU (NAME,URL,CONTENT) VALUES('Viewer(�{��)��ʂ̃T���v��','/sample_spring_mvc/viewer.html','WEB�T�[�r�X(REST)���Ăяo���A�擾����XML�ŉ�ʂ��\�z���܂�');
INSERT INTO APP.MENU (NAME,URL,CONTENT) VALUES('Retriever(����)��ʂ̃T���v��','/sample_spring_mvc/retriever.html','');
