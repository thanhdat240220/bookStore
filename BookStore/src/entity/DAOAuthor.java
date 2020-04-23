package entity;

import model.Author;

import java.sql.*;

public class DAOAuthor {
    Connection connection;

    public DAOAuthor(Connection connection) {
        this.connection = connection;
    }

//    public DAOAuthor(Connection connection) {
//        this.connection = connection;
//    }

    public int addAuthor(Author author) {
        int n = 0;
        String sql = "INSERT INTO author(name, year_of_birth) VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setInt(2, author.getYear_birthday());

            n = preparedStatement.executeUpdate();
            System.out.println("Added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int editAuthor(Author author) {
        int n = 0;
        String sql = "UPDATE author SET name=?,year_of_birth=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, author.getName());
            preparedStatement.setInt(2, author.getYear_birthday());
            preparedStatement.setInt(3, author.getId());

            if (!checkExistedAuthor(author.getId()).equals("")) {
                n = preparedStatement.executeUpdate();
                System.out.println("Edited successfully!");
            } else {
                System.out.println("No Author exist! Please try again!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public int removeAuthor(int id) {
        int n = 0;
        String sql = "DELETE FROM author WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            if (!checkExistedAuthor(id).equals("")) {
                n = statement.executeUpdate(sql);
                System.out.println("Deleted successfully!");
            } else {
                System.out.println("No author exist! Please try again!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void showAnAuthor(String sql) {
        ResultSet resultSet = getData(sql);
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int n = resultSetMetaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= n; i++) {
                    System.out.println(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showAllAuthor() {
//        int n = 0;
        String sql = "SELECT * FROM author";
//        Statement statement;
//
//        try {
//            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                int year_of_birth = resultSet.getInt("year_of_birth");
//
//                Author author = new Author(id, name, year_of_birth);
//                System.out.println(author);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        showAnAuthor(sql);
    }

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

    public ResultSet getData(String sql) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(sql);
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
