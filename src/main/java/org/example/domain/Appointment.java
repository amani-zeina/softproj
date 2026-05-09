package org.example.domain;
/**
 * Represents an appointment in the scheduling system.
 * Stores information such as date, time, duration,
 * participants, appointment type, status, and booking owner.
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

    public enum Status {
        AVAILABLE,
        CONFIRMED,
        LATE
    }

    public Appointment(int id, String date, String time,
            int duration, int maxParticipants,
            int currentParticipants,
            ServiceType serviceType, Status status,String bookedBy) {
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

    public int getId() { return id; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public int getDuration() { return duration; }

    public int getMaxParticipants() { return maxParticipants; }

    public int getCurrentParticipants() { return currentParticipants; }
    
    public ServiceType getServiceType() { return serviceType; }

    public Status getStatus() { return status; }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    @Override
    public String toString() {
        return id + " | " + date + " | " + time +
                " | " + serviceType.getLabel() +
                " | " + duration + " min | " +
                currentParticipants + "/" + maxParticipants +
                " | " + status +
                " | " + bookedBy;
    }

    public String toFileString() {
    	return id + "," + date + "," + time + "," + duration + "," +
    	        maxParticipants + "," + currentParticipants + "," +
    	        serviceType.name() + "," + status + "," + bookedBy;
    }
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

	public void setDate(String newDate) {
		this.date = newDate;
	}

	public void setTime(String newTime) {
		this.time=newTime;
	}

	public void setDuration(int newDuration) {
		this.duration=newDuration;
	}
}