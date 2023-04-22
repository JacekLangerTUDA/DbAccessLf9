package org.example.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLauncher {

    static final String OK = "HTTP/1.1 200 OK\r\n\r\n";
    static final int PORT = 8080;
    private static final String TEMPLATE_PATH = "src/main/resources/";

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        HttpContext homeContext = server.createContext("/");
        HttpContext staticContext = server.createContext("/static");
        HttpContext registerContext = server.createContext("/signup");

        System.out.println("=> listening on port 8080");
        homeContext.setHandler(ServerLauncher::handleGetRequest);
        staticContext.setHandler(ServerLauncher::handleStaticResources);
        registerContext.setHandler(ServerLauncher::registryHandler);
        server.start();
    }

    private static void registryHandler(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
            sendResponse("register.html", exchange);
        } else {
            processRequest(exchange);
        }
    }

    private static void processRequest(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        InputStream requestStream = exchange.getRequestBody();

        try {

            String req = new String(requestStream.readAllBytes());
            sendResponse(exchange.getRequestURI().getPath(),exchange);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendResponse(String filePath, HttpExchange exchange) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        try {
            byte[] bytes = Files.readAllBytes(Path.of(TEMPLATE_PATH + filePath));

            System.out.printf("=> loading %s \n", TEMPLATE_PATH + "register.html");
            exchange.sendResponseHeaders(200, bytes.length);
            outputStream.write(bytes);

        } catch (IOException e) {

            String msg = "Failed to load the respective file";
            exchange.sendResponseHeaders(404, msg.length());
            System.out.printf("\033[0;33m=> [%s] error: %s\033[0m\n", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")), e.getMessage());
            outputStream.write(msg.getBytes());

        } finally {
            outputStream.close();
        }
    }


    private static void handleGetRequest(HttpExchange exchange) throws IOException {

        OutputStream outputStream = exchange.getResponseBody();
        if (exchange.getRequestMethod().equalsIgnoreCase("get"))
            sendResponse("login.html", exchange);
        else
            processRequest(exchange);
    }

    private static void handleStaticResources(HttpExchange exchange) throws IOException {

        OutputStream outputStream = exchange.getResponseBody();
        try {
            byte[] bytes = Files.readAllBytes(
                    Path.of(TEMPLATE_PATH + exchange.getRequestURI().getPath()));

            exchange.sendResponseHeaders(200, bytes.length);
            outputStream.write(bytes);
        } catch (IOException e) {
            String msg = "Failed to load the static content";
            exchange.sendResponseHeaders(404, msg.length());

            System.out.printf("\033[0;33m=> [%s] error: %s\033[0m\n", LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy")), e.getMessage());
            outputStream.write(msg.getBytes());
        } finally {
            outputStream.close();
        }
    }

}
