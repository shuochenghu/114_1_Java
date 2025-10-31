import java.time.LocalDateTime;

// 檔案路徑: c:\Users\m306\Desktop\114_1_Java\114-10-31\CompositionDemo\src\Account.java
public class Account {
    private static int accountCount = 0; // 帳戶數量統計

    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶擁有者名稱
    private String ownerName;
    // 身分證字號或其他識別號
    private String idNumber;
    // 帳戶餘額
    private double balance;

    // 帳戶開立日期與時間
    private Date openingDate;
    private Time2 openingTime;

    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param ownerName 帳戶擁有者名稱
     * @param idNumber 身分證字號或其他識別號
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, String ownerName, String idNumber, double initialBalance) {
        LocalDateTime now = LocalDateTime.now(); // 取得目前日期與時間
        this.setAccountNumber(accountNumber);
        this.ownerName = ownerName;
        this.idNumber = idNumber;
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，將餘額設為0");
            this.balance = 0; // 若初始餘額不合法，設為 0
        }
        accountCount++; // 帳戶數量加1

        // 記錄帳戶開立的日期與時間
        this.openingDate = new Date(now.getMonthValue(), now.getDayOfMonth(), now.getYear());
        this.openingTime = new Time2(now.getHour(), now.getMinute(), now.getSecond());
    }

    // 原本的 3 參數建構子改為呼叫 4 參數版本（身分證預設為未知）
    public Account(String accountNumber, String ownerName, double initialBalance) {
        this(accountNumber, ownerName, "未知", initialBalance);
    }

    public Account(String accountNumber, double initialBalance) {
        this(accountNumber, "未知", "未知", initialBalance);
    }

    public Account() {
        this("未知", "未知", "未知", 0);
    }

    public Account(String accountNumber) {
        this(accountNumber, "未知", "未知", 0);
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }

    /**
     * 取得帳戶擁有者名稱
     * @return 帳戶擁有者名稱
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * 取得身分證字號
     * @return 身分證字號
     */
    public String getIdNumber() { return idNumber; }

    /**
     * 設定帳戶餘額
     * @param balance 欲設定的帳戶餘額，必須為正數
     * @throws IllegalArgumentException 若餘額非正數則拋出例外
     */
    public void setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance; // 設定新的帳戶餘額
        } else {
            throw new IllegalArgumentException("帳戶餘額必須為正數");
        }
    }

    /**
     * 設定帳戶號碼
     * @param accountNumber 欲設定的帳戶號碼
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 設定身分證字號
     * @param idNumber 欲設定的身分證字號
     */
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException 若金額非正數則拋出例外
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount; // 增加餘額
        } else {
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }

    /**
     * 提款方法，從帳戶扣除指定金額
     * @param amount 提款金額，必須為正數且不得超過餘額
     * @throws IllegalArgumentException 若金額不合法則拋出例外
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount; // 減少餘額
        } else {
            throw new IllegalArgumentException("提款金額不合法");
        }
    }

    /**
     * 以易讀格式回傳帳戶資訊（供 print 使用）
     */
    @Override
    public String toString() {
        return "帳戶號碼: " + accountNumber +
                ", 持有人: " + ownerName +
                ", 身分證: " + idNumber +
                ", 餘額: " + balance;
    }
}
