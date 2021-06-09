package sf.git;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Commit {

    private static final  String AUTHOR ="Author: ";
    private static final  String DATE ="Date: ";

    private String hash ;
    private String author ;
    private LocalDateTime date ;
    private List<String> comments ;

    public Commit(String hash) {
//        System.out.println( "new Commit ["+hash+"]");
        this.hash = hash;
        this.comments= new ArrayList<>();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void update(String line) {
        //System.out.println("------------------update line "+line);
        String keyLine ="";
        if(line.startsWith(DATE)){
            keyLine=DATE;
        }else if(line.startsWith(AUTHOR)){
            keyLine=AUTHOR;
        }else{

        }
       // keyLine=line.startsWith(AUTHOR)?AUTHOR:line.startsWith(DATE)?DATE:"";

        switch (keyLine){
            case AUTHOR :
                //System.out.println();
                this.setAuthor(line.substring(8));
                break;
            case DATE :
                this.setDate(extrectDate(line));break;
                //System.out.println();
            default:
                if(!"".equals(line.trim())){
                    this.comments.add(line);

                }
                break;

        }
    }

    private LocalDateTime extrectDate(String line) {
        this.setAuthor(line);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",o Lcale.ENGLISH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM [d] HH:mm:ss yyyy [X]",Locale.ENGLISH);
        //Date:                                                           Thu Oct 31 16:51:59 2019 +0100
        //Date:                                                           12345678901234567890123456789Thu Oct 31 16:51:59 2019 +0100

//                .ofPattern("EEE MMM dd HH:mm:ss yyyy XXXXX");

        String dateInString = "7-Jun-2013";
        //Date:   Thu Oct 31 16:51:59 2019 +0100

        String dateInString1 = line.substring(5).trim();//"7-Jun-2013";
  //      System.out.println("dateTime            -------------------              ["+dateInString1+"]");
        LocalDateTime dateTime = LocalDateTime.parse(dateInString1, formatter);
    //    System.out.printf("===================%s%n", date);
        return dateTime;

    }

    @Override
    public String toString() {
        return "Commit{" +
                "\nhash='" + hash + '\'' +
                "\nauthor='" + author + '\'' +
                "\ndate=" + date +
                "\ncomments=" + comments +
                "\n}";
    }

}
