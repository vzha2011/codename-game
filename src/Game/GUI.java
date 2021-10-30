package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import Main.Board;
import Main.Location;

public class GUI {
	// Some instance variables used throughout the project
	private JFrame jframe;
	private JPanel MainPanel;
	private JPanel CodeNamePanel;
	private JPanel BottomPanel;
	private JPanel SpymasterMessagePanel;
	private JPanel OptionPanel;
	private Board board;

	/*
	 * This is the constructor used to set up the basic layout and borders
	 * 
	 * @param jf allows you to pass in your instance of JFrame.
	 * 
	 * @param mp allows you to pass in your instance of JPanel.
	 * 
	 * @param cp allows you to pass in another instance of JPanel.
	 * 
	 * @param sp allows you to pass in another instance of JPanel.
	 * 
	 * @param op allows you to pass in another instance of JPanel.
	 * 
	 * @param bp allows you to pass in another instance of JPanel.
	 * 
	 * @param b allows you to pass in your instance of Board.
	 * 
	 * @author
	 * 
	 * @version
	 * 
	 * @since 2018-03-25
	 */
	public GUI(JFrame jf, JPanel mp, JPanel cp, JPanel sp, JPanel op, JPanel bp, Board b) {
		MainPanel = mp;
		CodeNamePanel = cp;
		SpymasterMessagePanel = sp;
		OptionPanel = op;
		BottomPanel = bp;
		board = b;
		jframe = jf;

		BottomPanel.setLayout(new BoxLayout(BottomPanel, BoxLayout.X_AXIS));
		// creates the menu bar
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("File");

		jframe.setJMenuBar(menubar);
		menubar.add(menu);

		// creates the menu tabs
		JMenuItem j1 = new JMenuItem("Start A New Game");
		j1.addActionListener(new GameStartHandler());
		menu.add(j1);

		JMenuItem j2 = new JMenuItem("Quit");
		j2.addActionListener(new QuitGameHandler());
		menu.add(j2);

		CodeNamePanel.setLayout(new GridLayout(5, 5));
		OptionPanel.setLayout(new BoxLayout(OptionPanel, BoxLayout.Y_AXIS));

		SpymasterMessagePanel.setLayout(new BoxLayout(SpymasterMessagePanel, BoxLayout.Y_AXIS));
		SpymasterMessagePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));

		JScrollPane sp2 = new JScrollPane();
		MainPanel.add(sp2);

		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));

	}

	/*
	 * This is the event handler class for the game start function. This class
	 * allows us to add a certain actionPreformed method to when we call gameStart.
	 * It implements ActionListener class provided by Java
	 * 
	 * @author
	 * 
	 * @version
	 * 
	 * @since 2018-03-27
	 */
	public class GameStartHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainPanel.removeAll();
			CodeNamePanel.removeAll();
			OptionPanel.removeAll();
			SpymasterMessagePanel.removeAll();
			// calls game start method from board
			board.GameStart();
			// Lets you know whos turn it is
			JFrame md = new JFrame("Message");
			JOptionPane.showMessageDialog(md, "It's Red Team's Turn!");

			// sets all 25 squares with the code name and identity or just the identity
			// depending on if it was reveled or not
			for (int i = 0; i < board.getListofLocation().size(); i++) {
				JLabel label;
				if (board.getListofLocation().get(i).getPersonfromLocation().CheckIfItIsRevealed()) {
					label = new JLabel(board.getListofLocation().get(i).getPersonfromLocation().getIdentity());
				}

				else {
					label = new JLabel(board.getListofLocation().get(i).getPersonfromLocation().getPersonName() + " , "
							+ board.getListofLocation().get(i).getPersonfromLocation().getIdentity());
				}
				setFont2(label);
				CodeNamePanel.add(label);

			}

			JLabel enterclue = new JLabel("Enter Clue:");
//				JLabel entercount=new JLabel("Enter Count:");
			JTextField jt = new JTextField(20);
			jt.setText("");
