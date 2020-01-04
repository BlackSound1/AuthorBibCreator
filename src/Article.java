// -----------------------------------------------------
// Written by: Matthew Segal
// ----------------------------------------------------
import java.util.Arrays;

public class Article {

    private String author;
    private String journal;
    private String title;
    private String year;
    private String volume;
    private String number;
    private String pages;
    private String[] keywords;
    private String doi;
    private String ISSN;
    private String month;

    /**
     * Gets the name of the author
     * @return The name of the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the name of the author
     * @param author The name of the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the name of the journal
     * @return The name of the journal
     */
    public String getJournal() {
        return journal;
    }

    /**
     * Sets the journal
     * @param journal The journal
     */
    public void setJournal(String journal) {
        this.journal = journal;
    }

    /**
     * Gets the title
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the year
     * @return The year
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the year
     * @param year The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Gets the volume
     * @return The volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Sets the volume
     * @param volume The volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * Gets the page range
     * @return The page range
     */
    public String getPages() {
        return pages;
    }

    /**
     * Sets the page range
     * @param pages The page range
     */
    public void setPages(String pages) {
        this.pages = pages;
    }

    /**
     * Gets the keywords
     * @return The keywords
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Sets the keywords
     * @param keywords The keywords
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    /**
     * Gets the DOI
     * @return The DOI
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Sets the DOI
     * @param doi The DOI
     */
    public void setDoi(String doi) {
        this.doi = doi;
    }

    /**
     * Gets the ISSN
     * @return The ISSN
     */
    public String getISSN() {
        return ISSN;
    }

    /**
     * Sets the ISSN
     * @param ISSN The ISSN
     */
    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    /**
     * Gets the month of publication
     * @return The month of publication
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month of publication
     * @param month The month of publication
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the journal number
     * @return The journal number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the journal number
     * @param number The journal number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * The Parameterized Constructor for an Article
     * @param author The name of the author
     * @param journal The name of the journal
     * @param title The title of the article
     * @param year The year of publication
     * @param volume The volume of the journal
     * @param pages The page range of the article
     * @param keywords Relevant keywords about the article
     * @param doi The Digital Object Identifier of the article
     * @param ISSN The ISSN of the article
     * @param month The month of publication
     * @param number The number of the journal in the specific volume
     */
    Article(String author, String journal, String title, String year, String volume,
            String pages, String[] keywords, String doi, String ISSN, String month, String number) {
        this.author = author;
        this.journal = journal;
        this.title = title;
        this.year = year;
        this.volume = volume;
        this.pages = pages;
        this.keywords = keywords;
        this.doi = doi;
        this.ISSN = ISSN;
        this.month = month;
        this.number = number;
    }

    /**
     * The Default Constructor for an Article
     */
    public Article(){
        author = "";
        journal = "";
        title = "";
        year = "";
        volume = "";
        pages = "";
        keywords = null;
        doi = "";
        ISSN = "";
        month = "";
    }

    /**
     * Returns useful information about the Article
     * @return A String of info about the Article
     */
    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", journal='" + journal + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", volume='" + volume + '\'' +
                ", pages='" + pages + '\'' +
                ", keywords=" + Arrays.toString(keywords) +
                ", doi='" + doi + '\'' +
                ", ISSN='" + ISSN + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
