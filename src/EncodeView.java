import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EncodeView
{
    //ID constant for different layout parameters
    private static final String MESSAGE_INPUT_AREA = "message_input_area";
    private static final String ENCODE_BUTTON = "encode_button";
    private static final String CHARACTERS_SPACE_LEFT_TEXT = "characters_space_left";

    //Final String containing the image path
    private final String imagePath;

    //Constructor to initialize the image path
    EncodeView(String imagePath)
    {
        this.imagePath = imagePath;
    }

    //Create getters for ID constants
    public static String getIDMessageInputArea()
    {
        return MESSAGE_INPUT_AREA;
    }

    public static String getIDEncodeButton()
    {
        return ENCODE_BUTTON;
    }

    public static String getIDCharactersSpaceLeftText() { return CHARACTERS_SPACE_LEFT_TEXT; }

    /* Create the view for the EncodeView class. EncodeView class consists of:

               CENTRE:
                   1: A message text
                   2: A text area to input message to be encoded
                   3: A text displaying total characters that can be encoded

               BOTTOM:
                   3: An encodeModel button to begin the process of encoding
        */
    public void createView()
    {
        //Create a new Stage
        Stage primaryStage = new Stage();

        //Give the stage a title
        primaryStage.setTitle("Encode");

        //Give the stage an icon
        Image image = new Image("C:\\Users\\harsh\\IdeaProjects\\Steganography\\Resource\\Lock Icon\\icons8-lock-16.png");
        primaryStage.getIcons().add(image);

        //Disable maximize button of primary stage
        primaryStage.setResizable(false);

        //Create a BorderPane as the root node and add padding around it
        BorderPane rootNode = new BorderPane();
        rootNode.setPadding(new Insets(10));

        //Add centre of the root node
        rootNode.setCenter(createCentre());

        //Create a button and set its ID
        Button encode = new Button("Encode");
        encode.setId(ENCODE_BUTTON);

        //Add the button to the bottom of the root node
        BorderPane.setAlignment(encode, Pos.BOTTOM_CENTER);
        rootNode.setBottom(encode);

        //Create a scene for the stage
        Scene primaryScene = new Scene(rootNode, 400, 280);

        //Add the scene to the stage
        primaryStage.setScene(primaryScene);

        //Restrict actions on other open windows
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        //Show the stage
        primaryStage.show();

        //Set controller of the view
        setController(primaryScene);
    }

    private void setController(Scene primaryScene)
    {
        try
        {
            EncodeViewController viewController = new EncodeViewController(primaryScene, imagePath);
            viewController.setListenerOnNodes();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    //Creates the centre part of EncodeView class.
    private FlowPane createCentre()
    {
        //Create a Flow pane as a parent node
        FlowPane parentNode = new FlowPane();

        //Set the width and height of flow pane
        parentNode.setMaxHeight(220);
        parentNode.setMaxWidth(200);

        //Set orientation, alignment and spacing of nodes in flow pane
        parentNode.setOrientation(Orientation.VERTICAL);
        parentNode.setAlignment(Pos.CENTER);
        parentNode.setVgap(5);

        //Create a text to display
        Text messageText = new Text("Message:");
        messageText.setFont(Font.font("Open Sans", 11));

        //Create a text area and set its ID
        TextArea messageInputArea = new TextArea();
        messageInputArea.setId(MESSAGE_INPUT_AREA);

        //Set the maximum height and width of text area
        messageInputArea.setMaxHeight(150);
        messageInputArea.setMaxWidth(200);

        //Set wrap text as true
        messageInputArea.setWrapText(true);

        //Set the prompt text
        messageInputArea.setPromptText("Enter the message");


        //Create a text displaying total characters that can be encoded and set its ID
        Text charactersSpaceLeftText = new Text("Characters Left: 0");
        charactersSpaceLeftText.setId(CHARACTERS_SPACE_LEFT_TEXT);
        charactersSpaceLeftText.setFont(Font.font("Open Sans", 11));

        //Add both the nodes to the parent node
        parentNode.getChildren().addAll(messageText, messageInputArea, charactersSpaceLeftText);

        return parentNode;
    }
}
