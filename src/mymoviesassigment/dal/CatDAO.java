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
            String query = "SELECT * FROM CatMovie INNER JOIN Movie ON CatMovie.MovieId = Movie.id WHERE CatMovie.CategoryId = ? "; // Gets all movies from a coresponding category.
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
}
