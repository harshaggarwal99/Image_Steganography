import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DialogView
{
    //Dialog showing that some operation has been performed successfully
    public static Alert createSuccessDialog(String successMessage)
    {
        //Obtain the image to be set as graphic
        Image iconAccept = new Image("C:\\Users\\harsh\\IdeaProjects\\Steganography\\Resource\\Accept Icon\\icons8-checked-40.png");

        //Create the dialog
        Alert successDialog = new Alert(Alert.AlertType.INFORMATION);

        //Set the header text, content text and title of the alert dialog
        successDialog.setTitle("Success");
        successDialog.setHeaderText(null);
        successDialog.setContentText(successMessage);

        //Add image to the alert dialog
        successDialog.setGraphic(new ImageView(iconAccept));

        return  successDialog;
    }

    //Dialog showing that error occurred while performing some operation
    public static Alert createErrorDialog(String errorMessage)
    {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);

        //Set the header text, content text and title of the alert dialog
        errorDialog.setTitle("Error");
        errorDialog.setHeaderText(null);
        errorDialog.setContentText(errorMessage);

        return errorDialog;
    }
}
