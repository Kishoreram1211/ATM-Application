import java.util.*;

public class Atm{
    public static void main(String[] args){
        Bank bank = new Bank(10000, "bank_admin", "bank_pass123");
        Admin admin = new Admin(bank, "admin_user", "admin123");
        Customer customer1 = new Customer("Kumar", "002", 2003, 1500);
        admin.addCustomer(customer1);
        Scanner sc = new Scanner(System.in);
        int choice;
        do{
            System.out.println("***Menu***");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Display Balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Enter account number: ");
                    String depositAccountNumber = sc.next();
                    Customer depositCustomer = admin.findCustomer(depositAccountNumber);
                    if(depositCustomer != null){
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = sc.nextDouble();
                        depositCustomer.deposit(depositAmount);
                        System.out.println("Deposit successful. New balance: " + depositCustomer.getBalance());
                    }
                    else{
                        System.out.println("Customer not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String withdrawAccountNumber = sc.next();
                    Customer withdrawCustomer = admin.findCustomer(withdrawAccountNumber);
                    if(withdrawCustomer != null){
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = sc.nextDouble();
                        if(withdrawCustomer.withdraw(withdrawAmount)){
                            System.out.println("Withdrawal successful. New balance: " + withdrawCustomer.getBalance());
                        }
                    } 
                    else{
                        System.out.println("Customer not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String accountNumber = sc.next();
                    admin.displayBalance(accountNumber);
                    break;
                case 4:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while(choice != 4);
        sc.close();
    }

    public static class Bank{
        private double amount;
        private String bankAdminLogin;
        private String bankAdminPassword;

        public Bank(double amount, String bankAdminLogin, String bankAdminPassword){
            this.amount = amount;
            this.bankAdminLogin = bankAdminLogin;
            this.bankAdminPassword = bankAdminPassword;
        }

        public double getAmount(){
            return amount;
        }

        public void updateAmount(double newAmount){
            this.amount = newAmount;
        }
    }

    public static class Customer{
        private String name;
        private String accountNumber;
        private int pin;
        private double balance;

        public Customer(String name, String accountNumber, int pin, double balance){
            this.name = name;
            this.accountNumber = accountNumber;
            this.pin = pin;
            this.balance = balance;
        }

        public double getBalance(){
            return balance;
        }

        public void deposit(double amount){
            balance += amount;
        }

        public boolean withdraw(double amount){
            if(balance >= amount){
                balance -= amount;
                return true;
            } 
            else{
                System.out.println("Insufficient funds");
                return false;
            }
        }

        public void changePin(int newPin){
            this.pin = newPin;
        }
    }

    public static class Admin{
        private Bank bank;
        private List<Customer> customers;
        private String adminUsername;
        private String adminPassword;

        public Admin(Bank bank, String adminUsername, String adminPassword){
            this.bank = bank;
            this.customers = new ArrayList<>();
            this.adminUsername = adminUsername;
            this.adminPassword = adminPassword;
        }

        public void addCustomer(Customer customer){
            customers.add(customer);
        }

        public void removeCustomer(Customer customer){
            customers.remove(customer);
        }

        public void displayBalance(String accountNumber){
            Customer customer = findCustomer(accountNumber);
            if(customer != null){
                System.out.println("Balance for account " + accountNumber + ": " + customer.getBalance());
            } 
            else{
                System.out.println("Customer not found.");
            }
        }

        public Customer findCustomer(String accountNumber){
            for(Customer customer : customers){
                if(customer.accountNumber.equals(accountNumber)){
                    return customer;
                }
            }
            return null;
        }
    }
}
