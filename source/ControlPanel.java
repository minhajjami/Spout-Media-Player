import javax.media.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class ControlPanel extends KeyAdapter implements ActionListener
{
	MediaPlayerFrame mpf = null;
	JButton play = null;
	JButton stop = null;
	JButton nexts = null;
	JButton prevs = null;
	JButton fscreen = null;
	JPanel cp = null;
	ControlBar cb = null;
	
	public ControlPanel(MediaPlayerFrame mpf)
	{
		this.mpf = mpf;
		play = new JButton("Play");
		play.setActionCommand("Play");
		play.addActionListener(this);
		stop = new JButton("Stop");
		stop.setActionCommand("Stop");
		stop.addActionListener(this);
		fscreen = new JButton("Full Screen");
		fscreen.setActionCommand("Full Screen");
		fscreen.addActionListener(mpf.mainmenu);
		nexts = new JButton("Next");
		nexts.setActionCommand("Next");
		nexts.addActionListener(mpf.mainmenu);
		prevs = new JButton("Previous");
		prevs.setActionCommand("Previous");
		prevs.addActionListener(mpf.mainmenu);
		
		cp = new JPanel();
		cp.setLayout(new BoxLayout(cp,BoxLayout.X_AXIS));
		
		cb = new ControlBar(mpf);				
		
		play.setBounds(0,0,20,10);
		cp.add(play);
		stop.setBounds(20,0,20,10);
		cp.add(stop);				
		
		play.removeAll();
		stop.removeAll();
				
		play.setFocusable(false);
		stop.setFocusable(false);		
		fscreen.setFocusable(false);
		nexts.setFocusable(false);
		prevs.setFocusable(false);

		play.addKeyListener(new KeysListen(mpf));
		stop.addKeyListener(new KeysListen(mpf));	
		
		
	}	
	
	public JPanel incom()
	{
		
		JPanel in = new JPanel();		
		in.setLayout(new BoxLayout(in,BoxLayout.X_AXIS));
		JSlider js = new JSlider(JSlider.HORIZONTAL);
		js.setEnabled(false);
		JButton b1 = new JButton("Play");		
		b1.setEnabled(false);
		in.add(b1);
		JButton b2 = new JButton("Stop");
		b2.setEnabled(false);
		in.add(b2);
		in.add(js);
		in.add(new JLabel("00:00:00 / 00:00:00"));
		JButton b3 = new JButton("Next");		
		b3.setEnabled(false);
		in.add(b3);
		JButton b4 = new JButton("Previous");		
		b4.setEnabled(false);
		in.add(b4);
		JButton b5 = new JButton("Full Screen");		
		b5.setEnabled(false);
		in.add(b5);
		return in;
	}
	
	public JPanel incom2()
	{
	
		JPanel in = new JPanel();
		in.setLayout(new BoxLayout(in,BoxLayout.X_AXIS));
		JSlider js = new JSlider(JSlider.HORIZONTAL);
		js.setEnabled(false);
		in.add(js);		
		return in;
	}
	
	public JPanel cnp()
	{		
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp,BoxLayout.X_AXIS));
		jp.add(play);
		jp.add(stop);
		jp.add(cb.setup());
		jp.add(nexts);
		jp.add(prevs);
		jp.add(fscreen);
		return jp;
	}
	
	public void actionPerformed(ActionEvent ae)
	{		
		String check = ae.getActionCommand();
			if(check.equals("Play"))
			{
				if(mpf.getPlayer().getState()!=mpf.getPlayer().Started)
				{
			
					mpf.getPlayer().start();					
				}
			}
			
			if(check.equals("Pause"))
			{
				if(mpf.getPlayer().getState()==mpf.getPlayer().Started)
				{
					mpf.getPlayer().stop();
				}
			}
			
			if(check.equals("Stop"))
			{
				mpf.getPlayer().setMediaTime(new Time(0));
				mpf.getPlayer().stop();
			}
	}
	
}