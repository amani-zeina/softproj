package org.example.domain;

/**
 * Represents available appointment service types
 * with booking constraints such as duration
 * and participant limits.
 *
 * @author Team
 * @version 1.0
 */
public enum ServiceType {

    URGENT("Urgent Appointment", 15, 30, 1, 1),
    FOLLOW_UP("Follow Up Appointment", 20, 45, 1, 2),
    ASSESSMENT("Assessment Appointment", 30, 60, 1, 3),
    VIRTUAL("Virtual Appointment", 15, 60, 1, 5),
    IN_PERSON("In Person Appointment", 30, 120, 1, 4),
    INDIVIDUAL("Individual Appointment", 15, 60, 1, 1),
    GROUP("Group Appointment", 30, 120, 2, 10);

    private final String label;
    private final int minDuration;
    private final int maxDuration;
    private final int minParticipants;
    private final int maxParticipants;

    /**
     * Creates a service type with booking rules.
     *
     * @param label service label
     * @param minDuration minimum allowed duration
     * @param maxDuration maximum allowed duration
     * @param minParticipants minimum participants
     * @param maxParticipants maximum participants
     */
    ServiceType(String label, int minDuration, int maxDuration,
                int minParticipants, int maxParticipants) {
        this.label = label;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }

    /**
     * Returns service label.
     *
     * @return service label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns minimum duration.
     *
     * @return minimum duration
     */
    public int getMinDuration() {
        return minDuration;
    }

    /**
     * Returns maximum duration.
     *
     * @return maximum duration
     */
    public int getMaxDuration() {
        return maxDuration;
    }

    /**
     * Returns minimum participants.
     *
     * @return minimum participants
     */
    public int getMinParticipants() {
        return minParticipants;
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
     * Prints all available service options.
     */
    public static void printOptions() {
        int i = 1;
        for (ServiceType t : values()) {
            System.out.println(i++ + ". " + t.label +
                    " | Duration: " + t.minDuration + "-" + t.maxDuration +
                    " min | Participants: " + t.minParticipants + "-" + t.maxParticipants);
        }
    }

    /**
     * Returns service type based on menu choice.
     *
     * @param c user choice
     * @return matching service type
     */
    public static ServiceType fromChoice(int c) {
        return values()[c - 1];
    }
}
