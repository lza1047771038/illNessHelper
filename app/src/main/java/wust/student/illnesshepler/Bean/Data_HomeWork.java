package wust.student.illnesshepler.Bean;


public class Data_HomeWork {
    public String Title,Author;
    public boolean isFinsh;

    public Data_HomeWork(String Title,String Author,boolean isFinsh){
        this.Title=Title;
        this.Author=Author;
        this.isFinsh=isFinsh;
    }

    public String getTitle(){return Title;}
    public String getAuthor(){return Author;}
    public boolean getIsFinsh(){return isFinsh;}
}
