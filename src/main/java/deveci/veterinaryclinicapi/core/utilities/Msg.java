package deveci.veterinaryclinicapi.core.utilities;

public class Msg {
    public static final String CREATED = "Data has been successfully stored.";
    public static final String OK = "The operation was successful.";
    public static final String VALIDATION_ERROR = "Invalid entry";
    public static final String NOT_FOUND = "Data not found.";
    public static final String NO_SUCH_CUSTOMER = "Customer not found in the database.";
    public static final String NO_SUCH_CUSTOMER_ID = "Customer ID not found in the database.";
    public static final String NO_SUCH_ANIMAL = "Animal not found in the database.";
    public static final String NO_SUCH_ANIMAL_ID = "Animal ID not found in the database.";
    public static final String NO_SUCH_DOCTOR_ID = "Doctor ID not found in the database.";
    public static final String NO_SUCH_AVAILABLE_DATE_ID = "Available date ID not found in the database.";
    public static final String NO_SUCH_APPOINTMENT_ID = "Appointment ID not found in the database.";
    public static final String NO_SUCH_VACCINE_ID = "Vaccine ID not found in the database.";
    public static final String DUPLICATE_ANIMAL = "This customer already has a record of this animal.";
    public static final String DELETED = "Data has been successfully deleted.";
    public static final String DR_EMAIL_EXISTS = "This email belongs to another doctor.";
    public static final String DR_PHONE_EXISTS = "This phone number belongs to another doctor.";
    public static final String OUT_OF_OFFICE = "The doctor is not working on this date.";
    public static final String CUSTOMER_EMAIL_EXISTS = "This email belongs to another customer.";
    public static final String CUSTOMER_PHONE_EXISTS = "This phone number belongs to another customer.";
    public static final String END_DATE_ISSUE = "Protection end date must be later than the protection start date.";
    public static final String ACTIVE_PROTECTION = "There is still an active vaccine providing protection.";
    public static final String NO_DATA_CRITERIA = "No results matching your criteria were found in the database.";
    public static final String AVAILABLE_DATE_EXISTS = "There is already a record of this date for this doctor.";
    public static final String EXISTING_APPOINTMENT = "An appointment exists on this date. Update or delete operations are not allowed.";
    public static final String APPOINTMENT_ALREADY_TAKEN = "An appointment exists on this date and time.";
    public static final String NO_APPOINTMENT_RECORDS = "There are no appointments matching your criteria.";
}
