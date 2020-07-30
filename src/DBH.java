import java.util.*;
import java.sql.*;
import java.time.*;

/**
 * DBH
 */
public class DBH
{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/steam";
    
    static final String USER = "root";
    static final String PASS = ""; // Check

    static Scanner scan = new Scanner(System.in);
    static Connection connection = null;
    static Statement statement = null;

    public void initialize()
    {
        try
        {
            Class.forName(JDBC_DRIVER);
            
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            statement = connection.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void displayGames()
    {
        System.out.println("Would you like to:");
        System.out.println("1. View all games");
        System.out.println("2. Search game by name");
        System.out.println("3. Search game by genre");

        int option = scan.nextInt();
        scan.nextLine();
        try
        {
            String query = null;
            if(option==1)
                query = "select * from Games";
            else if(option==2)
            {
                System.out.println("Enter the name of game: ");
                String name = scan.nextLine();
                query = "select * from Games where title = '" + name + "'";
            } 
            else if(option==3)
            {
                System.out.println("Enter the genre: ");
                String gen = scan.nextLine();
                query = "select * from Games where genre = '" + gen + "'";
            }
            ResultSet rSet = statement.executeQuery(query);
            while(rSet.next())
            {
                String result = rSet.getString("title");
                System.out.println(result);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void changePassword(String uName)
    {
        System.out.println("Enter your new password: ");
        String pass = scan.nextLine();

        try
        {
            String query = "update Customer set pass = '" + pass + "' where username = '" + uName + "'";
            statement.executeUpdate(query);
            System.out.println("Password changed");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminate()
    {
       try
       {
            if(statement!=null)
                statement.close();
            if(connection!=null)
                connection.close();
       } catch (SQLException e) {
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    public void createAccount()
    {
        try
        {
            System.out.println("Enter the username: ");
            String uName = scan.nextLine();

            String query = "select username from Customer";
            ResultSet rSet = statement.executeQuery(query);
            while(rSet.next())
            {
                String user = rSet.getString("username");
                if(user.equals(uName))
                {
                    System.out.println("Username exists already.");
                    return;
                }
            }

            query = "select max(cust_id) from Customer";
            rSet = statement.executeQuery(query);
            int max = 0;
            while(rSet.next())
            {
                max = rSet.getInt(1);
            }
            max += 1;

            System.out.println("Enter the password: ");
            String pass = scan.nextLine();
            query = "insert into Customer values ("+max+",'"+uName+"','"+pass+"',1)";
            statement.executeUpdate(query);
            System.out.println("Account created!");
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public String login()
    {
        System.out.println("Enter the username: ");
        String uName = scan.nextLine();
        System.out.println("Enter the password: ");
        String pass = scan.nextLine();
        try
        {
            String query = "select username,pass from Customer";
            ResultSet rSet = statement.executeQuery(query);
            int check = 0;
            while(rSet.next())
            {
                String user = rSet.getString("username");
                String password = rSet.getString("pass");
                if(user.equals(uName) && password.equals(pass))
                {
                    System.out.println("Login Successful!");
                    check+=1;
                }
            }
            if(check==0)
            {
                System.out.println("Unable to find user!");
                uName =  null;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return uName;
    }

    public void buyGame(String user)
    {
        try
        {
            System.out.println("Enter the name of the game: ");
            String gName = scan.nextLine();
            String query = "select game_id,title from Games";
            ResultSet rSet = statement.executeQuery(query);
            int check = 0;
            int g_id=0;
            while(rSet.next())
            {
                String game = rSet.getString("title");
                if(game.equals(gName))
                {
                    check+=1;
                    g_id = rSet.getInt("game_id");
                }
            }
            if(check!=0)
            {
                query = "select count(og_id) from Owned_Games";
                rSet = statement.executeQuery(query);
                int max=0;
                while(rSet.next())
                {
                    max = rSet.getInt(1);
                    max+=1;
                }
                query = "select cust_id from Customer where username = '"+user+"'";
                rSet = statement.executeQuery(query);
                int id=0;
                while(rSet.next())
                {
                    id = rSet.getInt("cust_id");
                }
                query = "insert into Owned_Games values ("+max+","+id+","+g_id+")";
                statement.executeUpdate(query);
                System.out.println("Game added successfully!");
            }
            else if(check==0)
                System.out.println("Game not found!");
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void viewCustGames(String uName)
    {
        try
        {
            String query = "select cust_id from Customer where username = '"+uName+"'";
            ResultSet rSet = statement.executeQuery(query);
            int id=0;
            while(rSet.next())
            {
                id = rSet.getInt("cust_id");
            }
            query = "select title from Games g,Owned_Games og where g.game_id = og.g_id and og.c_id = " + id;
            rSet = statement.executeQuery(query);
            while(rSet.next())
            {
                String game = rSet.getString("title");
                System.out.println(game);
            }

        } catch (SQLException e) {
           e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}