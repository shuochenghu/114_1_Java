import java.time.LocalDateTime;

public class Account {
    private String id;
    private String name;
    private double balance;

    private Date openingDate;
    private Time2 openingTime;

    public Account(String id, String name) {
        this(id, name, 0);
    }
    public Account(String id, String name, double balance) {  //建構子，constructor
        LocalDateTime now = LocalDateTime.now();
        this.id = id;
        this.name = name;
        if (balance >= 0)
            this.balance = balance;
        openingDate = new Date(now.getMonthValue(), now.getDayOfMonth(), now.getYear());
        openingTime = new Time2(now.getHour(), now.getMinute(), now.getSecond());
    }

    public String getId() {
        return this.id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.printf("存款完成，帳戶餘額：%.1f元", this.balance);
        }
        else {
            System.out.println("存款金額必須>0");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("提款金額必須>=0");
        }
        else if (amount > this.balance) {
            System.out.println("餘額不足");
        }
        else {
            this.balance -= amount;
            System.out.printf("提款完成，帳戶餘額：%.1f元", this.balance);
        }
    }

    public String toString(){
        return String.format("開戶日期時間：%s %s%n帳號：%s%n客戶姓名：%s%n帳戶餘額：%.1f%n%n",
                openingDate.toString(),openingTime.toString(),getId(),getName(), getBalance() );
    }
}
