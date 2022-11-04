import java.sql.SQLException;

public class VillageInfo {
    private final static String FILE_URL = "https://www.smartform.cz/download/kopidlno.xml.zip";
    private final static String ZIP_PATH = "./zipped_data.zip";
    private final static String UNZIP_PATH = "./unzipped_data.xml";

    /**
     * Simple example use of app
     * Calls getFile to download and unzip input file
     * Then calls parse to pick out data and store it in DB
     */
    public static void main (final String[] args) {
        XmlGetter xmlGetter = new XmlGetter();

        DatabaseController databaseController;
        try {
            databaseController = new DatabaseController();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        XmlParser xmlParser = new XmlParser(databaseController);

        try {
            databaseController.emptyTables();
            xmlGetter.getFile(FILE_URL, ZIP_PATH, UNZIP_PATH);
            xmlParser.parse(UNZIP_PATH);
            databaseController.print();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}