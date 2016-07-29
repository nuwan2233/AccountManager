package com.ui.forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import com.util.Database;

public class ReturnChequeWindow {

	private JFrame returnChequeFrame;
	private Connection connect = null;
	
	public void openReturnChequeWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnChequeWindow window = new ReturnChequeWindow();
					window.returnChequeFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReturnChequeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		returnChequeFrame = new JFrame();
		returnChequeFrame.getContentPane().setBackground(new Color(245, 245, 245));		
		returnChequeFrame.setBounds(100, 100, 551, 362);
		returnChequeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		returnChequeFrame.setResizable(false);
		returnChequeFrame.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		returnChequeFrame.setTitle("Return Cheques");
		returnChequeFrame.setLocationRelativeTo(null);
		returnChequeFrame.setIconImage(new ImageIcon(getClass().getResource(MainWindow.APP_ICON)).getImage());		
		returnChequeFrame.getContentPane().setLayout(null);
		
		final JFormattedTextField returnCheqNo = new JFormattedTextField();
		returnCheqNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
		returnCheqNo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		returnCheqNo.setDropMode(DropMode.INSERT);
		returnCheqNo.setColumns(10);
		returnCheqNo.setBounds(175, 23, 139, 34);
		MaskFormatter cheqNoFormatter;
		try {
			cheqNoFormatter = new MaskFormatter("# | # | # | # | # |#");
			cheqNoFormatter.setValidCharacters("0123456789AP");
			cheqNoFormatter.setPlaceholderCharacter(' ');     
			cheqNoFormatter.setValueClass(String.class);   
			DefaultFormatterFactory cheqNoFormatterFactory = new
			         DefaultFormatterFactory(cheqNoFormatter);
			returnCheqNo.setFormatterFactory(cheqNoFormatterFactory);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnChequeFrame.getContentPane().add(returnCheqNo);
		
		JButton rtnChequeBtn = new JButton("Return Cheque");
		rtnChequeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PreparedStatement prepStatement = null;
				String returnCheqNoValue = returnCheqNo.getText().toString();
				returnCheqNoValue = returnCheqNoValue.replace("|", "").replace(" ", "");
				
				System.out.println("Return cheque No: " + returnCheqNoValue);
				
				if(returnCheqNoValue.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill the cheque number", "Empty cheque number", JOptionPane.ERROR_MESSAGE);
					return;					
				}
				
				String rtnCheqNumStatement = "SELECT * FROM cheque"
						+ "WHERE chequeNo = ? AND visible = 'false'";
				
				try {
					connect = Database.getDBConnection();
					prepStatement = connect.prepareStatement(rtnCheqNumStatement);
					
					prepStatement.setString(1, returnCheqNoValue);					
					
					int returnChqNumValue = prepStatement.executeUpdate();
					
					System.out.println("Return cheque number SQL return value " + String.valueOf(returnChqNumValue));					
								
					
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
					returnCheqNo.setText("");					
				}
								
				
//				String rtnCheqUpdateStatement = "UPDATE cheque"
//						+ " SET visible = ? WHERE chequeNo = ?;";
//				
//				try {
//					connect = Database.getDBConnection();
//					prepStatement = connect.prepareStatement(rtnCheqUpdateStatement);
//					
//					prepStatement.setString(1, "false");
//					prepStatement.setString(2, returnCheqNoValue);
//					
//					// execute update SQL statement
//					int updateReturnValue = prepStatement.executeUpdate();
//					
//					System.out.println("Return cheque update SQL return value " + String.valueOf(updateReturnValue));
//					
//					if(updateReturnValue == 1) {
//						System.out.println("Return cheque is updated in cheque table!");
//						JOptionPane.showMessageDialog(null, "Cheque returned succefully", "Cheque Returned", JOptionPane.INFORMATION_MESSAGE);
//					} else if(updateReturnValue == 0) {
//						System.out.println("Return cheque update failed!");
//						JOptionPane.showMessageDialog(null, "Return Cheque number is invalid", "Cheque Return failed", JOptionPane.ERROR_MESSAGE);
//						
//					}					
//					
//				} catch (SQLException e) {
//					e.printStackTrace();
//				} finally { 
//					if (prepStatement != null) {
//						try {
//							prepStatement.close();
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//					}
//
//					if (connect != null) {
//						try {
//							connect.close();
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//					}
//					returnCheqNo.setText("");					
//				}				
				
			}
		});
		rtnChequeBtn.setBounds(326, 23, 114, 38);
		returnChequeFrame.getContentPane().add(rtnChequeBtn);
		
		JLabel lblReturnCheqNo = new JLabel("Cheque No. :");
		lblReturnCheqNo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblReturnCheqNo.setBounds(90, 32, 91, 18);
		returnChequeFrame.getContentPane().add(lblReturnCheqNo);
		
		JButton rtnCheqExitBtn = new JButton("Close");
		rtnCheqExitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnChequeFrame.setVisible(false);
				MainWindow.returnCheqWindow = null;
			}
		});
		rtnCheqExitBtn.setBounds(470, 294, 73, 36);
		returnChequeFrame.getContentPane().add(rtnCheqExitBtn);
		
		try {
			UIManager.setLookAndFeel(MainWindow.LOOK_AND_FEEL_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
