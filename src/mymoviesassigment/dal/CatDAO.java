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
import java.util.ArrayList;
import java.util.List;
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;

/**
 *
 * @author nedas
 */
public class CatDAO {

    SQLServerDataSource ds;

    public CatDAO() {
        this.ds = new SQLServerDataSource();
        ds.setDatabaseName(DatabaseConnectionDAO.getInstance().getProperty("databaseName"));
        ds.setUser(DatabaseConnectionDAO.getInstance().getProperty("userName"));
        ds.setPassword(DatabaseConnectionDAO.getInstance().getProperty("password"));
        ds.setServerName(DatabaseConnectionDAO.getInstance().getProperty("ip"));
        ds.setPortNumber(Integer.parseInt(DatabaseConnectionDAO.getInstance().getProperty("port")));
    }

    /*
    Gets a joint category query. Which is used to create a category list of movies.
     */
    public List<Movie> getCategoryMovie(int id) {
        List<Movie> newMovieList = new ArrayList();
        try (Connection con = ds.getConnection()) {
            String query = ""
                    + "SELECT Movie.name , Movie.userRating , Movie.imdbRating , Movie.lastview , Movie.filelink , Movie.id FROM CatMovie "
                    + "INNER JOIN Movie "
                    + "ON CatMovie.MovieId = Movie.id "
                    + "WHERE CatMovie.CategoryId = ? "; // Gets all movies from a coresponding category.
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                Movie mov = new Movie(rs.getString("name"), rs.getInt("userRating"), rs.getInt("imdbRating"), rs.getDate("lastview"), rs.getString("filelink"), rs.getInt("id"));
                newMovieList.add(mov); //adds movies to a movie array
            }
            return newMovieList;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public void addToCategory(Category selectedItem, Movie selectedMovie) {
        String sql = "INSERT INTO CatMovie(CategoryId,MovieId) VALUES (?,?)";
        try (Connection con = ds.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, selectedItem.getID());
            ps.setInt(2, selectedMovie.getID());
            ps.addBatch();
            ps.executeBatch();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void removeFromCategory(Category selectedItem, Movie selectedMovie) {
        try (Connection con = ds.getConnection()) {
            System.out.println(selectedItem.getID() +" " + selectedMovie.getID());
            String query = "DELETE from CatMovie WHERE CategoryId = ? AND MovieId = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, selectedItem.getID());
            preparedStmt.setInt(2, selectedMovie.getID());
            preparedStmt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


    public void removeFromCat(Category selectedItem) {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE from CatMovie WHERE CategoryId = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, selectedItem.getID());
            preparedStmt.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
