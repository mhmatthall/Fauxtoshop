import java.awt.Point;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MenuContrastController extends ToolController {
	private Point nodeOrigin = new Point();
	
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
    void node1Clicked(MouseEvent event) {
    	
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

    }

    @FXML
    void node1InTextChanged(ActionEvent event) {

    }

    @FXML
    void node1InTextScrolled(ScrollEvent event) {

    }

    @FXML
    void node1OutSliderUpdated(MouseEvent event) {

    }

    @FXML
    void node1OutTextChanged(ActionEvent event) {

    }

    @FXML
    void node1OutTextScrolled(ScrollEvent event) {

    }

    @FXML
    void node2Clicked(MouseEvent event) {
    	// right-click to reset position to default
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

    }

    @FXML
    void node2InTextChanged(ActionEvent event) {

    }

    @FXML
    void node2InTextScrolled(ScrollEvent event) {

    }

    @FXML
    void node2OutSliderUpdated(MouseEvent event) {

    }

    @FXML
    void node2OutTextChanged(ActionEvent event) {

    }

    @FXML
    void node2OutTextScrolled(ScrollEvent event) {

    }

    private void startNodeDrag(Circle node, MouseEvent event) {
    	double x = node.getLayoutX(); //+ node.getCenterX();
    	double y = node.getLayoutY(); //+ node.getCenterY();
    	nodeOrigin.setLocation(x, y);
    	node.setCursor(Cursor.CLOSED_HAND);
    }
    
    private void dragNode(Circle node, MouseEvent event) {
    	node.setCenterX(event.getX() - nodeOrigin.getX());
    	node.setCenterY(event.getY() - nodeOrigin.getY());
    }
    
    private void endNodeDrag(Circle node, MouseEvent event) {
    	node.setCursor(Cursor.HAND);
    }
    
}
