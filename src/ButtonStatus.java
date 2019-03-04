/**
 * An enumeration which allows the different states of a UI button to be defined
 * so that effects may be applied.
 * <p>
 * I declare that the following is my own work.
 * 
 * @author Matt Hall (961500)
 */
public enum ButtonStatus {
	SELECTED(0, "Selected"),
	HOVERED(1, "Hovered"),
	DESELECTED(2, "Deselected");

	private final int statusValue;
	private final String filename;

	private ButtonStatus(int statusValue, String filename) {
		this.statusValue = statusValue;
		this.filename = filename;
	}

	/**
	 * Gets the index value of the button state
	 * @return An integer corresponding to the button's current state
	 */
	public int getValue() {
		return statusValue;
	}
	
	/**
	 * Gets the filename suffix of the corresponding icon files
	 * @return The filename suffix
	 */
	public String getFilename() {
		return filename;
	}
}
