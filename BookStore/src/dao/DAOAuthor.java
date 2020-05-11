package dao;

import controller.BaseController;
import model.Author;

import java.sql.*;

public class DAOAuthor extends BaseController {

    Connection connection;

    public DAOAuthor(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public int addAuthor(Author author) {
        int n = 0;
        String sql = "INSERT INTO author(name, birth_date) VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getDate_of_birth());

            n = preparedStatement.executeUpdate();
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            System.out.println("Added fail!");
        }

        return n;
    }

    public int editAuthor(Author author) {
        int n = 0;
        String sql = "UPDATE author SET name=?,birth_date=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getDate_of_birth());
            preparedStatement.setInt(3, author.getId());

            n = preparedStatement.executeUpdate();
            System.out.println("Edited successfully!");
        } catch (SQLException e) {
            System.out.println("Edited fail!");
        }

        return n;
    }

    public int removeAuthor(int id) {
        int n = 0;
        String sql = "DELETE FROM author WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();

            n = statement.executeUpdate(sql);
            System.out.println("Deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void showAnAuthor(int idToShow) {
        String sql = "SELECT * FROM author WHERE id=" + idToShow;
        showAuthor(sql);
    }

    public void showAllAuthor() {
        String sql = "SELECT * FROM author";
        showAuthor(sql);
    }

    public void showAuthor(String sql) {
        ResultSet resultSet = getData(sql);

        try {
            makeMenuHeader("Show all Authors");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
//                String date_of_birth = resultSet.getString("birth_date");
                Date date_of_birth = resultSet.getDate("birth_date");

                makeRow("ID: " + id);
                makeRow("Name: " + name);
                makeRow("Date of Birth: " + date_of_birth);
                makeRow("--------------------------");
            }
            makeMenuFooter();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkAuthorBook(int book_id, int author_id) {
        String sql = "Select * From book_author where book_id=" + book_id + " and author_id=" + author_id;
        ResultSet resultSet = getData(sql);
        try {
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

//    public String checkExistedAuthor(int id) {
//        String sql = "SELECT * FROM author WHERE id=" + id;
//        String strToCheck = "";
//        ResultSet resultSet = getData(sql);
//
//        try {
//            while (resultSet.next()) {
//                resultSet.getString("name");
//                strToCheck = resultSet.getString("name");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return strToCheck;
//    }
//
//    public Author checkDuplicateAuthor() {
//        String sql = "SELECT * FROM author";
//        Author author = null;
//        ResultSet resultSet = getData(sql);
//
//        try {
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String date_of_birth = resultSet.getString("birth_date");
//
//                author = new Author(id, name, date_of_birth);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return author;
//    }
    public String checkExistedAuthor(int id) {
        String sql = "SELECT * FROM author WHERE id=" + id;
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

    public Author checkDuplicateAuthor() {
        String sql = "SELECT * FROM author";
        Author author = null;

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String birth_date = resultSet.getString("birth_date");

                author = new Author(id, name, birth_date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;
    }

    public String formatDate(String date) {
//        return String.join("/", (Collections.reverse(Arrays.asList(date.split("-")))));
//        System.out.println(Arrays.toString(date.split("-")));;

        String[] arrStr = date.split("-");

        for (int i = 0; i < arrStr.length / 2; i++) {
            String temp = arrStr[i];
            arrStr[i] = arrStr[arrStr.length - i - 1];
            arrStr[arrStr.length - i - 1] = temp;
        }

        return String.join("/", arrStr);
    }

    public ResultSet getData(String sql) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
