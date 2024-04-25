import java.awt.image.BufferedImage;
import java.util.BitSet;

public class DecodeModel
{
    private final BufferedImage IMAGE;
    private final StringBuilder DECODED_MESSAGE;
    private final BitSet CHARACTER_BITS;
    private int coordinateX;
    private int coordinateY;
    private int bitIndex;

    /* Public constructor to initialize all data members. Following actions take place in the constructor:
           1: IMAGE is set
           2: DECODED_MESSAGE is initialized as an empty string
           3: CHARACTER_BITS size is allocated
           4: coordinateX, coordinateY and bitIndex are set to 0
    */
    DecodeModel(BufferedImage bufferedImage)
    {
        //Set the IMAGE, DECODED_MESSAGE and CHARACTER_BITS
        IMAGE = bufferedImage;
        DECODED_MESSAGE = new StringBuilder();
        CHARACTER_BITS = new BitSet();

        coordinateX = coordinateY = bitIndex = 0;
    }

    /* The following methods reads 8 bits from the BitSet and converts them to
       their ASCII representation. It returns the decoded character.

       If any extra bits are present in the BitSet, then they are added in the
       start of the BitSet after 8 bits are read.
    */
    private int getCharacter(int extraBits)
    {
        int character = 0;

        //Loop to read 8 bits and form a character from them
        for (int i = 0;i < 8;i++)
        {
            if (CHARACTER_BITS.get(i)) character += (int)Math.pow(2, i);
        }

        int index = 0;
        boolean bitValue;

        //Add the bits left in the start
        while (index < extraBits)
        {
            bitValue = CHARACTER_BITS.get(8+index);
            CHARACTER_BITS.set(index++, bitValue);
        }

        return character;
    }

    /* Method to decodeModel characters from the input image. Message can only be decoded if it is encoded
       using the EncodeModel class. The method reads 3 encoded bits from each pixel. It passes the bits
       to getCharacter(int) method to decodeModel them. It returns the decoded message.

       If the image does not contain any message, then a String containing random characters
       is returned.
    */
    public String decode()
    {
        int character;

        do {
            do
            {
                setBits();

                //If 8 or more bits are present in the BitSet then decodeModel the bits
            }while (bitIndex < 8 && coordinateY != IMAGE.getHeight());

            //Get the encoded character and add it to DECODED_MESSAGE
            character = getCharacter(bitIndex - 8);
            DECODED_MESSAGE.append((char) character);

            //Set the correct bitIndex
            bitIndex = bitIndex-8;

            //Stop the loop if ` character is found
        }while (character != '`' && coordinateY != IMAGE.getHeight());

        //Remove the last character from DECODED_MESSAGE that denotes message end
        DECODED_MESSAGE.deleteCharAt(DECODED_MESSAGE.length()-1);

        return DECODED_MESSAGE.toString();
    }


    /* Method to check if there is a message hidden inside the image or not.
       '`' character which denotes message start is used to check for the presence of image
    */
    public boolean hasEncodedMessage()
    {
        //Initialize the necessary variables
        int character;

        //Get the first 9 bits out of which first 8 represent the first character
        while (bitIndex < 8) setBits();

        //Get the character and add the extra bit to the start
        character = getCharacter(1);
        //Reset bit index
        bitIndex = bitIndex-8;

        //Return if there is a message or not
        return character == '`';
    }

    /* Method to set next 3 bits of CHARACTER_BITS
    */
    private void setBits()
    {
        int pixel;
        int bitRed;
        int bitGreen;
        int bitBlue;

        //Get pixel
        pixel = IMAGE.getRGB(coordinateX, coordinateY);

        //Get encoded bits from pixel
        bitRed = (pixel >> 16) & 1;
        bitGreen = (pixel >> 8) & 1;
        bitBlue = pixel & 1;

        //Add the bits to BitSet
        CHARACTER_BITS.set(bitIndex++, bitRed != 0);
        CHARACTER_BITS.set(bitIndex++, bitGreen != 0);
        CHARACTER_BITS.set(bitIndex++, bitBlue != 0);

        //Increment the index of coordinateX and coordinateY to the next pixel coordinate
        coordinateX++;
        if (coordinateX == IMAGE.getWidth())
        {
            coordinateY++;
            coordinateX = 0;
        }
    }
}

