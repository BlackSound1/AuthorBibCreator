// -----------------------------------------------------
// Written by: Matthew Segal
// ----------------------------------------------------

import java.io.*;
import java.util.Scanner;

/**
 * This is the final version of AuthorBibCreator. Takes in an author name to create .json files for, using
 * NJ, IEEE, and ACM citation styles. Creats .json files after searching a database of
 * files for relevant articles written by the author.
 */
public class AuthorBibCreator3 {
    public static void main(String[] args) throws FileExistsException {
        // Sets up useful variables
        Scanner userIn = new Scanner(System.in);

        // Identifies the Source directory
        File directory = new File("src/");

        // Creates an array based on files in the Source directory that have the right extension
        File[] neededFiles = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".bib");
            }
        });

        //File[] neededFiles = new File("Assignment 3/").listFiles();

        Scanner[] openedFiles = null;

        // Intro
        System.out.println("WELCOME TO THE AUTHOR BIBLIOGRAPHY CREATOR");
        System.out.println("------------------------------------------");

        // Gets author name
        System.out.print("Enter the last name of the author you'd like to search the database for: ");
        String authorName = userIn.nextLine();

        userIn.close(); // Closes the user input Scanner

        // Opens each file in the needed files and saves each opened file to the openFiles Scanner array
        if (neededFiles != null) {
            openedFiles = openDatabaseFiles(neededFiles);
        }

        // Checks the existence of existing output files based on the authors name
        checkExistence(authorName);

        // Begins the reading process
        processBibFiles(authorName, openedFiles);

        //readFile(authorName,neededFiles);

        // Closes all opened files
        if (openedFiles != null) {
            closeOpenedFiles(openedFiles);
        }

        // Exits program
        System.out.println("Thank you! Goodbye!");
    }

    /**
     * Tries to open each file in the database of needed files. Closes if it can't open one
     * @param neededFiles The Array Object of needed files to scan
     * @return The Scanner Array of all the needed files to scan
     */
    private static Scanner[] openDatabaseFiles(File[] neededFiles) {
        int fileIndex = 0;
        Scanner[] openedFiles = new Scanner[neededFiles.length];
        Scanner input = null;

        // Loops through all the files in the needed files
        for (int i = 0; i < neededFiles.length; i++){
            // Tries to create an InputStream to the file
            try {
                input = new Scanner(new FileInputStream("src/" + neededFiles[i].getName()));

                openedFiles[i] = input; // Adds each new Scanner to the opened files array

                fileIndex++; // Increments the number of acceptable files

            } catch (IOException e){
                System.out.println("Could not open input file Latex" + i +
                        ".bib for reading! Please check if file exists!");
                // If a file can't have an InputStream applied to it, closes all InputStreams before the index
                // of the offending file
                for (int j = 1; j <= fileIndex; j++){
                    input.close();
                }

                System.exit(0); // Exits the program after closing all InputStreams
            }
        }
        return openedFiles; // Returns the Scanner array of opened files
    }

    /**
     * Checks for the existence of existing .json files for each of the citation formats, with the author name
     * @param authorName The name of the author
     * @throws FileExistsException If such a files already exists, throw exception, with additional utility methods
     */
    private static void checkExistence(String authorName) throws FileExistsException {
        // Creates a list of output files
        File[] outputFiles = new File("output").listFiles();

        // If any of the output files exists, throws a FileExistsException
        if (outputFiles != null) {
            for (File file: outputFiles) {
                try {
                    if (file.getName().equalsIgnoreCase(authorName + "-IEEE.json") ||
                            file.getName().equalsIgnoreCase(authorName + "-ACM.json") ||
                            file.getName().equalsIgnoreCase(authorName + "-NJ.json")){

                        // Checks if backup files exist
                        //checkBUExistence(authorName,outputFiles);

                        throw new FileExistsException("Exception: There is already an existing\n" +
                                " file for " + authorName + ". File will\n" +
                                "be renamed as a BU, and older BU files will be deleted!");
                    }
                } catch (FileExistsException e) {
                    checkBUExistence(authorName, outputFiles);
                    renameFile(authorName, file);

                }
            }
        }
    }

    /**
     * Begins the file writing process. Sets up PrintWriters, then uses them to actually write to the
     * different file types needed.
     * @param authorName The name of the author to scan for
     * @param openedFiles The Scanner Array of of all the successfully opened files
     */
    private static void processBibFiles(String authorName, Scanner[] openedFiles){
        PrintWriter[] writers = new PrintWriter[3];

        // Creates the relevant output files
        File IEEEFile = new File("output/" + authorName + "-IEEE.json");
        File ACMFile = new File("output/" + authorName + "-ACM.json");
        File NJFile = new File("output/" + authorName + "-NJ.json");

        // Puts the output files into an array
        File[] outputFiles = {IEEEFile, ACMFile, NJFile};

        // Tries to create PrintWriters for each output file
        for (File file: outputFiles) {
            if (file.getName().equalsIgnoreCase(authorName + "-IEEE.json")){

                try {
                    writers[0] = new PrintWriter(new FileOutputStream("output/" + file.getName(),true));
                } catch (FileNotFoundException e) {
                    System.out.println(file.getName() + "Could not be opened for writing!");

                    // Deletes all output files
                    for (File file2: outputFiles) {
                        file2.deleteOnExit();
                    }
                    break;
                }
            }else if (file.getName().equalsIgnoreCase(authorName + "-ACM.json")){

                try {
                    writers[1] = new PrintWriter(new FileOutputStream("output/" + file.getName(),true));
                }catch (FileNotFoundException e){
                    System.out.println(file.getName() + "Could not be opened for writing!");

                    // Deletes all output files
                    for (File file2: outputFiles) {
                        file2.deleteOnExit();
                    }
                    break;
                }
            }else if ( file.getName().equalsIgnoreCase(authorName + "-NJ.json")){
                try {
                    writers[2] = new PrintWriter(new FileOutputStream("output/" +  file.getName(),true));
                }catch (FileNotFoundException e){
                    System.out.println(file.getName() + "Could not be opened for writing!");

                    // Deletes all output files
                    for (File file2: outputFiles) {
                        file2.deleteOnExit();
                    }
                    break;
                }
            }
        }

        writeAll(openedFiles, writers, authorName);

        // Closes all PrintWriters
        closePrintWriters(writers);
    }

    /**
     * A number to increment ACM citations
     */
    private static int number = 0; // Used to increment ACM citation number

    /**
     * Writes the ACM, NJ, and IEEE files using the openedFiels Array, the Array of PrintWriters,
     * and the author name
     * @param openedFiles The Array of files that have been successfully opened
     * @param writers The Array of PrintWriters for output
     * @param authorName The name of the author
     */
    private static void writeAll(Scanner[] openedFiles, PrintWriter[] writers, String authorName){

        Article[] correctArticlesInThisFile;
        String authorsFormat;

        // Loops through openedFiles Array to find Correct Articles
        for (int i = 0; i < openedFiles.length; i++){

            // Populates correctArticlesInThisFile Array with Correct Articles
            correctArticlesInThisFile = readFile(authorName,i,openedFiles);

            // Once populated, prints each article in correctArticlesInThisFile for reference
            // Then writes correct NJ, ACM, and IEEE citations
            for (Article article : correctArticlesInThisFile) {
                System.out.println(article.toString());
                ++number;

                // Takes given author String and returns proper, NJ formatted one
                authorsFormat = article.getAuthor().replaceAll("and", "&");

                // Writes NJ citation
                writers[2].println(authorsFormat + ". " + article.getTitle() + ". " + article.getJournal() + ". " +
                        article.getVolume() + ", " + article.getPages() + "(" + article.getYear() + ").");


                // Takes the given author String and changes it to only include first author
                authorsFormat = article.getAuthor().substring(0, article.getAuthor().indexOf("and"));

                // Writes ACM
                writers[1].println("[" + (number) + "]\t" + authorsFormat + " et al. " + article.getYear() + ". " + article.getTitle() +
                        ". " + article.getJournal() + ". " + article.getVolume() + ", " + article.getNumber() +
                        " (" + article.getYear() + "), " + article.getPages() + ". DOI:" + article.getDoi());


                // Replaces the given author name String with a proper, IEEE formatted one
                authorsFormat = article.getAuthor().replaceAll("and", ",");

                writers[0].println(authorsFormat + ". " + '"' + article.getTitle() + '"' + ", " +
                        article.getJournal() + ", vol." + article.getVolume() + ", no." + article.getNumber() +
                        ", p." + article.getPages() + ", " + article.getMonth() + " " + article.getYear() + ".");
            }
        }
    }

    /**
     * Closes all opened Scanners using the openedFiles Scanner Array
     * @param array The Scanner Array
     */
    private static void closeOpenedFiles(Scanner[] array){
        for (Scanner scanner: array) {
            scanner.close();
        }
    }

    /**
     * Closes all PrintWriters in a given array
     * @param writers The PrintWriters to close
     */
    private static void closePrintWriters(PrintWriter[] writers){
        for (PrintWriter writer: writers) {
            writer.close();
        }
    }

    /**
     * Reads specific files from the given openedFiles Array, using an index.
     * Gets useful info about the articles contained within
     * @param authorName The name of the author
     * @param index The index of the openedFiles Array to read
     * @param openedFiles The openedFiles Array to be read from
     * @return an Array of correct Articles
     */
    private static Article[] readFile(String authorName, int index, Scanner[] openedFiles){
        String fileContentAsString;
        Article[] allArticlesByAuthor;

        // Sets up what each article has
        String author = null;
        String journal = null;
        String title = null;
        String year = null;
        String volume = null;
        String number = null;
        String pages = null;
        String[] keywords = null;
        String doi = null;
        String ISSN = null;
        String month = null;

        // Creates a String based on the Scanner
        fileContentAsString = openedFiles[index].useDelimiter("\\Z").next();

        // Creates an array of article Strings
        String[] allArticlesInFile = null;
        if (fileContentAsString != null) {
            allArticlesInFile = fileContentAsString.split("@ARTICLE\\{");
        }

        // Figures out how many Article objects the allArticlesByAuthor Array needs
        int correctArticles = 0;
        if (allArticlesInFile != null) {
            for (String article: allArticlesInFile) {
                if (article.contains(authorName)){
                    correctArticles++;
                }
            }
        }

        // Creates the allArticlesByAuthor with the correct size
        allArticlesByAuthor = new Article[correctArticles];

        // Loops through the array of articles
        if (allArticlesInFile != null) {

            int lastIndex = 1;

            // Loops through allArticlesByAuthor Array to only insert Articles that fit with the author name
            for (int j = 0; j < allArticlesByAuthor.length;) {

                // Loops through allArticlesInFile Array to locate Articles that fit the author name

                String article = allArticlesInFile[lastIndex];

                // Activates only if the article contains the authors name and isn't empty
                if (!article.equals("") && article.contains(authorName)) {

                    article = article.trim(); // Removes trailing whitespace of the article for easier scanning

                    //System.out.println(article); // Prints article for reference

                    // Attaches temporary Scanner to article
                    Scanner sc = new Scanner(article);

                    sc.nextLine(); // Gets the first line of each article, which is its ID number.
                    // Useless info, so its disregarded

                    // Loops through the article and gets it's attributes
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();

                        if (!line.matches("}")) {
                            // Sets up a cleaned up substring of the line without tags and other characters
                            String substring = line.substring(line.indexOf("{") + 1, line.lastIndexOf("}"));

                            // Uses Regex to assign the main variables to the correct substrings
                            if (line.matches("^author=\\{.+},\\s*")) author = substring;
                            else if (line.matches("^journal=\\{.+},\\s*")) journal = substring;
                            else if (line.matches("^title=\\{.+},\\s*")) title = substring;
                            else if (line.matches("^year=\\{.+},\\s*")) year = substring;
                            else if (line.matches("^volume=\\{.+},\\s*")) volume = substring;
                            else if (line.matches("^number=\\{.+},\\s*")) number = substring;
                            else if (line.matches("^pages=\\{.+},\\s*")) pages = substring;
                            else if (line.matches("^keywords=\\{.+},\\s*")) {
                                keywords = substring.split(";");
                            } else if (line.matches("^doi=\\{.+},\\s*")) doi = substring;
                            else if (line.matches("^ISSN=\\{.+},\\s*")) ISSN = substring;
                            else if (line.matches("^month=\\{.+},\\s*")) month = substring;
                            else System.out.println("this shouldn't happen");
                        }
                    }
                    // Creates a new Article based on the above work
                    allArticlesByAuthor[j] = new Article(author, journal, title, year, volume,
                            pages, keywords, doi, ISSN, month, number);

                    sc.close(); // Closes the temporary Scanner
                    lastIndex++;
                    j++;
                }else {
                    lastIndex++;
                    j = j;
                }
            }
        }
        // Returns the Array containing all the articles in the file by the author
        return allArticlesByAuthor;
    }

    /**
     * Checks if backup files exist in the output directory, if so, deletes them. Then
     * renames existing files to backup files
     * @param authorName The name of the author
     * @param outputFiles The Array of output files to scan through
     */
    private static void checkBUExistence(String authorName, File[] outputFiles){
        // Checks if backup files already exist, if so, deletes them
        for (File file: outputFiles) {
            if (file.getName().equals(authorName + "-IEEE-BU.json") ||
                    file.getName().equals(authorName + "-ACM-BU.json") ||
                    file.getName().equals(authorName + "-NJ-BU.json")){

                // Renames existing files into backup files
                //renameFile(authorName,file);

                if(file.delete()){
                    System.out.println("File deleted successfully");


                }else {
                    System.out.println("File not deleted successfully");
                }
            }
        }
    }

    /**
     *  Renames existing output files to backup files
     * @param authorName The name of the author
     * @param file The Array of output files
     */
    private static void renameFile(String authorName, File file){
        // Renames existing file to backup file

        if (file.getName().equals(authorName + "-IEEE.json")){
            file.renameTo(new File("output/" + authorName + "-IEEE-BU.json"));
        }else  if (file.getName().equals(authorName + "-ACM.json")){
            file.renameTo(new File("output/" + authorName + "-ACM-BU.json"));
        }else if (file.getName().equals(authorName + "-NJ.json")){
            file.renameTo(new File("output/" + authorName + "-NJ-BU.json"));
        }else {
            System.out.println("This should never happen!");
        }
    }
}
