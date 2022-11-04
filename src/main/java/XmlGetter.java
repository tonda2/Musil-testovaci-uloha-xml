import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

public class XmlGetter {
    /**
     * Method to fetch file from given URL and save and unzip it to given locations
     * @param fileUrl URL to download from
     * @param zipPath where to store downloaded file, existing file of given name will be deleted
     * @param unzipPath where to store unzipped file
     * @throws IOException if any file can't be read/written, exception is thrown
     */
    public void getFile(String fileUrl, String zipPath, String unzipPath) throws IOException{
        final Path target = Paths.get(zipPath);
        Files.deleteIfExists(target);
        Files.copy(
                new URL(fileUrl).openStream(),
                target);

        byte[] buffer = new byte[1024];
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
        FileOutputStream fileOutputStream = new FileOutputStream(unzipPath);
        zipInputStream.getNextEntry();

        int len;
        while ((len = zipInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, len);
        }

        fileOutputStream.close();
    }
}
