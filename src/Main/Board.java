package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Board {

	/* ArrayList containing words read from GameWord.txt */
	private ArrayList<String> gamewordslist;

	/* containing 25 distinct codenames selected at random; */
	private ArrayList<Person> codenamelist;

	/*
	 * list of codename's identity (RedAgent,BlueAgent,InnocentBystander,Assassin)
	 */
	private ArrayList<String> identity;

	/* list of 25 locations */
	private ArrayList<Location> location;

	/* Location chosen by the current team */
	private Location LocationChosen;

	/* determine which team is taking the turn */
	private int currentteam;

	/* Number of locations are not revealed */
	private int NumberOfLocationsLeft;

	/* Number of Red Agents are revealed */
	private int RedAgentCount;

	/* Number of Blue Agents are revealed */
	private int BlueAgentCount;

	/* list of locations that belongs to Red Team */
	private ArrayList<Location> RedTeam;

	/* list of locations that belongs to Blue Team */
	private ArrayList<Location> BlueTeam;

	/* clue */
	private String clue;

	/* Number of location related to Clue */
	private int count;

	public Board() {

	}

	/*
	 * Starts the game creating the locations and the randamized lists reading from
	 * the file and initializing variables.
	 * 
	 * @version 1.2
	 * 
	 * @since 2018-03-19
	 */
	public void GameStart() {
		// Change the filename in readFile if it cannot read.
		readFile("src/Main/codenames.txt");
		this.RedTeam = new ArrayList<>();
		this.BlueTeam = new ArrayList<>();
		this.location = new ArrayList<>();
		this.RedAgentCount = 0;
		this.BlueAgentCount = 0;
		this.currentteam = 0;
		this.NumberOfLocationsLeft = 25;
		this.clue = " ";
		this.count = 0;

		// generate list of Identity
		GenerateListOfIdentity();

		// Mix list of identity and codenamelist
		Collections.shuffle(gamewordslist);

		// add 25 codenames from gamewordslist to codenamelist
		getRandomList(gamewordslist);
        
		// add locations to list of locations
		for(int i = 0; i < 25; i++) {
			Location newloc = new Location(codenamelist.get(i));
			this.location.add(newloc);
		}
	}

	/*
	 * Successfully reads the text file.
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	public void readFile(String filename) {
		this.gamewordslist = new ArrayList<>();
		try {
			Scanner s = new Scanner(new File(filename));
			while (s.hasNextLine()) {

				this.gamewordslist.add(s.nextLine());
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Randamizes the list of people and adds them to the codenamelist.
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	public void getRandomList(ArrayList<String> names) {
		this.codenamelist = new ArrayList<Person>();
		Random r = new Random();
		int generator;
		for (int i = 0; i < 25; i++) {
			generator = r.nextInt(names.size());
			Person person = new Person(names.get(generator), identity.get(i), false);
			this.codenamelist.add(person);
		}
	}

	/*
	 * Creates List containing randomly generated assignments for each of the 9 Red
	 * Agents, 8 Blue Agents, 7 Innocent Bystanders, and 1 Assassin [10 points]
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	public void GenerateListOfIdentity() {
		this.identity = new ArrayList<>();
		this.identity.add("Assassin");
		int i = 0;
		while(i < 24) {
			if(i >= 0 && i < 9 ) {
				this.identity.add("RedAgent");
			}
			if(i >=9 && i < 17 ) {
				this.identity.add("BlueAgent");
			}
			if(i >=17) {
				this.identity.add("InnocentBystander");

			}
			i++;
		}
		Collections.shuffle(identity);
	}

	/*
	 * This is our turn taking method.
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	public void TakingTurn() {
		if (CheckIfClueIsLegal()) {
			if (currentteam == 1) {
				currentteam = 0;
			} else {
				currentteam = 1;
			}
		}

		else if (!CheckIfClueIsLegal()) {
			if (currentteam == 1) {
				currentteam = 1;
			} else if (currentteam == 0) {
				currentteam = 0;
			}
		}
	}

	public boolean CheckIfClueIsLegal() {
		for (int i = 0; i < location.size(); i++) {
			if (location.get(i).getPersonfromLocation().getPersonName().toUpperCase().equals(this.clue.replaceAll("\\s","").toUpperCase())) {
				if (location.get(i).getPersonfromLocation().CheckIfItIsRevealed()) {
					return true;
				} else if (!location.get(i).getPersonfromLocation().CheckIfItIsRevealed()) {
					return false;
				}
			}
		}
		return true;

	}

	/*
	 * Method checks if the clue is the space that was just selected and if the name
	 * is a match.
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	public boolean CheckIfLocationContainsCurrentTeamAgent() {
		if (LocationChosen.getPersonfromLocation().CheckIfItIsRevealed()) {
			this.NumberOfLocationsLeft--;
			if (currentteam == 0) {
				if (RedTeam.contains(LocationChosen)
						&& LocationChosen.getPersonfromLocation().getIdentity().equals("RedAgent")) {
					return true;
				}
			}
			if (currentteam == 1) {
				if (BlueTeam.contains(LocationChosen)
						&& LocationChosen.getPersonfromLocation().getIdentity().equals("BlueAgent")) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * Method checks if the location selected holds the current teams agent and
	 * decreases the number of locations left.
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	// Method defined which correctly returns whether or not the Board is in one of
	// the winning states [15 points]
	public boolean CheckWinningState() {
		// 9 Red Agents = win
		// 8 Blue Agents = win
		// One of team got "Assassin" = another team wins
		// If WhichTeamWins() doesn't return "Neither", it means that one of two teams
		// wins
		if (RedAgentCount == 9 || BlueAgentCount == 8 || !WhichTeamWins().equals("Neither")) {
			return true;
		}
		// Board is not in one of the winning states
		return false;
	}

	/*
	 * Method defined which correctly returns whether or not the Board is in one of
	 * the winning states [15 points]
	 * 
	 * @version 1.0.2
	 * 
	 * @since 2018-02-26
	 */
	// Method defined which correctly returns which team did not lose (i.e., win)
	// when the Assassin was revealed [10 points]
	public String WhichTeamWins() {
		// If Red Team got "Assassin", Blue Team wins
		if (currentteam == 0 && LocationChosen.getPersonfromLocation().getIdentity().equals("Assassin")) {
			return "Blue Team";
		}
		// If Blue Team got "Assassin", Red Team wins
		if (currentteam == 1 && LocationChosen.getPersonfromLocation().getIdentity().equals("Assassin")) {
			return "Red Team";
		}
		return "Neither";
	}

	public ArrayList<String> getList() {
		return this.gamewordslist;
	}

	public ArrayList<Person> getlistof25CodeNames() {
		return this.codenamelist;
	}

	public ArrayList<Location> getListofLocation() {
		return this.location;
	}

	public ArrayList<String> getListofIdentity() {
		return this.identity;
	}

	public ArrayList<Location> getRedTeam() {
		return this.RedTeam;
	}

	public ArrayList<Location> getBlueTeam() {
		return this.BlueTeam;
	}

	public Location getLocationChosen() {
		return this.LocationChosen;
	}

	public void setLocationChosen(Location loc) {
		this.LocationChosen = loc;
	}

	public int getCurrentTeam() {
		return this.currentteam;
	}

	public void setCurrentTeam(int t) {
		this.currentteam = t;
	}

	public int getNumberOfLocationLeft() {
		return this.NumberOfLocationsLeft;
	}

	public void setNumberOfLocationLeft(int n) {
		this.NumberOfLocationsLeft = n;
	}

	public int getRedAgentCount() {
		return RedTeam.size();
	}

	public int getBlueAgentCount() {
		return BlueTeam.size();
	}

	public void setRedAgentCount(int r) {
		this.RedAgentCount = r;
	}

	public void setBlueAgentCount(int b) {
		this.BlueAgentCount = b;
	}

	public ArrayList<Location> getLocationsOfRedTeam() {
		return this.RedTeam;
	}

	public ArrayList<Location> getLocationOfBlueTeam() {
		return this.BlueTeam;
	}

	public String getClue() {
		return this.clue;
	}

	public int getCount() {
		return this.count;
	}

	public void setClue(String c) {
		this.clue = c;
	}

	public void setCount(int c) {
		this.count = c;
	}
}
