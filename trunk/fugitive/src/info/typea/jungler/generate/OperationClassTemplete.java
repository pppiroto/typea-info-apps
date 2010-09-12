  package info.typea.jungler.generate;

import info.typea.fugitive.logging.LogUtil;
import info.typea.jungler.type.AmazonEcsType;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;


public class OperationClassTemplete {
	private static final Logger log = LogUtil.getLogger(OperationClassTemplete.class);
	
	private String packageName = null;
	private String clazzName;
	private String amazonEcsVersion;
	List<String> paramList         = new ArrayList<String>();
	List<String> reqParamList      = new ArrayList<String>();
	List<String> responseGroupList = new ArrayList<String>();
	public OperationClassTemplete(String clazzName) {
		this.clazzName = clazzName;
	}
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getAmazonEcsVersion() {
		return amazonEcsVersion;
	}
	public void setAmazonEcsVersion(String amazonEcsVersion) {
		this.amazonEcsVersion = amazonEcsVersion;
	}

	public void addParameter(String paramName) {
		paramList.add(paramName);
	}
	public void addRequiredParameter(String paramName) {
		reqParamList.add(paramName);
	}
	public void addAvailableResponseGroup(String responseGroup) {
		responseGroupList.add(responseGroup);
	}
	public void output(OutputStream out) throws IOException {
		if (this.clazzName == null || this.clazzName.trim().length() == 0) {
			log.error("invalid class name. can't create java file.");
			out.close();
			return;
		}
		Collections.sort(this.paramList);
		Collections.sort(this.reqParamList);
		ParameterTypeContext pctx = ParameterTypeContext.getInstance();
		
		PrintWriter w = new PrintWriter(new OutputStreamWriter(out));
		if (packageName != null) {
			w.println("package " + this.packageName + ";");
		}
		
		String clsName = this.clazzName + "Operation";
		w.println("import " + Operation.class.getName() + ";");
		w.println("import " + AmazonEcsType.class.getPackage().getName() + ".*;");
		w.println("");
		w.println("/**");
		w.println(" * AWSECommerceService Version : " + this.amazonEcsVersion + "");
		w.println(" * このクラスは、{@link " + AmazonEcsOperationClassGenerator.class.getName() + "}によって、自動生成されました。");
		w.println(" * created on " + new Date() + "");
		w.println(" * @see " + AmazonEcsOperationClassGenerator.class.getName() + "");
		w.println(" * @see " + OperationClassTemplete.class.getName() + "");
		w.println(" */");
		w.println("public class " + clsName + " extends Operation {");
		
		for (int i=0; i<reqParamList.size(); i++) {
			if (i == 0) {
				w.println(idt(1) + "/** required fields */");
				w.print(idt(1) + "protected final String[] REQUIRED_FIELDS = {");
			}
			w.print(((i==0)?"":", ") + "\"" + reqParamList.get(i) + "\"");
			if (i == (reqParamList.size()-1)) {
				w.println("};");
			}
		}
		String responseGroupEnumName = this.clazzName.toUpperCase() + "_RESPONSEGROUP";
		for (int i=0; i<responseGroupList.size(); i++) {
			if (i == 0) {
				w.println(idt(1) + "/** available ResponseGroups */");
				w.print(idt(1) + "public enum " + responseGroupEnumName + " {");
			}
			w.print(((i==0)?"":", ") + responseGroupList.get(i));
			if (i == (responseGroupList.size()-1)) {
				w.println("};");
			}
		}
		
		w.println(idt(1) + "/** Constructor */");
		w.println(idt(1) + "public " + clsName + "() {");
		w.println(idt(2) +     "setParameter(\"Operation\",\""+ this.clazzName + "\");");
		w.println(idt(1) + "}");
		
		w.println(idt(1) + "/** required parameter list */");
		if (reqParamList.size() > 0) {
			w.println(idt(1) + "public String[] getRequiredFields() {");
			w.println(idt(2) +     "return REQUIRED_FIELDS;");
			w.println(idt(1) + "}");
		}

		w.println(idt(1) + "/** set ResponseGroup */");
		if (responseGroupList.size() > 0) {
			w.println(idt(1) + "public void setResponseGroup("+ responseGroupEnumName + " responseGroup) {");
			w.println(idt(2) +     "super.setResponseGroup(responseGroup.toString());");
			w.println(idt(1) + "}");
		}
		
		w.println(idt(1) + "/* modifires */");
		for (String param : paramList) {
			String camelName = toCamelCase(param);
			String typeName = param + "Type";
			boolean isRegistoriedType = pctx.isContainType(typeName);
			typeName = ((isRegistoriedType)?typeName:"String");
			
			w.println(idt(1) + "public void set" + param + "(" + typeName + "[] " + camelName + ") {");
			w.println(idt(2) +     "setParameter(\"" + param + "\", " + camelName + ");");
			w.println(idt(1) + "}");
			
			w.println(idt(1) + "public void set" + param + "(" + typeName + " " + camelName + ") {");
			w.println(idt(2) +     "setParameter(\"" + param + "\", " + camelName + ");");
			w.println(idt(1) + "}");

			w.println(idt(1) + "public String[] get" + param + "() {");
			w.println(idt(2) +     "return getParameter(\"" + param + "\");");
			w.println(idt(1) + "}");
		}
		w.println("}");
		w.flush();
		w.close();
	}
	
	private String toCamelCase(String paramName) {
		if (paramName == null) return paramName;
		
		return paramName.substring(0,1).toLowerCase() + paramName.substring(1);
	}
	private String idt(int len) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<len; i++) {
			buf.append("\t");
		}
		return buf.toString();
	}
}
