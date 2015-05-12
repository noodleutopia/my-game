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
    private String transValue, chargeValue;

    public int aiThink(GamePlay gamePlay){
        this.aiGamePlay = gamePlay;
        this.bottomCards = gamePlay.getBottom();

        //做决策
        int move = ai_Decision();

        //行动
        transformOrCharge(move);

        if (move == 1){
            List<Card> temp = new ArrayList<>();
            aiGamePlay.setAiMove("换牌，换到了一张" + transValue);
            if (transValue.equals("Q")){
                temp = aiGamePlay.getAiHold();
                Movement movement = new Movement(1, 16, temp);
                aiGamePlay.recordMove(1, movement);
                Log.d("TEST","注意：" + aiGamePlay.getAiHoldValue()[3]);
            }
            else{
                temp = aiGamePlay.getAiHold();
                Movement movement = new Movement(1, Integer.valueOf(transValue), temp);
                aiGamePlay.recordMove(1, movement);
                Log.d("TEST", "注意：" + aiGamePlay.getAiHoldValue()[3]);
            }
            aiGamePlay.recordHoldSeq(); //记录手牌
        }
        else{
            aiGamePlay.setAiMove("钓牌");
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
        if (flag == 1){
            //抓一张牌
            Card card = bottomCards.remove(0);
            aiGamePlay.setBottom(bottomCards);
//            Log.d("TEST","bottomTop is : " + card.getValue());
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
                if (tempCard.getValue() > 8)
                    transValue = "Q";
                else
                    transValue = String.valueOf(tempCard.getValue());
        }
            aiGamePlay.setAiHold(aiHold);
        }

        //若钓牌
        if (flag == 2){
            List<Card> aiHold = aiGamePlay.getAiHold();
            List<Card> myHold = aiGamePlay.getMyHold();
            int i = 0;
            while (aiHold.get(i).getValue() != myHold.get(1).getValue() * 2){
                i++;
                if (i == 4)
                    break;
            }
            if (i < 4){
                //钓牌成功
//                Card card = aiHold.remove(i);   //AI移除被钓牌
//                aiHold.add(myHold.get(chargePosition.get(0)));  //AI拿一张钓牌
//                aiGamePlay.setAiHold(aiHold); //更新AI手牌
//                myHold.remove(chargePosition.get(0) + 2);    //移除三张手牌
//                myHold.remove(chargePosition.get(0) + 1);
//                myHold.remove(chargePosition.get(0).intValue());
//                myHold.add(card);   //钓到的牌加入手牌
//                myHold.addAll(new MainActivity().getBottomTop(2));  //抓2张底牌
//                aiGamePlay.setMyHold(myHold);     //更新手牌
//                drawHolds();    //绘制手牌
//                playMoveTv_1.setText("钓牌成功，钓到一张" + card.getValue()
//                        + ",抓两张牌");
//                nowCondition = AI_TURN;
//                gameCondition();

            }else{
                //钓牌失败
//                Card card = aiHold.remove(0);   //AI移除最小的手牌
//                aiHold.add(myHold.get(chargePosition.get(0)));  //AI拿一张钓牌
//                aiGamePlay.setAiHold(aiHold); //更新AI手牌
//                myHold.remove(chargePosition.get(0) + 2);    //移除三张手牌
//                myHold.remove(chargePosition.get(0) + 1);
//                myHold.remove(chargePosition.get(0).intValue());
//                myHold.add(card);   //钓到的牌加入手牌
//                myHold.addAll(getBottomTop(2));  //抓2张底牌
//                aiGamePlay.setMyHold(myHold);     //更新手牌
//                drawHolds();    //绘制手牌
//                playMoveTv_1.setText("钓牌失败，钓到一张" + card.getValue()
//                        + ",抓两张牌");
//                nowCondition = AI_TURN;
//                gameCondition();


            }

        }

    }

    /**
     * 作出本回合动作的决定
     * @return 动作决定
     */
    public int ai_Decision(){
        int decision = 0;

        decision = 1;   //换牌
        return decision;
    }

}
