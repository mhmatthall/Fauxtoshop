/**
 * An enumeration which allows the different states of a UI button to be defined
 * so that effects may be applied.
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

	public int getValue() {
		return statusValue;
	}
	
	public String getFilename() {
		return filename;
	}
}
