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
		writeLog("��ǰ·����" + currentPath);
		writeLog("��־·����" + System.getProperty("user.dir") + File.separator
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
					writeLog("����Ŀ¼��" + file.getAbsolutePath() + "\t result:"
							+ deleteDir(file));
				} else {
					cleanEclipse(file.getAbsolutePath());
				}
			} else {
				if (file.getName().endsWith(".class")) {
					writeLog("�����ļ���" + file.getAbsolutePath() + "\t result:"
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
					writeLog("����Ŀ¼��" + file.getAbsolutePath() + "\t result:"
							+ deleteDir(file));
				} else {
					cleanBuild(file.getAbsolutePath());
				}
			} else {
				if (file.getName().endsWith(".apk")
						) {
					System.out.println("����Ŀ¼��" + file.getAbsolutePath());
					writeLog("�����ļ���" + file.getAbsolutePath() + "\t result:"
							+ FileUtils.deleteFile(file.getAbsolutePath()));
				}
			}
		}
	}

	/**
	 * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
	 * 
	 * @param dir
	 *            ��Ҫɾ�����ļ�Ŀ¼
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// �ݹ�ɾ��Ŀ¼�е���Ŀ¼��
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Ŀ¼��ʱΪ�գ�����ɾ��
		return dir.delete();
	}

}
