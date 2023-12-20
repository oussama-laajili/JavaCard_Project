package clientpackage;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import com.sun.javacard.apduio.CadTransportException;
import com.sun.javacard.apduio.Apdu;

public class ClientInterface extends JFrame implements ActionListener {
	boolean carteIn = false;
	boolean creditOn = false;
	boolean debitOn = false;
	boolean debitDone = false;
	boolean pinIn = false;
	JPanel panePrincipale = new JPanel();
	JPanel panelEcran = new JPanel(null);
	JPanel paneLecteur = new JPanel();
	JPanel paneMoney = new JPanel();
    JLabel leteurLabel = new JLabel();
    JLabel moneyLabel = new JLabel();
    JLabel pinLabel = new JLabel("Put your card");
    JLabel balanceLabel = new JLabel();
	
	String s="";String shownS="";
	JButton ba1 = new JButton("Credit"); 
    JButton ba2 = new JButton("Debit");
    JButton ba3 = new JButton(">>>");
    JButton ba4 = new JButton(">>>");
    JButton ba5 = new JButton("Balance");
    JButton ba6 = new JButton("<<<");
    JButton ba7 = new JButton("<<<");
    JButton ba8 = new JButton("<<<");
    JButton joker1 = new JButton();
    JButton joker2 = new JButton();
    JButton joker5 = new JButton();
    JButton joker6 = new JButton();
    JButton eyeButton = new JButton();
    JButton hiddenButton = new JButton();
    
    JTextField tPin = new JTextField("");
    
	JButton b0 = new JButton();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();
    JButton b5 = new JButton();
    JButton b6 = new JButton();
    JButton b7 = new JButton();
    JButton b8 = new JButton();
    JButton b9 = new JButton();
    
    JButton cancelBtn = new JButton("Quit");
    JButton clearBtn = new JButton("Clear");
    JButton okBtn = new JButton("OK");
    JButton retourBtn = new JButton("Return"); 
    
    ImageIcon iconError = new ImageIcon("img/error.png");
    ImageIcon iconPadlock = new ImageIcon("img/padlock.png");
    
    Font msgLabel = new Font("Tahoma", Font.PLAIN, 22);
    
    public static final byte INS_INTERROGER_COMPTE = 0x01;
	public static final byte INS_INCREMENTER_COMPTE = 0x02;
	public static final byte INS_DECREMENTER_COMPTE = 0x03;
	public static final byte INS_INITIALISER_COMPTE = 0x04;
	public static final byte INS_TEST_CODE_PIN = 0x00;
	
	private ClientFunction client;
    
