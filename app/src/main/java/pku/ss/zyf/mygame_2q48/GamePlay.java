package pku.ss.zyf.mygame_2q48;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pku.ss.zyf.Utils.MyComparator;
import pku.ss.zyf.bean.Card;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-27
 * Time: 21:45
 */
public class GamePlay {

//    private Card[] myHold = new Card[5];
//    private Card[] aiHold = new Card[5];

    private List<Card> myHold;
    private List<Card> aiHold;
    private List<Card> bottom;

    private MyComparator comparator = new MyComparator();

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

    public List<Card> getMyHold() {
        Collections.sort(myHold,comparator);
        return myHold;
    }

    public void setMyHold(List<Card> myHold) {
        this.myHold = myHold;
    }

    public List<Card> getAiHold() {
        Collections.sort(aiHold,comparator);
        return aiHold;
    }

    public void setAiHold(List<Card> aiHold) {
        this.aiHold = aiHold;
    }

    public int[] getMyHoldValue(){

        int[] result;
        Collections.sort(myHold,comparator);
        result = new int[myHold.size()];
        for (int i = 0; i < myHold.size(); i++){
            result[i] = myHold.get(i).getValue();
        }
        return result;
    }
    public int[] getAiHoldValue(){

        int[] result;
        Collections.sort(aiHold,comparator);
        result = new int[aiHold.size()];
        for (int i = 0; i < aiHold.size(); i++){
            result[i] = aiHold.get(i).getValue();
        }
        return result;
    }

    public List<Card> getBottom() {
        return bottom;
    }

    public void setBottom(List<Card> bottom) {
        this.bottom = bottom;
    }
}

