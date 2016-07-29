package com.ui.forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.util.Database;
import com.util.DateLabelFormatter;

public class MainWindow {

	private JFrame mainWindowFrame;
	public static final String LOOK_AND_FEEL_URL = "com.seaglasslookandfeel.SeaGlassLookAndFeel";
	public static final String APP_ICON = "/icons/graph_icon.png";
	private JTextField bothAmount;
	private JFormattedTextField cheqDate, cheqNo;
	private JTextArea description;
	private Calendar calander;
	private int year, month, day;
	private static final int ONE = 1;
	private String transDatePickerText = "";
	private String[] datePickerParts;
	private Connection connect = null;
	public static ReturnChequeWindow returnCheqWindow = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainWindow window = new MainWindow();					
					window.mainWindowFrame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public MainWindow() {
		try {
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Clears all the main window fields
	 */
	private void clearFields() {
		cheqDate.setText("");
		cheqNo.setText("");
		description.setText("");
		bothAmount.setText("");
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {		
		
		mainWindowFrame = new JFrame();		
		mainWindowFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent event) {
				
			}
		});
		mainWindowFrame.setResizable(false);
		mainWindowFrame.setForeground(Color.WHITE);
		mainWindowFrame.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		mainWindowFrame.setBackground(Color.WHITE);
		mainWindowFrame.setBounds(100, 100, 678, 394);
		mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindowFrame.setTitle("Account Manager");
		mainWindowFrame.setLocationRelativeTo(null);
		mainWindowFrame.setIconImage(new ImageIcon(getClass().getResource(APP_ICON)).getImage());
		mainWindowFrame.getContentPane().setLayout(null);		
		
		try {
			UIManager.setLookAndFeel(LOOK_AND_FEEL_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 672, 366);
		mainWindowFrame.getContentPane().add(tabbedPane);
		
		JPanel financePanel = new JPanel();
		tabbedPane.addTab("Finance", null, financePanel, null);
		tabbedPane.setEnabledAt(0, true);
		financePanel.setLayout(null);
		
		JPanel currDateCheqPanel = new JPanel();
		currDateCheqPanel.setToolTipText("Date");
		currDateCheqPanel.setForeground(Color.BLACK);
		currDateCheqPanel.setBackground(SystemColor.menu);
		currDateCheqPanel.setBounds(25, 11, 134, 27);
		currDateCheqPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		financePanel.add(currDateCheqPanel);
		currDateCheqPanel.setLayout(null);
		
		calander = Calendar.getInstance();
		year = calander.get(Calendar.YEAR);
		month = calander.get(Calendar.MONTH) + ONE;
		day = calander.get(Calendar.DAY_OF_MONTH);
		
		JLabel currDateCheqYear = new JLabel("0000");
		currDateCheqYear.setBounds(10, 0, 40, 24);
		currDateCheqPanel.add(currDateCheqYear);
		currDateCheqYear.setFont(new Font("Segoe UI", Font.BOLD, 17));
		if(calander != null) {
			currDateCheqYear.setText(String.valueOf(year));
		}
		
		
		JLabel label_2 = new JLabel("/");
		label_2.setBounds(53, 0, 8, 24);
		currDateCheqPanel.add(label_2);
		label_2.setFont(new Font("Segoe UI", Font.BOLD, 17));
		
		JLabel currDateCheqMonth = new JLabel("00");
		currDateCheqMonth.setBounds(66, 0, 28, 24);
		currDateCheqPanel.add(currDateCheqMonth);
		currDateCheqMonth.setFont(new Font("Segoe UI", Font.BOLD, 17));		
		if (calander != null) {
			if(month < 10) {
				currDateCheqMonth.setText("0" + String.valueOf(month));
			} else {
				currDateCheqMonth.setText(String.valueOf(month));
			}
		}		
		
		JLabel label_3 = new JLabel("/");
		label_3.setBounds(93, 0, 8, 24);
		currDateCheqPanel.add(label_3);
		label_3.setFont(new Font("Segoe UI", Font.BOLD, 17));
		
		JLabel currDateCheqDay = new JLabel("00");
		currDateCheqDay.setBounds(104, 0, 28, 24);
		currDateCheqPanel.add(currDateCheqDay);
		currDateCheqDay.setFont(new Font("Segoe UI", Font.BOLD, 17));
		if(calander != null) {
			if(day < 10) {
				currDateCheqDay.setText("0" + String.valueOf(day));
			} else {
				currDateCheqDay.setText(String.valueOf(day));
			}
		}
		
		JPanel accBalanceCheqPanel = new JPanel();
		accBalanceCheqPanel.setToolTipText("Acc. Balance");
		accBalanceCheqPanel.setBounds(462, 11, 180, 78);
		accBalanceCheqPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		financePanel.add(accBalanceCheqPanel);
		accBalanceCheqPanel.setLayout(null);
		
		JLabel lblAccountBalancers = new JLabel("Account Balance :");
		lblAccountBalancers.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lblAccountBalancers.setBounds(10, 11, 164, 24);
		accBalanceCheqPanel.add(lblAccountBalancers);
		
		JLabel currAccBalanceCheq = new JLabel("Rs. 100,000.00");
		currAccBalanceCheq.setHorizontalAlignment(SwingConstants.CENTER);
		currAccBalanceCheq.setFont(new Font("Segoe UI", Font.BOLD, 18));
		currAccBalanceCheq.setBounds(10, 37, 164, 24);
		accBalanceCheqPanel.add(currAccBalanceCheq);
		
		final ButtonGroup transactionTypeButtonGroup = new ButtonGroup();
		final ButtonGroup typeButtonGroup = new ButtonGroup();
		
		JPanel transacTypePanel = new JPanel();
		transacTypePanel.setToolTipText("Cheque type");
		transacTypePanel.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Transaction Type", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		transacTypePanel.setBounds(25, 61, 204, 70);
		financePanel.add(transacTypePanel);
		transacTypePanel.setLayout(null);
		
		JLabel label_1 = new JLabel("|");
		label_1.setBounds(104, 24, 6, 24);
		transacTypePanel.add(label_1);
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setFont(new Font("Segoe UI", Font.BOLD, 17));
		
		JRadioButton withdrawRdButton = new JRadioButton("Withdraw");
		withdrawRdButton.setBounds(6, 22, 86, 30);
		transacTypePanel.add(withdrawRdButton);
		withdrawRdButton.setSelected(true);
		withdrawRdButton.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		withdrawRdButton.setActionCommand(withdrawRdButton.getText().toString());
		transactionTypeButtonGroup.add(withdrawRdButton);
		
		JRadioButton depositRdButton = new JRadioButton("Deposit");
		depositRdButton.setBounds(122, 22, 82, 30);
		transacTypePanel.add(depositRdButton);
		depositRdButton.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));		
		depositRdButton.setHorizontalTextPosition(JRadioButton.LEFT);
		depositRdButton.setActionCommand(depositRdButton.getText().toString());
		transactionTypeButtonGroup.add(depositRdButton);
		