    public static void main(String[] args) {
    	try {
			ClientInterface window = new ClientInterface();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public ClientInterface() {
    	client = new ClientFunction();
		client.Connect();
		try {
			client.select();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CadTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initiate();
    }
    public void initiate() {
    	setTitle("ATM");
		setResizable(false);
		setBounds(0, 0, 460, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Color bgColor = new Color(80, 140, 170);

        
        panePrincipale.setLayout(null);
           
        
        //**********************Panel Ecran ***********************
        panelEcran.setBounds(115,140, 220, 220);  
        panelEcran.setBackground(new Color(219, 250, 255));
        pinLabel.setFont(msgLabel);
        pinLabel.setBounds(30, 20, 180, 30);
        panelEcran.add(pinLabel);
        
        tPin.setFont(msgLabel);
        tPin.setBounds(15,60,160,30);
        tPin.setVisible(false);
        panelEcran.add(tPin);
        
        eyeButton.setBounds(180,60, 30, 28);
        ImageIcon iconeye = new ImageIcon("img/eye.png");
        eyeButton.setIcon(iconeye);
        panelEcran.add(eyeButton);
        eyeButton.setVisible(false);
        
        hiddenButton.setBounds(180,60, 30, 28);
        ImageIcon iconhid = new ImageIcon("img/hidden.png");
        hiddenButton.setIcon(iconhid);
        panelEcran.add(hiddenButton);
        hiddenButton.setVisible(false);
        
        retourBtn.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        retourBtn.setBounds(70, 100, 80,40);
        panelEcran.add(retourBtn);
        retourBtn.setVisible(false);
        
        
       
        ba1.setBounds(10, 140, 90, 40); 
        ba2.setBounds(10,200, 90, 40);
        ba3.setBounds(10,260, 90, 40);
        ba4.setBounds(10,320, 90, 40);
        ba5.setBounds(350,140, 90, 40);
        ba6.setBounds(350,200, 90, 40);
        ba7.setBounds(350,260, 90, 40);
        ba8.setBounds(350,320, 90, 40);
        
        ba1.setEnabled(false);
        ba2.setEnabled(false);
        ba3.setEnabled(false);
        ba4.setEnabled(false);
        ba5.setEnabled(false);
        ba6.setEnabled(false);
        ba7.setEnabled(false);
        ba8.setEnabled(false);
        
        //*********************** logo lecteur *************************
        
        ImageIcon iconLecteur = new ImageIcon("img/atm_carte.png");

        leteurLabel.setIcon(iconLecteur);
        paneLecteur.add(leteurLabel);
        paneLecteur.setBounds(10, 380, 100, 150);
        paneLecteur.setBackground(bgColor);
        
        leteurLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(!carteIn) {
            		pinLabel.setText("Enter your PIN : ");
                	paneLecteur.setVisible(false);
                	tPin.setVisible(true);
                	eyeButton.setVisible(true);
                	carteIn = true;
            	}else {
            		pinLabel.setText("Card ejected");
    				pinLabel.setVisible(true);
                	paneLecteur.setVisible(false);
                	carteIn = false;
            	}
            	
            }
        });
        
        // ***************************** money logo ************************
        
        ImageIcon iconMoney = new ImageIcon("img/atm_money.png");
        moneyLabel.setIcon(iconMoney);
        paneMoney.add(moneyLabel);
        paneMoney.setBounds(10, 380, 100, 150);
        paneMoney.setBackground(bgColor);
        paneMoney.setVisible(false);
        
        moneyLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	paneMoney.setVisible(false);
            	if(debitOn) {
            		pinLabel.setVisible(false);
            	}       	
            	System.out.println("money got");
            }
        });
        //********************* clavier ******************
        b0.setBounds(190,425, 65, 65);
        ImageIcon icon0 = new ImageIcon("img/n0.png");
        b0.setIcon(icon0);
        
        b1.setBounds(115, 200, 65, 65);
        ImageIcon icon1 = new ImageIcon("img/n1.png");
        b1.setIcon(icon1);
        
        b2.setBounds(190, 200, 65, 65);
        ImageIcon icon2 = new ImageIcon("img/n2.png");
        b2.setIcon(icon2);
        
        b3.setBounds(265,200, 65, 65);
        ImageIcon icon3 = new ImageIcon("img/n3.png");
        b3.setIcon(icon3);
        
        b4.setBounds(115, 275, 65, 65);
        ImageIcon icon4 = new ImageIcon("img/n4.png");
        b4.setIcon(icon4);
        
        b5.setBounds(190, 275, 65, 65);
        ImageIcon icon5 = new ImageIcon("img/n5.png");
        b5.setIcon(icon5);
        
        b6.setBounds(265, 275, 65, 65);
        ImageIcon icon6 = new ImageIcon("img/n6.png");
        b6.setIcon(icon6);
        
        b7.setBounds(115, 350, 65, 65);
        ImageIcon icon7 = new ImageIcon("img/n7.png");
        b7.setIcon(icon7);
        
        b8.setBounds(190, 350, 65, 65);
        ImageIcon icon8 = new ImageIcon("img/n8.png");
        b8.setIcon(icon8);
        
        b9.setBounds(265, 350, 65, 65);
        ImageIcon icon9 = new ImageIcon("img/n9.png");
        b9.setIcon(icon9);
        
        
        JPanel paneLogo = new JPanel();
        JLabel bankLabel = new JLabel();
        ImageIcon iconLogo = new ImageIcon("img/banque.png");

        bankLabel.setIcon(iconLogo);
        paneLogo.add(bankLabel);
        paneLogo.setBounds(125, 10, 200, 200);
        paneLogo.setBackground(bgColor);
        
        JLabel titleLabel1 = new JLabel("Kriwss");
        Font titleLabelFont = new Font("Times New Roman", Font.BOLD, 28); 
        titleLabel1.setFont(titleLabelFont);
        titleLabel1.setBounds(80, 60, 150, 30);
        
        JLabel titleLabel2 = new JLabel("Bank");
        titleLabel2.setFont(titleLabelFont);
        titleLabel2.setBounds(280, 60, 150, 30);
        
        
        cancelBtn.setBounds(350,375, 90, 50);
        cancelBtn.setBackground(new Color(238, 78, 78));
        clearBtn.setBounds(350,430, 90, 50);
        clearBtn.setBackground(new Color(240, 146, 63));
        okBtn.setBounds(350,485, 90, 50);
        okBtn.setBackground(new Color(99, 206, 99));
        
        Color cancelBtnBorderColor = Color.red;
        int borderThickness = 3; 
        cancelBtn.setBorder(BorderFactory.createLineBorder(cancelBtnBorderColor, borderThickness));
        
        Color clearBtnBorderColor = Color.orange;
        clearBtn.setBorder(BorderFactory.createLineBorder(clearBtnBorderColor, borderThickness));

        Color okBtnBorderColor = Color.green;
        okBtn.setBorder(BorderFactory.createLineBorder(okBtnBorderColor, borderThickness));
        
        panePrincipale.add(titleLabel1);
        panePrincipale.add(titleLabel2);
        //ajout du zone de sortie
        panePrincipale.add(panelEcran);
        // ajout du Logo
        panePrincipale.add(paneLogo); 
        panePrincipale.add(ba1);
        panePrincipale.add(ba2);
        panePrincipale.add(ba3);
        panePrincipale.add(ba4);
        panePrincipale.add(ba5);
        panePrincipale.add(ba6);
        panePrincipale.add(ba7);
        panePrincipale.add(ba8);
        panePrincipale.add(paneLecteur);
        panePrincipale.add(paneMoney);
        
        JPanel paneClavier = new JPanel();
        paneClavier.add(b1);
        paneClavier.add(b2);
        paneClavier.add(b3);
        paneClavier.add(b4);
        paneClavier.add(b5);
        paneClavier.add(b6);
        paneClavier.add(b7);
        paneClavier.add(b8);
        paneClavier.add(b9);
        paneClavier.add(b0);
        paneClavier.setBounds(100, 370, 250, 230);
        
        panePrincipale.add(cancelBtn);      
        panePrincipale.add(clearBtn);        
        panePrincipale.add(okBtn);        
        
        Border bordure = BorderFactory.createLineBorder(Color.BLACK);
        //paneClavier.setBorder(bordure);
        
        panePrincipale.add(paneClavier); // ajout du clavier
        paneClavier.setBackground(bgColor);
        panePrincipale.setBackground(bgColor);
        add(panePrincipale);
      
        
		setVisible(true);
		
		// ******************************* les evenements **************************
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		clearBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		okBtn.addActionListener(this);
		eyeButton.addActionListener(this);
		hiddenButton.addActionListener(this);
		ba5.addActionListener(this);
		ba6.addActionListener(this);
		ba1.addActionListener(this);
		ba2.addActionListener(this);
		ba3.addActionListener(this);
		ba4.addActionListener(this);
		ba7.addActionListener(this);
		ba8.addActionListener(this);
		retourBtn.addActionListener(this);
		joker1.addActionListener(this);
		joker2.addActionListener(this);
		joker5.addActionListener(this);
		joker6.addActionListener(this);
		
    }
    
    public void crediter_action(String a) {
		Scanner sc = new Scanner(a);
		byte[] montant = new byte[] {sc.nextByte() };
		Apdu apdu = null;
		try {
			apdu = client.Msg(INS_INCREMENTER_COMPTE, (byte) 0x01, montant, (byte) 0x7f);
			System.out.println(apdu);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CadTransportException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (apdu.getStatus() != 0x9000) {
			if (apdu.getStatus() == 0x6A84) {
				showMessage(this, "Max Balance", iconError);
			}
			pinLabel.setText("Operation Failed !");
			pinLabel.setVisible(true);
		} else {
			pinLabel.setText("Operation Done !");
			pinLabel.setVisible(true);
		}
	}
	
	public void debiter_action(String a) {
		Scanner sc = new Scanner(a);
		byte[] montant = new byte[] {sc.nextByte() };
		Apdu apdu = null;
		try {
			apdu = client.Msg(INS_DECREMENTER_COMPTE, (byte) 0x01, montant, (byte) 0x7f);
			System.out.println(apdu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CadTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sc.reset();
		if(apdu.getStatus() != 0x9000) {
			if (apdu.getStatus() == 0x6A85) {
				showMessage(this, "Insufficient Balance", iconError);
			}	
			pinLabel.setText("Operation Failed !");
			pinLabel.setVisible(true);
			} else {
				pinLabel.setText("Operation Done !");
				pinLabel.setVisible(true);
				debitDone = true;
			}		
	}
	

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		JButton var =null;
		Object src = e.getSource();
		if(src instanceof JButton)
			var = (JButton) src;
		
		if(carteIn) {
			if(shownS.length() <= 3 ) {
				if(var == b0)
				{
						s += "0";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}
				}
				else if(var == b1)
				{
						s += "1";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b2)
				{
						s += "2";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b3)
				{
						s += "3";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b4)
				{
						s += "4";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b5)
				{
						s += "5";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b6)
				{
						s += "6";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b7)
				{
						s += "7";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b8)
				{
						s += "8";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b9)
				{
						s += "9";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
			}
		}
		
		
		if(var == clearBtn && carteIn) {
			s="";
			shownS="";
			tPin.setText("");
			hiddenButton.setVisible(false);
			eyeButton.setVisible(true);
		}
		if(var == eyeButton && s.length() != 0) {
			eyeButton.setVisible(false);
			hiddenButton.setVisible(true);
			tPin.setText(s);
		}
		if(var == hiddenButton && s.length() != 0) {
			hiddenButton.setVisible(false);
			eyeButton.setVisible(true);
			tPin.setText(shownS);
		}
		if(var == cancelBtn) {
			if(carteIn && pinIn) {
				paneLecteur.setVisible(true);
				pinLabel.setText("Take your card");
				pinLabel.setVisible(true);
				
				ba1.setEnabled(false);
				ba2.setEnabled(false);
				ba5.setEnabled(false);
			} else {
				client.Deselect();
				System.exit(1);
			}
		}
		if(var == okBtn) {
			if(carteIn) {
				String code= s;
				if(code.length()!=4) {
					showMessage(this, "pin (4 digits) !", iconError);
					//System.out.println("Erreur: Le code PIN se compose de 4 chiffres ");
				}
				else {
					int a=0;
					try {
					a= Integer.parseInt(code);
					}catch(NumberFormatException Nfe) {}
					int a1=a/1000;
					int a2=(a/100)%10;
					int a3=(a/10)%10;
					int a4=a%10;
					byte[] pin_ok= {(byte) a1, (byte) a2, (byte) a3, (byte) a4};
					Apdu apdu = null;
					try {
						apdu = client.Msg(INS_TEST_CODE_PIN, (byte) 0x04, pin_ok, (byte) 0x7f);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CadTransportException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(apdu);
					if (apdu.getStatus() == 0x6300) {
							showMessage(this, "pin incorrect!", iconError);
					} else if(apdu.getStatus()== 0x6321) {
						showMessage(this, "card blocked!", iconPadlock);
					}
					else
						{
						ba1.setEnabled(true);
						ba2.setEnabled(true);
						//ba3.setEnabled(true);
						//ba4.setEnabled(true);
						ba5.setEnabled(true);
						//ba6.setEnabled(true);
						//ba7.setEnabled(true);
						//ba8.setEnabled(true);
						pinLabel.setVisible(false);
						hiddenButton.setVisible(false);
						eyeButton.setVisible(false);
						tPin.setVisible(false);
						clearBtn.setEnabled(false);
						okBtn.setEnabled(false);
						pinIn = true;
						}
				}
			}	
		}
		if(var == ba5) {
			cancelBtn.setEnabled(false);
			Apdu apdu = null;
			try {
				apdu = client.Msg(INS_INTERROGER_COMPTE, (byte) 0x00, null, (byte) 0x7f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (CadTransportException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (apdu.getStatus() != 0x9000) {
				System.out.println("Erreur : status word different de 0x9000");
			} else {
				
				BigInteger one;
				one= new BigInteger(apdu.dataOut);
				pinLabel.setText("Your balance is : ");
				pinLabel.setVisible(true);
				tPin.setVisible(true);
				tPin.setText(one+" TND");
				tPin.setHorizontalAlignment(SwingConstants.CENTER);
				tPin.setBounds(30,60,160,30);
				tPin.setEditable(false);
				
				ba1.setEnabled(false);
				ba2.setEnabled(false);
				ba3.setEnabled(false);
				ba4.setEnabled(false);
				ba5.setEnabled(false);
				ba6.setEnabled(false);
				ba7.setEnabled(false);
				ba8.setEnabled(false);
		        retourBtn.setVisible(true);        
		        /*retourBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	ba1.setEnabled(true);
						ba2.setEnabled(true);
						ba6.setEnabled(true);
						ba5.setEnabled(true);
						pinLabel.setVisible(false);
						tPin.setVisible(false);
						retourBtn.setVisible(false);
		                System.out.println("hello retour");
		            }
		        });*/
				System.out.println(one+"tnd");
			}
		}
		
		if(var == ba1) {
			cancelBtn.setEnabled(false);
			creditOn = true;
			ba3.setEnabled(true);
			ba4.setEnabled(true);
			ba7.setEnabled(true);
			ba8.setEnabled(true);
			ba1.setVisible(false);
			ba2.setVisible(false);
			ba5.setVisible(false);
			ba6.setVisible(false);
			
			joker1.setBounds(10, 140, 90, 40);
			joker2.setBounds(10,200, 90, 40);
			joker5.setBounds(350,140, 90, 40);
			joker6.setBounds(350,200, 90, 40);

			joker1.setVisible(true);
			joker2.setVisible(true);
			joker5.setVisible(true);
			joker6.setVisible(true);
			
			retourBtn.setVisible(true);
			
			panePrincipale.add(joker1);
			panePrincipale.add(joker2);
			panePrincipale.add(joker5);
			panePrincipale.add(joker6);
			joker1.setText("10 TND");
			joker2.setText("20 TND");
			ba3.setText("30 TND");
			ba4.setText("40 TND");
			joker5.setText("60 TND");
			joker6.setText("80 TND");
			ba7.setText("100 TND");
			ba8.setText("120 TND");			
		}
		
		if(var == ba2) {
			cancelBtn.setEnabled(false);
			debitOn = true;
			ba3.setEnabled(true);
			ba4.setEnabled(true);
			ba7.setEnabled(true);
			ba8.setEnabled(true);
			ba1.setVisible(false);
			ba2.setVisible(false);
			ba5.setVisible(false);
			ba6.setVisible(false);
			
			joker1.setBounds(10, 140, 90, 40);
			joker2.setBounds(10,200, 90, 40);
			joker5.setBounds(350,140, 90, 40);
			joker6.setBounds(350,200, 90, 40);

			joker1.setVisible(true);
			joker2.setVisible(true);
			joker5.setVisible(true);
			joker6.setVisible(true);
			
			retourBtn.setVisible(true);
			
			panePrincipale.add(joker1);
			panePrincipale.add(joker2);
			panePrincipale.add(joker5);
			panePrincipale.add(joker6);
			joker1.setText("10 TND");
			joker2.setText("20 TND");
			ba3.setText("30 TND");
			ba4.setText("40 TND");
			joker5.setText("60 TND");
			joker6.setText("80 TND");
			ba7.setText("100 TND");
			ba8.setText("120 TND");			
		}
		
		
		if(var == retourBtn) {
			creditOn = false;
			debitOn = false;
			joker1.setVisible(false);
			joker2.setVisible(false);
			joker5.setVisible(false);
			joker6.setVisible(false);
			
			ba3.setText(">>>");
			ba4.setText(">>>");
			ba7.setText("<<<");
			ba8.setText("<<<");
			
			ba1.setVisible(true);
			ba2.setVisible(true);
			ba3.setVisible(true);
			ba4.setVisible(true);
			ba5.setVisible(true);
			ba6.setVisible(true);
			ba7.setVisible(true);
			ba8.setVisible(true);
			
			cancelBtn.setEnabled(true);
			
			ba1.setEnabled(true);
			ba2.setEnabled(true);
			ba3.setEnabled(false);
			ba4.setEnabled(false);
			ba5.setEnabled(true);
			ba6.setEnabled(false);
			ba7.setEnabled(false);
			ba8.setEnabled(false);
			
			retourBtn.setVisible(false);
			pinLabel.setVisible(false);
			tPin.setVisible(false);
		}
		 // ************************ evenements credit ******************************
		if(var == joker1) {
			if(creditOn) {
				crediter_action("10");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("10");
				retourBtn.setVisible(true);
				if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
				
			}
			
		}
		if(var == joker2) {
			if(creditOn) {
				crediter_action("20");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("20");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba3) {
			if(creditOn) {
				crediter_action("30");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("30");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba4) {
			if(creditOn) {
				crediter_action("40");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("40");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == joker5) {
			if(creditOn) {
				crediter_action("60");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("60");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == joker6) {
			if(creditOn) {
				crediter_action("80");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("80");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba7) {
			if(creditOn) {
				crediter_action("100");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("100");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba8) {
			if(creditOn) {
				crediter_action("120");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("120");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
	}  
	
	
	
	private static void showMessage(JFrame parent, String message, ImageIcon icon) {
		Font msgLabel = new Font("Tahoma", Font.PLAIN, 18);
		int borderThickness = 3; 
		
        final JDialog errorDialog = new JDialog(parent, "Message", true);
        errorDialog.setLayout(null);

        JLabel MessageLabel = new JLabel(message);
        MessageLabel.setFont(msgLabel);
        MessageLabel.setBounds(120,10, 340, 30);
        errorDialog.add(MessageLabel);

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 40));
        okButton.setBackground(new Color(99, 206, 99));
        Color okBtnBorderColor = Color.green;
        okButton.setBorder(BorderFactory.createLineBorder(okBtnBorderColor, borderThickness));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorDialog.dispose(); 
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.setBounds(130, 130, 80, 50);
        errorDialog.add(buttonPanel);
        
        JLabel image = new JLabel();
        image.setIcon(icon);
        image.setBounds(140, 50, 70, 70);
        errorDialog.add(image);
        
        errorDialog.setSize(360, 240);
        errorDialog.setLocationRelativeTo(parent);

        errorDialog.setVisible(true);
    }
}


