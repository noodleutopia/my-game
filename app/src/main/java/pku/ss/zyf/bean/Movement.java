package pku.ss.zyf.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pku.ss.zyf.Utils.MyComparator;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-05-06
 * Time: 18:21
 *
 * 描述一次行动
 */
public class Movement {

    private int moveName;   //1为换牌，2为钓牌
    private int cardValue;  //得到的牌的值
    private int result;     //钓牌成功与否,0否1是
    private List<Card> currentHold; //现在的手牌
    private List<Integer> drawCardValues = new ArrayList<>();


    public Movement(){

    }
    /**
     *  换牌的构造函数
     * @param moveName 1为换牌，2为钓牌
     * @param cardValue 得到的牌的值
     * @param drawCardValue 摸到的牌的值
     * @param currentHold 现在的手牌
     */
    public Movement(int moveName, int drawCardValue, int cardValue, List<Card> currentHold) {
        this.moveName = moveName;
        this.cardValue = cardValue;
        this.currentHold = currentHold;
        this.drawCardValues.add(drawCardValue);
    }

    /**
     * 钓牌的构造函数
     * @param moveName 1为换牌，2为钓牌
     * @param cardValue 得到的牌的值
     * @param drawCardValues 摸到的牌的值
     * @param currentHold 现在的手牌
     * @param result 钓牌成功与否,0否1是
     *
     */
    public Movement(int moveName,List<Integer>drawCardValues, int result, int cardValue, List<Card> currentHold) {
        this.moveName = moveName;
        this.cardValue = cardValue;
        this.result = result;
        this.currentHold = currentHold;
        this.drawCardValues.addAll(drawCardValues);
    }

    public int getMoveName() {
        return moveName;
    }

    public void setMoveName(int moveName) {
        this.moveName = moveName;
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }

    public List<Card> getCurrentHold() {
        Collections.sort(currentHold, new MyComparator());
        return currentHold;
    }
    public int[] getCurrentHoldValue(){
        int[] result;
        Collections.sort(currentHold, new MyComparator());
        result = new int[currentHold.size()];
        for (int i = 0; i < currentHold.size(); i++){
            result[i] = currentHold.get(i).getValue();
        }
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setCurrentHold(List<Card> currentHold) {
        this.currentHold = currentHold;
    }

    public void setDrawCardValues(int cardValue) {
        this.drawCardValues.add(cardValue);
    }

    public List<Integer> getDrawCardValues() {
        return drawCardValues;
    }

    public int getResult() {
        return result;
    }
}
