package com.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileLogListener extends Thread
{
	FileInputStream inputStream;
	File file = new File(File.separator+"dev"+File.separator+"log"+File.separator+"main");
	byte[] buf = new byte[4000];
	int readByte = 0;
	int rLength = 0;

	public void run()
	{
		if (inputStream == null)
		{
			return;
		}
		try
		{
			inputStream.skip(readByte);
			// while ((rLength = inputStream.read(buf)) != -1)
			// {
			//                 
			// }
			rLength = inputStream.read(buf);
			System.out.println(new String(buf));
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			close();
		}

	}

	public FileLogListener()
	{
		try
		{
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close()
	{
		if (inputStream != null)
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
