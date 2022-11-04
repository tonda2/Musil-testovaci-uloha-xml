import java.sql.*;

/**
 * Simple database wrapper with easy to use insert methods
 */
public class DatabaseController {
    private final static String DB_URL = "jdbc:h2:./src/data/villageDB";
    private final static String villageSqlInsert = "insert into village values (?, ?)";
    private final static String villagePartSqlInsert = "insert into village_part values (?, ?, ?)";
    private final static String selectAllVillage = "select * from village";
    private final static String selectAllVillagePart = "select * from village_part";

    Connection connection;

    /**
     * New connection to database is created
     * @throws SQLException if database isn't available
     */
    public DatabaseController() throws SQLException {
            connection = DriverManager.getConnection(DB_URL);
    }

    /**
     * Simply removes all data from both used tables
     * @throws SQLException if a database access error occurs
     */
    void emptyTables () throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from village");
        statement.executeUpdate("delete from village_part");
    }

    /**
     * Inserts new row to village table
     * @param id to be inserted
     * @param name to be inserted
     * @throws SQLException if a database access error occurs
     */
    void insertIntoVillage (int id, String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(villageSqlInsert);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.executeUpdate();
    }

    /**
     * Inserts new row to village_part table
     * @param id to be inserted
     * @param name to be inserted
     * @param villageId villageId to be inserted
     * @throws SQLException if a database access error occurs
     */
    void insertIntoVillagePart (int id, String name, int villageId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(villagePartSqlInsert);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setInt(3, villageId);
        statement.executeUpdate();
    }

    /**
     * Prints all rows of given database
     * @param tableName table to be printed
     * @throws SQLException if a database access error occurs
     */
    void print (String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                tableName.equals("village") ? selectAllVillage
                                            : selectAllVillagePart);

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + resultSet.getString(2));
        }
    }
}
