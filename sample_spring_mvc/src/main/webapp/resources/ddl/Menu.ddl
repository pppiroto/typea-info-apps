DROP TABLE APP.MENU;

CREATE TABLE APP.MENU(
	MENU_ID 	INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	DISP_NO		INT NOT NULL,
	NAME    	VARCHAR(200),
	URL     	VARCHAR(200),
	CONTENT 	VARCHAR(2000)
);

DELETE FROM APP.MENU;
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(1,'REST で　XML を取得するサンプル','/sample_rest_service/city/all','WEBサービス(REST)を呼び出し、XMLを取得します');
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(2,'Viewer(閲覧)画面のサンプル','/sample_spring_mvc/viewer.html','WEBサービス(REST)を呼び出し、取得したXMLで画面を構築します');
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(3,'Retriever(検索)画面のサンプル','/sample_spring_mvc/retriever.html','');
INSERT INTO APP.MENU (DISP_NO,NAME,URL,CONTENT) VALUES(4,'Editor(編集)画面のサンプル','/sample_spring_mvc/editor.html','');
