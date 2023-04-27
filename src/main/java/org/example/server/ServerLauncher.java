package org.example.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.example.dal.DAL;
import org.example.dal.model.LoginDto;
import org.example.dal.model.RegisterUserDto;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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
    HttpContext loginContext = server.createContext("/");
    HttpContext homeContext = server.createContext("/home");
    HttpContext staticContext = server.createContext("/static");
    HttpContext registerContext = server.createContext("/signup");

    System.out.println("=> listening on port 8080");
    loginContext.setHandler(ServerLauncher::handleGetRequest);
    staticContext.setHandler(ServerLauncher::handleStaticResources);
    registerContext.setHandler(ServerLauncher::registryHandler);
    homeContext.setHandler(ServerLauncher::homeHandler);
    server.start();
  }

  private static void homeHandler(HttpExchange httpExchange) throws IOException {

    byte[] successPage = Files.readAllBytes(Path.of(TEMPLATE_PATH + "home.html"));
    System.out.println("=> Login successful, showing home.html");
    try (var out = httpExchange.getResponseBody()) {
      httpExchange.sendResponseHeaders(200, successPage.length);
      out.write(successPage);
    }
  }

  private static void registryHandler(HttpExchange exchange) throws IOException {

    if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
      sendResponse("register.html", exchange);
    } else {
      try (var in = exchange.getRequestBody();
           var out = exchange.getResponseBody()) {

        String json = new String(in.readAllBytes());
        Gson gson = new Gson();

        RegisterUserDto registerUserDto = gson.fromJson(json, RegisterUserDto.class);
        DAL dal = new DAL();
        String msg = """
                     {
                      success:%s
                     }
                     """;

        if (dal.register(registerUserDto)) {
          msg = String.format(msg, true);
          exchange.sendResponseHeaders(200, msg.length());
          out.write(msg.getBytes());
        } else {
          msg = String.format(msg, true);
          exchange.sendResponseHeaders(401, msg.length());
          out.write(msg.getBytes());
        }

      } catch (IOException e) {
        System.out.printf("=> An exception was raised: [%s]: [%s]", LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")), e.getMessage());
      }
    }
  }

  private static void processRequest(HttpExchange exchange) {

    try (var in = exchange.getRequestBody();
         var out = exchange.getResponseBody()) {

      String json = new String(in.readAllBytes());
      Gson gson = new Gson();

      LoginDto loginDto = gson.fromJson(json, LoginDto.class);
      DAL dal = new DAL();
      String msg = """
                   {
                    username:%s,
                    loginIsSuccess:%s
                   }
                   """;

      if (dal.login(loginDto.username(), loginDto.password())) {
        msg = String.format(msg, loginDto.username(), true);
        exchange.sendResponseHeaders(200, msg.length());
        out.write(msg.getBytes());
      } else {
        msg = String.format(msg, loginDto.username(), false);
        exchange.sendResponseHeaders(401, msg.length());
        out.write(msg.getBytes());
      }

    } catch (IOException e) {
      System.out.printf("=> An exception was raised: [%s]: [%s]", LocalDateTime.now().format(
          DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")), e.getMessage());
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
    if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
      sendResponse("login.html", exchange);
    } else {
      processRequest(exchange);
    }
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
