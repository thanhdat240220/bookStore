package entity;

import model.Book;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOProduct {
    Connection connection;

    public DAOProduct(Connection connection) {
        this.connection = connection;
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
                "publish_year=?, price=?,quantity=?, size=?, [weight]=? WHERE id=?";

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
            System.out.println("Edited successfully!");
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

    public String checkExistBook(int id) {
        String sql = "SELECT * FROM book WHERE id=" + id;
        String strToCheck = "";

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
//                int idToCheck = resultSet.getInt("id");
                resultSet.getString("name");
                strToCheck = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strToCheck;
    }

    public List<Integer> checkBookCategory() {
        String sql = "SELECT id FROM category";
        List<Integer> categoryIdToCheck = new ArrayList<>();

        Statement statement = null;
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                categoryIdToCheck.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryIdToCheck;
    }

    public int addCategory(Category category) {
        int n = 0;
        String sql = "INSERT INTO category(name) VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());

            n = preparedStatement.executeUpdate();
            System.out.println("Added category successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

}
