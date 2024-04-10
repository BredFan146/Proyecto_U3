import java.util.Scanner;
import java.util.Calendar;

public class Date {
    static Calendar Hora = Calendar.getInstance();
    static Calendar Fecha = Calendar.getInstance();
    Scanner scanner = new Scanner(System.in);
    private String birthDate;
    private String publishDate;
    private String borrowDate;


    public String birthDate() {
        System.out.printf("Birth date: %n");
        System.out.print("Day: ");
        String birthDay = Auxiliar.ReadStringData(scanner);
        System.out.print("Month: ");
        String birthMonth = Auxiliar.ReadStringData(scanner);
        System.out.print("Year: ");
        String birthYear = Auxiliar.ReadStringData(scanner);
        return birthDay + "/" + birthMonth + "/" + birthYear;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStringBirthDate() {
        return birthDate;
    }


    public String publishDate() {
        System.out.printf("Publish date: %n");
        System.out.print("Day: ");
        String publishDay = Auxiliar.ReadStringData(scanner);
        System.out.print("Month: ");
        String publishMonth = Auxiliar.ReadStringData(scanner);
        System.out.print("Year: ");
        String publishYear = Auxiliar.ReadStringData(scanner);
        this.setPublishDate(publishDay + "/" + publishMonth + "/" + publishYear);
        return this.publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getStringPublishDate() {
        return publishDate;
    }


    public void setBorrowDate() {
        int hour, minute, seg, day, month, year;
        String date, hourA;

        hour = Hora.get(Calendar.HOUR_OF_DAY);
        minute = Hora.get(Calendar.MINUTE);
        seg = Hora.get(Calendar.SECOND);

        day = Fecha.get(Calendar.DATE);
        month = Fecha.get(Calendar.MONTH);
        year = Fecha.get(Calendar.YEAR);

        date = "Date: " + day + "/" + month + "/" + year;
        hourA = "\tHour: " + hour + ":" + minute + ":" + seg;

        this.borrowDate = date + hourA;

    }

    public String getBorrowDate() {
        return borrowDate;
    }
}
