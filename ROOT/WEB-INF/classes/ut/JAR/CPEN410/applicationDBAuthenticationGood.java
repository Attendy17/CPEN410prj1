//This class belongs to the ut.JAR.CPEN410 package
package ut.JAR.CPEN410;

import java.sql.*;
//Import hashing functions
import org.apache.commons.codec.digest.DigestUtils;

/******
    This class authenticates users using email and password.
*/
public class applicationDBAuthenticationGoodComplete {

    // myDBConn is an MySQLCompleteConnector object for accessing the database
    private MySQLCompleteConnector myDBConn;

    /********
        Default constructor.
        It creates a new MySQLCompleteConnector object and opens a connection to the database.
    */
    public applicationDBAuthenticationGoodComplete() {
        // Create the MySQLCompleteConnector object
        myDBConn = new MySQLCompleteConnector();
        // Open the connection to the database
        myDBConn.doConnection();
    }

    /*******
        authenticate method.
        @param userName: the user's email (used as login username)
        @param userPass: the user's plain text password
        @return: A ResultSet containing the email, role_id and name of the authenticated user.
    */
    public ResultSet authenticate(String userName, String userPass) {
        // Declare function variables
        String fields, tables, whereClause, hashingVal;

        // Use new table names:
        // Table 'users' and join with 'user_roles' via the user id.
        tables = "users, user_roles";
        // Project email, role_id and name.
        fields = "users.email, user_roles.role_id, users.name";
        // Generate hash using email + password (la misma forma que al insertar el usuario)
        hashingVal = hashingSha256(userName + userPass);
        // Join condition: matching the user id and filtering por email and hashed password.
        whereClause = "users.id = user_roles.user_id AND users.email = '" 
                      + userName + "' AND users.password = '" + hashingVal + "'";

        System.out.println("Authenticating user...");
        // Return the ResultSet with the query
        return myDBConn.doSelect(fields, tables, whereClause);
    }

    /*******
        menuElements method.
        (No ajustado para el nuevo esquema; se usa en flujos posteriores)
    */
    public ResultSet menuElements(String userName) {
        System.out.println("menuElements method not implemented for the new schema.");
        return null;
    }

    /*******
        verifyUser method.
        (No ajustado para el nuevo esquema, ya que se usará en funcionalidades posteriores)
    */
    public ResultSet verifyUser(String userName, String currentPage, String previousPage) {
        System.out.println("verifyUser method not adjusted for the new schema.");
        return null;
    }

    /*******
        addUser method.
        Para el signup, inserta un usuario en la tabla 'users' con valores por defecto para 
        birth_date y gender (en este ejemplo, '1999-01-19' y 'Male'). Se ignoran otros campos.
        @param userName: se utiliza como email
        @param completeName: nombre completo del usuario
        @param userPass: contraseña en texto plano
        @param userTelephone: se ignora (no existe en la nueva tabla)
        @return: true si la inserción fue exitosa.
    */
    public boolean addUser(String userName, String completeName, String userPass, String userTelephone) {
        String table, values, hashingValue;
        hashingValue = hashingSha256(userName + userPass);
        table = "users";
        // La tabla 'users' tiene: id, name, email, password, birth_date, gender, profile_picture, last_page_id, created_at.
        // Se usan valores por defecto para: id (NULL, auto), birth_date ('1999-01-19'), gender ('Male'), profile_picture y last_page_id (NULL)
        values = "NULL, '" + completeName + "', '" + userName + "', '" + hashingValue 
                 + "', '1999-01-19', 'Male', NULL, NULL";
        boolean res = myDBConn.doInsert(table, values);
        System.out.println("Insertion result: " + res);
        return res;
    }

    /*********
        hashingSha256 method.
        Generates a hash value using the sha256 algorithm.
        @param plainText: text to hash.
        @return: the SHA-256 hash of plainText.
    */
    private String hashingSha256(String plainText) {
        return DigestUtils.sha256Hex(plainText);
    }

    /*********
        close method.
        Closes the connection to the database.
    */
    public void close() {
        myDBConn.closeConnection();
    }
}
