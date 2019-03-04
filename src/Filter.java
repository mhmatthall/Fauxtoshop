/**
 * An enumeration which allows for the easy modification and addition of filter
 * kernels, which will automatically be added as options in the Filter tool.
 * <p>
 * I declare that the following is my own work.
 * 
 * @author Matt Hall (961500)
 */
public enum Filter {
	LAPLACIAN_5(5, "Edge Detect (Laplacian 5x5)",
			new int[] { -4, -1, 0, -1, -4,
						-1, 2, 3, 2, -1,
						 0, 3, 4, 3, 0,
						-1, 2, 3, 2, -1,
						-4, -1, 0, -1, -4 }),
	GAUSSIAN_5(5, "Gaussian Blur (5x5)",
			new int[] { 1,  4,  6,  4, 1,
						4, 16, 26, 16, 4,
						6, 24, 36, 24, 6,
						4, 16, 24, 16, 4,
						1,  4,  6,  4, 1 });

	private final int filterSize;
	private final String filterDescription;
	private final int[] filterPattern;

	private Filter(int filterID, String filterDescription, int[] filterPattern) {
		this.filterSize = filterID;
		this.filterDescription = filterDescription;
		this.filterPattern = filterPattern;
	}

	/**
	 * Gets the size of the filter, which is the amount of pixels sampled per
	 * calculation
	 * <p>
	 * e.g. size 5 samples 5 x 5 = 25 pixels
	 * 
	 * @return The size of the filter
	 */
	public int getSize() {
		return filterSize;
	}

	/**
	 * Gets the description of the tool to be shown to the user
	 * 
	 * @return The tool's description message
	 */
	public String getDescription() {
		return filterDescription;
	}

	/**
	 * Gets the filter kernel multiplication pattern to be applied to the pixels
	 * surrounding the target
	 * 
	 * @return The array containing the filter's multiplication pattern
	 */
	public int[] getPattern() {
		return filterPattern;
	}
}
