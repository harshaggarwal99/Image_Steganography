import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class UploadImageModel
{
    public static boolean checkImage(String imagePath)throws IOException
    {
        File file = new File(imagePath);
        return file.exists() && ImageIO.read(file) != null;
    }
}
