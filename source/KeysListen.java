import java.awt.event.*;

public class KeysListen extends KeyAdapter
{
	MediaPlayerFrame mpf = null;

	public KeysListen(MediaPlayerFrame mpf)
	{
		this.mpf = mpf;
	}

public void keyPressed(KeyEvent ke)
	{
		mpf.keyEvents(ke);
	}
}