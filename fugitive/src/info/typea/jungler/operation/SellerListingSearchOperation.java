package info.typea.jungler.operation;
import info.typea.jungler.generate.Operation;
import info.typea.jungler.type.*;

/**
 * AWSECommerceService Version : 2006-11-14
 * このクラスは、{@link info.typea.jungler.generate.AmazonEcsOperationClassGenerator}によって、自動生成されました。
 * created on Sat Jun 30 00:37:10 JST 2007
 * @see info.typea.jungler.generate.AmazonEcsOperationClassGenerator
 * @see info.typea.jungler.generate.OperationClassTemplete
 */
public class SellerListingSearchOperation extends Operation {
	/** required fields */
	protected final String[] REQUIRED_FIELDS = {"SellerId"};
	/** available ResponseGroups */
	public enum SELLERLISTINGSEARCH_RESPONSEGROUP {Request, SellerListing};
	/** Constructor */
	public SellerListingSearchOperation() {
		setParameter("Operation","SellerListingSearch");
	}
	/** required parameter list */
	public String[] getRequiredFields() {
		return REQUIRED_FIELDS;
	}
	/** set ResponseGroup */
	public void setResponseGroup(SELLERLISTINGSEARCH_RESPONSEGROUP responseGroup) {
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
	public void setContentType(String[] contentType) {
		setParameter("ContentType", contentType);
	}
	public void setContentType(String contentType) {
		setParameter("ContentType", contentType);
	}
	public String[] getContentType() {
		return getParameter("ContentType");
	}
	public void setKeywords(String[] keywords) {
		setParameter("Keywords", keywords);
	}
	public void setKeywords(String keywords) {
		setParameter("Keywords", keywords);
	}
	public String[] getKeywords() {
		return getParameter("Keywords");
	}
	public void setListingPage(String[] listingPage) {
		setParameter("ListingPage", listingPage);
	}
	public void setListingPage(String listingPage) {
		setParameter("ListingPage", listingPage);
	}
	public String[] getListingPage() {
		return getParameter("ListingPage");
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
	public void setOfferStatus(String[] offerStatus) {
		setParameter("OfferStatus", offerStatus);
	}
	public void setOfferStatus(String offerStatus) {
		setParameter("OfferStatus", offerStatus);
	}
	public String[] getOfferStatus() {
		return getParameter("OfferStatus");
	}
	public void setSellerId(String[] sellerId) {
		setParameter("SellerId", sellerId);
	}
	public void setSellerId(String sellerId) {
		setParameter("SellerId", sellerId);
	}
	public String[] getSellerId() {
		return getParameter("SellerId");
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
	public void setTitle(String[] title) {
		setParameter("Title", title);
	}
	public void setTitle(String title) {
		setParameter("Title", title);
	}
	public String[] getTitle() {
		return getParameter("Title");
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
