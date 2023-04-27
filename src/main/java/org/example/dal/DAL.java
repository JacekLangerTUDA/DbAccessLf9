package org.example.dal;

import org.example.dal.model.RegisterUserDto;
import org.example.dal.model.UserModel;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAL {

  private static final Logger LOGGER = Logger.getLogger(DAL.class.getName());
  private final String connectionString = "jdbc:mariadb://localhost:3306/a34";

  public boolean register(RegisterUserDto user) {

    try (var con = DriverManager.getConnection(connectionString, "lf9", "lf9")) {

      PreparedStatement stmt = con.prepareStatement(
          "INSERT INTO T_Accounts(nickname,email,passwort) VALUES (?,?,?)");

      stmt.setString(1, user.username());
      stmt.setString(2, user.email());
      stmt.setString(3, user.password());

      int res = stmt.executeUpdate();   // returns 1 if successfully added instance

      if (res == 1) {
        return true;
      }

    } catch (SQLException e) {
      System.out.printf("[%s]: %s%n", e.getSQLState(), e.getMessage());
    }
    return false;
  }

  public boolean login(String username, String password) {

    boolean isSuccess = false;
    try (var con = DriverManager.getConnection(connectionString, "lf9", "lf9");
         var stmt = con.prepareStatement("SELECT * FROM T_Accounts WHERE nickname=?");) {

      stmt.setString(1, username);

      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String passw = rs.getString("passwort");
        isSuccess = passw.equals(password);
      }


    } catch (SQLException e) {
      LOGGER.log(Level.WARNING, String.format("=> [%s]: %s", e.getSQLState(), e.getMessage()));
    }
    return isSuccess;
  }

}
