import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.Point;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MenuContrastController extends ToolController {
	private final int MAX_ALLOWED_NODES = 2;
	
	private Point oldPos = new Point();
	private Point offset = new Point();
	private Point newPos = new Point();
	private int[][] nodePositions = new int[MAX_ALLOWED_NODES][2];
	
	// TODO add boolean to prevent lag caused by processing during drag
	
    @FXML
    private Group grpNodes;
	
    @FXML
    private Circle node1;

    @FXML
    private Circle node2;

    @FXML
    private Rectangle stretchArea;

    @FXML
    private TextField txtNode1In;

    @FXML
    private Slider sldNode1In;

    @FXML
    private TextField txtNode1Out;

    @FXML
    private Slider sldNode1Out;

    @FXML
    private TextField txtNode2In;

    @FXML
    private Slider sldNode2In;

    @FXML
    private TextField txtNode2Out;

    @FXML
    private Slider sldNode2Out;

	@FXML
	private void initialize() {
		// Set the nodes to their default positions (bottom left, top right)
		nodePositions[1][0] = 255;
		nodePositions[1][1] = 255;
		refreshValues();
	}
    
    @FXML
    void node1StartDrag(MouseEvent event) {
    	startNodeDrag(node1, event);
    }
    
    @FXML
    void node1Dragged(MouseEvent event) {
    	dragNode(node1, event);
    }
    
    @FXML
    void node1EndDrag(MouseEvent event) {
    	endNodeDrag(node1, event);
    }
    
    @FXML
    void node1InSliderUpdated(MouseEvent event) {
    	updatePosition(node1, 0, sldNode1In.getValue());
    }

    @FXML
    void node1InTextChanged(ActionEvent event) {
    	updatePosition(node1, 0, Double.valueOf(txtNode1In.getText()));
    }

    @FXML
    void node1InTextScrolled(ScrollEvent event) {
		updatePosition(node1, 0, nodePositions[0][0] + event.getDeltaY() * 0.125);
	}

    @FXML
    void node1OutSliderUpdated(MouseEvent event) {
    	updatePosition(node1, 1, sldNode1Out.getValue());
    }

    @FXML
    void node1OutTextChanged(ActionEvent event) {
    	updatePosition(node1, 1, Double.valueOf(txtNode1Out.getText()));
    }

    @FXML
    void node1OutTextScrolled(ScrollEvent event) {
    	updatePosition(node1, 1, nodePositions[0][1] + event.getDeltaY() * 0.125);
    }

    @FXML
    void node2StartDrag(MouseEvent event) {
    	startNodeDrag(node2, event);
    }
    
    @FXML
    void node2Dragged(MouseEvent event) {
    	dragNode(node2, event);
    }
    
    @FXML
    void node2EndDrag(MouseEvent event) {
    	endNodeDrag(node2, event);
    }

    @FXML
    void node2InSliderUpdated(MouseEvent event) {
    	updatePosition(node2, 0, sldNode2In.getValue());
    }

    @FXML
    void node2InTextChanged(ActionEvent event) {
    	updatePosition(node2, 0, Double.valueOf(txtNode2In.getText()));
    }

    @FXML
    void node2InTextScrolled(ScrollEvent event) {
    	updatePosition(node2, 0, nodePositions[1][0] + event.getDeltaY() * 0.125);
    }

    @FXML
    void node2OutSliderUpdated(MouseEvent event) {
    	updatePosition(node2, 1, sldNode2Out.getValue());
    }

    @FXML
    void node2OutTextChanged(ActionEvent event) {
    	updatePosition(node2, 1, Double.valueOf(txtNode2Out.getText()));
    }

    @FXML
    void node2OutTextScrolled(ScrollEvent event) {
    	updatePosition(node2, 1, nodePositions[1][1] + event.getDeltaY() * 0.125);
    }

    private void startNodeDrag(Circle node, MouseEvent event) {
    	// Set the origin as the current global mouse position
    	oldPos.setLocation(event.getSceneX(), event.getSceneY());
    	
    	// Change the cursor to indicate start of drag
    	node.setCursor(Cursor.CLOSED_HAND);
    }
    
	private void dragNode(Circle node, MouseEvent event) {
		// Set the offset as the delta between the current and previous mouse locations
		offset.setLocation(event.getSceneX() - oldPos.getX(), event.getSceneY() - oldPos.getY());

		// Add offset delta to circle's position
		newPos.setLocation(node.getCenterX() + offset.getX(), node.getCenterY() + offset.getY());

		// Check that X co-ord isn't out of bounds
		if (newPos.getX() > stretchArea.getWidth()) {
			newPos.setLocation(stretchArea.getWidth(), newPos.getY());
		} else if (newPos.getX() < 0) {
			newPos.setLocation(0, newPos.getY());
		}

		// Check that Y co-ord isn't out of bounds
		if (newPos.getY() > stretchArea.getHeight()) {
			newPos.setLocation(newPos.getX(), stretchArea.getHeight());
		} else if (newPos.getY() < 0) {
			newPos.setLocation(newPos.getX(), 0);
		}
		
		
		// Set the circle's new location
		updatePosition(node, 0, newPos.getX());
		updatePosition(node, 1, (255 - newPos.getY()));
		
		// Make sure we reset the old pos between each drag 
		oldPos.setLocation(event.getSceneX(), event.getSceneY());
	}

    private void endNodeDrag(Circle node, MouseEvent event) {
    	// Change the cursor to indicate end of drag
    	node.setCursor(Cursor.OPEN_HAND);
    	
    	// TODO add status bar info changing
    }

    private int getNodeNumber(Node node) {
    	return Integer.valueOf(node.getId().substring(node.getId().length() - 1));
    }
    
	private void refreshValues() {
		int currentPos = 0;
		
		currentPos = nodePositions[0][0];
		txtNode1In.setText(String.valueOf(currentPos));
		sldNode1In.setValue(currentPos);
		node1.setCenterX(currentPos);
		
		currentPos = nodePositions[0][1];
		txtNode1Out.setText(String.valueOf(currentPos));
		sldNode1Out.setValue(currentPos);
		node1.setCenterY(255 - currentPos);
		
		currentPos = nodePositions[1][0];
		txtNode2In.setText(String.valueOf(currentPos));
		sldNode2In.setValue(currentPos);
		node2.setCenterX(currentPos);
		
		currentPos = nodePositions[1][1];
		txtNode2Out.setText(String.valueOf(currentPos));
		sldNode2Out.setValue(currentPos);
		node2.setCenterY(255 - currentPos);
		
		// TODO draw a line between the nodes and the origin/255,255
	}
	
	private void updatePosition(Node node, int nodeDimension, double newValue) {
		if (newValue >= 0) {
			int newPosition = 0;

			if (newValue > 255) {
				// Ensure it doesn't exceed max range
				newPosition = 255;
			} else {
				// Truncate the number
				newPosition = (int) Math.floor(newValue);
			}

			// Ensure that node 1 is always smaller in both dimensions than node 2
			if (getNodeNumber(node) == 1) {
				if (nodeDimension == 0) {
					// Ensure that node 1 is always to the left of node 2
					if (nodePositions[1][0] < newPosition && nodePositions[1][0] == 0) {
						nodePositions[0][0] = 0;
					} else if (nodePositions[1][0] < newPosition) {
						nodePositions[0][0] = nodePositions[1][0] - 1;
					} else {
						nodePositions[0][0] = newPosition;
					}
				} else {
					// Ensure that node 1 is always below node 2
					if (nodePositions[1][1] < newPosition && nodePositions[1][1] == 0) {
						nodePositions[0][1] = 0;
					} else if (nodePositions[1][1] < newPosition) {
						nodePositions[0][1] = nodePositions[1][1] - 1;
					} else {
						nodePositions[0][1] = newPosition;
					}
				}
			} else {
				if (nodeDimension == 0) {
					// Ensure that node 2 is always to the right of node 1
					if (nodePositions[0][0] > newPosition && nodePositions[0][0] == 0) {
						nodePositions[1][0] = 0;
					} else if (nodePositions[0][0] > newPosition) {
						nodePositions[1][0] = nodePositions[0][0] + 1;
					} else {
						nodePositions[1][0] = newPosition;
					}
				} else {
					// Ensure that node 2 is always above node 1
					if (nodePositions[0][1] > newPosition && nodePositions[0][1] == 0) {
						nodePositions[1][1] = 0;
					} else if (nodePositions[0][1] > newPosition) {
						nodePositions[1][1] = nodePositions[0][1] + 1;
					} else {
						nodePositions[1][1] = newPosition;
					}
				}
			}

			// Update the UI elements to reflect the changed position
			refreshValues();

			// Only process the image after the initial UI load
			if (parentController != null) {
				// Then only process the image if one is present
				if (parentController.getImage() != null) {
					processedImage = contrastStretch(uneditedImage);
					updateImage();
				}
			}
		}
	}

	private Image contrastStretch(Image sourceImage) {
		double R1 = nodePositions[0][0];
		double S1 = nodePositions[0][1];
		double R2 = nodePositions[1][0];
		double S2 = nodePositions[1][1];

		// Find the dimensions of the source image
		int width = (int) sourceImage.getWidth();
		int height = (int) sourceImage.getHeight();

		// Create a new image
		WritableImage newImage = new WritableImage(width, height);
		// Get an interface to write to that image memory
		PixelWriter writer = newImage.getPixelWriter();
		// Get an interface to read from the original image passed as the
		// parameter to the function
		PixelReader reader = sourceImage.getPixelReader();

		// Pre-calculate the stretching gradients to save computation in the loop
		double stretchLookup[] = {
				(S1 / R1),
				(S2 - S1) / (R2 - R1),
				(255 - S2) / (255 - R2)				
		};		

		// Iterate over all pixels
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// For each pixel, get the colour
				Color color = reader.getColor(x, y);

				// Store each colour channel's value in an array
				double colours[] = { color.getRed(), color.getGreen(), color.getBlue() };

				// For each colour channel, apply the appropriate multiplication
				for (int i = 0; i < 3; i++) {
					if (colours[i] < (R1 / 255)) {
						colours[i] = colours[i] * stretchLookup[0];
					} else if (colours[i] >= (R2 / 255)) {
						colours[i] = (colours[i] - (R2 / 255)) * stretchLookup[2] + (S2 / 255);
					} else {
						colours[i] = (colours[i] - (R1 / 255)) * stretchLookup[1] + (S1 / 255);
					}
					
					if (colours[i] > 1) {
						colours[i] = 1;
					}
				}

				// Apply the new colour
				color = Color.color(colours[0], colours[1], colours[2]);
				writer.setColor(x, y, color);
			}
		}
		return newImage;
	}
}