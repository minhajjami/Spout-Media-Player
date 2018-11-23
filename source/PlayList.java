import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.io.*;
import javax.media.*;

public class PlayList extends MouseAdapter implements ActionListener
{
	Frame d = null;
	MediaPlayerFrame mpf = null;
	java.awt.List songs = null;
	Properties ht = null;
	String name = null;
	JButton btplay = null;
	JButton btadd = null;
	JButton btremove = null;
	JButton btremoveall = null;
	MenuItem btopen = null;
	MenuItem btnew = null;
	MenuItem btsaveas = null;
	MenuItem btsave = null;
	MenuItem bthide = null;
	
	String plpath = null;
	int playing = 0;
	
	
	public PlayList(MediaPlayerFrame mpf)
	{
		this.mpf = mpf;
		songs = new java.awt.List(6,false);
		songs.addMouseListener(this);
		ht = new Properties();
		d = new Frame();
		name = "default.plf";
		setButtons();
		process();		
		JFileChooser fd = new JFileChooser();
		File ef = fd.getCurrentDirectory();
		plpath = ef.getAbsolutePath();
		plpath+="\\myplaylists\\";				
		File filec = new File(plpath);
		filec.mkdir();
		d.setSize(250,300);
		d.setResizable(false);
	}
	
	
	public void setButtons()
	{
		btplay = new JButton("Play");
		btplay.setActionCommand("Play");
		btplay.addActionListener(this);
		
		btadd = new JButton("Add");
		btadd.setActionCommand("Add");
		btadd.addActionListener(this);
		
		btremove = new JButton("Remove");
		btremove.setActionCommand("Remove");
		btremove.addActionListener(this);
		
		btremoveall = new JButton("Remove All");
		btremoveall.setActionCommand("Remove All");
		btremoveall.addActionListener(this);
		
		btopen = new MenuItem("Open PlayList");
		btopen.setActionCommand("Open");
		btopen.addActionListener(this);
		
		btnew = new MenuItem("New PlayList");
		btnew.setActionCommand("New");
		btnew.addActionListener(this);
		
		btsave = new MenuItem("Save");
		btsave.setActionCommand("Save");
		btsave.addActionListener(this);
		
		bthide = new MenuItem("Hide");
		bthide.setActionCommand("Hide");
		bthide.addActionListener(this);
		
		btsaveas = new MenuItem("Save As");
		btsaveas.setActionCommand("Save As");
		btsaveas.addActionListener(this);
	}
	
	
	public void add()
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(true);
		jfc.showOpenDialog(mpf);
		
		File f[] = jfc.getSelectedFiles();
		
		if(f.length==0)
		return;
		
