package main.java.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * Created by joakimlindvall on 2017-10-28.
 */
@Entity
@Table(name = "calendar_table")
public class CalendarTable {

    public CalendarTable(){

    }

    private StringProperty date = new SimpleStringProperty();

    public StringProperty dateProperty(){
        return date;
    }

    @Id
    @Column(name="dt")
    public String getDate(){
        return date.get();
    }

    public void setDate(String d){
        date.set(d);
    }

    private IntegerProperty week = new SimpleIntegerProperty();

    public IntegerProperty weekProperty(){
        return week;
    }

    @Basic
    @Column (name="w")
    public int getWeek(){
        return week.get();
    }

    public void setWeek(int w){
        week.set(w);
    }

    private IntegerProperty month = new SimpleIntegerProperty();

    public IntegerProperty monthProperty(){
        return month;
    }

    @Basic
    @Column (name="m")
    public int getMonth(){
        return month.get();
    }

    public void setMonth(int m){
        month.set(m);
    }

    private IntegerProperty year = new SimpleIntegerProperty();

    public IntegerProperty yearProperty(){
        return year;
    }

    @Basic
    @Column (name="y")
    public int getYear(){
        return year.get();
    }

    public void setYear(int y){
        year.set(y);
    }

    private StringProperty monthName = new SimpleStringProperty();

    public StringProperty monthNameProperty(){
        return monthName;
    }

    @Basic
    @Column (name="month_name")
    public String getMonthName(){
        return monthName.get();
    }

    public void setMonthName(String mn){
        monthName.set(mn);
    }

    /** NO MAPPING REQUIRED? **/

}
