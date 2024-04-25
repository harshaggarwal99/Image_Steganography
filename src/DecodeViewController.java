import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DecodeViewController
{
    //Create the required member variables
    DecodeModel decodeModel;
    TextArea decodedMessageArea;

    DecodeViewController(Scene scene, String imagePath)throws IOException
    {
        BufferedImage image = ImageIO.read(new File(imagePath));

        //Initialize all the member variables
        decodeModel = new DecodeModel(image);
        decodedMessageArea = (TextArea)scene.lookup("#" + DecodeView.getIDDecodedMessageArea());
    }

    public void setListenerOnNodes()
    {
        //Set the decoded message area as non editable
        decodedMessageArea.setEditable(false);

        //Check if there is a message present in the image or not
        if (!decodeModel.hasEncodedMessage())
        {
            //If not show error and disable the decodedMessageArea
            DialogView.createErrorDialog("No message found!").show();
            decodedMessageArea.setDisable(true);
        }
        else
        {
            //If message is there, then decode the image
            String decodedMessage = decodeModel.decode();

            //Set the message in the text area
            decodedMessageArea.setText(decodedMessage);
        }
    }
}
