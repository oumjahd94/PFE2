package ma.mt.fo.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * The Class ApiError.
 */
public class ApiError {
	
	/** The status. */
	private HttpStatus status;
    
    /** The message. */
    private String message;
    
    /** The errors. */
    private List<String> errors;
 
    /**
     * Instantiates a new api error.
     *
     * @param status the status
     * @param message the message
     * @param errors the errors
     */
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
 
    /**
     * Instantiates a new api error.
     *
     * @param status the status
     * @param message the message
     * @param error the error
     */
    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
    // getters and setters

	/**
     * Gets the status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the new errors
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
