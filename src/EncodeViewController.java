import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EncodeViewController
{
    //Create the required member variables
    String imagePath;
    EncodeModel encodeModel;
    TextArea messageInputArea;
    Button encodeButton;
    Text charactersSpaceLeftText;
    final int maxCharactersInMessage;

    EncodeViewController(Scene scene, String imagePath)throws IOException
    {
        BufferedImage image = ImageIO.read(new File(imagePath));

        //Initialize all the member variables
        this.imagePath = imagePath.replaceFirst("\\.[a-z]*", "_Encoded.png");
        encodeModel = new EncodeModel(image);
        messageInputArea = (TextArea) scene.lookup("#" + EncodeView.getIDMessageInputArea());
        encodeButton = (Button) scene.lookup("#" + EncodeView.getIDEncodeButton());
        charactersSpaceLeftText = (Text) scene.lookup("#" + EncodeView.getIDCharactersSpaceLeftText());
        maxCharactersInMessage = encodeModel.getMaxCharactersInMessage();
    }

    public void setListenerOnNodes()
    {
        //Add listener to encodeModel button
        encodeButton.setOnAction(actionEvent ->
        {
            String message = messageInputArea.getText();

            if (message.isEmpty())
            {
                //Create and show a dialog showing that no message has been entered
                DialogView.createErrorDialog("No message entered!").showAndWait();
            }
            else
            {
                //Set the message in encodeModel object
                encodeModel.setMessage(message);
                //EncodeModel the message in the image
                BufferedImage encodedImage = encodeModel.encode();
                //Save the encoded image to the source image destination with different name
                try
                {
                    ImageIO.write(encodedImage, "png", new File(imagePath));
                }
                catch (IOException ioe)
                {
                    //Create and show a dialog showing that some error occurred while encoding the image
                    DialogView.createErrorDialog("Some error occurred starting the encoding process.").showAndWait();
                }

                //Create and show a dialog showing that message has been encoded successfully
                DialogView.createSuccessDialog("Message encoded successfully. New encoded image generated in the source image folder.").showAndWait();
            }
        });

        //Add listener to the message input area

        /* The following method can be improved since it results in another call of the same method due the usage
           of messageInputArea.textProperty().set(s); in the if condition
           It is bad coding and I am not proud of it.
        */
        messageInputArea.textProperty().addListener((observableValue, s, t1) ->
        {
            //If message is too big, report error
            if (t1.length() > maxCharactersInMessage)
            {
                messageInputArea.textProperty().set(s);
                DialogView.createErrorDialog("Input message is too long!").showAndWait();
            }
            
            updateCharactersSpaceLeft(messageInputArea.getText().length());
        });

        //Set the initial characters left
        updateCharactersSpaceLeft(0);
    }

    private void updateCharactersSpaceLeft(int charactersPresent)
    {
        String oldText = charactersSpaceLeftText.getText();
        charactersSpaceLeftText.setText(oldText.replaceFirst("[0-9]+", String.valueOf(maxCharactersInMessage-charactersPresent)));
    }
}
