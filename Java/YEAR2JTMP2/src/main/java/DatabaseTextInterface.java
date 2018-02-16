
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import org.apache.commons.collections4.list.GrowthList;

/**
 *
 * @author EEU211
 */
public class DatabaseTextInterface
{
    String userInput;
    Scanner in;
    DatabaseManager dbManager;
    
    /**
     * Default constructor starts the CLI, displaying the main menu and prompting
     * the user for input
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public DatabaseTextInterface() throws SQLException, IOException, ClassNotFoundException
    {
        in = new Scanner(System.in);
        dbManager = new DatabaseManager();
        Boolean end = false;
        while(!end)
        {
            System.out.println("Main Menu");
            System.out.println("********************");
            System.out.println("1. Students");
            System.out.println("2. Modules");
            System.out.println("3. Registrations");
            System.out.println("4. Reports");
            System.out.println("0. Quit");
            System.out.print(":> ");
            userInput = in.next();
            
            switch(userInput)
            {
                case "1":
                    subMenu("student");
                    break;
                    
                case "2":
                    subMenu("module");
                    break;
                    
                case "3":
                    subMenu("registered");
                    break;
                    
                case "4":
                    reportMenu();
                    break;
                    
                case "0":
                    end = true;
                    break;
                    
                default:
                    System.out.println("Invalid input");
            }
        }
    }
    
    /**
     * Sub menu for the first 3 choices of the main menu
     * Displays standard choices relevant to all basic database functions
     * @param menuType String passed in to the method to allow distinct menus
     * for each menu choice
     * @throws SQLException 
     */
    public void subMenu(String menuType) throws SQLException
    {
        Boolean endSub = false;
        
        while(!endSub)
        {
                System.out.println("Sub-Menu (" + menuType + ")");
                System.out.println("***************");
                System.out.println("1. Add " + menuType);
                System.out.println("2. Remove " + menuType);
                System.out.println("3. Update " + menuType);
                System.out.println("4. List " + menuType);
                System.out.println("0. Return to main menu");
                System.out.print(":> ");

                userInput = in.next();
                String id = "";
                String changeVal = "";
                
                switch(userInput)
                {
                    case "1":
                        String name = "";
                        String other = "";
                        switch(menuType)
                        {
                            case "registered":
                                System.out.print("Input student ID: ");
                                id = in.next();
                                System.out.print("Input module ID: ");
                                name = in.next();
                                dbManager.addToDB(id, name);
                                break;

                            default:
                                System.out.print("Input ID: ");
                                id = in.next();
                                System.out.print("Input name: ");
                                name = in.next();
                                switch(menuType)
                                {
                                    case "student":
                                        System.out.print("Input degree scheme: ");
                                        break;

                                    case "module":
                                        System.out.print("Input credits: ");
                                        break;
                                }
                                other = in.next();
                                dbManager.addToDB(menuType, id, name, other);
                                break;
                        }
                        break;

                    case "2":
                        switch(menuType)
                        {
                            case "student":
                                System.out.print("Input ID of student to "
                                        + "be deleted: ");
                                dbManager.removeFromDB(menuType, in.next());
                                break;

                            case "module":
                                System.out.print("Input ID of module to "
                                        + "be deleted: ");
                                dbManager.removeFromDB(menuType, in.next());
                                break;

                            case "registered":
                                System.out.print("Input student ID of "
                                        + "registry to be deleted: ");
                                String id1 = in.next();
                                System.out.print("Input module ID of "
                                        + "registry to be deleted: ");
                                dbManager.removeRegistryFromDB(id1, in.next());
                                break;
                        }
                        break;

                    case "3":
                        String updateBy;
                        switch(menuType)
                        {
                            case "student":
                                System.out.print("Enter ID of student to "
                                        + "be updated: ");
                                id = in.next();
                                System.out.println("What is to be updated?");
                                System.out.println("1. Student name");
                                System.out.println("2. Degree scheme");
                                System.out.print(":> ");
                                updateBy = in.next();

                                switch (updateBy)
                                {
                                    case "1":
                                        updateBy = "student_name";
                                        System.out.print("Enter new name: ");
                                        in.nextLine();
                                        changeVal = in.nextLine();
                                        dbManager.updateDB(menuType, id, updateBy, changeVal);
                                        break;

                                    case "2":
                                        updateBy = "degree_scheme";
                                        System.out.print("Enter new scheme: ");
                                        in.nextLine();
                                        changeVal = in.nextLine();
                                        dbManager.updateDB(menuType, id, updateBy, changeVal);
                                        break;
                                }
                                break;

                            case "module":
                                System.out.print("Enter ID of module to "
                                        + "be updated: ");
                                id = in.next();
                                System.out.println("What is to be updated?");
                                System.out.println("1. Module name");
                                System.out.println("2. Credits");
                                System.out.print(":> ");
                                updateBy = in.next();

                                switch (updateBy)
                                {
                                    case "1":
                                        updateBy = "module_name";
                                        System.out.print("Enter new name: ");
                                        dbManager.updateDB(menuType, id, updateBy, in.next());
                                        break;

                                    case "2":
                                        updateBy = "credits";
                                        System.out.print("Enter new credit amount: ");
                                        dbManager.updateDB(menuType, id, updateBy, in.next());
                                        break;
                                }
                                break;

                            case "registered":
                                System.out.println("No update possible");
                                break;
                        }
                        break;

                    case "4":
                        showList(dbManager.getList(menuType));
                        break;

                    case "0":
                        endSub = true;
                        break;
            }
        }
    }
    
