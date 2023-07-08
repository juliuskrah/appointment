package org.appointment;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import io.agroal.api.AgroalDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * @author Julius Krah
 */
@ApplicationScoped
public class Application {
    @Inject
    AgroalDataSource dataSource;
    private static final int DOCTOR_ID = 3;

    List<LocalDateTime> availableAppointments(LocalDate from) {
        String query = """
                    SELECT date, time FROM appointment
                    WHERE doctor_id = ? AND event_type = ?
                    AND date BETWEEN ? AND ?;
                """;
        return availableAppointments(from, query);
    }

    Map<LocalDate, Set<LocalTime>> availableAppointmentsByDate(LocalDate from) {
        // Returns a list of LocalDates
        return availableAppointments(from).stream().collect(
                groupingBy(
                        // Extract date as key
                        LocalDateTime::toLocalDate,
                        mapping(
                                // Extract time as list of values
                                LocalDateTime::toLocalTime,
                                toSet())));
    }

    List<LocalDateTime> availableSlots(LocalDate from) {
        String query = """
                    SELECT datetime::date as date, datetime::time as time
                    FROM appointment_slot
                    WHERE doctor_id = ? AND event_type = ?
                    AND datetime::date BETWEEN ? AND ?;
                """;
        return availableAppointments(from, query);
    }

    Map<LocalDate, Set<LocalTime>> availableSlotsByDate(LocalDate from) {
        String query = """
                    SELECT datetime::date as date, array_agg(datetime::time) as time
                    FROM appointment_slot
                    WHERE doctor_id = ? AND event_type = ?
                    AND datetime::date BETWEEN ? AND ?
                    GROUP BY datetime::date;
                """;
        Map<LocalDate, Set<LocalTime>> results = new HashMap<>();
        try (
                var connection = dataSource.getConnection();
                var ps = connection.prepareStatement(query);) {
            LocalDate to = from.plusDays(7);
            ps.setInt(1, DOCTOR_ID);
            ps.setString(2, "OPEN");
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            // Extract data from result set
            while (rs.next()) {
                Date date = rs.getDate("date");
                Array time = rs.getArray("time");
                Time[] times = (Time[]) time.getArray();

                results.put(
                        date.toLocalDate(),
                        Stream.of(times).map(Time::toLocalTime).collect(toSet()));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return results;
    }

    private List<LocalDateTime> availableAppointments(LocalDate from, String query) {
        List<LocalDateTime> results = new ArrayList<>();
        try (
                var connection = dataSource.getConnection();
                var ps = connection.prepareStatement(query);) {
            LocalDate to = from.plusDays(7);
            ps.setInt(1, DOCTOR_ID);
            ps.setString(2, "OPEN");
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            // Extract data from result set
            while (rs.next()) {
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");

                results.add(
                        LocalDateTime.of(date.toLocalDate(), time.toLocalTime()));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return results;
    }

    @PostConstruct
    void initDatabase() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("import.sql");
        String sql;
        try (
                var reader = new BufferedReader(new InputStreamReader(is));
                var connection = dataSource.getConnection();
                var stmt = connection.createStatement();) {
            sql = reader.lines().collect(joining(System.lineSeparator()));
            System.out.println("Executing SQL statement");
            System.out.println(sql);
            connection.setAutoCommit(true);
            stmt.executeUpdate(sql);
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
