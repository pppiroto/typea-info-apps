package info.typea.fugitive.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 変更があったファイルを抽出するツール
 * <br/>
 * コマンドラインや、Antから利用する。
 * 
 * @author totec yagi
 */
public class UpdatedFileExtractor extends AbstractBatchTool {
	private String srcDir = null;

	private String dstDir = null;

	private Date baseDate = null;

	/**
	 * ソースディレクトリ(およびそのサブディレクトリ)のうち、基準日以降に変更があったファイルを、
	 * 出力ディレクトリにコピーする
	 * 
	 * @param srcDir ソースディレクトリ
	 * @param dstDir 出力先ディレクトリ
	 * @param baseDate 基準日
	 * @throws Exception
	 */
	public void extractUpdatedFiles(String srcDir, String dstDir, Date baseDate) throws Exception {
		this.srcDir = srcDir;
		this.dstDir = dstDir;
		this.baseDate = baseDate;

		File srcFile = new File(srcDir);
		File dstFile = new File(dstDir);

		if (dstFile.exists()) {
			if (dstFile.list().length > 0) {
				System.err.println("destination directory has some file or directory. please ready empty one.");
				return;
			}
		} else {
			if (!dstFile.mkdir()) {
				System.err.println("can't create destination directory.");
				return;
			}
		}
		extractUpdatedFiles(srcFile);
	}

	/**
	 * @param srcFile
	 * @throws Exception
	 */
	private void extractUpdatedFiles(File srcFile) throws Exception {

		if (srcFile.isDirectory()) {
			String tmpPath = this.dstDir
					+ cutBaseDir(srcFile.getAbsolutePath());
			File tmpFile = new File(tmpPath);
			if (!tmpFile.exists() && !tmpFile.mkdir()) {
				System.err.println("can't create directory.");
				System.exit(0);
			}
			File[] childs = srcFile.listFiles();
			for (int i = 0; i < childs.length; i++) {
				extractUpdatedFiles(childs[i]);
			}
		} else {
			copyEditFile(srcFile);
		}
	}

	/**
	 * @param srcFile
	 * @throws Exception
	 */
	private void copyEditFile(File srcFile) throws Exception {
		Date srcDate = new Date(srcFile.lastModified());

		if (srcDate.compareTo(baseDate) > 0) {

			System.out.println("modified date is " + srcDate + " copy ..."
					+ srcFile.getName());

			String dstFileName = this.dstDir
					+ cutBaseDir(srcFile.getAbsolutePath());
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(srcFile));
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(dstFileName)));
			try {
				byte[] buf = new byte[256];
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
			} finally {
				out.close();
				in.close();
			}
			System.out.println("done.");
		}
	}

	/**
	 * @param absolutePath
	 * @return
	 */
	private String cutBaseDir(String absolutePath) {
		return absolutePath.substring(this.srcDir.length());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UpdatedFileExtractor me = new UpdatedFileExtractor();

		try {
			me.execute(args);
			System.out.println("finish.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see jp.dip.typea.libra.page.tool.BatchTool#execute(java.lang.String[])
	 */
	public void execute(String[] args) throws Exception {
		int staticIndex = 2;
		if (args.length < staticIndex) {
			exitWithUsage();
		}
		String srcDir = args[0].trim();
		String dstDir = args[1].trim();
		String fromDate = null;

		Properties argMap = parseArguments(staticIndex, new String[] { "-d" },
				args);

		fromDate = argMap.getProperty("-d");

		System.out.println("baseline dirctory : " + srcDir);
		SimpleDateFormat sf = (SimpleDateFormat) SimpleDateFormat.getInstance();
		sf.applyPattern("yyyy/MM/dd");
		Date baseDate = null;
		if (fromDate != null) {
			baseDate = sf.parse(fromDate);
		} else {
			String today = sf.format(new Date());
			baseDate = sf.parse(today);
		}
		extractUpdatedFiles(srcDir, dstDir, baseDate);
	}

	/* (non-Javadoc)
	 * @see jp.dip.typea.libra.page.tool.BatchTool#getUsage()
	 */
	public String getUsage() {
		return USAGE_TITLE + UpdatedFileExtractor.class.getName()
				+ " sourceDirectory destinationDirectory" + " [-d yyyy/MM/dd]";
	}
}