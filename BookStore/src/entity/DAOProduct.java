package entity;

import model.Book;
import model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOProduct {
    DBConnection dbConnection;
    Connection connection;

    public DAOProduct(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.connection = dbConnection.Connecting();
    }

    public int addProduct(Book book) {
        int n = 0;
        String sql = "INSERT INTO book(category_id, status_id, author_id, name, content_summary, publish_year, price," +
                " quantity, size, [weight]) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getCategory_id());
            preparedStatement.setInt(2, book.getStatus_id());
            preparedStatement.setInt(3, book.getAuthor_id());
            preparedStatement.setString(4, book.getName());
            preparedStatement.setString(5, book.getContent_summary());
            preparedStatement.setInt(6, book.getPublish_year());
            preparedStatement.setDouble(7, book.getPrice());
            preparedStatement.setInt(8, book.getQuantity());
            preparedStatement.setString(9, book.getSize());
            preparedStatement.setString(10, book.getWeight());

            n = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int editProduct(Book book) {
        int n = 0;
        String sql = "UPDATE book SET category_id=?, status_id=?, author_id=?, name=?, content_summary=?, " +
                "publish_year=?, price,quantity=?, size=?, [weight]=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getCategory_id());
            preparedStatement.setInt(2, book.getStatus_id());
            preparedStatement.setInt(3, book.getAuthor_id());
            preparedStatement.setString(4, book.getName());
            preparedStatement.setString(5, book.getContent_summary());
            preparedStatement.setInt(6, book.getPublish_year());
            preparedStatement.setDouble(7, book.getPrice());
            preparedStatement.setInt(8, book.getQuantity());
            preparedStatement.setString(9, book.getSize());
            preparedStatement.setString(10, book.getWeight());
            preparedStatement.setInt(11, book.getId());

            n = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int removeProduct(int id) {
        int n = 0;
        String sql = "DELETE FROM book WHERE id=" + id;

        try {
            Statement statement = connection.createStatement();
            n = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }
}
