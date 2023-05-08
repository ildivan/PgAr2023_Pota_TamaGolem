package pota;

import pota.element.Element;
import pota.element.ElementsBalance;
import pota.error.AttackWithDeadGolemException;
import pota.error.NoMoreGolemsException;
import pota.error.SameElementStonesException;

import java.util.Arrays;
import java.util.List;

public class Battle {
    private ElementsBalance balance;
    private int[] elementStoneStorage;

    public Battle(Player player1, Player player2, int numberOfElements, int golemHealth) {
        balance = ElementsBalance.newRandomBalance(numberOfElements,golemHealth);
        int numberOfStonesPerGolem = (int) Math.ceil((numberOfElements+1.0)/3.0) + 1;
        int numberOfGolemsPerTeam
                = (int) Math.ceil((numberOfElements-1.0)*(numberOfElements-2.0)/(2.0*numberOfStonesPerGolem));

        int  numberOfStonesPerElement = (int) Math.ceil(2.0*numberOfGolemsPerTeam*numberOfStonesPerGolem/numberOfElements);
        elementStoneStorage = new int[numberOfElements];
        Arrays.fill(elementStoneStorage,numberOfStonesPerElement);

        player1.createNewTeam(numberOfGolemsPerTeam, golemHealth);
        player2.createNewTeam(numberOfGolemsPerTeam, golemHealth);
        player1.getTeam().nextGolem();
        player2.getTeam().nextGolem();
    }

    public void start(Player player1, Player player2){
        summonFirstPlayerGolem(player1, player2, Element.FIRE);
        summonSecondPlayerGolem(player1, player2, Element.WATER);
        try{
            while (true) {
                try {
                    nextAttack(player1, player2);
                } catch (AttackWithDeadGolemException e) {
                    if (!player1.getTeam().getCurrentGolem().isAlive()) {
                        returnStonesToStorage(player1.getTeam().getCurrentGolem());
                        summonFirstPlayerGolem(player1, player2, Element.WATER);
                    } else {
                        returnStonesToStorage(player2.getTeam().getCurrentGolem());
                        summonSecondPlayerGolem(player1, player2, Element.CHAOS);
                    }
                }
            }
        }catch(NoMoreGolemsException e){
            if(!player1.getTeam().getCurrentGolem().isAlive()){
                System.out.println("Second player wins!");
            }else{
                System.out.println("First player wins!");
            }
        }
    }

    private void returnStonesToStorage(TamaGolem currentGolem) {
        List<Element> stones = currentGolem.getElementStones();
        for (Element stone : stones) {
            elementStoneStorage[stone.ordinal()]++;
        }
    }

    public void nextAttack(Player player1, Player player2) {
        TamaGolem firstPlayerCurrentGolem = player1.getTeam().getCurrentGolem();
        TamaGolem secondPlayerCurrentGolem = player2.getTeam().getCurrentGolem();

        if(firstPlayerCurrentGolem.isAlive() && secondPlayerCurrentGolem.isAlive()){
            Element firstStone = firstPlayerCurrentGolem.getNextStone();
            Element secondStone = secondPlayerCurrentGolem.getNextStone();


            int damage = balance.getDamage(firstStone,secondStone);
            if(damage > 0) {
                secondPlayerCurrentGolem.damageGolem(damage);
            }else if(damage < 0) {
                firstPlayerCurrentGolem.damageGolem(-damage);
            }
        }
        throw new AttackWithDeadGolemException();
    }

    public void summonFirstPlayerGolem(Player player1, Player player2, Element... stones){
        summonGolem(player1.getTeam(),player2.getTeam(),stones);
    }

    public void summonSecondPlayerGolem(Player player1, Player player2, Element... stones){
        summonGolem(player2.getTeam(),player1.getTeam(),stones);
    }

    private void summonGolem(Team summonerPlayerTeam, Team otherPlayerTeam,Element... stones){
        List<Element> firstPlayerStones = Arrays.asList(stones);
        List<Element> secondPlayerStones = otherPlayerTeam.getCurrentGolem().getElementStones();

        if(!firstPlayerStones.equals(secondPlayerStones)) {
            summonerPlayerTeam.nextGolem();
            summonerPlayerTeam.getCurrentGolem().setElementsStones(stones);
            return;
        }
        throw new SameElementStonesException();
    }
}