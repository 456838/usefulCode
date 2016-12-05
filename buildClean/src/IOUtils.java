import java.io.Closeable;
import java.io.IOException;

/**
 * IO utils
 * 
 * @author Vladislav Bauer
 */

public class IOUtils {
	/**
	 * �ر�һ������������
	 * 
	 * @param closeables
	 *            �ɹرյ��������б�
	 * @throws IOException
	 */
	public static void close(Closeable... closeables) throws IOException {
		if (closeables != null) {
			for (Closeable closeable : closeables) {
				if (closeable != null) {
					closeable.close();
				}
			}
		}
	}

	/**
	 * �ر�һ������������
	 * 
	 * @param closeables
	 *            �ɹرյ��������б�
	 */
	public static void closeQuietly(Closeable... closeables) {
		try {
			close(closeables);
		} catch (IOException e) {
			// do nothing
		}
	}

}