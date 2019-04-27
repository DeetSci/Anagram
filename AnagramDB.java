package project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import project.tester.Anagram;

public class AnagramDB {

    public static ArrayList<Anagram> getAnagram() throws DBException {
        ArrayList<Anagram> anagrams = new ArrayList<>();

        String query = "SELECT * FROM Anagram ORDER BY FirstAnagram";

        Connection connection = DBUtil.getConnection();
        try (   PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("AnagramID");
                String firstAnagram = rs.getString("FirstAnagram");
                String secondAnagram = rs.getString("SecondAnagram");

                Anagram c = new Anagram(id, firstAnagram, secondAnagram);
                anagrams.add(c);
            }
            return anagrams;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public static Anagram getAnagram(int id) throws DBException {
        String selectAnagram
                = "SELECT FirstAnagram, SecondAnagram "
                + "FROM Anagram "
                + "WHERE AnagramID = ?";

        Connection connection = DBUtil.getConnection();
        try (   PreparedStatement ps = connection.prepareStatement(selectAnagram)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String firstAnagram = rs.getString("FirstAnagram");
                String secondAnagram = rs.getString("SecondAnagram");
                Anagram c = new Anagram(id, firstAnagram, secondAnagram);
                return c;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void add(Anagram c, Anagram d) throws DBException {
        String insert
                = "INSERT INTO Anagram (FirstAnagram, SecondAnagram) "
                + "VALUES (?, ?)";
        Connection connection = DBUtil.getConnection();        
        try (   PreparedStatement ps = connection.prepareStatement(insert)) {
            ps.setString(1, c.getFirstAnagram());
            ps.setString(2, d.getSecondAnagram());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void update(Anagram anagram) throws DBException {
        String sql = "UPDATE Anagram SET "
                + "FirstAnagram = ?, "
                + "SecondAnagram = ?, "
                + "WHERE AnagramID = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, anagram.getFirstAnagram());
            ps.setString(2, anagram.getSecondAnagram());
            ps.setInt(3, anagram.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }        
    }
    
    public static void delete(Anagram c) throws DBException {
        String delete
                = "DELETE FROM Anagram "
                + "WHERE AnagramID = ?";
        Connection connection = DBUtil.getConnection();
        try (   PreparedStatement ps = connection.prepareStatement(delete)) {
            ps.setInt(1, c.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}