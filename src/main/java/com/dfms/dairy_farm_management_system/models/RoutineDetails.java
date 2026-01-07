package com.dfms.dairy_farm_management_system.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class RoutineDetails implements Model{
    private int id;
    private int stock_id;
    private String stock_name;
    private int routine_id;
    private float quantity;
    private String feeding_time;
    private Timestamp created_at;
    private Timestamp updated_at;

    public static int getLastId() {
        String query = "SELECT id FROM routine_has_feeds ORDER BY created_at DESC LIMIT 1";
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

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;

        String query = "SELECT `name` FROM `stocks` WHERE `id` = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setInt(1, stock_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    setStock_name(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public int getRoutine_id() {
        return routine_id;
    }

    public void setRoutine_id(int routine_id) {
        this.routine_id = routine_id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getFeeding_time() {
        return feeding_time;
    }

    public void setFeeding_time(String feeding_time) {
        this.feeding_time = feeding_time;
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

    @Override
    public boolean save() {
        created_at = Timestamp.valueOf(LocalDateTime.now());
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "INSERT INTO `routine_has_feeds` (stock_id, routine_id, quantity, feeding_time, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {

            statement.setInt(1, stock_id);
            statement.setInt(2, routine_id);
            statement.setFloat(3, quantity);
            statement.setString(4, feeding_time);
            statement.setTimestamp(5, created_at);
            statement.setTimestamp(6, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE `routine_has_feeds` SET stock_id = ?, routine_id = ?, quantity = ?, feeding_time = ?, updated_at = ? WHERE id = " + id;
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {

            statement.setInt(1, stock_id);
            statement.setInt(2, routine_id);
            statement.setFloat(3, quantity);
            statement.setString(4, feeding_time);
            statement.setTimestamp(5, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `routine_has_feeds` WHERE id = " + id;
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }
}
