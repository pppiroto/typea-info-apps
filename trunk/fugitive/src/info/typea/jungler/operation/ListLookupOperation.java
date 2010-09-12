package info.typea.jungler.operation;
import info.typea.jungler.generate.Operation;
import info.typea.jungler.type.*;

/**
 * AWSECommerceService Version : 2006-11-14
 * このクラスは、{@link info.typea.jungler.generate.AmazonEcsOperationClassGenerator}によって、自動生成されました。
 * created on Sat Jun 30 00:37:09 JST 2007
 * @see info.typea.jungler.generate.AmazonEcsOperationClassGenerator
 * @see info.typea.jungler.generate.OperationClassTemplete
 */
public class ListLookupOperation extends Operation {
	/** required fields */
	protected final String[] REQUIRED_FIELDS = {"ListId", "ListType"};
	/** available ResponseGroups */
	public enum LISTLOOKUP_RESPONSEGROUP {Request, ListFull, ListInfo, ListItems, ItemIds, Small, Medium, Large, Offers, OfferFull, OfferSummary, OfferListings, PromotionSummary, PromotionDetails, Variations, VariationMinimum, VariationSummary, ItemAttributes, Tracks, Accessories, EditorialReview, SalesRank, BrowseNodes, Images, Similarities, Reviews, Subjects, ListmaniaLists};
	/** Constructor */
	public ListLookupOperation() {
		setParameter("Operation","ListLookup");
	}
	/** required parameter list */
	public String[] getRequiredFields() {
		return REQUIRED_FIELDS;
	}
	/** set ResponseGroup */
	public void setResponseGroup(LISTLOOKUP_RESPONSEGROUP responseGroup) {
		super.setResponseGroup(responseGroup.toString());
	}
	/* modifires */
	public void setAWSAccessKeyId(String[] aWSAccessKeyId) {
		setParameter("AWSAccessKeyId", aWSAccessKeyId);
	}
	public void setAWSAccessKeyId(String aWSAccessKeyId) {
		setParameter("AWSAccessKeyId", aWSAccessKeyId);
	}
	public String[] getAWSAccessKeyId() {
		return getParameter("AWSAccessKeyId");
	}
	public void setAssociateTag(String[] associateTag) {
		setParameter("AssociateTag", associateTag);
	}
	public void setAssociateTag(String associateTag) {
		setParameter("AssociateTag", associateTag);
	}
	public String[] getAssociateTag() {
		return getParameter("AssociateTag");
	}
	public void setCondition(String[] condition) {
		setParameter("Condition", condition);
	}
	public void setCondition(String condition) {
		setParameter("Condition", condition);
	}
	public String[] getCondition() {
		return getParameter("Condition");
	}
	public void setContentType(String[] contentType) {
		setParameter("ContentType", contentType);
	}
	public void setContentType(String contentType) {
		setParameter("ContentType", contentType);
	}
	public String[] getContentType() {
		return getParameter("ContentType");
	}
	public void setDeliveryMethod(String[] deliveryMethod) {
		setParameter("DeliveryMethod", deliveryMethod);
	}
	public void setDeliveryMethod(String deliveryMethod) {
		setParameter("DeliveryMethod", deliveryMethod);
	}
	public String[] getDeliveryMethod() {
		return getParameter("DeliveryMethod");
	}
	public void setISPUPostalCode(String[] iSPUPostalCode) {
		setParameter("ISPUPostalCode", iSPUPostalCode);
	}
	public void setISPUPostalCode(String iSPUPostalCode) {
		setParameter("ISPUPostalCode", iSPUPostalCode);
	}
	public String[] getISPUPostalCode() {
		return getParameter("ISPUPostalCode");
	}
	public void setListId(String[] listId) {
		setParameter("ListId", listId);
	}
	public void setListId(String listId) {
		setParameter("ListId", listId);
	}
	public String[] getListId() {
		return getParameter("ListId");
	}
	public void setListType(String[] listType) {
		setParameter("ListType", listType);
	}
	public void setListType(String listType) {
		setParameter("ListType", listType);
	}
	public String[] getListType() {
		return getParameter("ListType");
	}
	public void setMarketplace(String[] marketplace) {
		setParameter("Marketplace", marketplace);
	}
	public void setMarketplace(String marketplace) {
		setParameter("Marketplace", marketplace);
	}
	public String[] getMarketplace() {
		return getParameter("Marketplace");
	}
	public void setMarketplaceDomain(String[] marketplaceDomain) {
		setParameter("MarketplaceDomain", marketplaceDomain);
	}
	public void setMarketplaceDomain(String marketplaceDomain) {
		setParameter("MarketplaceDomain", marketplaceDomain);
	}
	public String[] getMarketplaceDomain() {
		return getParameter("MarketplaceDomain");
	}
	public void setMerchantId(String[] merchantId) {
		setParameter("MerchantId", merchantId);
	}
	public void setMerchantId(String merchantId) {
		setParameter("MerchantId", merchantId);
	}
	public String[] getMerchantId() {
		return getParameter("MerchantId");
	}
	public void setProductGroup(String[] productGroup) {
		setParameter("ProductGroup", productGroup);
	}
	public void setProductGroup(String productGroup) {
		setParameter("ProductGroup", productGroup);
	}
	public String[] getProductGroup() {
		return getParameter("ProductGroup");
	}
	public void setProductPage(String[] productPage) {
		setParameter("ProductPage", productPage);
	}
	public void setProductPage(String productPage) {
		setParameter("ProductPage", productPage);
	}
	public String[] getProductPage() {
		return getParameter("ProductPage");
	}
	public void setReviewSort(String[] reviewSort) {
		setParameter("ReviewSort", reviewSort);
	}
	public void setReviewSort(String reviewSort) {
		setParameter("ReviewSort", reviewSort);
	}
	public String[] getReviewSort() {
		return getParameter("ReviewSort");
	}
	public void setSort(String[] sort) {
		setParameter("Sort", sort);
	}
	public void setSort(String sort) {
		setParameter("Sort", sort);
	}
	public String[] getSort() {
		return getParameter("Sort");
	}
	public void setStyle(String[] style) {
		setParameter("Style", style);
	}
	public void setStyle(String style) {
		setParameter("Style", style);
	}
	public String[] getStyle() {
		return getParameter("Style");
	}
	public void setValidate(String[] validate) {
		setParameter("Validate", validate);
	}
	public void setValidate(String validate) {
		setParameter("Validate", validate);
	}
	public String[] getValidate() {
		return getParameter("Validate");
	}
	public void setVersion(String[] version) {
		setParameter("Version", version);
	}
	public void setVersion(String version) {
		setParameter("Version", version);
	}
	public String[] getVersion() {
		return getParameter("Version");
	}
	public void setXMLEscaping(String[] xMLEscaping) {
		setParameter("XMLEscaping", xMLEscaping);
	}
	public void setXMLEscaping(String xMLEscaping) {
		setParameter("XMLEscaping", xMLEscaping);
	}
	public String[] getXMLEscaping() {
		return getParameter("XMLEscaping");
	}
}
