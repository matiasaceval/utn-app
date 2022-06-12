package ar.edu.utn.mdp.utnapp.commission;


import java.util.List;

public class Commission {

    private int id;
    private int year;
    private boolean expandable;
    private List<String> subjects;

    public Commission(int id, int year, List<String> subjects) {
        this.id = id;
        this.year = year;
        this.expandable = false;
        this.subjects = subjects;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return "Commission{" +
                "id=" + id +
                ", year=" + year +
                ", expandable=" + expandable +
                '}';
    }
}
