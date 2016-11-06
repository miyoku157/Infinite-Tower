package Class;

public class Chronometre
{
	static Chronometre chrono;
	private int heures;
	private int minutes;
	private Integer secondes;
	private boolean keepCounting;
	private CountingThread chronoThread;
	private int temp;
	
	private Chronometre()
	{
		secondes = 0;
		minutes= 0;
		heures = 0;
		temp = 0;
	}

	public static Chronometre getChrono()
	{
		if(chrono == null)
		{
			chrono = new Chronometre();
		}
		return chrono;
	}
	
	private class CountingThread extends Thread
	{
		int temp_calcul = 0;
		
		public CountingThread(int _temp)
		{
			temp_calcul = _temp;
			
			if(temp_calcul != 0)
			{
				heures = temp_calcul / 3600;
				if(heures != 0)
				{
					temp_calcul -= heures*3600;
				}
				
				minutes = temp_calcul / 60;
				if(minutes != 0)
				{
					temp_calcul -= minutes*60;
				}
				
				secondes = temp_calcul;
			}
		}
		
		@Override
		public void run()
		{
			while (keepCounting)
			{
				try
				{
                    Thread.sleep(1000);
                    synchronized(secondes)
                    {
                    	timeGoesBy();
                    }
                }
				catch (InterruptedException e)
				{
                    e.printStackTrace();
                }
			}
		}
	}
	
	public void pause()
	{
		keepCounting = false;
	}
	
	public void resume()
	{
		keepCounting = true;
		chronoThread = new CountingThread(temp);
		chronoThread.start();
	}
	
	public void reset()
	{
		secondes = 0;
		minutes= 0;
		heures = 0;
		temp = 0;
	}

	public String getTime()
	{
		String res;
		
		if(heures == 0 && minutes == 0)
		{
			res = Integer.toString(secondes) + "s";
		}
		else if(heures == 0)
		{
			res = Integer.toString(minutes) + "m " + Integer.toString(secondes) + "s";
		}
		else
		{
			res = Integer.toString(heures) + "h " + Integer.toString(minutes) + "m " + Integer.toString(secondes) + "s";
		}
		return res;
	}
	
	public int[] getTimeArray()
	{
		int[] tabSMH = new int[3];
		
		tabSMH[0] = secondes;
		tabSMH[1] = minutes;
		tabSMH[2] = heures;
		return tabSMH;
	}

	public void timeGoesBy()
	{
		secondes++;
		if(secondes >= 60)
		{
			secondes -= 60;
			minutes++;
			if(minutes >= 60)
			{
				minutes -= 60;
				heures++;
			}
		}
		temp++;
	}
	
	public void bonus()
	{
		if(secondes <= 5)
		{
			if(minutes >= 1)
			{
				secondes = 60 - (5 - secondes);
				minutes--;
			}
		}
		else
		{
			secondes -= 5;
		}
		
		
	}
}
