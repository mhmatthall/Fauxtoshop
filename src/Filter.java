public enum Filter {
	LAPLACIAN_5(5, "Edge Detect (Laplacian 5x5)",
			new int[] {	-4, -1,  0, -1, -4,
						-1,  2,  3,  2, -1,
						 0,  3,  4,  3,  0,
						-1,  2,  3,  2, -1,
						-4, -1,  0, -1, -4	}),
	GAUSSIAN_5(5, "Gaussian Blur (5x5)",
			new int[] { 1,  4,  6,  4, 1,
						4, 16, 26, 16, 4,
						6, 24, 36, 24, 6,
						4, 16, 24, 16, 4,
						1,  4,  6,  4, 1	});
	
	private final int filterSize;
	private final String filterDescription;
	private final int[] filterPattern;
	
	private Filter(int filterID, String filterDescription, int[] filterPattern) {
		this.filterSize = filterID;
		this.filterDescription = filterDescription;
		this.filterPattern = filterPattern;
	}
	
	public int getSize() {
		return filterSize;
	}
	
	public String getDescription() {
		return filterDescription;
	}
	
	public int[] getPattern() {
		return filterPattern;
	}
}
