package info.typea.fugitive.model;

/**
 * �C���X�^���X�� null �ł��邱�Ƃ�\���l�I�u�W�F�N�g
 * <br/>
 * @author totec yagi
 */
public final class NullValueBean extends ValueBean {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return true;
		return (obj instanceof NullValueBean);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "null instance";
	}
}
