import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverAddress = "localhost";
        int port = 12345;

        try (ContactApp client = new ContactApp()) {
            System.out.println("Welcome to the Contact Client!");
            while (true) {
                System.out.print("Enter command (ADD name,phone | VIEW name | UPDATE name,phone | DELETE name | EXIT): ");
                String command = scanner.nextLine();

                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
