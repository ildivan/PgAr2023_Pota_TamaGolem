package pota;

import it.kibo.fp.lib.AnsiColors;

/* In the class are the basic characteristics of each player,
 * while the Golem team is delineated in an additional class of its own.
 */
public class Player {
    private String name;
    private Team team;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return AnsiColors.BLUE + this.name + AnsiColors.RESET;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return this.team;
    }

    public void createNewTeam(int numberOfGolems, int golemHealthPoints) {
        team = new Team(numberOfGolems, golemHealthPoints);
    }
}