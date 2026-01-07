package com.dfms.dairy_farm_management_system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Routine implements Model {
    private int id;
    private String name;
    private String note;
    private Timestamp created_at;
    private Timestamp updated_at;

    @FunctionalInterface
    private interface StatementBinder {
        void bind(PreparedStatement st) throws SQLException;
    }

    private boolean executeUpdate(String sql, StatementBinder binder) {
        try (Connection connection = getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {

            binder.bind(st);
            return st.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    public static int getLastId() {
        String query = "SELECT id FROM routines ORDER BY created_at DESC LIMIT 1";
        try (PreparedStatement ps = getConnection().prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
        return -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<RoutineDetails> getDetails() {
        ArrayList<RoutineDetails> details = new ArrayList<>();
        String query = "SELECT * FROM routine_has_feeds WHERE routine_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    RoutineDetails routineDetails = new RoutineDetails();
                    routineDetails.setId(resultSet.getInt("id"));
                    routineDetails.setStock_id(resultSet.getInt("stock_id"));
                    routineDetails.setRoutine_id(resultSet.getInt("routine_id"));
                    routineDetails.setQuantity(resultSet.getFloat("quantity"));
                    routineDetails.setFeeding_time(resultSet.getString("feeding_time"));
                    routineDetails.setCreated_at(resultSet.getTimestamp("created_at"));
                    routineDetails.setUpdated_at(resultSet.getTimestamp("updated_at"));
                    details.add(routineDetails);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return details;
    }

    @Override
    public boolean save() {
        created_at = Timestamp.valueOf(LocalDateTime.now());
        updated_at = Timestamp.valueOf(LocalDateTime.now());

        String sql = "INSERT INTO routines (name, note, created_at, updated_at) VALUES (?, ?, ?, ?)";

        return executeUpdate(sql, st -> {
            st.setString(1, name);
            st.setString(2, note);
            st.setTimestamp(3, created_at);
            st.setTimestamp(4, updated_at);
        });
    }
    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());

        String sql = "UPDATE routines SET name = ?, note = ?, updated_at = ? WHERE id = ?";

        return executeUpdate(sql, st -> {
            st.setString(1, name);
            st.setString(2, note);
            st.setTimestamp(3, updated_at);
            st.setInt(4, id);
        });
    }

    @Override
    public boolean delete() {
        String sql = "DELETE FROM routines WHERE id = ?";

        return executeUpdate(sql, st -> st.setInt(1, id));
    }
}
