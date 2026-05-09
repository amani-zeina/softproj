package org.example.domain;
/**
 * Represents an appointment in the scheduling system.
 * Stores information such as date, time, duration,
 * participants, appointment type, status, and booking owner.
 package org.example.domain;

 /**
 * Represents an appointment in the scheduling system.
 * This class stores all appointment-related information
 * including date, time, duration, participants,
 * service type, booking status, and booking owner.
 *
 * This class is part of the domain layer and is used
 * throughout the system to manage appointment data.
 *
 * @author Team
 * @version 1.0
 */
public class Appointment {

    private int id;
    private String date;
    private String time;
    private int duration;
    private int maxParticipants;
    private int currentParticipants;
    private Status status;
    private ServiceType serviceType;
    private String bookedBy;

    /**
     * Represents possible appointment statuses.
     */
    public enum Status {
        AVAILABLE,
        CONFIRMED,
        LATE
    }

    /**
     * Creates a new appointment object.
     *
     * @param id appointment ID
     * @param date appointment date
     * @param time appointment time
     * @param duration appointment duration in minutes
     * @param maxParticipants maximum allowed participants
     * @param currentParticipants current participant count
     * @param serviceType appointment service type
     * @param status appointment status
     * @param bookedBy username of the booking owner
     */
    public Appointment(int id, String date, String time,
                       int duration, int maxParticipants,
                       int currentParticipants,
                       ServiceType serviceType, Status status, String bookedBy) {

        this.id = id;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.maxParticipants = maxParticipants;
        this.currentParticipants = currentParticipants;
        this.serviceType = serviceType;
        this.status = status;
        this.bookedBy = bookedBy;
    }

    /**
     * Returns appointment ID.
     *
     * @return appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns appointment date.
     *
     * @return appointment date
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns appointment time.
     *
     * @return appointment time
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns appointment duration.
     *
     * @return duration in minutes
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns maximum participants.
     *
     * @return maximum participants
     */
    public int getMaxParticipants() {
        return maxParticipants;
    }

    /**
     * Returns current participants count.
     *
     * @return current participants
     */
    public int getCurrentParticipants() {
        return currentParticipants;
    }

    /**
     * Returns appointment service type.
     *
     * @return service type
     */
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Returns appointment status.
     *
     * @return appointment status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Updates current participants count.
     *
     * @param currentParticipants new participant count
     */
    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    /**
     * Updates appointment status.
     *
     * @param status new appointment status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns booking owner username.
     *
     * @return username
     */
    public String getBookedBy() {
        return bookedBy;
    }

    /**
     * Updates booking owner username.
     *
     * @param bookedBy booking owner username
     */
    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    /**
     * Returns formatted appointment information.
     *
     * @return formatted appointment string
     */
    @Override
    public String toString() {
        return id + " | " + date + " | " + time +
                " | " + serviceType.getLabel() +
                " | " + duration + " min | " +
                currentParticipants + "/" + maxParticipants +
                " | " + status +
                " | " + bookedBy;
    }

    /**
     * Converts appointment object to file format.
     *
     * @return file-formatted string
     */
    public String toFileString() {
        return id + "," + date + "," + time + "," + duration + "," +
                maxParticipants + "," + currentParticipants + "," +
                serviceType.name() + "," + status + "," + bookedBy;
    }

    /**
     * Creates appointment object from stored file data.
     *
     * @param line appointment file line
     * @return appointment object
     */
    public static Appointment fromFileString(String line) {

        String[] p = line.split(",");

        return new Appointment(
                Integer.parseInt(p[0]),
                p[1],
                p[2],
                Integer.parseInt(p[3]),
                Integer.parseInt(p[4]),
                Integer.parseInt(p[5]),
                ServiceType.valueOf(p[6]),
                Status.valueOf(p[7]),
                p[8]
        );
    }

    /**
     * Updates appointment date.
     *
     * @param newDate new appointment date
     */
    public void setDate(String newDate) {
        this.date = newDate;
    }

    /**
     * Updates appointment time.
     *
     * @param newTime new appointment time
     */
    public void setTime(String newTime) {
        this.time = newTime;
    }

    /**
     * Updates appointment duration.
     *
     * @param newDuration new duration in minutes
     */
    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }
}