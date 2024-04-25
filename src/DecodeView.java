import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DecodeView
{
    //ID constant for different layout parameters
    private static final String DECODED_MESSAGE_AREA = "decoded_message_area";

    //Final String containing the image path
    private final String imagePath;

    DecodeView(String imagePath)
    {
        this.imagePath = imagePath;
    }

    //Create getters for ID constants
    public static String getIDDecodedMessageArea()
    {
        return DECODED_MESSAGE_AREA;
    }

    /* Create the view for the DecodeView class. DecodeView class consists of:

           CENTRE:
               1: A message text
               2: A text area to show the decoded message
    */
    public void createView()
    {
        //Create a new Stage
        Stage primaryStage = new Stage();

        //Give the stage a title
        primaryStage.setTitle("Decode");

        //Give the stage an icon
        Image image = new Image("C:\\Users\\harsh\\IdeaProjects\\Steganography\\Resource\\Lock Icon\\icons8-lock-16.png");
        primaryStage.getIcons().add(image);

        //Disable maximize button of primary stage
        primaryStage.setResizable(false);

        //Create a Flow pane as the root node and set its padding
        FlowPane rootNode = new FlowPane();
        rootNode.setPadding(new Insets(35));

        //Set orientation, alignment and spacing of nodes in flow pane
        rootNode.setOrientation(Orientation.VERTICAL);
        rootNode.setAlignment(Pos.BASELINE_CENTER);
        rootNode.setVgap(5);

        //Create a text to display
        Text messageText = new Text("Message:");
        messageText.setFont(Font.font("Open Sans", 11));
        
        //Create a text area to display the message and set its ID
        TextArea decodedMessageArea = new TextArea();
        decodedMessageArea.setId(DECODED_MESSAGE_AREA);

        //Set the maximum height and width of text area
        decodedMessageArea.setMaxHeight(250);
        decodedMessageArea.setMaxWidth(278);

        //Set wrap text as true
        decodedMessageArea.setWrapText(true);

        //Set text area as non editable
        decodedMessageArea.setEditable(false);
        
        //Set a starting message for demo
        decodedMessageArea.setText("");
        
        //Add both the nodes to the Flow pane
        rootNode.getChildren().addAll(messageText, decodedMessageArea);
        
        //Create a scene
        Scene primaryScene = new Scene(rootNode, 350, 300);

        //Set the scene on the stage
        primaryStage.setScene(primaryScene);

        //Show the stage
        primaryStage.show();

        //Set controller of the view
        setController(primaryScene);
    }

    private void setController(Scene primaryScene)
    {
        try
        {
            DecodeViewController viewController = new DecodeViewController(primaryScene, imagePath);
            viewController.setListenerOnNodes();
        }
        catch (IOException ioe)
        {
            DialogView.createErrorDialog("Some error occurred while starting the decoding process.").showAndWait();
        }
    }
}
