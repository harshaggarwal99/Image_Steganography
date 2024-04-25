import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.BitSet;

public class EncodeModel
{
    private final BufferedImage IMAGE;
    private  ByteBuffer MESSAGE_BYTES;
    private final BitSet CHARACTER_BITS;
    private int bitsetLength;

    /* Public constructor to initialize all data members. Following actions take place in the constructor:
           1: IMAGE is set
           2: CHARACTER_BITS size is allocated
           3: bitsetLength is set
    */
    public EncodeModel(BufferedImage bufferedImage)
    {
        //Set the IMAGE and bitsetLength
        this.IMAGE = bufferedImage;
        bitsetLength = (int)Math.ceil(bufferedImage.getWidth()*3/8.0)*8;

        //Create a new BitSet
        CHARACTER_BITS = new BitSet(bitsetLength);

        //Set MESSAGE_BITS as null
        MESSAGE_BYTES = null;
    }

    /* Method to add bits in a BitSet. If any bits are left to be written to the image, they are
       first added to the start the BitSet and then new bits are added to the BitSet
    */
    private void fillBitSet(int bitsLeft)
    {
        boolean bitValue;
        byte tempByte;
        int index  = 0;

        //Loop to add the remaining bits to the start of the BitSet
        while (index < bitsLeft)
        {
            bitValue = CHARACTER_BITS.get(bitsetLength-bitsLeft+index-1);
            CHARACTER_BITS.set(index++, bitValue);
        }

        //Loop to fill the remaining BitSet with bytes
        while (bitsetLength-index >= 8)
        {
            //Get the bytes of the character
            if (MESSAGE_BYTES.hasRemaining()) tempByte = MESSAGE_BYTES.get();
            else break;

            //Convert the byte to bits and add it to the BitSet
            for (int i = 0;i < 8;i++)
            {
                CHARACTER_BITS.set(index, tempByte%2 == 1);
                tempByte/=2;

                index++;
            }
        }

        bitsetLength = index;
    }

    /* Method to encodeModel the bytes in the image. The given code works for RBG, BGR, ARGB and ARGB Pre image representation.
       The code only uses the last bit of each Red, Green and Blue color of the image. Hence, each encoded pixel consists
       3 bits of the message to be encoded.
    */
    public BufferedImage encode()
    {
        //Set the bitIndex
        int bitIndex = bitsetLength;
        //Initialize the necessary variables
        int x, y, pixel, bitRed, bitGreen, bitBlue;
        x = y = 0;

        while (MESSAGE_BYTES.hasRemaining())
        {
            //Fill the bits in BitSet
            fillBitSet(bitsetLength-bitIndex);
            bitIndex = 0;

            //Add the bits to the pixel iff bits can be added to each red, green and blue color byte
            while (bitsetLength-bitIndex >= 3)
            {
                pixel = IMAGE.getRGB(x, y);

                bitRed = CHARACTER_BITS.get(bitIndex++) ? 1 : 0;
                bitGreen = CHARACTER_BITS.get(bitIndex++) ? 1 : 0;
                bitBlue = CHARACTER_BITS.get(bitIndex++) ? 1 : 0;

                //Unset bit 23 of pixel and set then it to bitRed
                pixel = pixel & ~(1<<16);
                pixel = pixel | (bitRed<<16);

                //Unset bit  of pixel and set then it to bitGreen
                pixel = pixel & ~(1<<8);
                pixel = pixel | (bitGreen<<8);

                //Unset bit 23 of pixel and set then it to bitBlue
                pixel = pixel & ~1;
                pixel = pixel | bitBlue;

                //Set the pixel in the image
                IMAGE.setRGB(x, y, pixel);

                //Update the coordinates of image
                x++;
                if (x == IMAGE.getWidth())
                {
                    x = 0;
                    y++;
                }
            }
        }

        //Check if any bits are left to written
        //If so, then write them
        if (bitsetLength-bitIndex >= 1)
        {
            pixel = IMAGE.getRGB(x, y);
            bitRed = CHARACTER_BITS.get(bitIndex++) ? 1 : 0;

            //Unset bit 23 of pixel and set then it to bitRed
            pixel = pixel & ~(1<<16);
            pixel = pixel | (bitRed<<16);

            if (bitsetLength-bitIndex == 1)
            {
                bitGreen = CHARACTER_BITS.get(bitIndex) ? 1 : 0;

                //Unset bit  of pixel and set then it to bitGreen
                pixel = pixel & ~(1<<8);
                pixel = pixel | (bitGreen<<8);
            }

            IMAGE.setRGB(x, y, pixel);
        }

        return IMAGE;
    }

    /* Method to set the message to be encoded. If message length is greater than
       maximum characters that can be encoded in the image then an IndexOutOfBoundsException
       is thrown
     */
    public void setMessage(String message)
    {
        //Check if message can be encoded in the image or not
        if (message.length() > getMaxCharactersInMessage()) throw new IndexOutOfBoundsException("Message length is too long!");

        //Add ` character at the start and end of the image to locate message start and message end
        message = '`' + message + '`';

        //Allocate a byte buffer to hold bytes for all characters of the message and null character
        MESSAGE_BYTES = ByteBuffer.allocate(message.length());
        MESSAGE_BYTES.order(ByteOrder.LITTLE_ENDIAN);

        //Add bytes to MESSAGE_BYTES
        for (int i = 0;i < message.length();i++)
        {
            //Obtain only the last 8 bits of the character as only ASCII characters
            //will be used in encoding
            byte tempByte = (byte)message.charAt(i);
            MESSAGE_BYTES.put(tempByte);
        }

        //Set the position to 0
        MESSAGE_BYTES.flip();
    }

    //This method returns the maximum number of characters that can be encoded in the image
    public int getMaxCharactersInMessage()
    {
        return (int)Math.floor(IMAGE.getHeight()*IMAGE.getWidth()*3.0/8.0)-2;
    }
}

