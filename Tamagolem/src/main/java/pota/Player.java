package pota;

public class Player {
    private String name;
    private Team team;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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
