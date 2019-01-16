/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mymoviesassigment.be.Movie;
import mymoviesassigment.dal.exceptions.daoException;

/**
 *
 * @author nedas
 */
public class MovieDAO {

    SQLServerDataSource ds;

    public MovieDAO() {
        this.ds = new SQLServerDataSource();
        ds.setDatabaseName(DatabaseConnectionDAO.getInstance().getProperty("databaseName"));
        ds.setUser(DatabaseConnectionDAO.getInstance().getProperty("userName"));
        ds.setPassword(DatabaseConnectionDAO.getInstance().getProperty("password"));
        ds.setServerName(DatabaseConnectionDAO.getInstance().getProperty("ip"));
        ds.setPortNumber(Integer.parseInt(DatabaseConnectionDAO.getInstance().getProperty("port")));
    }

    /*
    Initialises the constructor. Gets the array from the DatabaseConnectionDAO and sets up the database so the class can use it.
     */
    public List<Movie> getAllMovies() throws daoException {
        List<Movie> allMovies = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM Movie";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) { // Creates and adds song objects into an array list
                Movie mov = new Movie(rs.getString("name"), rs.getInt("userRating"), rs.getInt("imdbRating"), rs.getDate("lastview"), rs.getString("filelink"), rs.getInt("id"));
                allMovies.add(mov);
            }
            return allMovies; //Returns the full list
        } catch (SQLServerException ex) {
            throw new daoException("Cannot connect to server");
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

    public Movie createMovie(String name, int rating, int imdbrating, String url) throws daoException {
        String sql = "INSERT INTO Movie(name,userRating,imdbRating,filelink) VALUES (?,?,?,?)";
        try (Connection con = ds.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, rating);
            ps.setInt(3, imdbrating);
            ps.setString(4, url);
            ps.addBatch();
            ps.executeBatch();
            Movie mov = new Movie(name, rating, imdbrating, null, url, getNewestSongID()); // Creates a movie object
            return mov; //Returns the movie object
        } catch (SQLServerException ex) {
            throw new daoException("Cannot connect to server");
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

    /*
    Gets the top Movie ID from the database so it is possible to create the movie object
     */
    private int getNewestSongID() throws daoException {
        int newestID = -1; // Default ID not found
        try (Connection con = ds.getConnection()) {
            String query = "SELECT TOP(1) * FROM Movie ORDER by id desc"; //Selects the biggest song ID in the database
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                newestID = rs.getInt("id");
            }
            return newestID;
        } catch (SQLServerException ex) {
            throw new daoException("Cannot connect to server");
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

    public Movie updateMovie(Movie movieToEdit, String name, int rating, int imdbrating, String url) throws daoException {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE Movie set name = ?,userRating = ?,imdbRating = ?,filelink = ? WHERE id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, rating);
            preparedStmt.setInt(3, imdbrating);
            preparedStmt.setString(4, url);
            preparedStmt.setInt(5, movieToEdit.getID());
            preparedStmt.executeUpdate();
            Movie mov = new Movie(name, rating, imdbrating, movieToEdit.getLastView(), url, movieToEdit.getID()); //creates a new song object.
            return mov;
        } catch (SQLServerException ex) {
            throw new daoException("Cannot connect to server");
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

    public void removeMovie(Movie selectedItem) throws daoException {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE from Movie WHERE id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, selectedItem.getID());
            preparedStmt.execute();
        } catch (SQLServerException ex) {
            throw new daoException("Cannot connect to server");
        } catch (SQLException ex) {
            throw new daoException("Cannot execute query");
        }
    }

}
