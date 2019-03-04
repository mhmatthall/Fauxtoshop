/**
 * An enumeration which allows for the easy modification of the tools used in
 * the program, and streamlined loading of assets for each tool.
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

	public int getIndex() {
		return toolIndex;
	}

	public String getInternalToolName() {
		return internalToolName;
	}
}