		cheqDate = new JFormattedTextField();
		cheqDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
		cheqDate.setColumns(10);
		cheqDate.setDropMode(DropMode.INSERT);
		cheqDate.setBounds(25, 182, 115, 34);
		cheqDate.setFocusLostBehavior(JFormattedTextField.PERSIST);
		MaskFormatter dateFormatter;
		try {
			dateFormatter = new MaskFormatter("#### / ## / ##");
			dateFormatter.setValidCharacters("0123456789AP");
			dateFormatter.setPlaceholderCharacter(' ');     
			dateFormatter.setValueClass(String.class);   
			DefaultFormatterFactory dateFormatterFactory = new
			         DefaultFormatterFactory(dateFormatter);
			cheqDate.setFormatterFactory(dateFormatterFactory);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		financePanel.add(cheqDate);
		
		JLabel lblNewLabel = new JLabel("Date:");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(35, 214, 38, 18);
		financePanel.add(lblNewLabel);
		
		cheqNo = new JFormattedTextField();
		cheqNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		cheqNo.setDropMode(DropMode.INSERT);
		cheqNo.setColumns(10);
		cheqNo.setBounds(152, 182, 139, 34);
		cheqNo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		MaskFormatter cheqNoFormatter;
		try {
			cheqNoFormatter = new MaskFormatter("# | # | # | # | # |#");
			cheqNoFormatter.setValidCharacters("0123456789AP");
			cheqNoFormatter.setPlaceholderCharacter(' ');     
			cheqNoFormatter.setValueClass(String.class);   
			DefaultFormatterFactory cheqNoFormatterFactory = new
			         DefaultFormatterFactory(cheqNoFormatter);
			cheqNo.setFormatterFactory(cheqNoFormatterFactory);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		financePanel.add(cheqNo);
		
		JLabel lblChequeNo = new JLabel("Cheque No. :");
		lblChequeNo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblChequeNo.setBounds(162, 214, 91, 18);
		financePanel.add(lblChequeNo);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblDescription.setBounds(313, 266, 91, 18);
		financePanel.add(lblDescription);
		
		bothAmount = new JTextField();
		bothAmount.setHorizontalAlignment(SwingConstants.CENTER);
		bothAmount.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		bothAmount.setBounds(519, 182, 147, 34);
		financePanel.add(bothAmount);
		bothAmount.setColumns(10);
		
		JLabel lblAmountrs = new JLabel("Amount (Rs.):");
		lblAmountrs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblAmountrs.setBounds(529, 215, 91, 18);
		financePanel.add(lblAmountrs);
		
		JButton cheqEnterBtn = new JButton("Enter");
		cheqEnterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				String typeButtonGroupValue = typeButtonGroup.getSelection().getActionCommand();
				System.out.println("Type value: : " + typeButtonGroupValue);
				
				if(typeButtonGroupValue.equals("Cheque")) {
					
					double cheqAmount = 0.0;
					
					String cheqDateValue = cheqDate.getText().toString();
					String cheqNoValue = cheqNo.getText().toString();
					String cheqDescriptionValue = description.getText().toString();
					String cheqAmountValue = bothAmount.getText().toString();
					String cheqRadBtnValue = transactionTypeButtonGroup.getSelection().getActionCommand();
					
					cheqNoValue = cheqNoValue.replace("|", "").replace(" ", "");					
					cheqDateValue = cheqDateValue.replace(" ", "");
					
				if(!(cheqDateValue.matches(".*\\d+.*")) || cheqNoValue.equals("") || cheqAmountValue.equals("")) {
					JOptionPane.showMessageDialog(mainWindowFrame, "Please fill all the fields", "Not Complete", JOptionPane.ERROR_MESSAGE);
					return;
				}	
									
					try {
						cheqAmount = Double.parseDouble(cheqAmountValue);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(mainWindowFrame, "Cheque Amount must be a number", "Cheque Amount Error", JOptionPane.ERROR_MESSAGE);
						bothAmount.setText("");
						return;
					}				
					
					System.out.println("cheque type: " + cheqRadBtnValue);
					System.out.println("cheque date: " + cheqDateValue);
					System.out.println("cheque no.: " + cheqNoValue);
					System.out.println("cheque description: " + cheqDescriptionValue);	
					System.out.println("cheque amount: " + cheqAmountValue);	
					
					String insertTableStatment = "INSERT INTO cheque"
							+ "(chequeNo, chequeDate, description, amount, type) VALUES"
							+ "(?,?,?,?,?)";
					PreparedStatement prepStatement = null;
					
					
					try {
						connect = Database.getDBConnection();
						prepStatement = connect.prepareStatement(insertTableStatment);
						
						prepStatement.setString(1, cheqNoValue);
						prepStatement.setString(2, cheqDateValue);
						prepStatement.setString(3, cheqDescriptionValue);
						prepStatement.setDouble(4, cheqAmount);
						prepStatement.setString(5, cheqRadBtnValue);
						
						// execute insert SQL statement
						int insertReturnValue = prepStatement.executeUpdate();
						
						System.out.println("insert SQL return value " + String.valueOf(insertReturnValue));
						
						if(insertReturnValue == 1) {
							System.out.println("Record is inserted into cheque table!");
							JOptionPane.showMessageDialog(mainWindowFrame, "Cheque succefully added", "Cheque Added", JOptionPane.INFORMATION_MESSAGE);
						} 					
						
					} catch (SQLException e) {
						e.printStackTrace();
					} finally { 
						if (prepStatement != null) {
							try {
								prepStatement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}

						if (connect != null) {
							try {
								connect.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
						clearFields();
					}
				}
				else if((typeButtonGroupValue.equals("Cash"))) {
					
					double cashAmount = 0.0;
					
					String cashDateValue = "";
					String cashAmountValue = bothAmount.getText().toString();
					String cashDescriptionValue = description.getText().toString();
					String cashRadBtnValue = transactionTypeButtonGroup.getSelection().getActionCommand();
					
					if(cashAmountValue.equals("")) {
						JOptionPane.showMessageDialog(mainWindowFrame, "Please fill the amount field", "Not Complete", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(month < 10) {
						cashDateValue = String.valueOf(year) + "/0" + String.valueOf(month) + "/" + String.valueOf(day);
					} else {
						cashDateValue = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
					}					
					
					try {
						cashAmount = Double.parseDouble(cashAmountValue);
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(mainWindowFrame, "Cash Amount must be a number", "Cash Amount Error", JOptionPane.ERROR_MESSAGE);
						bothAmount.setText("");
						return;
					}
					
					System.out.println("cash date: " + cashDateValue);
					System.out.println("cash amount: " + cashAmountValue);
					System.out.println("cash description: " + cashDescriptionValue);
					System.out.println("cash type: " + cashRadBtnValue);
					
					String insertTableStatment = "INSERT INTO cash"
							+ "(cashDate, amount, description, type) VALUES"
							+ "(?,?,?,?)";
					PreparedStatement prepStatement = null;
					
					try {
						connect = Database.getDBConnection();
						prepStatement = connect.prepareStatement(insertTableStatment);
						
						prepStatement.setString(1, cashDateValue);
						prepStatement.setDouble(2, cashAmount);
						prepStatement.setString(3, cashDescriptionValue);						
						prepStatement.setString(4, cashRadBtnValue);
						
						// execute insert SQL statement
						int insertReturnValue = prepStatement.executeUpdate();
						
						System.out.println("insert SQL return value " + String.valueOf(insertReturnValue));
						
						if(insertReturnValue == 1) {
							System.out.println("Record is inserted into cheque table!");
							JOptionPane.showMessageDialog(mainWindowFrame, "Cash succefully added", "Cash Added", JOptionPane.INFORMATION_MESSAGE);
						}					
						
					} catch (SQLException e) {
						e.printStackTrace();
					} finally { 
						if (prepStatement != null) {
							try {
								prepStatement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}

						if (connect != null) {
							try {
								connect.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
						clearFields();
					}
					
				}				
				
			}
		});
		cheqEnterBtn.setBounds(573, 288, 93, 30);
		financePanel.add(cheqEnterBtn);
		
		description = new JTextArea();
		description.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		description.setBounds(303, 182, 204, 84);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		financePanel.add(description);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				clearFields();
			}
		});
		btnClear.setBounds(468, 288, 93, 30);
		financePanel.add(btnClear);
		
		JButton btnClose = new JButton("Exit");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.exit(0);
			}
		});
		btnClose.setBounds(6, 288, 93, 30);
		financePanel.add(btnClose);		
		
		JButton btnReturnCheques = new JButton("Return Cheques");
		btnReturnCheques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {	
				
				System.out.println("Return cheque window object: " + returnCheqWindow);
				
				if(returnCheqWindow == null) {
					returnCheqWindow = new ReturnChequeWindow();
					returnCheqWindow.openReturnChequeWindow();	
				}				
			}
		});
		btnReturnCheques.setBounds(111, 288, 121, 30);
		financePanel.add(btnReturnCheques);		
		
		JPanel typePanel = new JPanel();
		typePanel.setLayout(null);
		typePanel.setToolTipText("Cheque type");
		typePanel.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Type", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		typePanel.setBounds(281, 61, 161, 70);
		financePanel.add(typePanel);
		
		JLabel label_4 = new JLabel("|");
		label_4.setForeground(Color.LIGHT_GRAY);
		label_4.setFont(new Font("Segoe UI", Font.BOLD, 17));
		label_4.setBounds(72, 24, 6, 24);
		typePanel.add(label_4);
		
		JRadioButton cashRdButton = new JRadioButton("Cash");		
		cashRdButton.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		cashRdButton.setActionCommand("Withdraw");
		cashRdButton.setBounds(6, 22, 59, 30);
		typePanel.add(cashRdButton);
		cashRdButton.setActionCommand(cashRdButton.getText().toString());
		typeButtonGroup.add(cashRdButton);
		
		JRadioButton chequeRdButton = new JRadioButton("Cheque");
		chequeRdButton.setHorizontalTextPosition(SwingConstants.LEFT);
		chequeRdButton.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 14));
		chequeRdButton.setActionCommand("Deposit");
		chequeRdButton.setBounds(87, 22, 82, 30);
		chequeRdButton.setSelected(true);
		typePanel.add(chequeRdButton);
		chequeRdButton.setActionCommand(chequeRdButton.getText().toString());
		typeButtonGroup.add(chequeRdButton);
		
		JPanel transactionPanel = new JPanel();
		tabbedPane.addTab("Transactions", null, transactionPanel, null);
		tabbedPane.setEnabledAt(1, true);
		transactionPanel.setLayout(null);
		
		JPanel currDateTransPanel = new JPanel();
		currDateTransPanel.setLayout(null);
		currDateTransPanel.setForeground(Color.BLACK);
		currDateTransPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		currDateTransPanel.setBackground(SystemColor.menu);
		currDateTransPanel.setBounds(25, 11, 134, 27);
		transactionPanel.add(currDateTransPanel);
		
		JLabel currDateTransYear = new JLabel("0000");
		currDateTransYear.setFont(new Font("Segoe UI", Font.BOLD, 17));
		currDateTransYear.setBounds(10, 0, 40, 24);
		currDateTransPanel.add(currDateTransYear);
		if(calander != null) {
			currDateTransYear.setText(String.valueOf(year));
		}
		
		
		JLabel label_5 = new JLabel("/");
		label_5.setFont(new Font("Segoe UI", Font.BOLD, 17));
		label_5.setBounds(53, 0, 8, 24);
		currDateTransPanel.add(label_5);
		
		JLabel currDateTransMonth = new JLabel("00");
		currDateTransMonth.setFont(new Font("Segoe UI", Font.BOLD, 17));
		currDateTransMonth.setBounds(66, 0, 28, 24);
		currDateTransPanel.add(currDateTransMonth);
		if(calander != null) {
			if(month < 10) {
				currDateTransMonth.setText("0" + String.valueOf(month));
			} else {
				currDateTransMonth.setText(String.valueOf(month));
			}
		}		
		
		JLabel label_7 = new JLabel("/");
		label_7.setFont(new Font("Segoe UI", Font.BOLD, 17));
		label_7.setBounds(93, 0, 8, 24);
		currDateTransPanel.add(label_7);
		
		JLabel currDateTransDay = new JLabel("00");
		currDateTransDay.setFont(new Font("Segoe UI", Font.BOLD, 17));
		currDateTransDay.setBounds(104, 0, 28, 24);
		currDateTransPanel.add(currDateTransDay);
		if(calander != null) {
			if(day < 10) {
				currDateTransDay.setText("0" + String.valueOf(day));
			} else {
				currDateTransDay.setText(String.valueOf(day));
			}
		}		
		
		JPanel accBalanceTransPanel = new JPanel();
		accBalanceTransPanel.setLayout(null);
		accBalanceTransPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		accBalanceTransPanel.setBounds(462, 11, 180, 78);
		transactionPanel.add(accBalanceTransPanel);
		
		JLabel label = new JLabel("Account Balance :");
		label.setFont(new Font("Segoe UI", Font.BOLD, 19));
		label.setBounds(10, 11, 164, 24);
		accBalanceTransPanel.add(label);
		
		JLabel currAccBalanceTrans = new JLabel("Rs. 100,000.00");
		currAccBalanceTrans.setHorizontalAlignment(SwingConstants.CENTER);
		currAccBalanceTrans.setFont(new Font("Segoe UI", Font.BOLD, 18));
		currAccBalanceTrans.setBounds(10, 37, 164, 24);
		accBalanceTransPanel.add(currAccBalanceTrans);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setBounds(462, 269, 180, 60);
		transactionPanel.add(panel);
		
		JLabel lblProjectedAccBalance = new JLabel("Projected Acc. Balance :");
		lblProjectedAccBalance.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblProjectedAccBalance.setBounds(10, 6, 164, 24);
		panel.add(lblProjectedAccBalance);
		
		JLabel lblRs = new JLabel("Rs. 100,000.00");
		lblRs.setHorizontalAlignment(SwingConstants.CENTER);
		lblRs.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblRs.setBounds(10, 29, 164, 24);
		panel.add(lblRs);
		
		final JLabel transLargeMonth = new JLabel("00");
		transLargeMonth.setForeground(UIManager.getColor("seaGlassTextEnabledBorder"));
		transLargeMonth.setHorizontalAlignment(SwingConstants.CENTER);
		transLargeMonth.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 80));
		transLargeMonth.setBounds(451, 138, 89, 137);
		transactionPanel.add(transLargeMonth);
		if(calander != null) {
			if(month < 10) {
				transLargeMonth.setText("0" + String.valueOf(month));
			} else {
				transLargeMonth.setText(String.valueOf(month));
			}
			
		}
		
		final JLabel transLargeDay = new JLabel("00");
		transLargeDay.setHorizontalAlignment(SwingConstants.CENTER);
		transLargeDay.setForeground(UIManager.getColor("buttonBorderBaseEnabled"));
		transLargeDay.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 80));
		transLargeDay.setBounds(565, 138, 89, 137);
		transactionPanel.add(transLargeDay);
		if(calander != null) {
			if(day < 10) {
				transLargeDay.setText("0" + String.valueOf(day));
			} else {
				transLargeDay.setText(String.valueOf(day));
			}
		}
		
		JLabel label_9 = new JLabel("/");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setForeground(UIManager.getColor("buttonBorderBaseEnabled"));
		label_9.setFont(new Font("Segoe UI Semilight", Font.PLAIN, 80));
		label_9.setBounds(531, 138, 40, 137);
		transactionPanel.add(label_9);
		
		UtilDateModel model = new UtilDateModel();
		Properties datePickerProperties = new Properties();
		datePickerProperties.put("text.today", "Today");
		datePickerProperties.put("text.month", "Month");
		datePickerProperties.put("text.year", "Year");		
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, datePickerProperties);
		final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		datePicker.getJFormattedTextField().setHorizontalAlignment(SwingConstants.CENTER);
		datePicker.setToolTipText("Date picker");
		datePicker.setBounds(459,120,135,30);
		transactionPanel.add(datePicker);
		
		JButton datePickDone = new JButton("Done");
		datePickDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				transDatePickerText = datePicker.getJFormattedTextField().getText().toString();
				
				if(transDatePickerText != null && !(transDatePickerText.trim().equals("")) ) {
					datePickerParts = transDatePickerText.split("/");
					transLargeDay.setText(datePickerParts[2]);
					transLargeMonth.setText(datePickerParts[1]);
				}				
			}
		});
		datePickDone.setBounds(595, 120, 58, 30);
		transactionPanel.add(datePickDone);
		
		JPanel forcastPanel = new JPanel();
		tabbedPane.addTab("Forecast", null, forcastPanel, null);
		forcastPanel.setLayout(null);
		
		
		JPanel summaryPanel = new JPanel();
		tabbedPane.addTab("Summary", null, summaryPanel, null);
		summaryPanel.setLayout(null);
		
		JSpinner summYearSpinner = new JSpinner();
		summYearSpinner.setModel(new SpinnerListModel(new String[] {"2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040", "2041", "2042", "2043", "2044", "2045", "2046", "2047", "2048", "2049", "2050"}));
		summYearSpinner.setToolTipText("Year");
		summYearSpinner.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		summYearSpinner.setBounds(229, 0, 76, 34);
		summYearSpinner.setValue(String.valueOf(year));
		summaryPanel.add(summYearSpinner);
		
		JSpinner summMonthSpinner = new JSpinner();
		summMonthSpinner.setModel(new SpinnerListModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		summMonthSpinner.setToolTipText("Month");
		summMonthSpinner.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		summMonthSpinner.setBounds(308, 0, 62, 34);
		summaryPanel.add(summMonthSpinner);
		if(month < 10) {
			summMonthSpinner.setValue("0" + String.valueOf(month));
		} else {
			summMonthSpinner.setValue(String.valueOf(month));
		}
		
		JButton summGo = new JButton("Go");
		summGo.setBounds(381, 3, 55, 28);
		summaryPanel.add(summGo);
		
			
					
		
	}
}
