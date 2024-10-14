import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * @author ZH
 * @date 2024/10/14
 */
public class Client implements AutoCloseable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String serverAddress, int port) throws IOException  {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String sendCommand(String command) throws IOException {
        out.println(command);
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String serverAddress = "localhost";
        int port = 12345;

        try (Client client = new Client(serverAddress, port)) {
            System.out.println("Welcome to the Contact Client!");
            while (true) {
                System.out.print("Enter command (ADD name,phone | VIEW name | UPDATE name,phone | DELETE name | EXIT): ");
                String command = scanner.nextLine();

                if (command.equalsIgnoreCase("EXIT")) {
                    break;
                }

                String response = client.sendCommand(command);
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}