import org.apache.commons.collections4.list.GrowthList;

public class main
{
	public static void main (String[]args)
	{
		GrowthList bank = new GrowthList();
		BankAccount account1 = new BankAccount();
		BankAccount account2 = new BankAccount();
		BankAccount account3 = new BankAccount();
		
		bank.add(account1);
		bank.add(account2);
		bank.add(account3);
		
		System.out.println(bank.toString());
	}
}
