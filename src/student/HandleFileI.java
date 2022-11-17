package student;

import java.io.File;

/**
 * 处理文件接口
 * 
 * @author Administrator
 *
 */
public interface HandleFileI {

	public static class Tools {
		/**
		 * 递归浏览文件，并做一些操作
		 * 
		 * @param file
		 * @param handler
		 */
		public static void scanDirRecursion(File file, HandleFileI handler) {
			try {
				if (!file.exists()) {
					return;
				}
				if (file.canRead()) {
					if (file.isDirectory()) {
						String[] files = file.list();
						if (files != null) {
							for (int i = 0; i < files.length; i++) {
								scanDirRecursion(new File(file, files[i]), handler);
							}
						}
						handler.handleDir(file);
					} else {
						handler.handleFile(file);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对文件做某些操作
	 * 
	 * @param file
	 */
	void handleFile(File file) throws Exception;

	/**
	 * 对文件夹做某些操作
	 * 
	 * @param directory
	 */
	void handleDir(File directory) throws Exception;
}