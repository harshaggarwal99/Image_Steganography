import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class UploadImageController
{
    //Create the required member variables
    private final TextField filePathTextField;
    private final Button browseButton;
    private final Button uploadButton;
    private final Button encodeButton;
    private final Button decodeButton;

    public UploadImageController(Scene scene)
    {
        //Initialize all the member variables
        filePathTextField = (TextField)scene.lookup("#" + UploadImageView.getIDFilePathTextField());
        browseButton = (Button)scene.lookup("#" + UploadImageView.getIDBrowseButton());
        uploadButton = (Button)scene.lookup("#" + UploadImageView.getIDUploadButton());
        encodeButton = (Button)scene.lookup("#" + UploadImageView.getIDEncodeButton());
        decodeButton = (Button)scene.lookup("#" + UploadImageView.getIDDecodeButton());
    }

    public void setListenerOnNodes()
    {
        //Add listener to the filePathTextField
        filePathTextField.setOnAction(actionEvent -> uploadButton.fire());

        //Set prompt text to the filePathTextField
        filePathTextField.setPromptText("Enter the file path");

        //Add listener to the text property of filePathTextField
        filePathTextField.textProperty().addListener((observableValue, s, t1) -> setEncodeDecodeDisable(true));

        //Add listener to the browse button
        browseButton.setOnAction(actionEvent ->
        {
            //Open a file chooser dialog box 
            File inputFilePath = new FileChooser().showOpenDialog(browseButton.getScene().getWindow());
            
            //Set the path on the file path text field if it it not null
            if (inputFilePath != null) filePathTextField.setText(inputFilePath.getAbsolutePath());
        });


        //Add listener to the upload button
        uploadButton.setOnAction(actionEvent ->
        {
            //Get image path
            String imagePath = filePathTextField.getText();

            try
            {
                //Try to upload the image
                if (UploadImageModel.checkImage(imagePath))
                {
                    //Create and show a dialog showing that the file path exists
                    DialogView.createSuccessDialog("Image uploaded successfully").showAndWait();

                    //Enable the encodeModel and decodeModel button
                    setEncodeDecodeDisable(false);
                }
                else
                {
                    //Create and show a dialog showing that the file path does not exist
                    DialogView.createErrorDialog("The image path is invalid.").showAndWait();
                }
            }
            catch (IOException exception)
            {
                //Create and show a dialog showing that some error occurred while uploading the image
                DialogView.createErrorDialog("Some error occurred while uploading the image.").showAndWait();
            }
        });


        //Add listener to the encodeModel button
        encodeButton.setOnAction(actionEvent ->
        {
            //Display the encodeModel view window
            new EncodeView(filePathTextField.getText()).createView();
        });


        //Add listener to the decodeModel button
        decodeButton.setOnAction(actionEvent ->
        {
            //Display the encodeModel view window
            new DecodeView(filePathTextField.getText()).createView();
        });

        //Disable the encodeModel and decodeModel button in the start
        setEncodeDecodeDisable(true);
    }

    private void setEncodeDecodeDisable(boolean isDisable)
    {
        encodeButton.setDisable(isDisable);
        decodeButton.setDisable(isDisable);
    }
}
