package com.dfms.dairy_farm_management_system.models;

import java.sql.*;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Vaccination implements Model {
    private int id;
    private String animal_id;
    private int responsible_id;
    private String responsible_name;
    private int vaccine_id;
    private String vaccine_name;
    private Date vaccination_date;
    private Timestamp updated_at;
    private Timestamp created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public int getResponsible_id() {
        return responsible_id;
    }

    public void setResponsible_id(int responsible_id) {
        this.responsible_id = responsible_id;
    }

    public String getResponsible_name() {
        String fullName = fetchFullNameByUserId(getResponsible_id());
        if (fullName != null) {
            setResponsible_name(fullName);
        }
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public int getVaccine_id() {
        return vaccine_id;
    }

    public void setVaccine_id(int vaccine_id) {
        this.vaccine_id = vaccine_id;
    }

    public String getVaccine_name() {
        String name = fetchSingleString(
                "SELECT name FROM stocks WHERE id = ?",
                getVaccine_id()
        );
        if (name != null) {
            setVaccine_name(name);
        }
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public Date getVaccination_date() {
        return vaccination_date;
    }

    public void setVaccination_date(Date vaccination_date) {
        this.vaccination_date = vaccination_date;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean save() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        updated_at = now;
        created_at = now;

        String query = "INSERT INTO `vaccination` " +
                "(animal_id, responsible_id, vaccine_id, vaccination_date, updated_at, created_at) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        return executeUpdate(query, ps -> {
            ps.setString(1, animal_id);
            ps.setInt(2, responsible_id);
            ps.setInt(3, vaccine_id);
            ps.setDate(4, vaccination_date);
            ps.setTimestamp(5, updated_at);
            ps.setTimestamp(6, created_at);
        });
    }

    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());

        String query = "UPDATE `vaccination` SET " +
                "animal_id = ?, responsible_id = ?, vaccine_id = ?, vaccination_date = ?, updated_at = ? " +
                "WHERE id = ?";

        return executeUpdate(query, ps -> {
            ps.setString(1, animal_id);
            ps.setInt(2, responsible_id);
            ps.setInt(3, vaccine_id);
            ps.setDate(4, vaccination_date);
            ps.setTimestamp(5, updated_at);
            ps.setInt(6, id);
        });
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `vaccination` WHERE id = ?";
        return executeUpdate(query, ps -> ps.setInt(1, id));
    }

    // -------------------------
    // Helpers to reduce duplication
    // -------------------------

    private String fetchFullNameByUserId(int userId) {
        String query = "SELECT first_name, last_name FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1) + " " + rs.getString(2);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return null;
    }

    private String fetchSingleString(String query, int idParam) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idParam);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return null;
    }

    private interface StatementFiller {
        void fill(PreparedStatement ps) throws SQLException;
    }

    private boolean executeUpdate(String query, StatementFiller filler) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            filler.fill(statement);
            return statement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }
}
