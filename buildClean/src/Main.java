import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

	// private static ArrayList<String> filelist = new ArrayList<String>();
	static String currentPath = System.getProperty("user.dir");
	static String logPath = currentPath + File.separator
			+ "cleanlog.txt";
	public static void main(String args[]) throws Exception {
		
		writeLog("当前路径：" + currentPath);
		writeLog("日志路径：" +logPath);
		cleanBuild(currentPath);
		// String targetPath = filePath;
		// File file = ZipUtil.zip(targetPath,"F://zipCode");
		// System.out.println(file);
		// zipFile("D://Apkdb",new File(filePath+".zip"));
	}

	static void writeLog(String log) {
		System.out.println(log);
		FileUtils.writeFile(logPath, log+"\n", true);
	}

	static void cleanBuild(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getName().equals("build")
						|| file.getName().equals("bin")) {
//					System.out.println(file.getAbsolutePath());
					// boolean isdel = file.delete();
					writeLog("清理目录：" + file.getAbsolutePath() + "\t result:"
							+ deleteDir(file));
				} else {
					cleanBuild(file.getAbsolutePath());
				}
			} else {
				if (file.getName().endsWith(".apk")) {
					writeLog("清理文件：" + file.getAbsolutePath() + "\t result:"
							+ FileUtils.deleteFile(file.getAbsolutePath()));
				}
			}
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

}
