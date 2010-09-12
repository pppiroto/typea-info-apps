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
			�a��:465610
			�m��:573662
			�G���N�g���j�N�X:3210991
			�z�[�����L�b�`��:3839151
			�|�s�����[���y:562032
			�N���V�b�N:701040
			DVD:562002
			�r�f�I:561972
			�\�t�g�E�F�A:637630
			�Q�[��:637872
			�������ၕ�z�r�[:1329953
		*/
		BrowseNodeLookupOperation op = new BrowseNodeLookupOperation();
		op.setBrowseNodeId("13602871");
		
		System.out.println(op.toRestRequest(true));
	}
	public void itemSearchOperationTest() {
		ItemSearchOperation op = new ItemSearchOperation();
		op.setSearchIndex(SearchIndexType.Books);
		//op.setKeywords("���w");
		op.setResponseGroup(ITEMSEARCH_RESPONSEGROUP.ItemIds);
		op.setBrowseNode("13602871");
		System.out.println(op.toRestRequest(true));
	}
	public void itemSearchTest() {
		ItemSearchOperation op = new ItemSearchOperation();
		
		op.setAuthor("��������");
		op.setSearchIndex(SearchIndexType.Blended);
		System.out.println(op.toRestRequest(true));
	}
}
