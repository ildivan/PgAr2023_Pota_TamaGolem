package pota;

import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;
import it.kibo.fp.lib.PrettyStrings;
import pota.element.Element;
import pota.element.ElementsBalance;
import pota.error.AttackWithDeadGolemException;
import pota.error.NoMoreGolemsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Battle {

    private final int numberOfElements;
    private final int numberOfGolemsPerTeam;
    private final int numberOfStonesPerGolem;
    private final int numberOfStonesPerElement;
    private final int golemHealth;
    private ElementsBalance balance;
    private final int[] elementStoneStorage;
    private Player firstPlayer;
    private Player secondPlayer;

    /**
     * Constructor for the Battle class.
     * @param firstPlayer The first player of the battle.
     * @param secondPlayer The second player of the battle.
     * @param numberOfElements The number of elements that the battle will use.
     * @param golemHealth The max health of a golem.
     */
    public Battle(Player firstPlayer, Player secondPlayer, int numberOfElements, int golemHealth) {
        this.numberOfElements = numberOfElements;
        this.golemHealth = golemHealth;

        numberOfStonesPerGolem = (int) Math.ceil((numberOfElements+1.0)/3.0) + 1;
        numberOfGolemsPerTeam
                = (int) Math.ceil((numberOfElements-1.0)*(numberOfElements-2.0)/(2.0*numberOfStonesPerGolem));

        numberOfStonesPerElement
                = (int) Math.ceil(2.0*numberOfGolemsPerTeam*numberOfStonesPerGolem/numberOfElements);
        elementStoneStorage = new int[numberOfElements];
        Arrays.fill(elementStoneStorage,numberOfStonesPerElement);

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;


    }

    /**
     * Starts the battle with the parameters passed in the constructor, it can be called any amount of times
     * as it will generate a new balance every time and reset the Team of the two Players, but the battle will have
     * always the same amount of elements and the maximum health of golems will be the same.
     */
    public void start(){
        //Setup of the battle
        balance = ElementsBalance.newRandomBalance(numberOfElements,golemHealth);
        firstPlayer.createNewTeam(numberOfGolemsPerTeam, golemHealth);
        secondPlayer.createNewTeam(numberOfGolemsPerTeam, golemHealth);

        //Starting player is chosen.
        Random random = new Random();
        if(random.nextBoolean()){
            Player temp = firstPlayer;
            firstPlayer = secondPlayer;
            secondPlayer = temp;
        }

        //Summon the first golems of the players.
        summonFirstPlayerGolem();
        summonSecondPlayerGolem();

        //Print initial health of the golems.
        printGolemsHealth();
        try{
            /* Until the golems die, attacks are thrown consecutively and cyclically
               according to the algorithm used to make the elements react with each other */
            while (true) {
                try {
                    TamaMenu.printSeparator();
                    nextAttack();
                    printGolemsHealth();
                } catch (AttackWithDeadGolemException e) {
                    /* When a golem dies, a new one will be summoned.*/
                    if (!firstPlayer.getTeam().getCurrentGolem().isAlive()) {
                        System.out.printf(Literals.DEAD_GOLEM_MESSAGE_MESSAGE,firstPlayer.getName());
                        returnStonesToStorage(firstPlayer.getTeam().getCurrentGolem());
                        //Summon new golem for first player
                        summonFirstPlayerGolem();
                    } else {
                        System.out.printf(Literals.DEAD_GOLEM_MESSAGE_MESSAGE,secondPlayer.getName());
                        returnStonesToStorage(secondPlayer.getTeam().getCurrentGolem());
                        //Summon new golem for second player
                        summonSecondPlayerGolem();
                    }
                }
            }
        }catch(NoMoreGolemsException e){
            /* When all the Golems of a player are dead, the winner is declared. */
            if(!firstPlayer.getTeam().getCurrentGolem().isAlive()){
                TamaMenu.printWinner(secondPlayer);
            }else{
                TamaMenu.printWinner(firstPlayer);
            }
            //Prints the element balance as a matrix.
            System.out.println(Literals.DAMAGE_TABLE);
            TamaMenu.printElementBalance(getBalance());
        }

    }

    //Prints the remaining health of both the player's golem.
    private void printGolemsHealth() {
        System.out.println(Literals.GOLEMS_REMAINING_HEALTH);
        System.out.printf(Literals.GOLEM_HEALTH_MESSAGE,
                firstPlayer.getName(),firstPlayer.getTeam().getCurrentGolem().getHealthPoints(),golemHealth);
        System.out.printf(Literals.GOLEM_HEALTH_MESSAGE,
                secondPlayer.getName(),secondPlayer.getTeam().getCurrentGolem().getHealthPoints(),golemHealth);
    }

    //Calculates next attack, if not possible throws an exception.
    private void nextAttack() {
        TamaGolem firstPlayerCurrentGolem = firstPlayer.getTeam().getCurrentGolem();
        TamaGolem secondPlayerCurrentGolem = secondPlayer.getTeam().getCurrentGolem();

        if(firstPlayerCurrentGolem.isAlive() && secondPlayerCurrentGolem.isAlive()){
            Element firstStone = firstPlayerCurrentGolem.getNextStone();
            Element secondStone = secondPlayerCurrentGolem.getNextStone();

            System.out.printf(Literals.REACTING_STONES_MESSAGE,firstStone,secondStone);

            int damage = balance.getDamage(firstStone,secondStone);

            //It is printed which Golem was attacked and the value of the damage
            if(damage > 0) {
                secondPlayerCurrentGolem.damageGolem(damage);
                System.out.printf(Literals.ATTACK_DAMAGE_MESSAGE,
                        firstPlayer.getName(),damage,secondPlayer.getName());
            }else if(damage < 0) {
                firstPlayerCurrentGolem.damageGolem(-damage);
                System.out.printf(Literals.ATTACK_DAMAGE_MESSAGE,
                        secondPlayer.getName(),-damage,firstPlayer.getName());
            }
            return;
        }
        throw new AttackWithDeadGolemException();
    }

    private void summonFirstPlayerGolem(){
        summonGolem(firstPlayer,secondPlayer);
    }

    private void summonSecondPlayerGolem(){
        summonGolem(secondPlayer,firstPlayer);
    }

    //Summons a golem for the player, checks that the golems of the two players have different stones.
    private void summonGolem(Player summoner, Player other){
        Team summonerTeam = summoner.getTeam();
        Team otherTeam = other.getTeam();

        summonerTeam.nextGolem();
        
        while(true) {
            Element[] stones = retrieveStones(summoner.getName());

            List<Element> summonerStones = Arrays.asList(stones);
            List<Element> otherGolemStones;

            //If the current golem is null it means that it is the first time a golem is summoned in the battle.
            if(otherTeam.getCurrentGolem() != null) {
                otherGolemStones = otherTeam.getCurrentGolem().getElementStones();
            }else{
                otherGolemStones = new ArrayList<>();
            }

            if(!summonerStones.equals(otherGolemStones)) {
                summonerTeam.getCurrentGolem().setElementsStones(stones);
                return;
            }

            System.out.println(Literals.SAME_STONES_ERROR);
            TamaMenu.pauseProgram();
            returnStonesToStorage(stones);
        }
    }

    //Gets the element stones to give to a golem from the user.
    private Element[] retrieveStones(String nameSummoner) {
        do {
            Element[] stones = new Element[numberOfStonesPerGolem];
            for (int i = 0; i <= numberOfStonesPerGolem; i++) {
                Menu.clearConsole();
                System.out.println(PrettyStrings.frame(Literals.BATTLE_MESSAGE,
                        60, true, false));

                System.out.printf(Literals.SELECT_ELEMENTS_MESSAGE,
                                     nameSummoner, numberOfStonesPerGolem);

                printRemainingStones();

                //Displays the stones that the user has already selected.
                System.out.print(Literals.YOU_HAVE_SELECTED);
                for (int j = 0; j < i ; j++) {
                    System.out.printf("%s ", stones[j]);
                }

                //Prompts the user to confirm the elements he selected, eventually lets the user select them again.
                if(i == numberOfStonesPerGolem && InputData.readYesOrNo(Literals.ELEMENT_CONFIRMATION)) {
                    return stones;
                }else{
                    Element chosenElement = retrieveStoneFromStorage();
                    stones[i] = chosenElement;
                    elementStoneStorage[chosenElement.ordinal()]--;
                }
            }

            //returns the previously selected stones before retrieving stones from the beginning.
            returnStonesToStorage(stones);
        } while (true);
    }

    //Displays how many stones are left in the storage for each element.
    private void printRemainingStones() {
        for (int j = 0; j < numberOfElements; j++){
            System.out.printf(Literals.REMAINING_ELEMENT_IN_STORAGE_MESSAGE,
                                Element.elementOfValue(j).toString(), elementStoneStorage[j],
                                numberOfStonesPerElement);
        }
    }

    //Returns an element chosen by the user, checks if the stone wanted is in storage,
    //if not the user has to enter another element.
    private Element retrieveStoneFromStorage() {
        Element chosenElement = readElement();
        while(elementStoneStorage[chosenElement.ordinal()] <= 0){
            System.out.printf(Literals.ELEMENT_STONE_OUT_OF_STORAGE_MESSAGE, chosenElement);
            chosenElement = readElement();
            TamaMenu.pauseProgram();
        }
        return chosenElement;
    }

    //Returns an element chosen by the user, checks if the element exists
    //if not the user has to enter another element.
    private Element readElement() {
        while(true){
            try{
                String elementName
                        = InputData.readNonEmptyString(Literals.INSERT_ELEMENT_NAME, true);
                return Element.elementOfName(elementName);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //It adds the element stones of the golem passed as argument to the stone storage.
    private void returnStonesToStorage(TamaGolem deadGolem) {
        Element[] stones = deadGolem.getElementStones().toArray(Element[]::new);
        returnStonesToStorage(stones);
    }

    //It adds the elements passed as argument to the stone storage.
    private void returnStonesToStorage(Element... stones) {
        for (Element stone : stones) {
            elementStoneStorage[stone.ordinal()]++;
        }
    }

    //Returns the balance as a table of strings with the names of the elements also.
    private String[][] getBalance() {
        String[][] balanceMatrix = new String[numberOfElements+1][numberOfElements+1];
        balanceMatrix[0][0] = "";
        for (int i = 0; i < numberOfElements; i++) {
            balanceMatrix[0][i+1] = Element.elementOfValue(i).toString();
            balanceMatrix[i+1][0] = Element.elementOfValue(i).toString();
        }

        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                Element firstElement = Element.elementOfValue(i);
                Element secondElement = Element.elementOfValue(j);
                balanceMatrix[i+1][j+1] = String.valueOf(balance.getDamage(firstElement,secondElement));
            }
        }
        return balanceMatrix;
    }
}