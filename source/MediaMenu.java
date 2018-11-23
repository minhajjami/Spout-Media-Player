import javax.media.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class MediaMenu implements ActionListener,ItemListener
{		
	KeyListen ml = null;
	MediaPlayerFrame mpf = null;

	GraphicsEnvironment ge = null;
	GraphicsDevice gd = null;
	DisplayMode dm = null;
	
public MediaMenu(MediaPlayerFrame mpf)
{	
		this.mpf = mpf;
		MenuBar m = new MenuBar();
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open File");
        open.addActionListener(this);
        file.add(open);        
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(this);
        file.add(exit);
        m.add(file);

        Menu view = new Menu("View");
	MenuItem fscreen = new MenuItem("Full Screen");
        fscreen.addActionListener(this);
        view.add(fscreen);        
	MenuItem playlist = new MenuItem("PlayList");
        playlist.addActionListener(this);
        view.add(playlist);        	
	m.add(view);
	
        
	ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	gd = ge.getDefaultScreenDevice();
	dm = gd.getDisplayMode();  
	mpf.ff = new Frame();
	mpf.ff.setLayout(new BorderLayout());
	mpf.ff.setSize(dm.getWidth(),dm.getHeight());   		
	mpf.ff.setUndecorated(true);
	mpf.ff.setFocusable(true);

        Menu options = new Menu("Options");
        CheckboxMenuItem loop = new CheckboxMenuItem("Loop");
        loop.addItemListener(this);
        options.add(loop);         
        
           
	ml = new KeyListen(mpf,ml); 
	
	mpf.ff.addKeyListener(new KeysListen(mpf));
	mpf.bg.addKeyListener(new KeysListen(mpf));

        m.add(options);
        mpf.setMenuBar(m);
}

public void actionPerformed(ActionEvent ae)
    {	
    	String check = ae.getActionCommand();
    	if(check.equals("Open File"))
    	{
		mpf.pl.add();    	
    	}   	
    	
    	if(check.equals("Exit"))
    	{
    	mpf.oprocessEvent();
    	}    	    	
	
	if(check.equals("Full Screen"))
	{
		if(mpf.fs==false)
		return;

		if(mpf.getPlayer().getVisualComponent()!=null)
		{
		mpf.ff.removeAll();
		mpf.ff.setFocusable(true);
		
		mpf.setVisible(false);
    		mpf.ff.add(mpf.getPlayer().getVisualComponent(),BorderLayout.CENTER);    				
		mpf.ff.setVisible(true);
		}
		else
		{					
    		mpf.ff.removeAll();
		mpf.ff.setFocusable(true);
		
		mpf.setVisible(false);
    		mpf.ff.add(mpf.bg,BorderLayout.CENTER);    				
		mpf.ff.setVisible(true);	
		}		    		
    		

    	}  

	if(check.equals("PlayList"))	
	{
		mpf.pl.showList();
	}
	
	if(check.equals("Next"))	
	{		
		try
		{
		mpf.getPlayer().close();
		}
		catch(Exception e){}
		mpf.pl.nextSong();
	}    	

	if(check.equals("Previous"))	
	{
		try
		{
		mpf.getPlayer().close();
		}
		catch(Exception e){}
		mpf.pl.previousSong();
	}

    }
    
    public void itemStateChanged(ItemEvent ie)
    {       	
    	if(ie.getStateChange()==ie.SELECTED)
    	{    	
    		mpf.loops = true;    		
    	}
    	
    	if(ie.getStateChange()==ie.DESELECTED)
    	{    		
    		mpf.loops = false;    		    		
    	}    	
    }

}

class KeyListen extends KeyAdapter
{
	MediaPlayerFrame mpf = null;
	KeyListen ml = null;
	
	public KeyListen(MediaPlayerFrame mpf,KeyListen ml)
	{		
		this.ml = ml;
		this.mpf = mpf;
	}
	
