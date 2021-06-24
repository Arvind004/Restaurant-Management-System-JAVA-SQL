package DBMS;
import DBMS.InsertTables;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
//import java.sql.*;
@SuppressWarnings({ "serial", "unused" })
public class UserLogin extends Frame implements ActionListener
{
	Menu login,signup;
	MenuBar mb1;
	MenuItem m01,m02,m03;	
	
	//Dialog
		private static Dialog d;
		
	String loginId,userName,password;
	
	TextField cust_idText, cust_nameText, ph_noText, emailText,addressText;
	TextField login_idText,login_nameText,login_passwordText;
	
	TextArea errorText1;
	Connection connection1;
	Statement statement1;
	ResultSet rs;

	
	Button create,submit;
	static int x=0;
	
	public UserLogin()
	{
		try 
		{
			Class.forName ("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB ();
	}

	public void connectToDB() 
    {
		try 
		{
		  connection1=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","arvind");
		  statement1 = connection1.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void Login()
	{
		
				setTitle("Online restaurant Management System");
				
				setSize(300, 500);
				setVisible(true);
				
				//MenuBar
				mb1 = new MenuBar();
				this.setMenuBar(mb1);  
		        this.setSize(550,500);  
		        this.setLayout(null);  
		        this.setVisible(true);
		        
		        //Menu 
		         login=new Menu("Login");  
		         signup=new Menu("SignUp");
		         
		         
		         
		        
		         m01=new MenuItem("Staff Login");  
		       //  m02=new MenuItem("Guest Login");  
		         
		         m03=new MenuItem("Create Account");
		            
		        login.add(m01);  
		       // login.add(m02);
		        
		        signup.add(m03);
		        
		        mb1.add(login);
		        mb1.add(signup);
		        
		         
		    
		       //Registering action Listeners
		        m01.addActionListener(this);
		      //  m02.addActionListener(this);
		        m03.addActionListener(this);
		        setBackground(Color.CYAN);
     
		       // repaint();
		        Panel first = new Panel();
				first.setLayout(new GridLayout(1,1));
				first.add(new Label("Online Restraunt Management System"));
				
				add(first);
	}
	/*public void paint(Graphics g)
	{
		
		Font f = new Font("Calibri",Font.ITALIC,75);
		g.setFont(f);
		setForeground(Color.red);
		g.drawString("Restraunt",175,200 );
		f = new Font("Calibri",Font.ITALIC,55);
		g.setFont(f);
		setForeground(Color.red);
		g.drawString("Management System",70,290 );
		UserLogin.x++;
		
	}*/
	public void actionPerformed(ActionEvent ae)
	{
		removeAll();
		//repaint();
		String arg = ae.getActionCommand();
		if(arg.equals("Staff Login"))
		{
			removeAll();
			//repaint();
			submit = new Button("Login");
			submit.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					boolean isValidUser=false;
					try 
					{
						String txt = "select login_id,name,password from login where login_id = '"+login_idText.getText()+"' and password ='"+login_passwordText.getText()+"' and name ='"+login_nameText.getText()+"'";
						rs=statement1.executeQuery(txt);
						if(rs.next()) 
						{
							isValidUser = true;
						}
						rs.close();
						if(isValidUser) 
						{
							this.dialogPopUp("Login Success!\nClick to Continue","Login Success");
							InsertTables it = new InsertTables();
							it.addWindowListener(new WindowAdapter(){
							  public void windowClosing(WindowEvent e) 
							  {
								System.exit(0);
							  }
							});
							setVisible(false);
							it.buildFrame();
							
						}
						else
							this.dialogPopUp("Login Failed!\nPlease Try Again!","Login Failed");
					}
				    catch (SQLException insertException) 
					{
					  displaySQLErrors(insertException);
					}
					
				  }

				private void dialogPopUp(String msg, String msg2)
				{
					// TODO Auto-generated method stub
					Frame f= new Frame(); 
					f.setBackground(Color.magenta);
			        d = new Dialog(f ,msg2, true);  
			        d.setLayout( new FlowLayout() );  
			        Button b = new Button ("Click to Continue");  
			        b.addActionListener ( new ActionListener()  
			        {  
			            public void actionPerformed( ActionEvent e )  
			            {  
			                UserLogin.d.setVisible(false);  
			            }  
			        });  
			        d.add( new Label (msg));  
			        d.add(b);   
			        d.setSize(300,300);    
			        d.setVisible(true);  
					
				}
					
				

			});
			login_idText = new TextField(30);
			login_nameText = new TextField(30);
			login_passwordText = new TextField(30);
			login_passwordText.setEchoChar('*');
				
			errorText1 = new TextArea(10, 40);
			errorText1.setEditable(false);

			Panel first = new Panel();
			first.setLayout(new GridLayout(3, 2));
			first.add(new Label("Login ID:"));
			first.add(login_idText);
			first.add(new Label("Enter  Name:"));
			first.add(login_nameText);
			first.add(new Label("Enter password:"));
			first.add(login_passwordText);
			
			first.setBounds(125,90,200,100);
			
			Panel second = new Panel(new GridLayout(4, 1));
			second.add(submit);
	        		second.setBounds(125,230,150,100);         
			
			Panel third = new Panel();
			third.add(errorText1);
			third.setBounds(125,330,300,200);
			
	

			add(first);
			add(second);
			add(third);
		    
			setTitle("Online Restaurant Management System -Login");
			setSize(500, 600);
			setVisible(true);
		}
		if(arg.equals("Create Account"))
		{
			removeAll();
			create = new Button("Create New Account");
			create.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					try 
					{		  
					  String query= "INSERT INTO cust_account VALUES('" + cust_idText.getText() + "','" + cust_nameText.getText() + "'," + ph_noText.getText() + ",'" + emailText.getText() + "','" + addressText.getText() + "')";
					  int i = statement1.executeUpdate(query);
					  errorText1.append("\nCreated " + i + " Account successfully");
					  
					  //Pop Up
					   this.dialogPopUp("New Account Created Successfully!","SUCCESS!");
					  
						InsertTables it = new InsertTables();
						it.addWindowListener(new WindowAdapter(){
						  public void windowClosing(WindowEvent e) 
						  {
							System.exit(0);
						  }
						});
						setVisible(false);
						it.buildFrame();
					 }

	 
					catch (SQLException insertException) 
					{
					  displaySQLErrors(insertException);
					}
				}

				private void dialogPopUp(String msg,String msg2)
				{
					// TODO Auto-generated method stub
					Frame f= new Frame();  
			        d = new Dialog(f ,msg2, true);  
			        d.setLayout( new FlowLayout() );  
			        Button b = new Button ("Click to Continue");  
			        b.addActionListener ( new ActionListener()  
			        {  
			            public void actionPerformed( ActionEvent e )  
			            {  
			                UserLogin.d.setVisible(false);  
			            }  
			        });  
			        d.add( new Label (msg));  
			        d.add(b);   
			        d.setSize(200,200);    
			        d.setVisible(true);  
					
				}
			});	
			cust_idText = new TextField(30);
			cust_nameText = new TextField(30);
			ph_noText = new TextField(30);
			addressText = new TextField(30);
			emailText=new TextField(30);

			
			errorText1 = new TextArea(10, 40);
			errorText1.setEditable(false);

