package routeempire.model;

public class Transaction{
    private final long amount;
    private final String reason;
    private final boolean isIncome; 

    public Transaction(long amount, String reason, boolean isIncome){
        this.amount = amount;
        this.reason = reason;
        this.isIncome = isIncome;
    }

    public long getAmount(){
        return amount;
    }

    public String getReason(){
        return reason;
    }

    public boolean isIncome(){
        return isIncome;
    }

}