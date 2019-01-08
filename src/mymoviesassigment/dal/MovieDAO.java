/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymoviesassigment.dal;

/**
 *
 * @author nedas
 */
public class MovieDAO {

    public MovieDAO() {
        System.out.println(DatabaseConnectionDAOnew.getInstance().getProperty("databaseName"));
    }
    
}
