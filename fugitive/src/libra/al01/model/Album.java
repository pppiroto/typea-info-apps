package libra.al01.model;

import info.typea.fugitive.model.ValueBean;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Album extends ValueBean {
	private int baseDirIndex;
	private String baseDir;
	private String dirName;
	private List<String> src;
	
	public static final String THUMBNAIL_DIR = "thumbnail";
	
	public String getThumbDirName() {
		return getDirName() + "/" + THUMBNAIL_DIR; 
	}
	
	public int getBaseDirIndex() {
		return baseDirIndex;
	}
	public void setBaseDirIndex(int baseDirIndex) {
		this.baseDirIndex = baseDirIndex;
	}
	public Album(int baseDirIndex) {
		this.setBaseDirIndex(baseDirIndex);
		src = new ArrayList<String>();
	}
	public void addSrc(String imgUrl) {
		src.add(imgUrl);
	}
	public String getSrc(int index) {
		return src.get(index);
	}
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String folder) {
		this.dirName = folder;
	}
	public List<String> getSrc() {
		return src;
	}
	public void setSrc(List<String> imgUrls) {
		this.src = imgUrls;
	}
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
}
