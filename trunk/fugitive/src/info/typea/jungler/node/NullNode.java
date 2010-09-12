package info.typea.jungler.node;

public class NullNode extends Node {
	private static final long serialVersionUID = 1L;

	public NullNode() {
		setId(null);
		setName(null);
		setComplete(true);
	}
}