	public void keyPressed(KeyEvent ke)
        	{		
			int key = ke.getKeyCode();

			if(key==ke.VK_ESCAPE)
			{
				if(mpf.ff!=null)
				{
				if(mpf.ff.isVisible()==false)
				{
				mpf.getPlayer().setMediaTime(new Time(0));
				mpf.getPlayer().stop();
				return;
				}
				}
			}
			
        		if(key==ke.VK_ESCAPE)
        		{
				if(mpf.getPlayer().getVisualComponent()!=null)        		
		            	mpf.mainPanel.add(mpf.getPlayer().getVisualComponent(),BorderLayout.CENTER);
				else
				mpf.mainPanel.add(mpf.bg,BorderLayout.CENTER);
	        		mpf.setVisible(true);
        			mpf.ff.setVisible(false);        		       		
        		}

			if(key==ke.VK_LEFT)
			{
				mpf.cpp.cb.setValue((int)mpf.getPlayer().getMediaTime().getSeconds()-10);
			}

			if(key==ke.VK_RIGHT)
			{				
				mpf.cpp.cb.setValue((int)mpf.getPlayer().getMediaTime().getSeconds()+10);	
			}     

			if(key==ke.VK_UP)
			{
				String v = mpf.getPlayer().getVolumeLevel();				
				int vol = Integer.parseInt(v);
				if(vol<6)
				{	
				vol++;
				v = String.valueOf(vol);
				mpf.getPlayer().setVolumeLevel(v);						
				mpf.cpp.cb.vb.setValue(vol);
				}
				
			}

			if(key==ke.VK_DOWN)
			{
				String v = mpf.getPlayer().getVolumeLevel();				
				int vol = Integer.parseInt(v);
				if(vol>0)
				{	
				vol--;
				v = String.valueOf(vol);
				mpf.getPlayer().setVolumeLevel(v);						
				mpf.cpp.cb.vb.setValue(vol);						
				}				
			}

			if(key==ke.VK_SPACE)
			{
				if(mpf.getPlayer().getState()==mpf.getPlayer().Started)
				mpf.getPlayer().stop();
				else
				{
				if(mpf.getPlayer().getState()==mpf.getPlayer().Prefetched)
				mpf.getPlayer().start();
				}
			}
			

			if(key==ke.VK_F)
			{
				if(mpf.fs==false)
				return;
				
				if(mpf.ff.isVisible()==false)
				{	
				if(mpf.getPlayer().getVisualComponent()!=null)
				{
				mpf.ff.removeAll();
				mpf.ff.setFocusable(true);
				
				mpf.setVisible(false);
    				mpf.ff.add(mpf.getPlayer().getVisualComponent(),BorderLayout.CENTER);  
				mpf.ff.setVisible(true);
				}
				else 
				{				   		
				mpf.ff.removeAll();
				mpf.ff.setFocusable(true);
				mpf.setVisible(false);
		    		mpf.ff.add(mpf.bg,BorderLayout.CENTER);    									mpf.ff.setVisible(true);
				}
		    			
					}	

				else if(mpf.ff.isVisible()==true)
					{	
					if(mpf.getPlayer().getVisualComponent()!=null)        		
			            	mpf.mainPanel.add(mpf.getPlayer().getVisualComponent(),BorderLayout.CENTER);		
					else
					mpf.mainPanel.add(mpf.bg,BorderLayout.CENTER);	
					mpf.setVisible(true);					
        				mpf.ff.setVisible(false);        		       		
					}
				}

			if(key==ke.VK_S)
			{
				if(mpf.getPlayer().getState()==mpf.getPlayer().Started)
				{
				mpf.getPlayer().setMediaTime(new Time(0));
				mpf.getPlayer().stop();
				}				
			}	
			
			if(key==ke.VK_N)
			{
				mpf.pl.nextSong();				
			}

			if(key==ke.VK_P)
			{
				mpf.pl.previousSong();				
			}			

   	}    				

}