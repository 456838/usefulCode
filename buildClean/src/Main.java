import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

	// private static ArrayList<String> filelist = new ArrayList<String>();

	static String currentPath ;
	public static void main(String args[]) throws Exception {
		String currentPath = System.getProperty("user.dir");
//		currentPath ="F:\\10backup\\ftpServer\\code";
		writeLog("当前路径：" + currentPath);
		writeLog("日志路径：" + System.getProperty("user.dir") + File.separator
				+ "cleanlog.txt");
//		cleanBuild(currentPath);
		cleanEclipse(currentPath);
		// String targetPath = filePath;
		// File file = ZipUtil.zip(targetPath,"F://zipCode");
		// System.out.println(file);
		// zipFile("D://Apkdb",new File(filePath+".zip"));
	}

	static void writeLog(String log) {
		System.out.println(log);
		String logpath =currentPath+File.separator
				+ "cleanlog.txt";
		FileUtils.writeFile(logpath, log + "\n", true);
	}
	
	static void cleanEclipse(String filePath){
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
//			System.out.println(file.getAbsolutePath());
			if (file.isDirectory()) {
				if (file.getName().equals("bin")||file.getName().equals("gen")) {
					// boolean isdel = file.delete();
					writeLog("清理目录：" + file.getAbsolutePath() + "\t result:"
							+ deleteDir(file));
				} else {
					cleanEclipse(file.getAbsolutePath());
				}
			} else {
				if (file.getName().endsWith(".class")) {
					writeLog("清理文件：" + file.getAbsolutePath() + "\t result:"
							+ FileUtils.deleteFile(file.getAbsolutePath()));
				}
			}
		}
	}

	static void cleanBuild(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				if (file.getName().equals("build")
						|| file.getName().equals(".gradle")) {
					// boolean isdel = file.delete();
					writeLog("清理目录：" + file.getAbsolutePath() + "\t result:"
							+ deleteDir(file));
				} else {
					cleanBuild(file.getAbsolutePath());
				}
			} else {
				if (file.getName().endsWith(".apk")
						) {
					System.out.println("清理目录：" + file.getAbsolutePath());
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
