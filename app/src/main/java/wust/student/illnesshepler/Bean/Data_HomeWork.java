package wust.student.illnesshepler.Bean;


public class Data_HomeWork {
    public String Theme;
    public String[] Options;

    public Data_HomeWork(String Theme,String[] Options){
        this.Theme=Theme;
        this.Options=Options;
    }

    public String getTheme(){return Theme;}
    public String[] getOptions(){return Options;}
}
