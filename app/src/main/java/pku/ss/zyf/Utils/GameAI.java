package pku.ss.zyf.Utils;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pku.ss.zyf.bean.Card;
import pku.ss.zyf.bean.Movement;
import pku.ss.zyf.mygame_2q48.GamePlay;
import pku.ss.zyf.mygame_2q48.MainActivity;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-05-05
 * Time: 00:03
 */
public class GameAI{

    private GamePlay aiGamePlay = new GamePlay();
    private List<Integer> transPosition = new ArrayList<>();   //换牌手牌位置
    private List<Integer> chargePosition = new ArrayList<>();  //钓牌手牌位置
    private List<Card> bottomCards = new ArrayList<>();
    int [] aiHoldValue;
    private String transValue, chargeValue;
    private boolean chargeGood;
    private MainActivity mainActivity = (MainActivity) MainActivity.getMyActivity();
    private Movement movement;

    public int aiThink(GamePlay gamePlay){
        this.aiGamePlay = gamePlay;
        this.bottomCards = gamePlay.getBottom();

        //做决策
        int move = ai_Decision();

        //行动
        transformOrCharge(move);

        if (move == 1){
            //若完成了换牌
            aiGamePlay.setAiMove("换牌，换到了一张" + transValue + ": ,摸了一张" +movement.getDrawCardValues().get(0));
            aiGamePlay.recordMove(1, movement);
            aiGamePlay.recordHoldSeq(); //记录手牌
        }
        else{
            //若完成了钓牌
            if (chargeGood){
                //钓牌成功
                aiGamePlay.setAiMove("钓牌成功，钓到一张" + chargeValue + ",抓两张牌:" + movement.getDrawCardValues().get(0) + "," + movement.getDrawCardValues().get(1));
                aiGamePlay.recordMove(1, movement);
                }else {
                //钓牌失败
                aiGamePlay.setAiMove("钓牌失败，钓到一张" + chargeValue + ",抓两张牌:" + movement.getDrawCardValues().get(0) + "," + movement.getDrawCardValues().get(1));
                aiGamePlay.recordMove(1, movement);
            }
            aiGamePlay.recordHoldSeq(); //记录手牌
        }
        return 1;
    }

