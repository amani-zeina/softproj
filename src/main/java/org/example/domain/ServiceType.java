package org.example.domain;

public enum ServiceType {

	Consultation("Consultation/Contract", 30, 60, 1, 4),//استشارة/عقد
	Pickup("Car Delivery/Pickup", 45, 90, 1, 3),//استلام/تسليم سيارة
	Full_Inspection("Full Inspection", 60, 120, 1, 3),//فحص شامل
	Upgrades("Accessories/Upgrades", 60, 120, 1, 4),//تركيب إضافات
	Routine_Maintenance("Routine Maintenance", 30, 120, 1, 2);//صيانة دورية

    private final String label;
    private final int minDuration;
    private final int maxDuration;
    private final int minParticipants;
    private final int maxParticipants;

    ServiceType(String label, int minDuration, int maxDuration,
                int minParticipants, int maxParticipants) {
        this.label = label;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
    }

    public String getLabel() {
        return label;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public static void printOptions() {
        int i = 1;
        for (ServiceType t : values()) {
            System.out.println(i++ + ". " + t.label +
                    " | Duration: " + t.minDuration + "-" + t.maxDuration +
                    " min | Participants: " + t.minParticipants + "-" + t.maxParticipants);
        }
    }

    public static ServiceType fromChoice(int c) {
        return values()[c - 1];
    }
}