import javax.media.*;
import javax.media.renderer.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.media.bean.playerbean.*;

public class MediaPlayerFrame extends JFrame {	
	
	Frame ff = null;
		
    static final String FRAME_TITLE = "Media Player";
    static final String PLAYER_TITLE = "Player";
	
	public boolean loops = false;
	public boolean fs = false;	

    private static final int LOC_X = 200;
    private static final int LOC_Y = 100;
    private static final int HEIGHT = 450;
    private static final int WIDTH = 500;
	
	public	PlayList pl = null;	
	

    String a = null;
    String b = null;

	MediaMenu  mainmenu = null;

	JPanel mainPanel = null;
	
	JPanel navigation = null;
	
    private MediaPlayer player = null;
    
    ControlPanel cpp = null;

   JPanel tabPane = null;
    
    private Container con = null;

	JPanel  bg = null;

	public void keyEvents(KeyEvent ke)
	{
		processKeyEvent(ke);
	}

    public void oprocessEvent()
    {
    	WindowEvent we = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
    	processWindowEvent(we);
    }      

   public void aprocessEvent(ActionEvent ae)
	{
		processEvent((AWTEvent)ae);
	}		

	public void kprocessEvent(KeyEvent ke)
	{
		processKeyEvent(ke);
	}
    
    public void end()
    {
    	try
    	{
    		if(player!=null)
    		{   
    			Time tt = new Time(0);
    			player.setMediaTime(tt);
    			player.stop();
    		}
    	}
    	catch(Exception e)
    	{
    	}
    }   
    
    
    public MediaPlayer getPlayer()
    {
    	return player;
    }

    public MediaPlayerFrame() {
        super(FRAME_TITLE);
        setLocation(LOC_X, LOC_Y);
        setSize(WIDTH, HEIGHT);
	setLayout(new GridLayout());

	setFocusable(true);
        setFocusCycleRoot(true);
        setFocusTraversalKeysEnabled(true);

	bg = new JPanel();
	bg.setLayout(new GridLayout());
	ImageIcon ii = new ImageIcon("bg.jpg");
	JLabel jl = new JLabel(ii);		
	bg.setFocusable(false);
	bg.add(jl);

	pl = new PlayList(this);
	
    	mainmenu = new MediaMenu(this);
	
    
	addKeyListener(mainmenu.ml);	
    	
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        navigation = new JPanel();
        navigation.setLayout(new BorderLayout());
        
        tabPane = new JPanel();
	tabPane.setLayout(new GridLayout());
	tabPane.addKeyListener(new KeysListen(this));
        tabPane.add(createMainPanel());         
        
        con = getContentPane();
        con.add(tabPane);
	con.addKeyListener(new KeysListen(this));                      
        
        addWindowListener(new WindowAdapter() {
       
                              public void windowClosing(WindowEvent e) {
                                  closeCurrentPlayer();   								  
                              }
                          });                    
	setVisible(true);        

    }    

   
    private JPanel createMainPanel() {        
          
        mainPanel = new JPanel();                
        mainPanel.setLayout(new BorderLayout());   
        cpp = new ControlPanel(this);
        navigation.add(cpp.incom(),BorderLayout.NORTH);    			
        navigation.add(cpp.incom2(),BorderLayout.SOUTH);	
	mainPanel.add(bg,BorderLayout.CENTER);
        mainPanel.add(navigation,BorderLayout.SOUTH);
        player = new MediaPlayer();
        ControlListen cl = new ControlListen(this);	
        player.addControllerListener(cl);
		player.realize();

	navigation.setFocusable(true);
	tabPane.setFocusable(true);
	mainPanel.setFocusable(true);	

		return mainPanel;
    }
	
	public void setLocator(MediaLocator locator)
	{		
			try{
				player.stop();
				player.close();
			}
			catch(Exception e)
			{	
			}
			tabPane.setFocusable(true);
			try{								
			player.setMediaLocator(locator);
			getPlayer().prefetch();				
        }
        catch(Exception e)
        {
        	this.setTitle(FRAME_TITLE);
        	JOptionPane.showMessageDialog(null,"Unable to play this file","Error",JOptionPane.PLAIN_MESSAGE);        	
        	e.printStackTrace();
        	return;
        }       	
                
    }
       
    private void closeCurrentPlayer() {
        if (player != null) {
            player.stop();
            player.close();            
        }
    }

    
    public static void printUsage() {
        System.out.println("Usage: java MediaPlayerFrame mediaLocator");
    }

    
    public static void main(String[] args) {
        try {
               MediaPlayerFrame mpf = new MediaPlayerFrame();              
            
            }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}