import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class BankAccountViewer extends JFrame {

	private JPanel contentPane;
	private JTextField withdrawField;
	private JTextField depositField;
	private static BankAccount account;
	JLabel balanceField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		account = new BankAccount();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankAccountViewer frame = new BankAccountViewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BankAccountViewer() {
		setTitle("BankAccountViewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		ActionListener listener = new ButtonListener();
		
		JLabel lblBankOfUtopia = new JLabel("Bank of Utopia");
		lblBankOfUtopia.setHorizontalAlignment(SwingConstants.CENTER);
		lblBankOfUtopia.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lblBankOfUtopia, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.setBounds(63, 25, 99, 23);
		withdrawButton.addActionListener(listener);
		panel.add(withdrawButton);
		
		withdrawField = new JTextField();
		withdrawField.setBounds(273, 26, 116, 20);
		panel.add(withdrawField);
		withdrawField.setColumns(10);
		
		JButton depositButton = new JButton("Deposit");
		depositButton.setBounds(63, 83, 99, 23);
		depositButton.addActionListener(listener);
		panel.add(depositButton);
		
		depositField = new JTextField();
		depositField.setBounds(273, 84, 116, 20);
		panel.add(depositField);
		depositField.setColumns(10);
		
		JButton balanceButton = new JButton("Show balance");
		balanceButton.setBounds(147, 147, 121, 23);
		balanceButton.addActionListener(listener);
		panel.add(balanceButton);
		
		balanceField = new JLabel("Your balance is: ");
		balanceField.setHorizontalAlignment(SwingConstants.CENTER);
		balanceField.setBounds(10, 184, 404, 14);
		panel.add(balanceField);
	}
	
	public class ButtonListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent arg0)
		{
			String buttonPressed = arg0.getActionCommand();
			double amount;
			
			switch(buttonPressed)
			{
			case "Withdraw":
				amount = Double.parseDouble(withdrawField.getText());
				account.withdraw(amount);
				break;
			
			case "Deposit":
				amount = Double.parseDouble(depositField.getText());
				account.deposit(amount);
				break;
				
			case "Show balance":
				balanceField.setText("Your balance is: " + account.getBalance());
				break;
			}
		}
		
	}
}
