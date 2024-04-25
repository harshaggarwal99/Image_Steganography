import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UploadImageView extends Application
{
    //ID constant for different layout parameters
    private static final String FILE_PATH_TEXT_FIELD = "file_path_tf";
    private static final String BROWSE_BUTTON = "browse_button";
    private static final String UPLOAD_BUTTON = "upload_button";
    private static final String ENCODE_BUTTON = "encode_button";
    private static final String DECODE_BUTTON = "decode_button";

    //Create getters for ID constants
    public static String getIDBrowseButton()
    {
        return BROWSE_BUTTON;
    }

    public static String getIDUploadButton()
    {
        return UPLOAD_BUTTON;
    }

    public static String getIDEncodeButton()
    {
        return ENCODE_BUTTON;
    }

    public static String getIDDecodeButton()
    {
        return DECODE_BUTTON;
    }

    public static String getIDFilePathTextField()
    {
        return FILE_PATH_TEXT_FIELD;
    }

    /* Create the view for the UploadImageView class. UploadImageView class consists of:
           
           CENTRE:
               1: A text field to input file path
               2: A button to opening the browse path dialog box
               3: Upload button to upload the image
               
           BOTTOM:
               4: An encodeModel button to create EncodeView
               5: An decodeModel button to create DecodeView
    */
    @Override
    public void start(Stage primaryStage)
    {
        //Give the stage a title
        primaryStage.setTitle("Image Steganography");

        //Give the stage an icon
        Image image = new Image("C:\\Users\\harsh\\IdeaProjects\\Steganography\\Resource\\Lock Icon\\icons8-lock-16.png");
        primaryStage.getIcons().add(image);

        //Disable maximize button of primary stage
        primaryStage.setResizable(false);

        //Get BorderPane as the root node and add padding around it
        BorderPane rootNode = new BorderPane();
        rootNode.setPadding(new Insets(10));

        //Get AnchorPane which contains layout of the centre
        AnchorPane centreLayout = createCentre();

        //Add the centreLayout to rootNode
        rootNode.setCenter(centreLayout);

        //Get AnchorPane  which contains layout of the bottom
        AnchorPane bottomLayout = createBottom();

        //Add the centreLayout to rootNode
        rootNode.setBottom(bottomLayout);

        //Create a scene to add on the primary stage
        Scene primaryScene = new Scene(rootNode, 400, 300);

        //Add the scene to the primary stage
        primaryStage.setScene(primaryScene);

        //Display the primary stage
        primaryStage.show();

        //Set controller of the view
        setController(primaryScene);
    }

    //This method creates the centre layout of the application. 
    private AnchorPane createCentre()
    {
        //Create a Anchor Pane as the parentNode of the centre layout
        AnchorPane parentNode = new AnchorPane();

        /* Create the child nodes of the parent node. These are the required child nodes:
               1: HBox containing a Text Field and Button
               2: Button for uploading the image
        */

        //Create a HBox to hold text field and folder button
        HBox inputNodes = new HBox();
        inputNodes.setSpacing(5.0);
        
        //Create the input text field and set its ID
        TextField filePathField = new TextField();
        filePathField.setId(FILE_PATH_TEXT_FIELD);

        //Create the browse button and its ID
        Button browse = new Button();
        browse.setId(BROWSE_BUTTON);

        //Add icon to the button
        ImageView image = new ImageView(new Image("C:\\Users\\harsh\\IdeaProjects\\Steganography\\Resource\\Folder Icon\\icons8-folder-18.png"));
        browse.setGraphic(image);
        
        //Add all the nodes in the HBox
        inputNodes.getChildren().addAll(filePathField, browse);

        //Create an upload button and sets its ID
        Button uploadButton = new Button("Upload");
        uploadButton.setId(UPLOAD_BUTTON);

        //Add anchor to the text field
        AnchorPane.setTopAnchor(inputNodes, (double) 110);
        AnchorPane.setLeftAnchor(inputNodes, (double)100);

        //Add anchor to the Button
        AnchorPane.setTopAnchor(uploadButton, (double)140);
        AnchorPane.setLeftAnchor(uploadButton, (double)158);

        //Add all node to the parent node
        parentNode.getChildren().addAll(inputNodes, uploadButton);

        return parentNode;
    }

    //This method creates the bottom layout of the application.
    private AnchorPane createBottom()
    {
        //Create a Anchor Pane as the parentNode of the bottom layout
        AnchorPane parentNode = new AnchorPane();

        //Create two buttons encodeModel and decodeModel and set their ID
        Button encode = new Button("Encode");
        encode.setId(ENCODE_BUTTON);

        Button decode = new Button("Decode");
        decode.setId(DECODE_BUTTON);

        //Add anchor to the encodeModel Button
        AnchorPane.setBottomAnchor(encode, (double)0);
        AnchorPane.setLeftAnchor(encode, (double)0);

        //Add anchor to the decodeModel Button
        AnchorPane.setBottomAnchor(decode, (double)0);
        AnchorPane.setRightAnchor(decode, (double)0);

        //Add all node to the parent node
        parentNode.getChildren().addAll(encode, decode);

        return parentNode;
    }

    private void setController(Scene scene)
    {
        new UploadImageController(scene).setListenerOnNodes();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
