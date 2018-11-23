import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.media.*;
import javax.swing.event.*;

public class ControlBar extends JSlider implements ChangeListener, ActionListener
{
	CThread t = null;
	MediaPlayerFrame mpf = null;
	Timer tm = null;
	JSlider vb = null;
	JLabel ttm = null;
	
	int h=0,m=0,s=0;
	
	public ControlBar(MediaPlayerFrame mpf)
	{
		super(JSlider.HORIZONTAL);		
		this.removeAll();
		this.mpf = mpf;	
		this.setEnabled(false);
		tm = new Timer(500,this);		
		addChangeListener(this);
		addKeyListener(new KeysListen(mpf));
		ttm = new JLabel("0:0:0 / 0:0:0");					
		vb = new JSlider(JSlider.HORIZONTAL);
		vb.removeAll();
		vb.setMinimum(0);
		vb.setMaximum(5);
		vb.setValue(2);
		vb.setFocusable(false);
		this.setFocusable(false);
		VolumeListen vl = new VolumeListen(mpf,vb);
		vb.addChangeListener(vl);			
		vb.addKeyListener(new KeysListen(mpf));

		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception e)
		{
			System.out.println("lnf not found");
		}

		vb.updateUI();
		this.updateUI();
		
	}
	
	public JPanel setup()
	{		
		
		JPanel jp = new JPanel();		
		
		jp.setLayout(new BoxLayout(jp,BoxLayout.X_AXIS));
		
		
		this.setEnabled(true);
		this.setMinimum(0);
		this.setMaximum((int)mpf.getPlayer().getDuration().getSeconds());
		this.setValue(0);		
		
		double l = mpf.getPlayer().getDuration().getSeconds();
		
		s = (int) l%60; 		
		m = (int)(l/60)%60;		
		h = (int)((l/60)/60)%60;		
		
		
		this.setBounds(0,0,60,10);
		
		vb.setBounds(0,0,20,10);
		jp.add(vb);
		
		ttm.setBounds(20,0,20,10);		
		
		JPanel ttmp = new JPanel();
		ttmp.setLayout(new BoxLayout(ttmp,BoxLayout.X_AXIS));
		ttmp.add(ttm);
		
		jp.add(ttmp);
		
		tm.start();
		
		return jp;
	}
	
	public JPanel cpanel()
	{
		JPanel cbp = new JPanel();
		cbp.setLayout(new GridLayout());
		cbp.add(this);
		return cbp;
	}
	
	public void stateChanged(ChangeEvent ce)	
	{			
		mpf.getPlayer().setMediaTime(new Time((double)getValue()));
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		t = new CThread(this);		
	}
	
}

class CThread implements Runnable
{
	Thread tt = null;
	ControlBar cb = null;
	
	public CThread(ControlBar cb)
	{
		this.cb = cb;
		tt = new Thread(this,"CThread");
		tt.start();
	}
	
	public void run()
	{
		cb.removeChangeListener(cb);
		cb.setValue((int)cb.mpf.getPlayer().getMediaTime().getSeconds());
		
		
		double l = cb.mpf.getPlayer().getMediaTime().getSeconds();
		
		int seconds = (int) l%60; 
		
		int minutes = (int)(l/60)%60;
		
		int hour = (int)((l/60)/60)%60;
		
		cb.ttm.setText(hour+":"+minutes+":"+seconds+" / "+cb.h+":"+cb.m+":"+cb.s);
		cb.addChangeListener(cb);				
	}
	
}

class VolumeListen implements ChangeListener
{
	MediaPlayerFrame mpf = null;
	JSlider js = null;
	
	public VolumeListen(MediaPlayerFrame mpf, JSlider js)
	{
		this.js = js;
		this.mpf = mpf;
	}
	
	public void stateChanged(ChangeEvent ce)	
	{			
		mpf.tabPane.setFocusable(true);
		mpf.getPlayer().setVolumeLevel(String.valueOf(js.getValue()));
	}
}