			Panel first = new Panel();
			first.setLayout(new GridLayout(5, 2));
			first.add(new Label("Customer ID:"));
			first.add(cust_idText);
			first.add(new Label("Customer Name:"));
			first.add(cust_nameText);
			first.add(new Label("Phone Number:"));
			first.add(ph_noText);
			first.add(new Label("Email:"));
			first.add(emailText);
			first.add(new Label("Address:"));
			first.add(addressText);
			first.setBounds(125,90,200,100);
			
			Panel second = new Panel(new GridLayout(4, 1));
			second.add(create);
	        		second.setBounds(125,230,150,100);         
			
			Panel third = new Panel();
			third.add(errorText1);
			third.setBounds(125,330,300,200);
			
	

			add(first);
			add(second);
			add(third);
		    
			setTitle("Online Restaurant Management System New Sign-Up!!");
			setSize(500, 600);
			setVisible(true);
		}
			
		
	}
	
	
	public void displaySQLErrors(SQLException e) 
	{
		errorText1.append("\nSQLException: " + e.getMessage() + "\n");
		errorText1.append("SQLState:     " + e.getSQLState() + "\n");
		errorText1.append("VendorError:  " + e.getErrorCode() + "\n");
	}
	public static void main(String args[])
	{
		UserLogin obj=new UserLogin();
		obj.Login();
		obj.addWindowListener(new WindowAdapter(){
			  public void windowClosing(WindowEvent e) 
			  {
				System.exit(0);
			  }
			});
	}
	
}
	
