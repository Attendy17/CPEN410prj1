//This class belongs to the ut.JAR.CPEN410 package
package ut.JAR.CPEN410;

import java.sql.*;

/******
    This class manages a connection to the database and should not be accessed directly from the front end.
*/
public class MySQLCompleteConnector {

    // Database credential. Actualizamos el nombre de la base de datos a 'cpen410p1'
    private String DB_URL = "jdbc:mysql://localhost/cpen410p1";

    // Database authorized user information
    private String USER = "student";
    private String PASS = "password";

    // Connection objects
    private Connection conn;
    private Statement stmt;

    /********
        Default constructor.
    */
    public MySQLCompleteConnector() {
        conn = null;
        stmt = null;
    }

    /********
        doConnection method.
        Creates a new connection to the database.
    */
    public void doConnection() {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Connecting to database...");
            // Open a connection using the database credentials
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            System.out.println("Statement OK...");
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /********
        closeConnection method.
        Closes the connection to the database.
    */
    public void closeConnection() {
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***********
        doSelect method.
        Executes a SELECT query with a WHERE clause.
    */
    public ResultSet doSelect(String fields, String tables, String where) {
        ResultSet result = null;
        String selectionStatement = "Select " + fields + " from " + tables + " where " + where + " ;";
        System.out.println(selectionStatement);
        try {
            result = stmt.executeQuery(selectionStatement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /***********
        doSelect method without WHERE clause.
    */
    public ResultSet doSelect(String fields, String tables) {
        ResultSet result = null;
        String selectionStatement = "Select " + fields + " from " + tables + ";";
        System.out.println(selectionStatement);
        try {
            result = stmt.executeQuery(selectionStatement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /***********
        doSelect method with ORDER BY.
    */
    public ResultSet doSelect(String fields, String tables, String where, String orderBy) {
        ResultSet result = null;
        String selectionStatement = "Select " + fields + " from " + tables + " where " + where + " order by " + orderBy + ";";
        try {
            result = stmt.executeQuery(selectionStatement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /***********
        doInsert method.
        Executes an INSERT statement.
    */
    public boolean doInsert(String table, String values) {
        boolean res = false;
        String charString = "INSERT INTO " + table + " values (" + values + ");";
        System.out.println(charString);
        try {
            res = stmt.execute(charString);
            System.out.println("MySQLCompleteConnector insertion: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    /***********
        Debugging method.
    */
    public static void main(String[] args) {
        System.out.println("Testing");
        MySQLCompleteConnector conn = new MySQLCompleteConnector();
        String fields = "dept_name, building";
        String tables = "department";
        String whereClause = "budget>1000";

        try {
            System.out.println("Connecting...");
            conn.doConnection();
            ResultSet res = conn.doSelect(fields, tables, whereClause);
            int count = 0;
            while (res.next()) {
                count++;
            }
            System.out.println("Count: " + count);
            res.close();
            conn.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
