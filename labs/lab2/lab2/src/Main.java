import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;


public class Main {

    /*DO NOT CHANGE!!!*/
    public static void main(String[] args) {
        Child[] children = {
                new Child("Edan", "04-02-2018", "Soccer"),
                new Child("Esther", "07-10-2018", "Frisbee"),
                new Child("Ori", "13-12-2018", "Acting"),
                new Child("Noa", "01-01-2018", "Math"),
                new Child("Lior", "07-07-2018", "Soccer"),
                new Child("Erel", "08-08-2018", "Computer Games"),
                new Child("Eldar", "09-09-2018", "Baseball"),
                new Child("Omri", "10-10-2018", "Painting"),
                new Child("Rachel", "11-11-2018", "Lego"),
                new Child("Dan", "12-12-2018", "Dancing"),
                new Child("Roy", "02-02-2018", "Reading"),
        };
        KindergartenTeacher teacher = new KindergartenTeacher(children.length);

        Kindergarten kindergarten=new Kindergarten(children,teacher);

        List<LocalDate> listOfDates = getListOfDates();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(var date: listOfDates){
            teacher.isItTimeToCelebrate(formatter.format(date));
        }
    }

    private static List<LocalDate> getListOfDates() {
        // Our code is using some built-in java library, can you figure out what it does even without reading the documentation?
        // Food for thought: What kinds of decisions go into naming variables and methods so that they are easy to understand
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.with(firstDayOfYear());
        LocalDate endDate = now.with(lastDayOfYear());
        List<LocalDate> listOfDates = startDate.datesUntil(endDate).collect(Collectors.toList());
        return listOfDates;
    }
}


/*Implement all your classes below this line*/

class Child {
    private String name;
    private String birthDay;
    private String favoriteActivity;

    public Child(String childName, String childBirthdate, String childFavoriteActivity) {
        this.name = childName;
        this.birthDay = childBirthdate;
        this.favoriteActivity = childFavoriteActivity;
    }



    public String getName() {
        return this.name;
    }

    public String getBirthDay() {
        return this.birthDay;
    }

    public String getFavoriteActivity() {
        return this.favoriteActivity;
    }
}


class BirthdayEvent {
    private Child child;

    public BirthdayEvent(Child child) {
        this.child = child;
    }

    public String getName() {
        return this.child.getName();
    }

    public String getBirthDay() {
        return this.child.getBirthDay();
    }

    public String getFavoriteActivity() {
        return this.child.getFavoriteActivity();
    }
}

class Calendar {
    private int maxSize;
    private int currentSize;
    private BirthdayEvent[] calendar;

    public Calendar(int maxSize) {
        this.maxSize = maxSize;
        this.calendar = new BirthdayEvent[maxSize];
        this.currentSize = 0;
    }

    public void add (BirthdayEvent event) {
        if (this.currentSize < this.maxSize) {
            this.calendar[this.currentSize] = event;
            this.currentSize++;
        }
    }
    public BirthdayEvent getEvent(String date) {
        for (int i = 0; i < this.calendar.length; i++){
            String dat = date.substring(0, 5);
            String other = this.calendar[i].getBirthDay().substring(0, 5);
            if (dat.equals(other)){
                return calendar[i];
            }
        }
        return null;
    }

}

class KindergartenTeacher {
    private int maxNumOfChildren;
    private Calendar calendar;

    public KindergartenTeacher(int maxNumOfChildren) {
        this.maxNumOfChildren = maxNumOfChildren;
        this.calendar = new Calendar(maxNumOfChildren);
    }

    public void isItTimeToCelebrate(String date) {
        BirthdayEvent event = this.calendar.getEvent(date);
        if (event != null) {

            System.out.println("For " + event.getName() + "'s Birthday party I need to prepare his favorite activity: " + event.getFavoriteActivity());

        }

    }

    public Calendar getCalendar() {
        return this.calendar;
    }
}

class Kindergarten {
    private KindergartenTeacher teacher;
    private Child[] children;

    public Kindergarten(Child[] children, KindergartenTeacher teacher) {
        this.teacher = teacher;
        this.children = new Child[children.length];
        for (int i = 0; i < children.length; i++) {
            this.children[i] = children[i];
            BirthdayEvent event = new BirthdayEvent(children[i]);
            teacher.getCalendar().add(event);
        }
    }
}



