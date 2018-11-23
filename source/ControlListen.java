import javax.media.*;
import java.awt.*;

public class ControlListen implements ControllerListener
{	
	MediaPlayerFrame mpf = null;
	
	public ControlListen(MediaPlayerFrame mpf)
	{
		this.mpf = mpf;		
	}
	
	public void controllerUpdate(ControllerEvent ce)
	{
		if(ce instanceof EndOfMediaEvent)
		{
			if(mpf.loops==true)
			{			
				mpf.pl.nextSong();
			}
			else
			{
				mpf.getPlayer().stop();
			}
		} 
		
		if(ce instanceof StartEvent)
		{
			mpf.cpp.play.setText("Pause");
			mpf.cpp.play.setActionCommand("Pause");
		}
		
		if(ce instanceof StopEvent)
		{
			mpf.cpp.play.setText("Play");
			mpf.cpp.play.setActionCommand("Play");
		}
		
		if(ce instanceof RealizeCompleteEvent)
		{			
		}
		
		if(ce instanceof PrefetchCompleteEvent)
		{		
			mpf.mainPanel.removeAll();
			mpf.mainPanel.setFocusable(true);
			mpf.mainPanel.addKeyListener(new KeysListen(mpf));
			mpf.fs = true;

			if(mpf.getPlayer()!=null)      
        		{
        		if(mpf.getPlayer().getVisualComponent()!=null)        		
			{
				mpf.getPlayer().getVisualComponent().addKeyListener(new KeysListen(mpf));		
				mpf.getPlayer().getVisualComponent().setFocusable(true);
				if(mpf.ff.isVisible()==false)
				{
		            	mpf.mainPanel.add(mpf.getPlayer().getVisualComponent(),BorderLayout.CENTER);
				}
				else
				{
				mpf.ff.removeAll();
				mpf.ff.setFocusable(true);
				mpf.ff.setVisible(false);
				mpf.ff.add(mpf.getPlayer().getVisualComponent(),BorderLayout.CENTER);
				mpf.ff.setVisible(true);
				}
			}
			else		            	
			{
			if(mpf.ff.isVisible()==true)
			{
				mpf.ff.removeAll();
				mpf.ff.setFocusable(true);
				mpf.ff.setVisible(false);
				mpf.ff.add(mpf.bg,BorderLayout.CENTER);
				mpf.ff.setVisible(true);
			}
			else
			mpf.mainPanel.add(mpf.bg,BorderLayout.CENTER);	              
	    		}
			
			if(mpf.getPlayer()!=null)
		    	{       	
    			mpf.navigation.removeAll();
			mpf.navigation.setFocusable(true);
			mpf.navigation.addKeyListener(new KeysListen(mpf));
    			mpf.navigation.add(mpf.cpp.cnp(),BorderLayout.NORTH);
    			mpf.navigation.add(mpf.cpp.cb.cpanel(),BorderLayout.SOUTH);
    			mpf.mainPanel.add(mpf.navigation,BorderLayout.SOUTH);
    			}    	
			}    		   		
    		
    			mpf.getPlayer().start();		
		}
		
	}
}