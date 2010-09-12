package info.typea.jungler.generate;

import info.typea.fugitive.logging.LogUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ParameterTypeContext {
	private static final Logger log = LogUtil.getLogger(ParameterTypeContext.class);
	
	protected static ParameterTypeContext me;
	private List<String>list;
	
	protected ParameterTypeContext() {
		list = new ArrayList<String>();
		registryFieldType("SearchIndexType");
	}

	public static ParameterTypeContext getInstance() {
		if (me == null) {
			me = new ParameterTypeContext();
		}
		return me;
	}
	public void registryFieldType(String typeName) {
		list.add(typeName);
	}
	public boolean isContainType(String typeName) {
		return list.contains(typeName);
	}
}
