package pota;

import pota.element.Element;
import pota.element.ElementsBalance;
import pota.error.AttackWithDeadGolemException;

public class Battle {
    private ElementsBalance balance;
    private Player firstPlayer;
    private Player secondPlayer;

    public Battle(int numberOfElements, int golemHealth) {
        balance = ElementsBalance.newRandomBalance(numberOfElements,golemHealth);

        int numberOfStonesPerGolem = (int) Math.ceil((numberOfElements+1.0)/3.0) + 1;
        int numberOfGolemsPerTeam
                = (int) Math.ceil((numberOfElements-1.0)*(numberOfElements-2.0)/(2.0*numberOfStonesPerGolem));
        firstPlayer = new Player(numberOfGolemsPerTeam,golemHealth);
        secondPlayer = new Player(numberOfGolemsPerTeam,golemHealth);
        firstPlayer.nextGolem();
        firstPlayer.nextGolem();
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
        firstPlayer.nextGolem();
        firstPlayer.getCurrentGolem().setElementsStones(stones);
    }
}
