import org.example.dal.DAL;
import org.example.dal.model.UserModel;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

public class Entry {
    public void login() throws IOException {
        DAL dal = new DAL("jack", "pass");
        Scanner scanner = new Scanner(System.in);
        String password, username = null;

        System.out.println("Welcome to Spaghetti Hell, do you want to Login? (Y / n)");
        String inp = scanner.nextLine();

        while(!("y".equalsIgnoreCase(inp) || "n".equalsIgnoreCase(inp))){
            System.out.println("Invalid input please provide (Y / n) as answer, press ESC to exit.");

            if(System.in.read() == KeyEvent.VK_ESCAPE) return;
        }

        if ("n".equalsIgnoreCase(inp)) {
            registerNewUser(dal, scanner, username);
            return; //terminate function
        }

        boolean isSuccess = false;

        while(!isSuccess){
            System.out.println("Enter username: ");
            username = scanner.nextLine();
            System.out.println("Enter Password: ");
            password = scanner.nextLine();
            isSuccess = dal.login(username,password);
        }
        System.out.printf("Welcome %s \n", username);
    }

    private static void registerNewUser(DAL dal, Scanner scanner, String username) {
        String password;
        System.out.println("Do you want to register a new user ?");

        if("y".equalsIgnoreCase(scanner.nextLine())){
            boolean isRegisterd = false;
            while(! isRegisterd) {
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Username: ");
                username = scanner.nextLine();
                System.out.println("Password: ");
                password = scanner.nextLine();
                isRegisterd = dal.register(new UserModel(username,email,null), password);
            }
        }
        System.out.printf("Welcome %s, please verify your email.\n", username);
    }
}
