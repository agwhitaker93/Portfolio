

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.commons.collections4.list.GrowthList;

/**
 *
 * @author EEU211
 */
public class DatabaseManager
{
    private static String url;
    private static String username;
    private static String password;
    
    /**
     * Default constructor for the manager to intialize the SimpleDataSource
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public DatabaseManager() throws IOException, ClassNotFoundException
    {
        InputStream stream = DatabaseManager.class.getResourceAsStream("/database.properties");
        SimpleDataSource.init(stream);
    }
    
    /**
     * Helper method for the Manager to established a connection to the MySQL server
     * @return
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException
    {
      return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Retrieves a database ResultSet
     * @param listType The database table to be retrieved from
     * @return The ResultSet stored in a GrowthList
     * @throws SQLException 
     */
    public GrowthList getList(String listType) throws SQLException
    {
        GrowthList gList = new GrowthList();
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            String command = "SELECT * FROM " + listType;
            ResultSet rs = st.executeQuery(command);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int rsColumnCount = rsMetaData.getColumnCount();
                for (int i = 1; i <= rsColumnCount; i++)
                {
                    gList.add(rsMetaData.getColumnName(i));
                }
            while(rs.next())
            {
                for (int i = 1; i <= rsColumnCount; i++)
                {
                    gList.add(rs.getString(i));
                }
            }
        } finally {
            st.close();
            conn.close();
        }
        return gList;
    }
    
    /**
     * Adds an entry to a given database
     * @param dbName The database to be added to
     * @param id The ID number of the new entry
     * @param name The name of the new entry
     * @param other The additional variable to be added.
     * It is turned into an int if adding a module
     * @throws SQLException 
     */
    public void addToDB(String dbName, String id, String name, String other)
            throws SQLException
    {
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            //create string with variable parts
            String command;
            switch(dbName)
            {
                case "student":
                    command = "INSERT INTO student (`student_ID`,`student_name`,"
                            + "`degree_scheme`) VALUES('" + id + "','" + name +
                            "','" + other + "')";
                    break;
                    
                case "module":
                    command = "INSERT INTO module (`module_ID`,`module_name`,"
                            + "`credits`) VALUES('" + id + "','" + name + "','"
                            + Integer.parseInt(other) + "')";
                    break;
                    
                default:
                    command = "";
                    break;
            }
            st.execute(command);
        } finally {
            st.close();
            conn.close();
        }
    }
    
    /**
     * Adds an entry to the registered database
     * @param id1 The Student ID of the new entry
     * @param id2 The Module ID of the new entry
     * @throws SQLException 
     */
    public void addToDB(String id1, String id2)
            throws SQLException
    {
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            //create string with variable parts
            String command = "INSERT INTO registered (`student_ID`,`module_ID`)"
                    + " VALUES('" + id1 + "','"  + id2+ "')";
            st.execute(command);
        } finally {
            st.close();
            conn.close();
        }
    }
    
    /**
     * Removes an entry from a given database
     * @param dbName The table to be removed from
     * @param id The ID of the entry to be removed
     * @throws SQLException 
     */
    public void removeFromDB(String dbName, String id) throws SQLException
    {
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            //create string with variable parts
            String command = "";
            switch (dbName)
            {
                case "student":
                    command = "DELETE FROM student WHERE `student_ID` = '"
                            + id + "'";
                    break;
                    
                case "module":
                    command = "DELETE FROM module WHERE `module_ID` = '"
                            + id + "'";
                    break;
            }
            st.execute(command);
        } finally {
            st.close();
            conn.close();
        }
    }
    
    /**
     * Removes an entry from the registered table
     * @param id1 The Student ID of the entry to be removed
     * @param id2 The Module ID of the entry to be removed
     * @throws SQLException 
     */
    public void removeRegistryFromDB(String id1, String id2)
            throws SQLException
    {
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            //create string with variable parts
            String command = "DELETE FROM registered WHERE "
                    + "(`student_ID`,`module_ID`) = "
                            + "('" + id1 + "','" + id2 + "')";
            st.execute(command);
        } finally {
            st.close();
            conn.close();
        }
    }
    
    /**
     * Updates an entry of a database table
     * @param dbName The database to be updated
     * @param id The ID number of the entry to be updated
     * @param toUpdate The column to be updated
     * @param newVal The new value for the column
     * @throws SQLException 
     */
    public void updateDB(String dbName, String id, String toUpdate, String newVal)
            throws SQLException
    {
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            //create string with variable parts
            String command = "";
            
            switch(dbName)
            {
                case "student":
                    command = "UPDATE `student` SET `" + toUpdate + "` = '" + newVal
                            + "' WHERE `student_ID` = '" + id + "'";
                    System.out.println(newVal);
                    break;
                    
                case "module":
                    command = "UPDATE `module` SET `" + toUpdate + "` = '" + newVal
                            + "' WHERE `module_ID` = '" + id + "'";
                    break;
                    
                case "registered":
                    break;
            }
            st.execute(command);
        } finally {
            st.close();
            conn.close();
        }        
    }
    
    /**
     * Gets a list of all modules taught by a given member of staff
     * @param name The member of staff to search for
     * @return A GrowthList of the selected list
     * @throws SQLException 
     */
    public GrowthList modulesTaught(String name) throws SQLException
    {
        GrowthList gList = new GrowthList();
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            String command = "SELECT staff_ID FROM `staff` WHERE '" + name + "' = staff_name;";
            ResultSet rs = st.executeQuery(command);
            
            String id = "";
            if(rs.next())
                id = rs.getString(1);
            
            command = "SELECT module.module_ID, module.module_name "
                    + "FROM `staff` "
                    + "JOIN `teaches` on staff.staff_ID = teaches.staff_ID and staff.staff_ID = '" + id + "' "
                    + "JOIN `module` on teaches.module_ID = module.module_ID";
            rs = st.executeQuery(command);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int rsColumnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= rsColumnCount; i++)
            {
                gList.add(rsMetaData.getColumnName(i));
            }
            while(rs.next())
            {
                for (int i = 1; i <= rsColumnCount; i++)
                {
                    gList.add(rs.getString(i));
                }
            }
        } finally {
            st.close();
            conn.close();
        }
        return gList;
    }
    
    /**
     * Gets a list of all students registered to a given module
     * @param name The module to be searched for
     * @return A GrowthList of the selected list
     * @throws SQLException 
     */
    public GrowthList studentsRegistered(String name) throws SQLException
    {
        GrowthList gList = new GrowthList();
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            String command = "SELECT module_ID FROM `module` WHERE '" + name + "' = module_name;";
            ResultSet rs = st.executeQuery(command);
            
            String id = "";
            if(rs.next())
                id = rs.getString(1);
            
            command = "SELECT student.student_ID, student.student_name "
                    + "FROM `module` "
                    + "JOIN `registered` on module.module_ID = registered.module_ID and module.module_ID = '" + id + "' "
                    + "JOIN `student` on registered.student_ID = student.student_ID";
            rs = st.executeQuery(command);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int rsColumnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= rsColumnCount; i++)
            {
                gList.add(rsMetaData.getColumnName(i));
            }
            while(rs.next())
            {
                for (int i = 1; i <= rsColumnCount; i++)
                {
                    gList.add(rs.getString(i));
                }
            }
        } finally {
            st.close();
            conn.close();
        }
        return gList;
    }
    
    /**
     * Gets a list of all members of staff taught by a given student
     * @param name The student to be searched for
     * @return A GrowthList of the selected list
     * @throws SQLException 
     */
    public GrowthList staffTeaches(String name) throws SQLException
    {
        GrowthList gList = new GrowthList();
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            String command = "SELECT student_ID FROM `student` WHERE '" + name + "' = student_name;";
            ResultSet rs = st.executeQuery(command);
            
            String id = "";
            if(rs.next())
                id = rs.getString(1);
            
            command = "SELECT staff.staff_ID, staff.staff_name, registered.module_ID "
                    + "FROM `student` "
                    + "JOIN `registered` on student.student_ID = registered.student_ID and student.student_ID = '" + id + "' "
                    + "JOIN `teaches` on registered.module_ID = teaches.module_ID "
                    + "JOIN `staff` on teaches.staff_ID = staff.staff_ID";
            rs = st.executeQuery(command);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int rsColumnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= rsColumnCount; i++)
            {
                gList.add(rsMetaData.getColumnName(i));
            }
            while(rs.next())
            {
                for (int i = 1; i <= rsColumnCount; i++)
                {
                    gList.add(rs.getString(i));
                }
            }
        } finally {
            st.close();
            conn.close();
        }
        return gList;
    }
    
    /**
     * Gets a list of all members of staff who teach more than a given number
     * of modules
     * @param val The number to be evaluated against
     * @return A GrowthList of the selected members of staff
     * @throws SQLException 
     */
    public GrowthList staffMoreThan(String val) throws SQLException
    {
        GrowthList gList = new GrowthList();
        Connection conn = SimpleDataSource.getConnection();
        Statement st = conn.createStatement();
        try
        {
            String command = "SELECT staff.staff_ID, staff.staff_name\n" +
                    "FROM `teaches`\n" +
                    "JOIN `staff` on teaches.staff_ID = staff.staff_ID\n" +
                    "GROUP BY staff.staff_ID\n" +
                    "HAVING COUNT(*) > " + Integer.parseInt(val);
            ResultSet rs = st.executeQuery(command);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int rsColumnCount = rsMetaData.getColumnCount();
            for (int i = 1; i <= rsColumnCount; i++)
            {
                gList.add(rsMetaData.getColumnName(i));
            }
            while(rs.next())
            {
                for (int i = 1; i <= rsColumnCount; i++)
                {
                    gList.add(rs.getString(i));
                }
            }
        } finally {
            st.close();
            conn.close();
        }
        return gList;
    }
}
