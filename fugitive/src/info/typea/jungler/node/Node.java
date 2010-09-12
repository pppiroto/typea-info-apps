package info.typea.jungler.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String name;
	protected boolean isComplete;
	protected Node parent;
	protected List<Node> childs = new ArrayList<Node>();
	
	public Node() {
	}

	public Node(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String toString() {
		return this.id + ":" + this.name + "[complete:" + this.isComplete + "]";
	}
	
	public void addChild(Node child) {
		childs.add(child);
	}
	public List<Node> getChilds() {
		return childs;
	}
	
	public Node get(int index) {
		return childs.get(index);
	}
	public int size() {
		return childs.size();
	}
	public void clear() {
		childs.clear();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Node getParent() {
		if (this.parent == null) {
			return new NullNode();
		}
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
}
