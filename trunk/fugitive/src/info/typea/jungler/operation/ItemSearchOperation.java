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
public class ItemSearchOperation extends Operation {
	/** required fields */
	protected final String[] REQUIRED_FIELDS = {"SearchIndex"};
	/** available ResponseGroups */
	public enum ITEMSEARCH_RESPONSEGROUP {Request, ItemIds, Small, Medium, Large, Offers, OfferFull, OfferSummary, OfferListings, PromotionSummary, PromotionDetails, VariationMinimum, VariationSummary, Variations, ItemAttributes, MerchantItemAttributes, Tracks, Accessories, EditorialReview, SalesRank, BrowseNodes, Images, Similarities, Subjects, Reviews, ListmaniaLists, PromotionalTag, SearchBins, AlternateVersions, Collections};
	/** Constructor */
	public ItemSearchOperation() {
		setParameter("Operation","ItemSearch");
	}
	/** required parameter list */
	public String[] getRequiredFields() {
		return REQUIRED_FIELDS;
	}
	/** set ResponseGroup */
	public void setResponseGroup(ITEMSEARCH_RESPONSEGROUP responseGroup) {
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
	public void setActor(String[] actor) {
		setParameter("Actor", actor);
	}
	public void setActor(String actor) {
		setParameter("Actor", actor);
	}
	public String[] getActor() {
		return getParameter("Actor");
	}
	public void setAdditionalSearchParameters(String[] additionalSearchParameters) {
		setParameter("AdditionalSearchParameters", additionalSearchParameters);
	}
	public void setAdditionalSearchParameters(String additionalSearchParameters) {
		setParameter("AdditionalSearchParameters", additionalSearchParameters);
	}
	public String[] getAdditionalSearchParameters() {
		return getParameter("AdditionalSearchParameters");
	}
	public void setArtist(String[] artist) {
		setParameter("Artist", artist);
	}
	public void setArtist(String artist) {
		setParameter("Artist", artist);
	}
	public String[] getArtist() {
		return getParameter("Artist");
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
	public void setAudienceRating(String[] audienceRating) {
		setParameter("AudienceRating", audienceRating);
	}
	public void setAudienceRating(String audienceRating) {
		setParameter("AudienceRating", audienceRating);
	}
	public String[] getAudienceRating() {
		return getParameter("AudienceRating");
	}
	public void setAuthor(String[] author) {
		setParameter("Author", author);
	}
	public void setAuthor(String author) {
		setParameter("Author", author);
	}
	public String[] getAuthor() {
		return getParameter("Author");
	}
	public void setAvailability(String[] availability) {
		setParameter("Availability", availability);
	}
	public void setAvailability(String availability) {
		setParameter("Availability", availability);
	}
	public String[] getAvailability() {
		return getParameter("Availability");
	}
	public void setBrand(String[] brand) {
		setParameter("Brand", brand);
	}
	public void setBrand(String brand) {
		setParameter("Brand", brand);
	}
	public String[] getBrand() {
		return getParameter("Brand");
	}
	public void setBrowseNode(String[] browseNode) {
		setParameter("BrowseNode", browseNode);
	}
	public void setBrowseNode(String browseNode) {
		setParameter("BrowseNode", browseNode);
	}
	public String[] getBrowseNode() {
		return getParameter("BrowseNode");
	}
	public void setCity(String[] city) {
		setParameter("City", city);
	}
	public void setCity(String city) {
		setParameter("City", city);
	}
	public String[] getCity() {
		return getParameter("City");
	}
	public void setComposer(String[] composer) {
		setParameter("Composer", composer);
	}
	public void setComposer(String composer) {
		setParameter("Composer", composer);
	}
	public String[] getComposer() {
		return getParameter("Composer");
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
	public void setConductor(String[] conductor) {
		setParameter("Conductor", conductor);
	}
	public void setConductor(String conductor) {
		setParameter("Conductor", conductor);
	}
	public String[] getConductor() {
		return getParameter("Conductor");
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
	public void setCuisine(String[] cuisine) {
		setParameter("Cuisine", cuisine);
	}
	public void setCuisine(String cuisine) {
		setParameter("Cuisine", cuisine);
	}
	public String[] getCuisine() {
		return getParameter("Cuisine");
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
	public void setDirector(String[] director) {
		setParameter("Director", director);
	}
	public void setDirector(String director) {
		setParameter("Director", director);
	}
	public String[] getDirector() {
		return getParameter("Director");
	}
	public void setDisableParentAsinSubstitution(String[] disableParentAsinSubstitution) {
		setParameter("DisableParentAsinSubstitution", disableParentAsinSubstitution);
	}
	public void setDisableParentAsinSubstitution(String disableParentAsinSubstitution) {
		setParameter("DisableParentAsinSubstitution", disableParentAsinSubstitution);
	}
	public String[] getDisableParentAsinSubstitution() {
		return getParameter("DisableParentAsinSubstitution");
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
	public void setItemPage(String[] itemPage) {
		setParameter("ItemPage", itemPage);
	}
	public void setItemPage(String itemPage) {
		setParameter("ItemPage", itemPage);
	}
	public String[] getItemPage() {
		return getParameter("ItemPage");
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
	public void setManufacturer(String[] manufacturer) {
		setParameter("Manufacturer", manufacturer);
	}
	public void setManufacturer(String manufacturer) {
		setParameter("Manufacturer", manufacturer);
	}
	public String[] getManufacturer() {
		return getParameter("Manufacturer");
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
	public void setMaximumPrice(String[] maximumPrice) {
		setParameter("MaximumPrice", maximumPrice);
	}
	public void setMaximumPrice(String maximumPrice) {
		setParameter("MaximumPrice", maximumPrice);
	}
	public String[] getMaximumPrice() {
		return getParameter("MaximumPrice");
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
	public void setMinimumPrice(String[] minimumPrice) {
		setParameter("MinimumPrice", minimumPrice);
	}
	public void setMinimumPrice(String minimumPrice) {
		setParameter("MinimumPrice", minimumPrice);
	}
	public String[] getMinimumPrice() {
		return getParameter("MinimumPrice");
	}
	public void setMusicLabel(String[] musicLabel) {
		setParameter("MusicLabel", musicLabel);
	}
	public void setMusicLabel(String musicLabel) {
		setParameter("MusicLabel", musicLabel);
	}
	public String[] getMusicLabel() {
		return getParameter("MusicLabel");
	}
	public void setNeighborhood(String[] neighborhood) {
		setParameter("Neighborhood", neighborhood);
	}
	public void setNeighborhood(String neighborhood) {
		setParameter("Neighborhood", neighborhood);
	}
	public String[] getNeighborhood() {
		return getParameter("Neighborhood");
	}
	public void setOrchestra(String[] orchestra) {
		setParameter("Orchestra", orchestra);
	}
	public void setOrchestra(String orchestra) {
		setParameter("Orchestra", orchestra);
	}
	public String[] getOrchestra() {
		return getParameter("Orchestra");
	}
	public void setPostalCode(String[] postalCode) {
		setParameter("PostalCode", postalCode);
	}
	public void setPostalCode(String postalCode) {
		setParameter("PostalCode", postalCode);
	}
	public String[] getPostalCode() {
		return getParameter("PostalCode");
	}
	public void setPower(String[] power) {
		setParameter("Power", power);
	}
	public void setPower(String power) {
		setParameter("Power", power);
	}
	public String[] getPower() {
		return getParameter("Power");
	}
	public void setPublisher(String[] publisher) {
		setParameter("Publisher", publisher);
	}
	public void setPublisher(String publisher) {
		setParameter("Publisher", publisher);
	}
	public String[] getPublisher() {
		return getParameter("Publisher");
	}
	public void setReleaseDate(String[] releaseDate) {
		setParameter("ReleaseDate", releaseDate);
	}
	public void setReleaseDate(String releaseDate) {
		setParameter("ReleaseDate", releaseDate);
	}
	public String[] getReleaseDate() {
		return getParameter("ReleaseDate");
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
	public void setSearchIndex(SearchIndexType[] searchIndex) {
		setParameter("SearchIndex", searchIndex);
	}
	public void setSearchIndex(SearchIndexType searchIndex) {
		setParameter("SearchIndex", searchIndex);
	}
	public String[] getSearchIndex() {
		return getParameter("SearchIndex");
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
	public void setState(String[] state) {
		setParameter("State", state);
	}
	public void setState(String state) {
		setParameter("State", state);
	}
	public String[] getState() {
		return getParameter("State");
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
	public void setTextStream(String[] textStream) {
		setParameter("TextStream", textStream);
	}
	public void setTextStream(String textStream) {
		setParameter("TextStream", textStream);
	}
	public String[] getTextStream() {
		return getParameter("TextStream");
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
	public void setVariationPage(String[] variationPage) {
		setParameter("VariationPage", variationPage);
	}
	public void setVariationPage(String variationPage) {
		setParameter("VariationPage", variationPage);
	}
	public String[] getVariationPage() {
		return getParameter("VariationPage");
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
