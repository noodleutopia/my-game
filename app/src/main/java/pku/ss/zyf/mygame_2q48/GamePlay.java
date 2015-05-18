package pku.ss.zyf.mygame_2q48;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pku.ss.zyf.Utils.MyComparator;
import pku.ss.zyf.bean.Card;
import pku.ss.zyf.bean.Movement;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-27
 * Time: 21:45
 */
public class GamePlay {

    private List<Card> myHold;
    private List<Card> aiHold;
    private List<Card> bottom;
    private String aiMove;
    private List<Movement> myMoveSeq = new ArrayList<>();   //行动序列记录
    private List<Movement> aiMoveSeq = new ArrayList<>();
    private List<List<Card>> myHoldSeq = new ArrayList<>(); //手牌变化记录
    private List<List<Card>> aiHoldSeq = new ArrayList<>();



    private MyComparator comparator = new MyComparator();

    public List<Card> getMyHold() {
        List<Card> tempHold = new ArrayList<>();
        Collections.sort(myHold,comparator);
        tempHold.addAll(myHold);
        return tempHold;
    }

    public void setMyHold(List<Card> myHold) {
        this.myHold = myHold;
    }

    public List<Card> getAiHold() {
        List<Card> tempHold = new ArrayList<>();
        Collections.sort(aiHold,comparator);
        tempHold.addAll(aiHold);
        return tempHold;
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

    public String getAiMove() {
        return aiMove;
    }

    public void setAiMove(String aiMove) {
        this.aiMove = aiMove;
        Log.d("TEST","AI本轮行动：" + aiMove);
    }

    /**
     * 行动记录
     * @param player    玩家编号，AI为1，玩家为2
     * @param movement  行动
     */
    public void recordMove(int player, Movement movement){

        //AI行动
        if (player == 1){
            aiMoveSeq.add(movement);
        }
        //玩家行动
        if (player == 2){
            myMoveSeq.add(movement);
        }
    }

    /**
     * 得到AI行动序列
     * @return  行动序列
     */
    public List<String> getAiMoveSeq(){
        List<String> resStr = new ArrayList<>();
        int i = 0;
        while(i < aiMoveSeq.size()){
            Movement tempMove = aiMoveSeq.get(i);
            if (tempMove.getMoveName() == 1){
                StringBuilder temp = new StringBuilder("换牌，"+ "换到一张" + tempMove.getCardValue() + "，现在手牌是：");
                for (int value : tempMove.getCurrentHoldValue()){
                    temp.append(value);
                }
                resStr.add(temp.toString());
            }
            else {
                StringBuilder temp = new StringBuilder("钓牌，"+"钓到一张"+tempMove.getCardValue() +"，现在手牌是：");
                for (int value : aiMoveSeq.get(i).getCurrentHoldValue()){
                    temp.append(value);
                }
                resStr.add(temp.toString());
            }
            i++;
        }
        return resStr;
    }

    /**
     * 记录本局手牌序列
     */
    public void recordHoldSeq(){
        List<Card> temp1 = new ArrayList<>();
        List<Card> temp2 = new ArrayList<>();
        temp1.addAll(aiHold);
        temp2.addAll(myHold);
        aiHoldSeq.add(temp1);
        StringBuilder sb = new StringBuilder();
        for (Card card : temp1){
            sb.append(card.getValue());
        }
        Log.d("TEST","AI手牌记录：" + sb.toString());
        myHoldSeq.add(temp2);
        sb = new StringBuilder();
        for (Card card : temp2){
            sb.append(card.getValue());
        }
        Log.d("TEST","PLAYER手牌记录：" + sb.toString());
    }

    public List<List<Card>> getHoldSeq(int player){
        List<List<Card>> res = new ArrayList<>();
        if (player == 1){
            res.addAll(aiHoldSeq);
        }
        else{
            res.addAll(myHoldSeq);
        }
        return res;
    }



}

