package editor;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.scene.Group;

import java.io.*;
import java.util.List;

/**
 * This program copies all of the text in one file to a new file. The first command line argument
 * should be the name of the file to copy from, and the second command line argument should be the
 * name of the file to copy to.
 */
public class ReadFile {

    public static void addToList(List<SingleLetter> singleLetterList, Group root, String inputFilename) {
        try {
            File inputFile = new File(inputFilename);
            // Check to make sure that the input file exists!
            if (!inputFile.exists()) {
                System.out.println("Unable to copy because file with name " + inputFilename
                        + " does not exist");
                return;
            }
            FileReader reader = new FileReader(inputFilename);
            // It's good practice to read files using a buffered reader.  A buffered reader reads
            // big chunks of the file from the disk, and then buffers them in memory.  Otherwise,
            // if you read one character at a time from the file using FileReader, each character
            // read causes a separate read from disk.  You'll learn more about this if you take more
            // CS classes, but for now, take our word for it!
            BufferedReader bufferedReader = new BufferedReader(reader);
            // Create a FileWriter to write to outputFilename. FileWriter will overwrite any data
            // already in outputFilename.

            int intRead = -1;
            // Keep reading from the file input read() returns -1, which means the end of the file
            // was reached.
            int checker = 0;
            while ((intRead = bufferedReader.read()) != -1) {
                //TODO BUG WITH NEW LINES
                // The integer read can be cast to a char, because we're assuming ASCII.
                char charRead = (char) intRead;
                String newChar = String.valueOf(charRead);
                if (intRead == 10) {
                    newChar = "\n";
                }
                SingleLetter currentChar = new SingleLetter(0, 0, newChar);
                singleLetterList.add(checker, currentChar);
                checker += 1;
                currentChar.addToRoot(root);
            }
            // Close the reader and writer.
            bufferedReader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Error when copying; exception was: " + ioException);
        }
    }

    public static void saveFile(List<SingleLetter> singleLetterList, String filename) {
        try {
            File inputFile = new File(filename);
            // Check to make sure that the input file exists!
            if (!inputFile.exists()) {
                System.out.println("Unable to save because file with name " + filename
                        + " does not exist");
                return;
            }

            FileWriter writer = new FileWriter(filename);

            // Keep reading from the file input read() returns -1, which means the end of the file
            // was reached.
            for (SingleLetter x: singleLetterList) {
                String c = x.getCharacter().getText();
                writer.write(c);
            }

            System.out.println("Successfully saved file " + filename);

            // Close the reader and writer.
            writer.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found! Exception was: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("Error when copying; exception was: " + ioException);
        }
    }

}
