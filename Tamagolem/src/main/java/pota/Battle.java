package pota;

import it.kibo.fp.lib.InputData;
import pota.element.Element;
import pota.element.ElementsBalance;
import pota.error.AttackWithDeadGolemException;
import pota.error.NoMoreGolemsException;
import pota.error.SameElementStonesException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Battle {

    private final int numberOfElements;
    private final int numberOfStonesPerGolem;
    private final ElementsBalance balance;
    private final int[] elementStoneStorage;
    private Player firstPlayer;
    private Player secondPlayer;

    public Battle(Player firstPlayer, Player secondPlayer, int numberOfElements, int golemHealth) {
        this.numberOfElements = numberOfElements;
    //The maximum number of items chosen by the user is 10
    //in case of lower quantity they are considered up to the desired number in the order they are marked in the class "Element"

        balance = ElementsBalance.newRandomBalance(numberOfElements,golemHealth);
        numberOfStonesPerGolem = (int) Math.ceil((numberOfElements+1.0)/3.0) + 1;
        int numberOfGolemsPerTeam
                = (int) Math.ceil((numberOfElements-1.0)*(numberOfElements-2.0)/(2.0*numberOfStonesPerGolem));

        int numberOfStonesPerElement
                = (int) Math.ceil(2.0*numberOfGolemsPerTeam*numberOfStonesPerGolem/numberOfElements);
        elementStoneStorage = new int[numberOfElements];
        Arrays.fill(elementStoneStorage,numberOfStonesPerElement);

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        firstPlayer.createNewTeam(numberOfGolemsPerTeam, golemHealth);
        secondPlayer.createNewTeam(numberOfGolemsPerTeam, golemHealth);
        firstPlayer.getTeam().nextGolem();
        secondPlayer.getTeam().nextGolem();
    }

    public void start(){

        Random random = new Random();
        if(random.nextBoolean()){
            Player temp = firstPlayer;
            firstPlayer = secondPlayer;
            secondPlayer = temp;
        }
        System.out.printf("%s starts!\n",firstPlayer.getName());

        summonFirstPlayerGolem();
        summonSecondPlayerGolem();
        try{
            while (true) {
                try {
                    nextAttack();
                } catch (AttackWithDeadGolemException e) {
                    if (!firstPlayer.getTeam().getCurrentGolem().isAlive()) {
                        returnStonesToStorage(firstPlayer.getTeam().getCurrentGolem());
                        summonFirstPlayerGolem();
                    } else {
                        returnStonesToStorage(secondPlayer.getTeam().getCurrentGolem());
                        summonSecondPlayerGolem();
                    }
                }
            }
        }catch(NoMoreGolemsException e){
            if(!firstPlayer.getTeam().getCurrentGolem().isAlive()){
                System.out.println("Second player wins!");
            }else{
                System.out.println("First player wins!");
            }
        }
        /* Play is continued by reducing health points and moving on to the next Golem based on attacks suffered.
    *Through the elimination of all of a player's Golems, the winner is declared. */
    }

    private void nextAttack() {
        TamaGolem firstPlayerCurrentGolem = firstPlayer.getTeam().getCurrentGolem();
        TamaGolem secondPlayerCurrentGolem = secondPlayer.getTeam().getCurrentGolem();

        if(firstPlayerCurrentGolem.isAlive() && secondPlayerCurrentGolem.isAlive()){
            Element firstStone = firstPlayerCurrentGolem.getNextStone();
            Element secondStone = secondPlayerCurrentGolem.getNextStone();

            System.out.printf("Le pietre %s e %s stanno reagendo!\n",firstStone,secondStone);

            /* Until the golems die, attacks are thrown consecutively and cyclically
            * according to the algorithm used to make the elements react with each other */

            int damage = balance.getDamage(firstStone,secondStone);
            if(damage > 0) {
                secondPlayerCurrentGolem.damageGolem(damage);
                System.out.printf("Il golem di %s fa danno %d al golem di %s\n",firstPlayer.getName(),damage,secondPlayer.getName());
            }else if(damage < 0) {
                firstPlayerCurrentGolem.damageGolem(-damage);
                System.out.printf("Il golem di %s fa danno %d al golem di %s\n",secondPlayer.getName(),damage,firstPlayer.getName());
            }
            // Each turn it is printed which Golem was attacked and the value of the damage
        }
        throw new AttackWithDeadGolemException();
    }

    private void summonFirstPlayerGolem(){
        summonGolem(firstPlayer.getTeam(),secondPlayer.getTeam());
    }

    private void summonSecondPlayerGolem(){
        summonGolem(secondPlayer.getTeam(),firstPlayer.getTeam());
    }

    private void summonGolem(Team summonerTeam, Team otherTeam){
        Element[] stones = retrieveStones();

        List<Element> firstPlayerStones = Arrays.asList(stones);
        List<Element> secondPlayerStones = otherTeam.getCurrentGolem().getElementStones();

        if(!firstPlayerStones.equals(secondPlayerStones)) {
            summonerTeam.nextGolem();
            summonerTeam.getCurrentGolem().setElementsStones(stones);
            return;
        }
        throw new SameElementStonesException();
    }

    private Element[] retrieveStones() {
        Element[] stones = new Element[numberOfStonesPerGolem];

        System.out.printf("Prendi %d pietre degli elementi dalle rimanenti:\n",numberOfStonesPerGolem);
        for (int i = 0; i < numberOfElements; i++){
            System.out.printf("- %s (rimanenti %d)\n",Element.elementOfValue(i).name(), elementStoneStorage[i]);
        }
        for (int i = 0; i < numberOfStonesPerGolem; i++) {
            Element chosenElement;
            do{
                chosenElement = readElement();
            }while(elementStoneStorage[chosenElement.ordinal()] <= 0);
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

    public String[][] getBalance() {
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