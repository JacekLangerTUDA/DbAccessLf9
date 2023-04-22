package org.example.dal;

import org.example.dal.model.UserModel;

import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAL {

    private final String connectionString = "jdbc:mariadb://localhost:3306/a34";
    private static final Logger LOGGER = Logger.getLogger(DAL.class.getName());

    private String user;
    private String password;

    public DAL(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void query(){

        String query = "SELECT * FROM Versand.T_Kunden";
        try(var con = DriverManager.getConnection(connectionString, user,password)){
            Statement sqlStatement = con.createStatement();
            ResultSet result = sqlStatement.executeQuery(query);

            printAllResults(result);

        } catch (SQLException e) {
//            LOGGER.log(Level.WARNING, String.format("[%s]: %s", e.getSQLState(),e.getMessage()));
//            System.out.println("Invalid user credentials, access denied!");
        }
    }

    private void printAllResults(ResultSet result) throws SQLException {

        while(result.next()){
            String firstName=result.getString("vname");
            String lastName=result.getString("nname");
            String lastOrder=result.getString("letztebestellung");
            System.out.printf("%s,%s,%s\n",firstName, lastName, lastOrder);
        }
    }

    public boolean register(UserModel user, String password) {
        try (var con = DriverManager.getConnection(connectionString,"jack", "pass")) {

            PreparedStatement stmt = con.prepareStatement("INSERT INTO T_Accounts(nickname,email,avatar,passwort) VALUES (?,?,?,?)");

            stmt.setString(1, user.getNickname());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, Arrays.toString(user.getAvatar()));
            stmt.setString(4, password);

            ResultSet res = stmt.executeQuery();

            if(res.rowInserted()) return true;

        } catch (SQLException e) {
            System.out.printf("[%s]: %s%n",e.getSQLState(),e.getMessage());
        }
        return false;
    }
    public boolean login(String username, String password) {

        boolean isSuccess = false;
        try(var con = DriverManager.getConnection(connectionString, "jack","pass")){

            var stmt = con.prepareStatement("SELECT * FROM T_Accounts WHERE nickname=?");
            stmt.setString(1,username);
            String passw = stmt.executeQuery().getString("passwort");

            isSuccess = password.equals(passw);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, String.format("=> [%s]: %s", e.getSQLState(), e.getMessage()));
        }
        return isSuccess;
    }
}
