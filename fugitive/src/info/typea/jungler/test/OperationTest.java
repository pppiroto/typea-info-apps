package info.typea.jungler.test;

import info.typea.jungler.operation.BrowseNodeLookupOperation;
import info.typea.jungler.operation.HelpOperation;
import info.typea.jungler.operation.ItemSearchOperation;
import info.typea.jungler.operation.BrowseNodeLookupOperation.BROWSENODELOOKUP_RESPONSEGROUP;
import info.typea.jungler.operation.ItemSearchOperation.ITEMSEARCH_RESPONSEGROUP;
import info.typea.jungler.type.SearchIndexType;

public class OperationTest {
	public static void main(String[] args) {
		OperationTest me = new OperationTest();
		int id = 2;
		switch(id) {
		case 0:
			me.helpTest();
			break;
		case 1:
			me.browseNodeLookuupTest();
			break;
		case 2:
			me.itemSearchOperationTest();
			break;
		case 3:
			me.itemSearchTest();
			break;
		}
	}
	public void helpTest() {
		HelpOperation op = new HelpOperation();
		op.setAbout("ItemSearch");
		op.setHelpType("Operation");
		System.out.println(op.toRestRequest());
	}
	public void browseNodeLookuupTest() {
		/*
			和書:465610
			洋書:573662
			エレクトロニクス:3210991
			ホーム＆キッチン:3839151
			ポピュラー音楽:562032
			クラシック:701040
			DVD:562002
			ビデオ:561972
			ソフトウェア:637630
			ゲーム:637872
			おもちゃ＆ホビー:1329953
		*/
		BrowseNodeLookupOperation op = new BrowseNodeLookupOperation();
		op.setBrowseNodeId("13602871");
		
		System.out.println(op.toRestRequest(true));
	}
	public void itemSearchOperationTest() {
		ItemSearchOperation op = new ItemSearchOperation();
		op.setSearchIndex(SearchIndexType.Books);
		//op.setKeywords("数学");
		op.setResponseGroup(ITEMSEARCH_RESPONSEGROUP.ItemIds);
		op.setBrowseNode("13602871");
		System.out.println(op.toRestRequest(true));
	}
	public void itemSearchTest() {
		ItemSearchOperation op = new ItemSearchOperation();
		
		op.setAuthor("佐藤健二");
		op.setSearchIndex(SearchIndexType.Blended);
		System.out.println(op.toRestRequest(true));
	}
}
