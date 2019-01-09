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
import mymoviesassigment.be.Category;
import mymoviesassigment.be.Movie;

/**
 *
 * @author nedas
 */
public class CategoryDAO {

    SQLServerDataSource ds;
    private final CatDAO catDAO;

    public CategoryDAO() {
        catDAO = new CatDAO();
        this.ds = new SQLServerDataSource();
        ds.setDatabaseName(DatabaseConnectionDAO.getInstance().getProperty("databaseName"));
        ds.setUser(DatabaseConnectionDAO.getInstance().getProperty("userName"));
        ds.setPassword(DatabaseConnectionDAO.getInstance().getProperty("password"));
        ds.setServerName(DatabaseConnectionDAO.getInstance().getProperty("ip"));
        ds.setPortNumber(Integer.parseInt(DatabaseConnectionDAO.getInstance().getProperty("port")));
    }

    public List<Category> getAllCategories() {
        List<Category> allCategories = new ArrayList<>(); // Creates a category array to store all category

        try (Connection con = ds.getConnection()) {
            String sqlStatement = "SELECT * FROM Category";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                List<Movie> allMovies = catDAO.getCategoryMovie(id); //Puts all movies into the category
                Category categoryNew = new Category(id, name, allMovies.size(), allMovies); //Creates a new category object
                allCategories.add(categoryNew); // Adds the category to the category array
            }
            return allCategories; // Returns the category array
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    /*
    Creates Category with given name
     */
    public Category createCategory(String name) {
        List<Movie> allMoviesInCategory = new ArrayList<>();
        String sql = "INSERT INTO Category(name) VALUES (?)";
        try (Connection con = ds.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.addBatch();
            ps.executeBatch();
            Category cat = new Category(getNewestID(), name, 0, allMoviesInCategory); //Creates a Category object and specifies that there are no movies present.
            return cat;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    /*
    Gets the newest inserted Category ID in order to create a Category object.
     */
    private int getNewestID() {
        int newestID = -1;
        try (Connection con = ds.getConnection()) {
            String query = "SELECT TOP(1) * FROM Category ORDER by id desc";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                newestID = rs.getInt("id");
            }
            return newestID;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return newestID;
        } catch (SQLException ex) {
            System.out.println(ex);
            return newestID;
        }
    }

    public Category updateCategory(Category editingList, String name) {
        try (Connection con = ds.getConnection()) {
            String query = "UPDATE Category set name = ? WHERE id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, editingList.getID());
            preparedStmt.executeUpdate();
            List<Movie> allMovies = catDAO.getCategoryMovie(editingList.getID()); //Puts all movies into the category
            Category cat = new Category(editingList.getID(), name, allMovies.size(), allMovies); //Creates a Category object and specifies that there are no movies present.
            return cat;
        } catch (SQLServerException ex) {
            System.out.println(ex);
            return null;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public void deleteCategory(Category selectedItem) {
        try (Connection con = ds.getConnection()) {
            String query = "DELETE from Category WHERE id = ?";
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
