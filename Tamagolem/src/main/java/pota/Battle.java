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
    private Player firstPlayer;
    private Player secondPlayer;

    public Battle(int numberOfElements, int golemHealth) {
        balance = ElementsBalance.newRandomBalance(numberOfElements,golemHealth);
        int numberOfStonesPerGolem = (int) Math.ceil((numberOfElements+1.0)/3.0) + 1;
        int numberOfGolemsPerTeam
                = (int) Math.ceil((numberOfElements-1.0)*(numberOfElements-2.0)/(2.0*numberOfStonesPerGolem));

        int  numberOfStonesPerElement = (int) Math.ceil(2.0*numberOfGolemsPerTeam*numberOfStonesPerGolem/numberOfElements);
        elementStoneStorage = new int[numberOfElements];
        Arrays.fill(elementStoneStorage,numberOfStonesPerElement);

        firstPlayer = new Player(numberOfGolemsPerTeam,golemHealth);
        secondPlayer = new Player(numberOfGolemsPerTeam,golemHealth);
        firstPlayer.nextGolem();
        secondPlayer.nextGolem();
    }

    public void start(){
        summonFirstPlayerGolem(Element.FIRE);
        summonSecondPlayerGolem(Element.WATER);
        try{
            while (true) {
                try {
                    nextAttack();
                } catch (AttackWithDeadGolemException e) {
                    if (!firstPlayer.getCurrentGolem().isAlive()) {
                        returnStonesToStorage(firstPlayer.getCurrentGolem());
                        summonFirstPlayerGolem(Element.WATER);
                    } else {
                        returnStonesToStorage(secondPlayer.getCurrentGolem());
                        summonSecondPlayerGolem(Element.CHAOS);
                    }
                }
            }
        }catch(NoMoreGolemsException e){
            if(!firstPlayer.getCurrentGolem().isAlive()){
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

    public void nextAttack() {
        if(firstPlayer.getCurrentGolem().isAlive() && secondPlayer.getCurrentGolem().isAlive()){
            Element firstStone = firstPlayer.getCurrentGolem().getNextStone();
            Element secondStone = secondPlayer.getCurrentGolem().getNextStone();

            int damage = balance.getDamage(firstStone,secondStone);
            if(damage > 0) {
                secondPlayer.getCurrentGolem().damageGolem(damage);
            }else if(damage < 0) {
                firstPlayer.getCurrentGolem().damageGolem(-damage);
            }
        }
        throw new AttackWithDeadGolemException();
    }

    public void summonFirstPlayerGolem(Element... stones){
        summonGolem(firstPlayer,secondPlayer,stones);
    }

    public void summonSecondPlayerGolem(Element... stones){
        summonGolem(secondPlayer,firstPlayer,stones);
    }

    private void summonGolem(Player playerThatSummons, Player otherPlayer,Element... stones){
        List<Element> firstPlayerStones = Arrays.asList(stones);
        List<Element> secondPlayerStones = otherPlayer.getCurrentGolem().getElementStones();

        if(!firstPlayerStones.equals(secondPlayerStones)) {
            playerThatSummons.nextGolem();
            playerThatSummons.getCurrentGolem().setElementsStones(stones);
            return;
        }
        throw new SameElementStonesException();
    }
}
