/* **************************************************************
AUTHOR Tamanna Rahman

This program simulates an alien pet which you will look after.
*************************************************************** */
import javax.swing.*; // import the swing library for I/O
import java.util.Random;// import the Random library for the emotional levels
import java.io.*;

class alien
{
	public static void main (String[] param) throws IOException
	{
		PrintWriter outputStream = new PrintWriter(new FileWriter("alienpet.txt"));
		BufferedReader inputStream = new BufferedReader(new FileReader("alienpet.txt"));
		int i = 0;
		int numOfPets = askForPets();
		String[] petName = name(numOfPets);
		outputStream.println(numOfPets + "\n" + petName);
		outputStream.close();
		timePass(numOfPets, petName);//Method looks at the pet's emtional state etc.

		System.exit(0);

	}//END main

	/* **********************************************
	This method will ask for the number of pets the user wants.
	********************************************** */
	public static int askForPets()
	{
		String numPetsText = JOptionPane.showInputDialog("Hello and welcome to the alien pets!\n You will be looking after your own pet or pets by tending to their different needs.\n So let's begin!\n How many pets would you like?");
		int numPets = Integer.parseInt(numPetsText);
		return numPets;
	}

	/* **************************************
	This method will ask for the pet's names.
	************************************** */
	public static String[] name(int numPets)
	{

		String[] petNames = new String[numPets];
		for(int i = 0; i < numPets; i++)
		{
			String name = JOptionPane.showInputDialog("Please name your pet : ");
			petNames[i] = name;

			System.out.println("Welcome to your new home " + petNames[i] + "!");
		}
		return petNames;
	}//END name

	/* ***********************************************************************************
	This method will print a message about the pet's hunger, thirst and irritability level.
	************************************************************************************ */
	public static void timePass(int numPets, String[] petName)
	{
		String ans = "yes";//variable for while loop
		int j = 0;//variable for the days

		while((ans.equalsIgnoreCase("yes")))
		{
			JOptionPane.showMessageDialog(null, "Day " + (j+1));
			final int[] emotionLevel = new int [4];
			final String[] timeOfDay = {"Good morning", "Good afternoon", "Good evening"};
			int[] overall = new int[numPets];
			final String[] emotions = {"hunger", "tiredness", "irritability"};

			for(String time: timeOfDay)
			{
				int emotionState = 0;
				for(int k = 0; k < numPets; k++)
				{
					emotionLevel[k] = emotionalState();
					JOptionPane.showMessageDialog(null, time + "! On a scale of 0-10, " + petName[k] + "'s " + " hunger rates " + emotionLevel[0] +  ", tiredness rates " + emotionLevel[1] +  " and irritability rates " + emotionLevel[2] + ".");
					emotionState = average(emotionLevel);
					JOptionPane.showMessageDialog(null, petName[k] + "'s overall emotional state rates " + emotionState + ".");
					printAnger(petName, emotionState, k);
					overall[k] = emotionState;
				}
				sortAnger(numPets, petName, overall, k);
				pick(numPets, petName, k, emotionState, emotions, emotionLevel);
			}
			ans = JOptionPane.showInputDialog("Do you want to move onto the next day?");
			j++;

		}
	}//END timePass

	/* *********************************************************************************************************
	This method will assign a random number to the different emotional states (hunger, thirst and irritability).
	********************************************************************************************************** */
	public static int emotionalState()
	{
		final int RATE = 10;
		Random level = new Random();
		int Rate = level.nextInt( RATE );
		return Rate;
	}//END emotionalState

	/* *******************************************************************
	This method will give the alien pets overall emotional state by calculating the average.
	******************************************************************* */
	public static int average(int[] emotionLevel)
	{
		int overall = (emotionLevel[0] + emotionLevel[1]  + emotionLevel[2] ) / 3;
		return overall;
	}//END average

