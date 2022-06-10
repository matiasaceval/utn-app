package ar.edu.utn.mdp.utnapp.commission;

import androidx.annotation.NonNull;

public class Commission {

    private int id;
    private int year;
    private boolean expandable;

    public Commission(int id, int year) {
        this.id = id;
        this.year = year;
        this.expandable = false;
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