//				JTextField jt2=new JTextField();
			JButton sure = new JButton("OK");
//				
			/*
			 * This is the event handler for the OK button after the clue text box
			 * 
			 * @version
			 * 
			 * @Author
			 * 
			 * @since 2018-03-25
			 */
			sure.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setClue(jt.getText());
					// checks if clue is legal and if it is not it returns a warning message
					while (board.CheckIfClueIsLegal() == false) {
						JFrame md1 = new JFrame("Warning Message");
						JOptionPane.showMessageDialog(md1, "Enter a legal clue!");
						board.setClue("");
						jt.setText("");
					}
					// implements a easter egg; if you set the clue to easter egg you automatically
					// win the game
					JFrame ld = new JFrame("You've found a secret! 0.0");
					if (jt.getText().equals("easter egg") || jt.getText().equals("Easter Egg")
							|| jt.getText().equals("Easter egg")) {
						JOptionPane.showMessageDialog(ld, "The Game has ended and your team has won!!!!!");
						board.setClue("");
						jt.setText("");
					}
					board.setClue(jt.getText());

					JLabel j1 = (JLabel) SpymasterMessagePanel.getComponent(1);
					j1.setText("Clue:" + board.getClue());
				}
			});
			/*
			 * This is the event handler for the OK button after the count text box
			 * 
			 * @version
			 * 
			 * @Author
			 * 
			 * @since 2018-03-25
			 */
//				JButton sure2=new JButton("OK");
//				sure2.addActionListener(new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						board.setCount(Integer.parseInt(jt2.getText()));
//						while(board.getCount()<0||board.getCount()>25) {
//							JFrame md2=new JFrame("Warning Message");
//							JOptionPane.showMessageDialog(md2, "The count related to clue must be greater than 0 or less and equal to 25!");
//							jt2.setText("1");
//							board.setCount(1);
//						}
//						
//					    board.setCount(Integer.parseInt(jt2.getText()));
//					    JLabel j2=(JLabel)SpymasterMessagePanel.getComponent(2);
//					    j2.setText("Count:"+board.getCount());
//					    
//					}	
//				}
//				);
			OptionPanel.add(enterclue);
			OptionPanel.add(jt);
			OptionPanel.add(sure);
//				OptionPanel.add(entercount);
//				OptionPanel.add(jt2);
//				OptionPanel.add(sure2);
			JLabel Spym = new JLabel("Spymaster's Message");
			JLabel ClueMessage = new JLabel("Clue:");
//				JLabel CountMessage=new JLabel("Count:");
			Spym.setFont(new Font("Helivetica", Font.BOLD, 25));
			Spym.setForeground(Color.BLACK);
			ClueMessage.setFont(new Font("Helivetica", Font.BOLD, 25));
			ClueMessage.setForeground(Color.RED);
//				CountMessage.setFont(new Font("Helivetica", Font.BOLD, 25));
//				CountMessage.setForeground(Color.RED);
			SpymasterMessagePanel.add(Spym);
			SpymasterMessagePanel.add(ClueMessage);
//				SpymasterMessagePanel.add(CountMessage);
			SpymasterMessagePanel
					.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));

			JLabel j1 = (JLabel) SpymasterMessagePanel.getComponent(1);
			j1.setText("Clue:" + board.getClue());
