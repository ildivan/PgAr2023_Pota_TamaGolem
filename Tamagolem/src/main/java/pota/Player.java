package pota;

import it.kibo.fp.lib.AnsiColors;

/**
 * The <strong>Player</strong> class creates a Player that contains a 
 * custom name and stores the Team of golem of a battle.
 */
public class Player {
    private String name;
    private Team team;

    /**
     * Constructor that creates a <code>Player</code> object specifying 
     * the custom name.
     * @param name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Retrieves the Player's name and adds an ANSI code to color it blue.
     * @return A String representing the name of the Player.
     */
    public String getName() {
        return AnsiColors.BLUE + this.name + AnsiColors.RESET;
    }

    /**
     * Sets the Player's name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Player's current team.
     * @return A Team object.
     */
    public Team getTeam() {
        return this.team;
    }

    /**
     * Creates a new Team object and assigns it to the player.
     * @param numberOfGolems
     * @param golemHealthPoints
     */
    public void createNewTeam(int numberOfGolems, int golemHealthPoints) {
        team = new Team(numberOfGolems, golemHealthPoints);
    }
}