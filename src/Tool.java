/**
 * An enumeration which allows for the easy modification of the tools used in
 * the program, and streamlined loading of assets for each tool.
 * <p>
 * I declare that the following is my own work.
 * 
 * @author Matt Hall (961500)
 */
public enum Tool {
	EFFECTS(0, "Effects"),
	GAMMA_CORRECTION(1, "Gamma"),
	CONTRAST_STRETCHING(2, "Contrast"),
	FILTER(3, "Filter");

	private final int toolIndex;
	private final String internalToolName;

	private Tool(int toolIndex, String internalToolName) {
		this.toolIndex = toolIndex;
		this.internalToolName = internalToolName;
	}

	/**
	 * Gets the order of the tool in the tool pane
	 * 
	 * @return The index value
	 */
	public int getIndex() {
		return toolIndex;
	}

	/**
	 * Gets the name used to identify files related to the tool
	 * 
	 * @return The internal tool ID name
	 */
	public String getInternalToolName() {
		return internalToolName;
	}
}
