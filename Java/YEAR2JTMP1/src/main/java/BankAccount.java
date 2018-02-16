public class BankAccount
{
	public BankAccount()
	{
		this.balance = 0;
	}

	public BankAccount(double initialBalance)
	{
		this.balance = initialBalance;
	}

	public void deposit(double amount)
	{
		this.balance = this.balance + amount;
	}

	public void withdraw(double amount)
	{
		this.balance = this.balance - amount;
	}

	public double getBalance()
	{
		return this.balance;
	}

	/**
	 * Transfers from this to other
	 * 
	 * @param amount
	 *            the sum to be transferred
	 * @param other
	 *            the account into which money is transferred
	 */
	public void transfer(double amount, BankAccount other)
	{
		this.withdraw(amount);
		other.deposit(amount);
	}

	private double balance;
}