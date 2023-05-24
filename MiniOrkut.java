import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MiniOrkut {

    private static final String BASE_URL = "http://localhost:8081/";
    private static final Map<String, String> users = new HashMap<>();
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Mini Orkut Project!");

        // Create the HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/main", new MainHandler());
        server.setExecutor(null); // Use the default executor
        server.start();

        // Get the user name from the user.
        System.out.print("Enter your user name: ");
        String userName = System.console().readLine();

        // Check if the user name already exists.
        if (users.containsKey(userName)) {
            System.out.println("The user name already exists.");
            return;
        }

        // Get the password from the user.
        System.out.print("Enter your password: ");
        String password = new String(System.console().readPassword());

        // Save the user name and password to the map.
        users.put(userName, password);

        // Print the user name and password to the console.
        System.out.println("Your user name is: " + userName);
        System.out.println("Your password is: " + password);

        // Redirect the user to the main page.
        URL url = new URL(BASE_URL + "main");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
    }

    static class MainHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Welcome to the main page!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
