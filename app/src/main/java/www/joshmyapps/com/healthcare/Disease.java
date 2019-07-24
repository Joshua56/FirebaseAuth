package www.joshmyapps.com.healthcare;

/**
 * Created By David Odari
 * On 25/07/19
 **/
public final class Disease {
    private String type;
    private String days;
    private String medication;

    Disease(String type, String days, String medication) {
        this.type = type;
        this.days = days;
        this.medication = medication;
    }

    String getType() {
        return type;
    }

    String getDays() {
        return days;
    }

    String tookMedication() {
        return medication;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "type='" + type + '\'' +
                ", days=" + days +
                ", medication='" + medication + '\'' +
                '}';
    }
}
