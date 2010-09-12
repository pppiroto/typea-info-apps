package info.typea.fugitive.database;

import java.util.TreeMap;

/**
 * インデックス付きでSQL文用パラメータを管理する。
 * <br/>
 * インデックスは 1 から開始
 * 
 * @author totec yagi
 */
public class Parameter {
	/**
	 * パラメータを管理するMap
	 */
	protected TreeMap<Integer, Object> paramMap = new TreeMap<Integer, Object>();
	
	/**
	 * コンストラクタ
	 */
	public Parameter() {}
	
	/**
     * コンストラクタ
     * パラメータ配列から、インスタンスを生成する
	 * @param params パラメータ配列
	 */
	public Parameter(Object...params) {
		int idx = 1;
		for (Object param : params) {
			paramMap.put(idx++, param);
		}
	}
	/**
     * 指定されたインデックスにパラメータを挿入する。
	 * @param index インデックス
	 * @param param パラメータ
	 */
	public void put(int index, Object param) {
		if (index < 1) {
			throw new IllegalArgumentException();
		}
		paramMap.put(index, param);
	}
    
	/**
     * 指定されたインデックスのパラメータを取得する。
	 * @param index インデックス
	 * @return パラメータ
	 */
	public Object get(int index) {
		return paramMap.get(index);
	}
    
	/**
	 * 保持するパラメータの後半の一部を切り捨てる
	 * @param max 取得するパラメータObject[] の件数
	 * @return 後半の一部を切り捨てたパラメータ配列
	 */
	public Object[] toObjectArray(int max) {
		int size = paramMap.lastKey();
		if (max > 0) {
			size = Math.min(size, max);
		}
		
		Object[] params = new Object[size];
		for (int i=1; i<=size; i++) {
			if (paramMap.containsKey(i)) {
				params[i-1] = paramMap.get(i);
			} else {
				params[i-1] = "";
			}
		}
		return params;
	}
    
	/**
     * 保持するパラメータをObjectの配列として返す。
	 * @return パラメータ配列
	 */
	public Object[] toObjectArray() {
		return toObjectArray(-1);
	}
    
	/**
     * 可変引数として渡された、パラメータを、オブジェクトの配列として返す。
	 * @param params パラメータ配列
	 * @return パラメータオブジェクトの配列
	 */
	public static Object[] makeObjectArray(Object...params) {
		return params;
	}
}
