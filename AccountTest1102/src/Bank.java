import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    //private static Account[] customers = new Account[10];
    //private static int customerCounter = 0;
    public static void main(String args[]) {
        //Account[] customers = new Account[10];
        ArrayList<Account> customersList = new ArrayList<Account>();
        Account customer1 = new Account("1200-6618","老王", 1000);
        addNewCustomer(customersList,customer1);
        Account customer2 = new Account("1200-6628","小趙", -25000);
        addNewCustomer(customersList,customer2);
        Account customer3 = new Account("1200-6638","李中二", 6000);
        addNewCustomer(customersList,customer3);
//        printAllCustomers();

//        Account selectedCustomer = customerInAction(customer1, customer2, customer3);
//        printInfo(selectedCustomer);
          operation(customersList);
//        printInfo(selectedCustomer);
    }

    public static void addNewCustomer(ArrayList<Account> customers,Account newCustomer) {
        customers.add(newCustomer);
    }

    public static void addNewCustomer(ArrayList<Account> customers,String id, String name) {
        Account newCustomer = new Account(id, name);
        customers.add(newCustomer);
    }

    public static void addNewCustomer(ArrayList<Account> customers, String id, String name, double balance) {
        Account newCustomer = new Account(id, name, balance);
        customers.add(newCustomer);
    }

    public static void operation(ArrayList<Account> customers) {
        Account selectedCustomer;
        Scanner input = new Scanner(System.in);
        menu();

        while (input.hasNext()) {
            int choice = input.nextInt();
            try {
                switch (choice) {
                    case 1:
                        System.out.print("帳號：");
                        String id = input.next();
                        System.out.print("姓名：");
                        String name = input.next();
                        System.out.print("開戶金額：");
                        double balance = input.nextDouble();
                        addNewCustomer(customers, id, name, balance);
                        break;
                    case 2:
                        selectedCustomer = customerInAction(customers);
                        //System.out.printf("帳戶餘額：%.1f 元", selectedCustomer.getBalance());
                        System.out.println(selectedCustomer.toString());
                        break;
                    case 3:
                        selectedCustomer = customerInAction(customers);
                        System.out.print("輸入存款金額：");
                        double depositAmount = input.nextDouble();
                        selectedCustomer.deposit(depositAmount);
                        break;
                    case 4:
                        selectedCustomer = customerInAction(customers);
                        System.out.print("輸入提款金額：");
                        double withdrawAmount = input.nextDouble();
                        selectedCustomer.withdraw(withdrawAmount);
                        break;
                    case 5:
                        selectedCustomer = customerInAction(customers);
                        customers.remove(selectedCustomer);
                        printAllCustomers(customers);
                    default:
                        System.out.println("選項錯誤，請重新輸入");
                        break;
                }
            }
            catch (NullPointerException ex) {
                menu();
                continue;
            }

            menu();
        }
        printAllCustomers(customers);
    }

//    public static void printInfo(Account customer) {
//        if ( customer != null)
//            System.out.printf("帳號：%s%n客戶姓名：%s%n帳戶餘額：%.1f%n%n",
//                customer.getId(),customer.getName(), customer.getBalance() );
//    }

    public static void printAllCustomers(ArrayList<Account> customers) {
        for (Account c : customers)
            System.out.println(c.toString());
    }

    public static void menu() {
//        try{
//            System.in.read();
//        }catch(Exception e){};
//        clearConsole();
        System.out.println("\n\n阿飄銀行你好：");
        System.out.print("1.新增客戶\n2.查詢餘額\n3.存款\n4.提款\n5.刪除\n請輸入： ");
    }

    public static Account customerInAction(ArrayList<Account> customers) {
        Scanner input = new Scanner(System.in);
        System.out.print("請輸入帳號： ");
        String accountId = input.next();

        for (int i = 0; i < customers.size(); i++) {
            if (accountId.equals(customers.get(i).getId()))
                return customers.get(i);
        }
//        for (Account c : customers) {
//            if (accountId.equals(c.getId()))
//                return c;
//        }
        System.out.println("帳號輸入錯誤");
        return null;
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Something wrong!");
        }
    }
}
