public class KeyListen extends KeyAdapter
{
	MediaPlayerFrame mpf = null;

	public KeyListen(MediaPlayerFrame mpf)
	{
		this.mpf = mpf;
	}

public void keyPressed(KeyEvent ke)
	{
		mpf.keyEvents(ke);
	}
}