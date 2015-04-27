package pku.ss.zyf.mygame_2q48;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import pku.ss.zyf.bean.Card;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-27
 * Time: 21:45
 */
public class GamePlay {

    private Card[] myHold;
    private Card[] aiHold = new Card[5];

//    private int [] myHoldValue =new int[5];
//    private int [] aiHoldValue =new int[5];

    /**
     *
     * 换牌
     *
     * @param hold 手牌
     * @param bottom 底牌
     * @return 换牌结果
     */
    public Card[] transCard(Card[] hold, Card[] bottom){
        Card[] result = new Card[4];
        for (int i = 0; i < 4; i++){
            result[i] = new Card();
        }
        return result;
    }

    /**
     * 钓牌
     * @param hold 自己手牌
     * @param targetHold    目标手牌
     * @param bottom    底牌
     * @return 钓牌后双方手牌结果
     */
    public Card[] chargeCard(Card[] hold, Card[] targetHold, Card[] bottom){
        Card[] result = new Card[8];
        for (int i = 0; i < 8; i++){
            result[i] = new Card();
        }
        return result;
    }

    public Card[] getMyHold() {
        return myHold;
    }

    public void setMyHold(Card[] myHold) {
        this.myHold = myHold;
    }

    public Card[] getAiHold() {
        return aiHold;
    }

    public void setAiHold(Card[] aiHold) {
        this.aiHold = aiHold;
    }

    public int[] getMyHoldValue(){

        int[] result;
        if (myHold[4].getValue() == 0){
             result = new int[4];
            for (int i = 0; i < 4; i++){
                result[i] = myHold[i].getValue();
            }
        }else{
             result = new int[5];
            for (int i = 0; i < 5; i++){
                result[i] = myHold[i].getValue();
            }
        }

        Arrays.sort(result);
        return result;
    }
    public int[] getAiHoldValue(){

        int[] result;
        if (aiHold[4].getValue() == 0){
            result = new int[4];
            for (int i = 0; i < 4; i++){
                result[i] = aiHold[i].getValue();
            }
        }else{
            result = new int[5];
            for (int i = 0; i < 5; i++){
                result[i] = aiHold[i].getValue();
            }
        }

        Arrays.sort(result);
        return result;
    }
}

