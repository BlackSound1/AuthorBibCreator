// -----------------------------------------------------
// Written by: Matthew Segal
// ----------------------------------------------------
import java.io.File;

public class FileExistsException extends Exception {

    /**
     *  Default Constructor for FileExistException
     */
    public FileExistsException(){
        super();
    }

    /**
     * Parameterized Constructor for FileExistsException
     * @param authorName The name of the author
     * @param outputFiles The Array of output files
     */
    FileExistsException(String authorName, File[] outputFiles){
        System.out.println("Exception: There is already an existing\n" +
                " file for " + authorName + ". File will\n" +
                "be renamed as a BU, and older BU files will be deleted!");
    }

    /**
     * Message Constructor for FileExistsException
     * @param message The message to return
     */
    public FileExistsException(String message){
        System.out.println(message);
    }

}