		for(int i =0;i<f.length;i++)
		{				
			ht.put(String.valueOf(songs.getItemCount()),f[i].getAbsolutePath());			
			songs.add(f[i].getName());
		}
		playSong(songs.getItemCount()-1);
		process();
		refresh();
	}
	
	public void createNew()
	{		
		String n = JOptionPane.showInputDialog(mpf,"Enter File Name");
		if(n==null)
		return;
		name = n;
		name+=".plf";
		songs = new java.awt.List(6,false);
		songs.addMouseListener(this);
		ht = new Properties();		
		process();
		refresh();
	}
	
	public void saveList()
	{
		try{
		FileOutputStream fos = new FileOutputStream(plpath+name);
		ht.store(fos,"PlayList");
		fos.close();
			}
		catch(Exception e)
		{
			System.out.println(e);		
		}
		process();
		refresh();
	}
	
	public void saveAsList()
	{
		try{
		String n = JOptionPane.showInputDialog(mpf,"Enter file name to save as");
		if(n==null)
		return;
		name = n;
		name +=".plf";
		FileOutputStream fos = new FileOutputStream(plpath+name);
		ht.store(fos,"PlayList");
		fos.close();		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		process();
		refresh();
	}
	
	public void openList()
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		File filec = new File(plpath);
		jfc.setCurrentDirectory(filec);
		jfc.showOpenDialog(mpf);

		ht = new Properties();
		
		File f = jfc.getSelectedFile();
		
		if(f==null)
		return;

		name = f.getName();
			
		songs = new java.awt.List(6,false);
		songs.addMouseListener(this);
		
		FileInputStream fin = null;
		
		try
			{
			fin = new FileInputStream(f.getAbsolutePath());		
			ht.load(fin);						
			fin.close();
			}
			catch(Exception e){				
			}
		
		Enumeration em = ht.elements();
		
		int count = 0;
		
		java.awt.List temp = new java.awt.List(1,false);
		
		while(em.hasMoreElements())
		{
			temp.add((String)em.nextElement());
			count++;
		}
		
		for(int i=count-1;i!=-1;i--)	
		{
			StringTokenizer st = new StringTokenizer(temp.getItem(i),"\\");
			String g = null;
			while(st.hasMoreElements())				
			{
				g = st.nextToken();			
			}			
			songs.add(g);
		}
		
		process();
		refresh();
	}
	
	public void process()
	{
		d.removeAll();

		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception e)
		{
			System.out.println("lnf not found");
		}		
	
		MenuBar mnb = new MenuBar();
		Menu mn = new Menu("File");
		mn.add(btnew);
		mn.add(btopen);
		mn.add(btsave);
		mn.add(btsaveas);
		mn.add(bthide);		
		mnb.add(mn);
		d.setLayout(new BoxLayout(d,BoxLayout.X_AXIS));	
		
		d.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				d.setVisible(false);
			}
		}
		);
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));

		JPanel bt = new JPanel();
		bt.setLayout(new BoxLayout(bt,BoxLayout.X_AXIS));

		btplay.updateUI();
		btadd.updateUI();
		btremove.updateUI();
		btremoveall.updateUI();

		bt.add(btplay);
		bt.add(btadd);
		bt.add(btremove);
		bt.add(btremoveall);
		
		
		
		jp.add(songs);
		jp.add(bt);
		
		d.setTitle(name);
		d.setMenuBar(mnb);
		d.setFocusable(true);
		d.addKeyListener(new KeysListen(mpf));				
		d.add(jp);		
		
	}
	
	
	public void playSong(int index)
	{	
		String ss = (String)ht.get(String.valueOf(index));
		if(ss==null)
		return;
		try
		{		
		playing = index;
		mpf.setTitle(songs.getItem(playing)+" - "+mpf.FRAME_TITLE);						
		MediaLocator ml = new MediaLocator((new File(ss)).toURL());
		mpf.setLocator(ml);		
		}
		catch(Exception e){
			System.out.println(e);
			System.out.println(ss);
		}
	}

	public int totalSongs()
	{		
		
		int count = songs.getItemCount();
		return count;
	}
	
	public void nextSong()
	{		
	try
	{
		if((playing+1)>=totalSongs())
		playing = 0;		
		else
		playing++;		
		
		playSong(playing);		
	}
	catch(Exception e)
	{
		nextSong();					
	}
	}

	public void previousSong()
	{
	try
	{
		if(playing<=0)
		playing = totalSongs()-1;		
		else
		playing--;		
	

		playSong(playing);		
	}
	catch(Exception e)
	{		
		previousSong();
	}
	}
	
	public void refresh()
	{
		if(d.isVisible()==true)
		{
			d.setVisible(false);
			d.setVisible(true);
		}		
	}

	public void updateList()
	{
		try{		
		FileOutputStream fos = new FileOutputStream(plpath+name);		
		ht.store(fos,"PlayList");
		fos.close();		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		process();
		refresh();	
	}
	
	
	public void hideList()
	{
		d.setVisible(false);
	}
	
	public void showList()
	{
		d.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String check = ae.getActionCommand();
		if(check.equals("Add"))
		{
			add();
		}
		
		if(check.equals("Remove"))
		{
			if(songs.getSelectedIndex()==-1)
			return;

			for(int i=songs.getSelectedIndex();i<totalSongs()-1;i++)
			{
				ht.put(String.valueOf(i),(String)ht.get(String.valueOf(i+1)));					
			}
			

			ht.remove(String.valueOf(totalSongs()-1));
			songs.remove(songs.getSelectedIndex());
			updateList();
			process();
			refresh();
		}
		
		if(check.equals("Remove All"))
		{
			ht = new Properties();
			songs = new java.awt.List(6,false);
			updateList();
			process();
			refresh();
			playing = 0;
		}
		
		if(check.equals("Open"))
		{
			openList();
		}
		
		if(check.equals("New"))
		{
			createNew();
		}
		
		if(check.equals("Save"))
		{
			saveList();
		}
		
		if(check.equals("Save As"))
		{
			saveAsList();
		}
		
		if(check.equals("Play"))
		{			
			if(songs.getSelectedIndex()==-1)
			return;			
			playing = songs.getSelectedIndex();
			playSong(playing);
		}
		
		if(check.equals("Hide"))
		{			
			hideList();
		}
		
	}
	
	public void mouseClicked(MouseEvent me)
	{
		if(me.getClickCount()==2 && me.getButton()==1)
		{
		if(songs.getSelectedIndex()==-1)
		return;			
		playing = songs.getSelectedIndex();
		playSong(playing);
		}
		else
		return;
	}
	
}
