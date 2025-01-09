package management_system.domain.constant;

public class ResponseConstants {
    public static final int SUCCESS_CODE = 1000;
    public static final String SUCCESS_MESSAGE = "Request processed successfully.";

    public static final int ERROR_CODE = 2000;
    public static final String ERROR_MESSAGE = "An error occurred while processing the request.";

    public static final int AUTH_FAILURE_CODE = 2001;
    public static final String AUTH_FAILURE_MESSAGE = "Authentication failed. Please log in again.";

    public static final int PERMISSION_DENIED_CODE = 2002;
    public static final String PERMISSION_DENIED_MESSAGE = "You do not have permission to perform this action.";

    public static final int DATA_NOT_FOUND_CODE = 2003;
    public static final String DATA_NOT_FOUND_MESSAGE = "The requested data was not found.";
}