	/* *******************************************************************
	This method will check the pet's anger level based on its overall emotion level.
	******************************************************************** */
	public static void printAnger(String[] petName, int emotion, int count)
	{

		if (emotion <= 4)
		{
			JOptionPane.showMessageDialog(null, petName[count] + " is looking dangerous...GET OUT OF THERE!!!");
		}

		else if (emotion <= 6)
		{
			JOptionPane.showMessageDialog(null, petName[count] + " is feeling content.");
		}

		else if (emotion <= 10)
		{
			JOptionPane.showMessageDialog(null, petName[count] + " is happy!");
		}

		return;
	}//END printAnger

	/* **********************************************************************************
	This method will list the pets sorted by anger, allowing the player to choose the order they looked after.
	********************************************************************************** */
	public static void sort(int[] overall, String[] petName)
	{
		boolean sorted=false;
		while (!sorted)
		{
			sorted = true;
			for (int i=0; i < overall.length-1; i++)
			{
				if (overall[i] > overall [i+1])
				{
					// swap them
					int tmp = overall[i+1];
					overall[i+1] = overall[i];
					overall[i] = tmp;

					String temp = petName[i+1];
					petName[i+1] = petName[i];
					petName[i] = temp;
					// array wasn't sorted
					sorted = false;
				}
			}
		}
	}//END sort

	public static void sortAnger (int nPets, String[] petName, int[] overall, int count)
	{
		for (int i=0; i < nPets; i++)
		{
			System.out.print(petName[i] + ": "+ overall[i] + "\n");
		}

		sort(overall, petName);

		for (int i=0; i < nPets; i++)
		{
			System.out.print(petName[i] + ": " + overall[i] + "\n");
		}

	}//END sortAnger

	/********************************************************
	This method will ask the user to choose which pet they want to tend to.
	******************************************************* */
	public static void pick(int nPets, String[] petName, int count, int emotionState, String[] emotions, int[] emotionLevel)
	{
		for(count=0; count < nPets; count++)
		{
			String ask = JOptionPane.showInputDialog("Which pet would you like to tend to?");
			if((ask.equalsIgnoreCase(petName[count])) || (ask.equalsIgnoreCase(petName[count++])))
			{
				reduceAnger(petName, emotions, emotionLevel, emotionState, count);
			}
			else
			{
				return;
			}
		}

	}//END pick

	/* *******************************************************************
	This method will reduce the pet's emotional(hunger, tiredness and irritabilitly) level.
	******************************************************************** */
	public static void reduceAnger(String[] petName, String[] emotions, int[] emotionLevel, int eState, int count)
	{
		String question = "";
		if(eState <= 4)
		{
			question = JOptionPane.showInputDialog("What would you like to do to improve your pets emotional state? Feed, rest, sing or all?");
			if((question.equalsIgnoreCase("Feed")))
			{
				emotionLevel[0] = emotionLevel[0] + 2;
				JOptionPane.showMessageDialog(null, petName[count] + " is feeling better.\n " + petName[count] + "'s " + emotions[0] + " now rates " + emotionLevel[0] + ".");
			}
			else if((question.equalsIgnoreCase("Rest")))
			{
				emotionLevel[1] = emotionLevel[1] + 2;
				JOptionPane.showMessageDialog(null, petName[count] + " is feeling better.\n " + petName[count] + "'s " + emotions[1] + " now rates " + emotionLevel[1] + ".");
			}
			else if((question.equalsIgnoreCase("Sing")))
			{
				emotionLevel[2] = emotionLevel[2] + 2;
				JOptionPane.showMessageDialog(null, petName[count] + " is feeling better. \n" + petName[count] + "'s " + emotions[2] + " now rates " + emotionLevel[2] + ".");
			}
			else if((question.equalsIgnoreCase("All")))
			{
				emotionLevel[0] = 10;
				emotionLevel[1] = 10;
				emotionLevel[2] = 10;
				JOptionPane.showMessageDialog(null, petName[count] + " is feeling better!\n " + petName[count] + "'s " + emotions[0] + " now rates " + emotionLevel[0] + ", " + emotions[1] + " now rates " + emotionLevel[1] + " and " + emotions[2] + " now rates " + emotionLevel[2] + "." );
			}
		}
		else
		{
			return;
		}

		return;
	}//END reduce

}//END alien