//				 JLabel j2=(JLabel)SpymasterMessagePanel.getComponent(2);
//				 j2.setText("Count:"+board.getCount());

			/*
			 * This is the event handler for the button if the spymaster round is finished
			 * 
			 * @version
			 * 
			 * @Author
			 * 
			 * @since 2018-03-25
			 */
			JButton sure3 = new JButton("Click here if finished!");
			setFont(sure3);
			SpymasterMessagePanel.add(sure3);
			sure3.addActionListener(new FinishEnterSpyMasterMessageHandler());

			MainPanel.add(CodeNamePanel);
			BottomPanel.add(OptionPanel);
			BottomPanel.add(SpymasterMessagePanel);
			MainPanel.add(BottomPanel);
			jframe.add(MainPanel);
			jframe.setVisible(true);

		}

	}

	/*
	 * This is the event handler for quiting the game
	 * 
	 * @version
	 * 
	 * @Author
	 * 
	 * @since 2018-03-27
	 */
	public class QuitGameHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/*
	 * This is the event handler for finishing the spy master rounds
	 * 
	 * @version
	 * 
	 * @Author
	 * 
	 * @since 2018-03-27
	 */
	public class FinishEnterSpyMasterMessageHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CodeNamePanel.removeAll();
			OptionPanel.removeAll();
			SpymasterMessagePanel.removeAll();
			for (int i = 0; i < board.getListofLocation().size(); i++) {
				JButton button;
				if (board.getListofLocation().get(i).getPersonfromLocation().CheckIfItIsRevealed()) {
					button = new JButton(board.getListofLocation().get(i).getPersonfromLocation().getIdentity());
				}

				else {
					button = new JButton(board.getListofLocation().get(i).getPersonfromLocation().getPersonName());
				}
				setFont(button);
				CodeNamePanel.add(button);
				button.addActionListener(new ActionListener() {
					/*
					 * This is the event handler for guessing the identity of the squares
					 * 
					 * @version
					 * 
					 * @Author
					 * 
					 * @since 2018-03-27
					 */
					@Override
					public void actionPerformed(ActionEvent e) {

						for (Location loc : board.getListofLocation()) {
							if (button.getText().equals("RedAgent") || button.getText().equals("BlueAgent")
									|| button.getText().equals("Assassin")
									|| button.getText().equals("InnocentBystander")) {
								if (button.getForeground() == Color.BLACK) {
									JFrame wm = new JFrame("Warning Message");
									JOptionPane.showMessageDialog(wm,
											"Can't select the location that is revealed already!");
									break;
								}
							}
							if (button.getText().equals(loc.getPersonfromLocation().getPersonName())) {
								JFrame endturn = new JFrame("Warning Message");
								board.setLocationChosen(loc);
								loc.getPersonfromLocation().SetPersonRevealed();
								board.setCount(board.getCount() - 1);
								button.setText(loc.getPersonfromLocation().getIdentity());

//								if(board.getCount()<0) {
//									JOptionPane.showMessageDialog(endturn, "The count is below 0!Turn Ends!");
//									break;
//								}
//								
								if (loc.getPersonfromLocation().getIdentity().equals("Assassin")) {
									button.setForeground(Color.GREEN);
									if (board.WhichTeamWins().equals("Blue Team")) {
										JOptionPane.showMessageDialog(endturn, "Blue Team Wins!Game Over!");
										break;
									} else if (board.WhichTeamWins().contains("Red Team")) {
										JOptionPane.showMessageDialog(endturn, "Red Team Wins!Game Over!");
										break;
									}
								}

								else if (loc.getPersonfromLocation().getIdentity().equals("RedAgent")) {
									board.getRedTeam().add(loc);
									button.setForeground(Color.RED);
									if (board.getRedAgentCount() == 9) {
										JOptionPane.showMessageDialog(endturn, "Red Team Wins!Game Over!");
										break;

									}

									else {
										JOptionPane.showMessageDialog(endturn, "Turn Ends!");
										break;
									}

								}

								else if (loc.getPersonfromLocation().getIdentity().equals("BlueAgent")) {
									board.getBlueTeam().add(loc);
									button.setForeground(Color.BLUE);

									if (board.getBlueAgentCount() == 8) {
										JOptionPane.showMessageDialog(endturn, "Blue Team Wins!Game Over!");
										break;
									}

									else  {
										JOptionPane.showMessageDialog(endturn, "Turn Ends!");
										break;

									}
								}

								else if (loc.getPersonfromLocation().getIdentity().equals("InnocentBystander")) {
									button.setForeground(Color.YELLOW);
									JOptionPane.showMessageDialog(endturn, "Turn Ends!");
									break;
								}

							}

						}

					}

				});
			}

			JLabel Spym = new JLabel("Spymaster's Message");
			JLabel ClueMessage = new JLabel("Clue:");
