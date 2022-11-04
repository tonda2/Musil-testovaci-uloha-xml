import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Wrapper class responsible for handling xml parsing
 */
public class XmlParser {
    private final static String VILLAGE_TAG = "vf:Obec";
    private final static String CODE_TAG = "obi:Kod";
    private final static String VILLAGE_PART_TAG = "vf:CastObce";
    private final static String VILLAGE_NAME_TAG = "obi:Nazev";
    private final static String VILLAGE_PART_NAME_TAG = "coi:Nazev";
    private final static String VILLAGE_PART_CODE_TAG = "coi:Kod";
    private final static String VILLAGE_PART_VILLAGE_CODE_TAG = "coi:Obec";

    DatabaseController databaseController;

    /**
     * @param databaseController controller requires existing database connection in form of databaseController class
     */
    public XmlParser(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    /**
     * Finds all village occurrences in given xml file, inserts info into database
     * @param path path to xml file
     * @throws Exception if the file cannot be read or database doesn't respond
     */
    void parseVillage (String path) throws Exception {
        File xmlFile = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        NodeList nodeList = document.getElementsByTagName(VILLAGE_TAG);
        Element element = (Element) nodeList.item(0);

        databaseController.insertIntoVillage(
                Integer.parseInt(element.getElementsByTagName(CODE_TAG).item(0).getTextContent()),
                element.getElementsByTagName(VILLAGE_NAME_TAG).item(0).getTextContent());
    }

    /**
     * Finds all village part occurrences in given xml file, inserts info into database
     * @param path path to xml file
     * @throws Exception if the file cannot be read or database doesn't respond
     */
    void parseVillagePart (String path) throws Exception {
        File xmlFile = new File(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        NodeList nodeList = document.getElementsByTagName(VILLAGE_PART_TAG);
        for (int i = 0; i < nodeList.getLength(); ++ i) {
            Element villagePartElement = (Element) nodeList.item(i);
            Element villageCodeElement = (Element) villagePartElement.getElementsByTagName(VILLAGE_PART_VILLAGE_CODE_TAG).item(0);

            databaseController.insertIntoVillagePart(
                    Integer.parseInt(villagePartElement.getElementsByTagName(VILLAGE_PART_CODE_TAG).item(0).getTextContent()),
                    villagePartElement.getElementsByTagName(VILLAGE_PART_NAME_TAG).item(0).getTextContent(),
                    Integer.parseInt(villageCodeElement.getElementsByTagName(CODE_TAG).item(0).getTextContent())
            );
        }
    }

    /**
     * Wrapper for two separate parsing methods
     * @param path path to xml file
     * @throws Exception passed from called methods
     */
    void parse (String path) throws Exception {
        this.parseVillage(path);
        this.parseVillagePart(path);
    }
}
