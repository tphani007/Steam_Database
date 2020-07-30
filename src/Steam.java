import java.util.* ;

public class Steam
{
    public static void startScreen()
    {
        System.out.println("\nWelcome to Steam\nLet's get started by selecting any of the following options:");
        System.out.println("1.Login");
        System.out.println("2.Sign Up");
        System.out.println("3.Exit");
        System.out.print("Please select your action: ");
    }

    public static void userScreen()
    {
        System.out.println("\n1.Search game");
        System.out.println("2.Purchase game");
        System.out.println("3.View Games Owned");
        System.out.println("4.Change Password");
        System.out.println("5.Logout");
        System.out.print("Choose wisely: ");
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        DBH dbh = new DBH();
        
        dbh.initialize();

        while(true)
        {
            startScreen();
            int choice = scanner.nextInt();
            switch (choice)
            {
                case 1:
                    String userString = dbh.login();
                    System.out.println(userString);
                    while(userString!=null)
                    {
                        userScreen();
                        int option = scanner.nextInt();
                        switch (option)
                        {
                            case 1:
                                dbh.displayGames();
                                break;
                            case 2:
                                dbh.buyGame(userString);
                                break;
                            case 3:
                                dbh.viewCustGames(userString);
                                break;
                            case 4:
                                dbh.changePassword(userString);
                                break;
                            case 5:
                                userString = null;
                                break;
                            default:
                                System.out.println("Please choose a valid option");
                                break;
                        }
                    }
                    break;
                case 2:
                    dbh.createAccount();
                    break;
                case 3:
                    System.out.println("Thank you for using Steam.");
                    dbh.terminate();
                    System.exit(0);
        
                default:
                    System.out.println("Please enter a valid option.");
                    break;
            }
        }
    }
}