//			JLabel CountMessage=new JLabel("Count:");
			Spym.setFont(new Font("Helivetica", Font.BOLD, 25));
			Spym.setForeground(Color.BLACK);
			ClueMessage.setFont(new Font("Helivetica", Font.BOLD, 25));
			ClueMessage.setForeground(Color.RED);
//			CountMessage.setFont(new Font("Helivetica", Font.BOLD, 25));
//			CountMessage.setForeground(Color.RED);
			SpymasterMessagePanel.add(Spym);
			SpymasterMessagePanel.add(ClueMessage);
//			SpymasterMessagePanel.add(CountMessage);
			SpymasterMessagePanel
					.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));

			JLabel j1 = (JLabel) SpymasterMessagePanel.getComponent(1);
			j1.setText("Clue:" + board.getClue());
//			 JLabel j2=(JLabel)SpymasterMessagePanel.getComponent(2);
//			 j2.setText("Count:"+board.getCount());

			JButton sure4 = new JButton("Click here if Turn Ends!");
			setFont(sure4);
			SpymasterMessagePanel.add(sure4);
			sure4.addActionListener(new FinishTeamsTurnHandler());

//			JLabel label=new JLabel("End your turn");
//			label.setFont(new Font("Helivetica", Font.BOLD, 25));
//			JButton yes=new JButton("YES");
//			yes.addActionListener(new FinishTeamsTurnHandler() );
//			setFont(yes);
//			OptionPanel.add(label);
//			OptionPanel.add(yes);
//			OptionPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));

			MainPanel.add(CodeNamePanel);
			BottomPanel.add(OptionPanel);
			BottomPanel.add(SpymasterMessagePanel);
			MainPanel.add(BottomPanel);
		}

	}

	/*
	 * This is the event handler for when the teams turn is finished
	 * 
	 * @version
	 * 
	 * @Author
	 * 
	 * @since 2018-03-27
	 */
	public class FinishTeamsTurnHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			board.TakingTurn();

			MainPanel.removeAll();
			CodeNamePanel.removeAll();
			OptionPanel.removeAll();
			SpymasterMessagePanel.removeAll();

			if (board.getCurrentTeam() == 0) {
				JFrame md = new JFrame("Message");
				JOptionPane.showMessageDialog(md, "It's Red Team's Turn!");

			}

			else if (board.getCurrentTeam() == 1) {
				JFrame md = new JFrame("Message");
				JOptionPane.showMessageDialog(md, "It's Blue Team's Turn!");

			}

			JLabel Spym = new JLabel("Spymaster's Message");
			JLabel ClueMessage = new JLabel("Clue:");
//			JLabel CountMessage=new JLabel("Count:");
			Spym.setFont(new Font("Helivetica", Font.BOLD, 25));
			Spym.setForeground(Color.BLACK);
			ClueMessage.setFont(new Font("Helivetica", Font.BOLD, 25));
			ClueMessage.setForeground(Color.RED);
//			CountMessage.setFont(new Font("Helivetica", Font.BOLD, 25));
//			CountMessage.setForeground(Color.RED);
			SpymasterMessagePanel.add(Spym);
			SpymasterMessagePanel.add(ClueMessage);
//			SpymasterMessagePanel.add(CountMessage);
			SpymasterMessagePanel
					.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));
			JLabel j1 = (JLabel) SpymasterMessagePanel.getComponent(1);
			j1.setText("Clue:" + board.getClue());
