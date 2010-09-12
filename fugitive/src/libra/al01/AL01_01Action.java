package libra.al01;

import info.typea.fugitive.action.BaseAction;
import info.typea.fugitive.auth.AuthInfo;
import info.typea.fugitive.auth.CheckAuth;
import info.typea.fugitive.image.ThumnailImageCreater;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libra.al01.model.Album;
import libra.al01.model.AlbumAuthInfo;
import libra.al01.model.AlbumList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AL01_01Action extends BaseAction {

	@Override
	public String getActionId() {
		return "AL01_01";
	}
    
	/**
	 * @param actionForm
	 * @return
	 */
	private AL01_01Form getForm(ActionForm actionForm) {
		AL01_01Form form = (AL01_01Form)actionForm;
		if (form == null) {
			form = new AL01_01Form();
		}
		return form;
	}
    
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return init(mapping , actionForm, req, res);
	}

	/**
	 * アルバムを表示
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		AlbumList<Album> albums = new AlbumList<Album>();
		AlbumAuthInfo aai = getAlbumAuthInfo(req);
		if (aai != null && aai.isAuthorized()) {
			for (int i=0; i<aai.getPathList().size(); i++) {
				String path = aai.getPathList().get(i);
				File fldr = new File(path);
				
				if (fldr.isDirectory()) {
					File[] folders = fldr.listFiles();
					Arrays.sort(folders);
					for (File fld : folders) {
						if (fld.isDirectory()) {
							Album albm = new Album(i);
							albm.setBaseDir(fldr.getAbsolutePath());
							albm.setDirName(fld.getName());
							File[] files = fld.listFiles (
									new MyAlubumFileFilter()
							);
							Arrays.sort(files);
							for (File f : files) {
								albm.addSrc(f.getName());
							}
							albums.add(albm);
						}
					}
				}
			}
		}
		boolean isAdmin = false;
		if (aai != null) {
			isAdmin = aai.isInRole("admin");
		}
		req.setAttribute("username", aai.getUserName());
		req.setAttribute("isAdmin",  isAdmin);
		req.setAttribute("albums",   albums);
		
		return mapping.findForward(SUCCESS);
	}
	/**
	 * 指定された画像を表示
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward disp(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		AlbumAuthInfo aai = getAlbumAuthInfo(req);
		AL01_01Form form = getForm(actionForm);
		//	            &bid=${album.baseDirIndex}&dn=${album.dirName}&src=${src}
		String param = "trxId=get"
			         + "&bid=" + form.getBid() 
		             + "&dn="  + form.getDn()
		             + "&src=" + form.getSrc();
		
		req.setAttribute("username", aai.getUserName());
		req.setAttribute("photo_title", form.getSrc());
		req.setAttribute("actionParam", param);
		
		return mapping.findForward("display");
	}
	/**
	 * 指定されたファイルの物理削除
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward del(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
		AlbumAuthInfo aai = getAlbumAuthInfo(req);
		if (aai != null) {
			String filePath = getFilePath(actionForm, aai);
			File f = new File(filePath);
			File parentDir = f.getParentFile();
			File thumf = new File(parentDir.getAbsoluteFile() + File.separator + 
									Album.THUMBNAIL_DIR + File.separator + f.getName());
			
			if (f.delete()) {
				log.info(f.getAbsoluteFile() + " is deleted by " + aai.getUserName());
			}
			if (thumf.delete()) {
				log.info(f.getAbsoluteFile() + " is deleted by " + aai.getUserName());
			}
		}
		return init(mapping, actionForm, req, res);
	}	

	/**
	 * ファイルをGET
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward get(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		AlbumAuthInfo aai = getAlbumAuthInfo(req);
		if (aai != null) {
			byte[] buf = new byte[1024 * 4];
			
			String filePath = getFilePath(actionForm, aai);
			File f = new File(filePath);
			res.setHeader("Cache-Control", "public"); // IE対策
			res.setContentType("image/jpeg");
			res.setHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");
			FileInputStream r = new FileInputStream(f);
			OutputStream o = res.getOutputStream();
			while (r.read(buf) > 0) {
				o.write(buf);
				o.flush();
			}
			o.flush();
			
			o.close();
			r.close();
		}		
		return null;
	}
	/**
	 * サムネイルを作成
	 * @param mapping
	 * @param actionForm
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward thumb(ActionMapping mapping, ActionForm actionForm, HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		AlbumAuthInfo aai = getAlbumAuthInfo(req);
		if (aai != null) {
			File fld = new File(getFileDir(actionForm, aai));
			File[] files = fld.listFiles (
					new MyAlubumFileFilter()
			);
			
			ThumnailImageCreater creator = new ThumnailImageCreater();
			for (File f : files) {
				String sf = f.getAbsolutePath();
				File thumbDir = new File(f.getParentFile().getAbsolutePath() + File.separator
				                         + Album.THUMBNAIL_DIR);
				if (!thumbDir.exists()) {
					thumbDir.mkdir();
				}
				String df = thumbDir.getAbsolutePath() + File.separator + f.getName();
				creator.writeThumbNailImage(
						new FileInputStream(sf), 
						new FileOutputStream(df), 
						0.05f
						);
			}
		}		
		return init(mapping, actionForm, req, res);
	}
	/**
	 * ActionFormのパラメータから、ファイルのパスを生成する
	 * @param actionForm
	 * @param aai
	 * @return
	 */
	private String getFilePath(ActionForm actionForm, AlbumAuthInfo aai) {
		List<String> pathList = aai.getPathList();
		AL01_01Form form = getForm(actionForm);
		
		int id = Integer.parseInt(form.getBid());
		String path = pathList.get(id) + "/" + form.getDn(); 
		String img  = form.getSrc();
		
		String filePath = path + "/" + img;
		if ("\\".equals(File.separator)) {
			filePath = "c:" + filePath.replaceAll("/", "\\\\");
		}
		return filePath;
	}
	/**
	 * ActionFormのパラメータから、ファイルのディレクトリを生成する
	 * @param actionForm
	 * @param aai
	 * @return
	 */
	private String getFileDir(ActionForm actionForm, AlbumAuthInfo aai) {
		List<String> pathList = aai.getPathList();
		AL01_01Form form = getForm(actionForm);
		
		int id = Integer.parseInt(form.getBid());
		String path = pathList.get(id) + "/" + form.getDn(); 
		
		String filePath = path;
		if ("\\".equals(File.separator)) {
			filePath = "c:" + filePath.replaceAll("/", "\\\\");
		}
		return filePath;
	}
	/**
	 * HttpRequest から、アルバム認証情報を取得する
	 * @param req
	 * @return AlbumAuthInfo
	 */
	public AlbumAuthInfo getAlbumAuthInfo(HttpServletRequest req) {
		AlbumAuthInfo aai = null;
		CheckAuth a = (CheckAuth)req.getSession(true).getAttribute(AlbumAuthInfo.SESSION_KEY);
		AuthInfo ai = a.getAuthInfo();
		if (ai instanceof AlbumAuthInfo) {
			aai = (AlbumAuthInfo) ai;
		}
		return aai;
	}
	/**
	 * @author Administrator
	 *
	 */
	class MyAlubumFileFilter implements FileFilter {
		public boolean accept(File pathname) {
			return (pathname.getName().matches(".*\\.[jJ][pP][eE]{0,1}[gG]$"));
		}
	}
}
