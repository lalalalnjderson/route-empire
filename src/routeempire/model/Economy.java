package routeempire.model;
import java.util.ArrayList;
import java.util.List;

public class Economy{
    public static final long STARTING_CAPITAL = 10_000;
    private long capital;
    private long totalIncome;
    private long totalExpenses;
    private final List<Transaction> history;

    public Economy(){
        this.capital = STARTING_CAPITAL;
        this.totalIncome = 0;
        this.totalExpenses = 0;
        this.history = new ArrayList<>();
    }

    public long getCapital(){
        return capital;
    }

    public long getTotalIncome(){
        return totalIncome;
    }

    public long getTotalExpenses(){
        return totalExpenses;
    }

    public boolean canAfford(long amount){
        return capital >= amount;
    }

    public boolean isBankrupt(){
        return capital < 0;
    }

    public void addIncome(long amount, String reason){
        capital += amount;
        totalIncome += amount;
        history.add(new Transaction(amount, reason, true));
    }

    public boolean addExpense(long amount, String reason){
        capital -= amount;
        totalExpenses += amount;
        history.add(new Transaction(amount, reason, false));
        return capital >= 0;
    }

    public long calculateDeliveyPayment(CargoType cargo, int quantity, double distance){
        return Math.round(quantity * cargo.getBaseRate() * (1.0 + distance * 0.05));
    }

    public List<Transaction> getRecentTransactions(int count){
        int size = history.size();
        if (count >= size){
            return new ArrayList<>(history);
        }
        return new ArrayList<>(history.subList(size - count, size));
    }
}