//			 JLabel j2=(JLabel)SpymasterMessagePanel.getComponent(2);
//			    j2.setText("Count:"+board.getCount());

			JButton sure3 = new JButton("Click here if finished!");
			setFont(sure3);
			SpymasterMessagePanel.add(sure3);
			sure3.addActionListener(new FinishEnterSpyMasterMessageHandler());

			for (int i = 0; i < board.getListofLocation().size(); i++) {
				JLabel label;
				if (board.getListofLocation().get(i).getPersonfromLocation().CheckIfItIsRevealed()) {
					label = new JLabel(board.getListofLocation().get(i).getPersonfromLocation().getIdentity());
				}

				else {
					label = new JLabel(board.getListofLocation().get(i).getPersonfromLocation().getPersonName() + " , "
							+ board.getListofLocation().get(i).getPersonfromLocation().getIdentity());
				}
				setFont2(label);
				CodeNamePanel.add(label);
			}

			JLabel enterclue = new JLabel("Enter Clue:");
//			JLabel entercount=new JLabel("Enter Count:");
			JTextField jt = new JTextField();
			jt.setText(" ");
//			JTextField jt2=new JTextField();
			JButton sure = new JButton("OK");

			sure.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					board.setClue(jt.getText());
					while (board.CheckIfClueIsLegal() == false) {
						JFrame md1 = new JFrame("Warning Message");
						JOptionPane.showMessageDialog(md1, "Enter a legal clue!");
						board.setClue(" ");
						jt.setText(" ");
					}

					board.setClue(jt.getText());

					JLabel j1 = (JLabel) SpymasterMessagePanel.getComponent(1);
					j1.setText("Clue:" + board.getClue());
				}
			});
//			JButton sure2=new JButton("OK");
//			sure2.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					board.setCount(Integer.parseInt(jt2.getText()));
//					while(board.getCount()<0||board.getCount()>25) {
//						JFrame md2=new JFrame("Warning Message");
//						JOptionPane.showMessageDialog(md2, "The count related to clue must be greater than 0 or less and equal to 25!");
//						jt2.setText("1");
//						board.setCount(1);
//					}
//					
//				    board.setCount(Integer.parseInt(jt2.getText()));
//				    JLabel j2=(JLabel)SpymasterMessagePanel.getComponent(2);
//				    j2.setText("Count:"+board.getCount());
//				    
//				}	
//			}
//			);
			OptionPanel.add(enterclue);
			OptionPanel.add(jt);
			OptionPanel.add(sure);
//			OptionPanel.add(entercount);
//			OptionPanel.add(jt2);
//			OptionPanel.add(sure2);

			// adds everything to the JPanel and then sets the jfame visable to refresh the
			// frame
			MainPanel.add(CodeNamePanel);
			BottomPanel.add(OptionPanel);
			BottomPanel.add(SpymasterMessagePanel);
			MainPanel.add(BottomPanel);
			jframe.add(MainPanel);
			jframe.setVisible(true);

		}

	}

	/*
	 * This sets the font
	 * 
	 * @version
	 * 
	 * @Author
	 * 
	 * @since 2018-03-27
	 */
	public void setFont(JButton b) {
		b.setFont(new Font("Helivetica", Font.BOLD, 25));
		b.setBackground(Color.WHITE);
		b.setForeground(Color.BLACK);
		b.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));
		b.setOpaque(true);
	}

	/*
	 * This sets the font
	 * 
	 * @version
	 * 
	 * @Author
	 * 
	 * @since 2018-03-27
	 */
	public void setFont2(JLabel l) {
		l.setFont(new Font("Helivetica", Font.BOLD, 25));
		l.setBackground(Color.WHITE);
		l.setForeground(Color.BLACK);
		l.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.GRAY, Color.LIGHT_GRAY));
		l.setOpaque(true);
	}

	/*
	 * This is the main method which allows you to run the entire method
	 * 
	 * @version
	 * 
	 * @Author
	 * 
	 * @since 2018-03-27
	 */
	public static void main(String[] args) {
		JPanel jp = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();
		JPanel jp4 = new JPanel();
		JPanel jp5 = new JPanel();
		Board b = new Board();
		JFrame jframe = new JFrame("codenames");
		jframe.setVisible(true);
		jframe.pack();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI g = new GUI(jframe, jp, jp2, jp3, jp4, jp5, b);

	}

}
