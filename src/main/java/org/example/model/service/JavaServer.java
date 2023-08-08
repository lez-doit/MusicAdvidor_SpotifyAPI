package org.example.model.service;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.example.model.data.ServerConfig;
import org.example.model.error.ServerError;

public class JavaServer {
  private static JavaServer instance;
  private final HttpClient client;

  private JavaServer() {
    this.client = HttpClient.newBuilder().build();
  }

  public static JavaServer getInstance() {
    if (instance == null) {
      instance = new JavaServer();
    }
    return instance;
  }

  public void runCodeReceiver() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      server.createContext(
          "/callback",
          exchange -> {
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.contains("code")) {
              ServerConfig.AUTH_CODE.set(query.substring(5));
              query = "Authentication code has been received.";
            } else {
              query = "Authentication code not found. Try again.";
            }
            exchange.sendResponseHeaders(200, query.length());
            exchange.getResponseBody().write(query.getBytes());
            exchange.getResponseBody().close();
          });
      server.start();
      while (ServerConfig.AUTH_CODE.get().isEmpty()) {
        Thread.sleep(10);
      }
      server.stop(10);
    } catch (IOException | InterruptedException e) {
      throw new ServerError("Something went wrong while running server: " + e.getMessage());
    }
  }

  public String sendAuthorizationRequest() {
    HttpRequest request =
        HttpRequest.newBuilder()
            .header("Content-Type", "application/x-www-form-urlencoded")
            .uri(URI.create(ServerConfig.ACCESS.get() + "/api/token"))
            .POST(
                HttpRequest.BodyPublishers.ofString(
                    "grant_type=authorization_code"
                        + "&code="
                        + ServerConfig.AUTH_CODE
                        + "&redirect_uri="
                        + ServerConfig.REDIRECT_URI
                        + "&client_id="
                        + ServerConfig.CLIENT_ID
                        + "&client_secret="
                        + ServerConfig.CLIENT_SECRET))
            .build();

    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (IOException | InterruptedException e) {
      throw new ServerError(
          "Error occurred while sending authorization request: " + e.getMessage());
    }
  }

  public String sendGetRequest(String path) {
    HttpRequest request =
        HttpRequest.newBuilder()
            .header("Authorization", "Bearer " + ServerConfig.ACCESS_TOKEN)
            .uri(URI.create(path))
            .GET()
            .build();

    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public String sendPutRequest(String path) {
    HttpRequest request =
            HttpRequest.newBuilder()
                    .header("Authorization", "Bearer " + ServerConfig.ACCESS_TOKEN)
                    .uri(URI.create(path))
                    .PUT(HttpRequest.BodyPublishers.ofString(""))
                    .build();

    try {
      return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
