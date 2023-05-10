package pota;

import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
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
    private final int golemHealth;
    private final ElementsBalance balance;
    private final int[] elementStoneStorage;
    private Player firstPlayer;
    private Player secondPlayer;

    public Battle(Player firstPlayer, Player secondPlayer, int numberOfElements, int golemHealth) {
        this.numberOfElements = numberOfElements;
        this.golemHealth = golemHealth;
    //The maximum number of items chosen by the user is 10
    //in case of lower quantity they are considered up to the desired number in the order they are marked in the class "Element"

        balance = ElementsBalance.newRandomBalance(numberOfElements,golemHealth);
        numberOfStonesPerGolem = (int) Math.ceil((numberOfElements+1.0)/3.0) + 1;
        numberOfGolemsPerTeam
                = (int) Math.ceil((numberOfElements-1.0)*(numberOfElements-2.0)/(2.0*numberOfStonesPerGolem));

        int numberOfStonesPerElement
                = (int) Math.ceil(2.0*numberOfGolemsPerTeam*numberOfStonesPerGolem/numberOfElements);
        elementStoneStorage = new int[numberOfElements];
        Arrays.fill(elementStoneStorage,numberOfStonesPerElement);

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;


    }

    public void start(){
        firstPlayer.createNewTeam(numberOfGolemsPerTeam, golemHealth);
        secondPlayer.createNewTeam(numberOfGolemsPerTeam, golemHealth);

        Random random = new Random();
        if(random.nextBoolean()){
            Player temp = firstPlayer;
            firstPlayer = secondPlayer;
            secondPlayer = temp;
        }

        summonFirstPlayerGolem();
        summonSecondPlayerGolem();
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
                        System.out.printf("Il golem di %s è morto!\n",firstPlayer.getName());
                        returnStonesToStorage(firstPlayer.getTeam().getCurrentGolem());
                        summonFirstPlayerGolem();
                    } else {
                        System.out.printf("Il golem di %s è morto!\n",secondPlayer.getName());
                        returnStonesToStorage(secondPlayer.getTeam().getCurrentGolem());
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
            System.out.println("\n\nTABELLA DEI DANNI: ");
            TamaMenu.printElementBalance(getBalance());
        }

    }

    private void printGolemsHealth() {
        System.out.println("Salute rimanente dei golem: ");
        System.out.printf("Golem di %s: %d/%d\n",
                firstPlayer.getName(),firstPlayer.getTeam().getCurrentGolem().getHealthPoints(),golemHealth);
        System.out.printf("Golem di %s: %d/%d\n",
                secondPlayer.getName(),secondPlayer.getTeam().getCurrentGolem().getHealthPoints(),golemHealth);
    }

    private void nextAttack() {
        TamaGolem firstPlayerCurrentGolem = firstPlayer.getTeam().getCurrentGolem();
        TamaGolem secondPlayerCurrentGolem = secondPlayer.getTeam().getCurrentGolem();

        if(firstPlayerCurrentGolem.isAlive() && secondPlayerCurrentGolem.isAlive()){
            Element firstStone = firstPlayerCurrentGolem.getNextStone();
            Element secondStone = secondPlayerCurrentGolem.getNextStone();

            System.out.printf("Le pietre %s e %s stanno reagendo!\n",firstStone,secondStone);

            int damage = balance.getDamage(firstStone,secondStone);
            if(damage > 0) {
                secondPlayerCurrentGolem.damageGolem(damage);
                System.out.printf("Il golem di %s fa danno %d al golem di %s\n",
                        firstPlayer.getName(),damage,secondPlayer.getName());
            }else if(damage < 0) {
                firstPlayerCurrentGolem.damageGolem(-damage);
                System.out.printf("Il golem di %s fa danno %d al golem di %s\n",
                        secondPlayer.getName(),-damage,firstPlayer.getName());
            }
            return;
            // Each turn it is printed which Golem was attacked and the value of the damage
        }
        throw new AttackWithDeadGolemException();
    }

    private void summonFirstPlayerGolem(){
        summonGolem(firstPlayer,secondPlayer);
    }

    private void summonSecondPlayerGolem(){
        summonGolem(secondPlayer,firstPlayer);
    }

    private void summonGolem(Player summoner, Player other){
        Team summonerTeam = summoner.getTeam();
        Team otherTeam = other.getTeam();

        summonerTeam.nextGolem();
        
        while(true) {
            System.out.printf("%s",summoner.getName());
            Element[] stones = retrieveStones();

            List<Element> summonerStones = Arrays.asList(stones);
            List<Element> otherGolemStones;
            if(otherTeam.getCurrentGolem() != null) {
                otherGolemStones = otherTeam.getCurrentGolem().getElementStones();
            }else{
                otherGolemStones = new ArrayList<>();
            }

            if(!summonerStones.equals(otherGolemStones)) {
                summonerTeam.getCurrentGolem().setElementsStones(stones);
                return;
            }
            System.out.println(AnsiColors.RED + "I due Golem non possono avere le stesse pietre!" + AnsiColors.RESET);
            returnStonesToStorage(stones);
        }
    }

    private Element[] retrieveStones() {
        Element[] stones = new Element[numberOfStonesPerGolem];

        System.out.printf(", prendi %d pietre degli elementi dalle rimanenti:\n",numberOfStonesPerGolem);
        for (int i = 0; i < numberOfElements; i++){
            System.out.printf("- %s (rimanenti %d)\n",Element.elementOfValue(i).toString(), elementStoneStorage[i]);
        }
        for (int i = 0; i < numberOfStonesPerGolem; i++) {
            Element chosenElement= readElement();
            while(elementStoneStorage[chosenElement.ordinal()] <= 0){
                System.out.printf("Le pietre di tipo %s sono finite!\n", chosenElement);
                chosenElement = readElement();
            }
            stones[i] = chosenElement;
            elementStoneStorage[chosenElement.ordinal()]--;
        }
        return stones;
    }

    private Element readElement() {
        while(true){
            try{
                String elementName = InputData.readNonEmptyString("Inserisci il nome di un elemento: ",true);
                return Element.elementOfName(elementName);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void returnStonesToStorage(TamaGolem deadGolem) {
        List<Element> stones = deadGolem.getElementStones();
        for (Element stone : stones) {
            elementStoneStorage[stone.ordinal()]++;
        }
    }

    private void returnStonesToStorage(Element... stones) {
        for (Element stone : stones) {
            elementStoneStorage[stone.ordinal()]++;
        }
    }

    private String[][] getBalance() {
        String[][] balanceMatrix = new String[numberOfElements+1][numberOfElements+1];
        balanceMatrix[0][0] = "";
        for (int i = 0; i < numberOfElements; i++) {
            balanceMatrix[0][i+1] = Element.elementOfValue(i).name();
            balanceMatrix[i+1][0] = Element.elementOfValue(i).name();
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