    /**
     * 换牌或钓牌逻辑
     * @param flag 标记
     */
    private void transformOrCharge(int flag){
        int [] aiHoldValue;
        //若换牌，手牌一定有5张
        movement = new Movement();
        movement.setMoveName(1);
        if (flag == 1){
            //抓一张牌
            Card card = bottomCards.remove(0);
            aiGamePlay.setBottom(bottomCards);
//            Log.d("TEST","bottomTop is : " + card.getValue());
            movement.setDrawCardValues(card.getValue());
            List<Card> aiHold = aiGamePlay.getAiHold();
            aiHold.add(card);
            //更新手牌
            aiGamePlay.setAiHold(aiHold);
            aiHoldValue = aiGamePlay.getAiHoldValue();
            List<String> btnTitle = new ArrayList<>();
            transPosition = new ArrayList<>();
            int i = 0;
            int j = 0;
            while(i < (aiHoldValue.length - 1)){
                if (aiHoldValue[i] == aiHoldValue[i+1]){
                    if (btnTitle.size() == 0){
                        transPosition.add(j++,i); //记录换牌位置
                        if (aiHoldValue[i] == 8){
                            btnTitle.add("换个Q");
                        }else if (aiHoldValue[i] == 16)
                            btnTitle.add("换个2");
                        else
                            btnTitle.add("换个" + aiHoldValue[i] * 2);
                    }
                    else if (aiHoldValue[i] != aiHoldValue[i-1]){
                        transPosition.add(j++, i); //记录换牌位置
                        if (aiHoldValue[i] == 8){
                            btnTitle.add("换个Q");
                        }else if (aiHoldValue[i] == 16)
                            btnTitle.add("换个2");
                        else
                            btnTitle.add("换个" + aiHoldValue[i] * 2);
                    }
                }
                i++;
            }//endWhile;

            //更新手牌
            aiHold = aiGamePlay.getAiHold();
            //若可以换牌
            if (transPosition.size() > 0){
                Card tempCard;
                if (aiHold.get(transPosition.get(0)).getValue() == 16)
                    tempCard = new Card(2); //新增一张高一级的牌
                else
                    tempCard = new Card(aiHold.get(transPosition.get(0)).getValue() * 2); //新增一张高一级的牌
                aiHold.remove(transPosition.get(0) + 1);    //移除两张手牌
                aiHold.remove(transPosition.get(0).intValue());
                aiHold.add(tempCard);
                movement.setCardValue(tempCard.getValue());
                if (tempCard.getValue() > 8)
                    transValue = "Q";
                else
                    transValue = String.valueOf(tempCard.getValue());
        }
            aiGamePlay.setAiHold(aiHold);
            movement.setCurrentHold(aiGamePlay.getAiHold());
        }

        //若钓牌
        if (flag == 2){
            movement = new Movement();
            movement.setMoveName(2);    //钓牌
            List<Card> aiHold = aiGamePlay.getAiHold();
            List<Card> myHold = aiGamePlay.getMyHold();
            int i = 0;
            while (myHold.get(i).getValue() != aiHold.get(1).getValue() * 2){
                i++;
                if (i == 4)
                    break;
            }
            if (i < 4){
                //钓牌成功
                movement.setResult(1);  //成功
                chargeGood = true;
                Card card = myHold.remove(i);   //Player移除被钓牌
                myHold.add(new Card(2));        //Player拿一张2
                aiGamePlay.setMyHold(myHold);         //更新Player手牌
                aiHold.remove(chargePosition.get(0) + 2);    //移除三张手牌
                aiHold.remove(chargePosition.get(0) + 1);
                aiHold.remove(chargePosition.get(0).intValue());
                aiHold.add(card);   //钓到的牌加入手牌
                List<Card> tempCards = new ArrayList<>(mainActivity.getBottomTop(2));
                movement.setCardValue(card.getValue());     //钓到的牌
                movement.setDrawCardValues(tempCards.get(0).getValue());    //摸到的牌
                movement.setDrawCardValues(tempCards.get(1).getValue());
                aiHold.addAll(tempCards);  //抓2张底牌
                aiGamePlay.setAiHold(aiHold);     //更新手牌
                movement.setCurrentHold(aiGamePlay.getAiHold());
                if (card.getValue() > 8)
                    chargeValue = "Q";
                else
                    chargeValue = String.valueOf(card.getValue());
            }else{
                //钓牌失败
                movement.setResult(0);  //失败
                chargeGood = false;
                Card card = myHold.remove(0);   //Player移除最小的手牌
                myHold.add(aiHold.get(chargePosition.get(0)));  //Player拿一张钓牌
                aiGamePlay.setMyHold(myHold);   //更新Player手牌
                aiHold.remove(chargePosition.get(0) + 2);    //移除三张手牌
                aiHold.remove(chargePosition.get(0) + 1);
                aiHold.remove(chargePosition.get(0).intValue());
                aiHold.add(card);   //钓到的牌加入手牌
                List<Card> tempCards = new ArrayList<>(mainActivity.getBottomTop(2));
                movement.setCardValue(card.getValue());     //钓到的牌
                movement.setDrawCardValues(tempCards.get(0).getValue());    //摸到的牌
                movement.setDrawCardValues(tempCards.get(1).getValue());
                aiHold.addAll(tempCards);  //抓2张底牌
                aiGamePlay.setAiHold(aiHold);     //更新手牌
                movement.setCurrentHold(aiGamePlay.getAiHold());
                if (card.getValue() > 8)
                    chargeValue = "Q";
                else
                    chargeValue = String.valueOf(card.getValue());
            }
        }
    }

    /**
     * 作出本回合动作的决定
     * @return 动作决定
     */
    public int ai_Decision(){
        int finalDecision = 0;

        aiHoldValue = aiGamePlay.getAiHoldValue();

        //判断是否有可能钓牌
        if (aiHoldValue[0] == aiHoldValue[1] && aiHoldValue[2] == aiHoldValue[1] ||
                aiHoldValue[2] == aiHoldValue[3] && aiHoldValue[2] == aiHoldValue[1]){
            if (aiHoldValue[0] == aiHoldValue[1]){
                chargePosition.add(0);
            }else{
                chargePosition.add(1);
            }
        }
        if (chargePosition.size() > 0){
            //可以钓牌，执行决策函数
            finalDecision = dicisionMaker(2);
        }else{
            finalDecision = 1;   //换牌
        }

        return finalDecision;
    }

    /**
     * 策略制定机，可以选择使用哪一种AI
     * @param num 采用的AI编号
     * @return 本次行动编号，1为换牌，2为钓牌
     */
    private int dicisionMaker(int num){
        int decision = 1;

        if (num == 0){
            //策略0：只会换牌
            decision = 1;
        }else if(num == 1){
            //策略1：若能钓牌则钓牌，不能钓牌则换牌
            decision = 2;
            if (aiHoldValue[1] == 8 && aiHoldValue[2] == 8 && aiHoldValue[3] == 8 && aiHoldValue[0] !=8){
                //只有当后三张都为8时，不钓牌
                decision = 1;
            }
        }else if (num == 2){
            //策略2：看到对方换到了某张牌，才钓牌，否则换牌
            Movement lastMove = aiGamePlay.getLastMove(1);
            if (lastMove.getCardValue() == aiHoldValue[chargePosition.get(0)] * 2){
                decision = 2;
            }
        }
        return decision;
    }

}