    /**
     * A distinct sub menu for the reports option of the main menu
     * @throws SQLException 
     */
    public void reportMenu() throws SQLException
    {
        Boolean endRepMenu = false;
        while (!endRepMenu)
        {
            System.out.println("Reports");
            System.out.println("*******");
            System.out.println("1. Modules taught by");
            System.out.println("2. Students registered on");
            System.out.println("3. Staff who teach student");
            System.out.println("4. Staff who teach more than");
            System.out.println("0. Return to main menu");
            System.out.print(":> ");
            userInput = in.next();
            
            switch (userInput)
            {
                case "1":
                    System.out.print("Member of staff: ");
                    in.nextLine();
                    showComplexList(dbManager.modulesTaught(in.nextLine()));
                    break;
                    
                case "2":
                    System.out.print("Module name: ");
                    in.nextLine();
                    showComplexList(dbManager.studentsRegistered(in.nextLine()));
                    break;
                    
                case "3":
                    System.out.print("Student name: ");
                    in.nextLine();
                    showComplexList(dbManager.staffTeaches(in.nextLine()));
                    break;
                    
                case "4":
                    System.out.print("Module count: ");
                    showComplexList(dbManager.staffMoreThan(in.next()));
                    break;
                    
                case "0":
                    endRepMenu = true;
                    break;
            }
        }
    }
    
    /**
     * Prints lists retrieved from the database
     * @param list The GrowthList passed into the method to be deconstructed
     * @throws SQLException 
     */
    public void showList(GrowthList list) throws SQLException
    {
        if ("module_ID".equals(list.get(1)))
        {
            System.out.println(list.get(0) + "\t" + list.get(1));
            System.out.println("**********\t**********");
            for (int i = 2; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t" + list.get(++i));
            }
        }
        
        else if ("student_name".equals(list.get(1)))
        {
            System.out.println(list.get(0) + "\t\t" + list.get(1) + "\t\t"
                    + list.get(2));
            System.out.println("*************\t\t*************\t\t*************");
            for (int i = 3; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t\t" + list.get(++i) + "\t\t"
                + list.get(++i));
            }
        }
        
        else if ("module_name".equals(list.get(1)))
        {
            System.out.println(list.get(0) + "\t\t" + list.get(1) + "\t\t\t\t"
                    + list.get(2));
            System.out.println("*************\t\t*************\t\t\t\t*************");
            for (int i = 3; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t\t" + list.get(++i) + "\t\t"
                + list.get(++i));
            }
        }
    }
    
    /**
     * Performs similarly to the showList method, but is called for the reports
     * sub menu choices
     * @param list GrowthList passed in to be deconstructed
     * @throws SQLException 
     */
    public void showComplexList(GrowthList list) throws SQLException
    {
        if ("module_ID".equals(list.get(2)))
        {
            System.out.println(list.get(0) + "\t\t" + list.get(1) + "\t\t"
                    + list.get(2));
            System.out.println("**********\t\t**********\t\t**********");
            for (int i = 3; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t\t" + list.get(++i) + "\t\t"
                + list.get(++i));                
            }
        }
        
        else if ("module_name".equals(list.get(1)))
        {
            System.out.println(list.get(0) + "\t\t" + list.get(1));
            System.out.println("**********\t\t**********");
            for (int i = 2; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t\t" + list.get(++i));                
            }
        }
        
        else if ("student_name".equals(list.get(1)))
        {
            System.out.println(list.get(0) + "\t\t" + list.get(1));
            System.out.println("**********\t\t**********");
            for (int i = 2; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t\t" + list.get(++i));                
            }
        }
        
        else if ("staff_name".equals(list.get(1)))
        {
            System.out.println(list.get(0) + "\t\t" + list.get(1));
            System.out.println("**********\t\t**********");
            for (int i = 2; i < list.size(); i++)
            {
                System.out.println(list.get(i) + "\t\t\t" + list.get(++i));                
            }
        }
    }
}
