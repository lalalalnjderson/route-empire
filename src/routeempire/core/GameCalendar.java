package routeempire.core;

public class GameCalendar {
    public static final int TICKS_PER_DAY = 20;
    public static final int DAYS_PER_MONTH = 30;
    public static final int MONTHS_PER_YEAR = 12;
    public static final int TICKS_PER_MONTH = TICKS_PER_DAY * DAYS_PER_MONTH;

    private int year;
    private int month;
    private int day;
    private int ticksThisDay;

    // flags that last for one tick, checked by GameLogic
    private boolean monthEnded;
    private boolean yearEnded;

    public GameCalendar() {
        this.year = 2000;
        this.month = 1;
        this.day = 1;
        this.ticksThisDay = 0;
        this.monthEnded = false;
        this.yearEnded = false;
    }

    public void tick() {
        monthEnded = false;
        yearEnded = false;
        ticksThisDay++;

        if (ticksThisDay >= TICKS_PER_DAY) {
            ticksThisDay = 0;
            day++;

            if (day > DAYS_PER_MONTH) {
                day = 1;
                month++;
                monthEnded = true;

                if (month > MONTHS_PER_YEAR) {
                    month = 1;
                    year++;
                    yearEnded = true;
                }
            }
        }
    }

    public boolean isMonthEnd() {
        return monthEnded;
    }

    public boolean isYearEnd() {
        return yearEnded;
    }

    public int getTicksPerMonth() {
        return TICKS_PER_MONTH;
    }

    public String getDateString() {
        String[] monthNames = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        return monthNames[month - 1] + " " + day + ", " + year;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
}