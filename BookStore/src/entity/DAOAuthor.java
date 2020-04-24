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

            n = preparedStatement.executeUpdate();
            System.out.println("Edited successfully!");
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
                System.out.println("Author not Found! Please try again!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void showAnAuthor(int idToShow) {
        String sql = "SELECT * FROM author WHERE id=" + idToShow;
        Statement statement;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if (!checkExistedAuthor(idToShow).equals("")) {
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int year_of_birth = resultSet.getInt("year_of_birth");

                    System.out.println("ID: " + id + "\t | \t Name: " + name + "\t | \t Year of Birth:" + year_of_birth);
                }
            } else {
                System.out.println("Author not Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void showAllAuthor() {
        String sql = "SELECT * FROM author";
        Statement statement;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int year_of_birth = resultSet.getInt("year_of_birth");

                System.out.println("ID: " + id + "\t | \t Name: " + name + "\t | \t Year of Birth: " + year_of_birth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    public Author checkDuplicateAuthor() {
        String sql = "SELECT * FROM author";
        Author author = null;

        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int year_birthday = resultSet.getInt("year_of_birth");

                author = new Author(id, name, year_birthday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return author;
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
