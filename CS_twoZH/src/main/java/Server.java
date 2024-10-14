import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author ZH
 * @date 2024/10/14
 */
public class Server {
    private Map<String, String> contacts = new HashMap<>();
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server is running...");
        startHandlingClients();
    }

    private void startHandlingClients() {
        new Thread(() -> {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        processCommand(inputLine, out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void processCommand(String command, PrintWriter out) {
        String[] parts = command.split(" ", 2);
        String action = parts[0];
        String rest = parts.length > 1 ? parts[1] : "";

        switch (action) {
            case "ADD":
                String[] addParts = rest.split(",", 2);
                if (addParts.length == 2) {
                    contacts.put(addParts[0], addParts[1]);
                    out.println("Contact added.");
                } else {
                    out.println("Invalid ADD command format.");
                }
                break;
            case "VIEW":
                out.println(contacts.getOrDefault(rest, "Contact not found."));
                break;
            case "UPDATE":
                String[] updateParts = rest.split(",", 2);
                if (updateParts.length == 2) {
                    contacts.put(updateParts[0], updateParts[1]);
                    out.println("Contact updated.");
                } else {
                    out.println("Invalid UPDATE command format.");
                }
                break;
            case "DELETE":
                contacts.remove(rest);
                out.println("Contact deleted.");
                break;
            default:
                out.println("Unknown command.");
        }
    }

    public static void main(String[] args) {
        try {
            new Server(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}