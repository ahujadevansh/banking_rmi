
class Customer
{
    String username, name;
    int mpin;
    private float balance;
    Customer(String username, String name, int mpin) {
        this.username = username;
        this.name = name;
        this.mpin = mpin;
    }

    public float getBalance() {
        return this.balance;
    }
    public float deposit(float amount){
        
        this.balance += amount;
        System.out.println("Deposit for " + this.username + ", Amount - " + amount);
        return this.getBalance();
    }

    public boolean withdraw(float amount){
        
        if (this.balance >= amount) {
            this.balance -= amount;
            System.out.println("Withdraw for " + this.username + ", Amount - " + amount);
            return true;
        }
        else {
        	System.out.println("False Withdraw for " + this.username);
            return false;
        }
    }

    public String toString() {
        // return "Hello" + this.name + ",\nyour balance is" + this.balance;

        return "Current Balance of " + this.name + " : " + this.balance + " Rs";
    }
}