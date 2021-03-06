package pku.ss.zyf.mygame_2q48;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pku.ss.zyf.Utils.MyComparator;
import pku.ss.zyf.bean.AiSeeBean;
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
    private String myMove;
    private List<Movement> myMoveSeq = new ArrayList<>();   //行动序列记录
    private List<Movement> aiMoveSeq = new ArrayList<>();
    private List<int []> myHoldSeq = new ArrayList<>(); //手牌变化记录
    private List<int []> aiHoldSeq = new ArrayList<>();

    private AiSeeBean aiSee = new AiSeeBean(); //AI记忆

    private int aiLevel;
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
        String temp = aiMove.substring(0,aiMove.indexOf(":"));
        return temp;
    }
    public String getMyMove() {
        String temp = myMove.substring(0,myMove.indexOf(":"));
        return temp;
    }

    public int getAiLevel() {
        return aiLevel;
    }

    public void setAiLevel(int aiLevel) {
        this.aiLevel = aiLevel;
    }

    public void setAiMove(String aiMove) {
        this.aiMove = aiMove;
        Log.d("TEST","AI本轮行动：" + aiMove);
    }
    public void setMyMove(String myMove) {
        this.myMove = myMove;
        Log.d("TEST","PLAYER本轮行动：" + myMove);
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
     * 得到所有行动序列
     * @param player 玩家编号，0为AI，1为玩家
     * @return 行动序列
     */
    public List<Movement> getAllMove(int player){
        if (player == 0){
            return aiMoveSeq;
        }
        else {
            return myMoveSeq;
        }
    }
    /**
     * 获得最后一次行动
     * @param player 玩家编号，0为AI，1为PLAYER
     * @return 最后一次行动
     */
    public Movement getLastMove(int player){
        Movement lastMove = new Movement();
        if (player == 0){
            if (aiMoveSeq.size() > 0)
                lastMove = aiMoveSeq.get(aiMoveSeq.size() - 1);
        }
        if (player == 1)
        {
            if (myMoveSeq.size() > 0)
                lastMove = myMoveSeq.get(myMoveSeq.size() - 1);
        }
        return lastMove;
    }



    /**
     * 记录本局手牌序列
     */
    public void recordHoldSeq(){
        int [] temp1= this.getAiHoldValue();
        int [] temp2 = this.getMyHoldValue();
        aiHoldSeq.add(temp1);
        StringBuilder sb = new StringBuilder();
        for (int cardValue : temp1){
            sb.append(cardValue);
        }
        Log.d("TEST","AI手牌记录：" + sb.toString());
        myHoldSeq.add(temp2);
        sb = new StringBuilder();
        for (int cardValue : temp2){
            sb.append(cardValue);
        }
        Log.d("TEST","PLAYER手牌记录：" + sb.toString());
    }

    public List<int[]> getHoldSeq(int player){
        List<int[]> res = new ArrayList<>();
        if (player == 1){
            res.addAll(aiHoldSeq);
        }
        else{
            res.addAll(myHoldSeq);
        }
        return res;
    }

    public AiSeeBean getAiSee(){
        return aiSee;
    }

    public void setAiSee(AiSeeBean aiSee){
        this.aiSee = aiSee;
    }

    public float getBottomPercent(int cardValue){
        float percent = 0;

        float bottomHas = bottom.size();

        switch (cardValue){
            case 2:
                percent = (36f - aiSee.getTwoCount()) / bottomHas;
                break;
            case 4:
                percent = (6f - aiSee.getFourCount()) / bottomHas;
                break;
            case 8:
                percent = (2f - aiSee.getEightCount()) / bottomHas;
                break;
            case 16:
                percent = 0f;
                break;
        }
        if (bottomHas != 0){
            return 0;
        }
        return percent;
    }

    public boolean isOverHand(){
        int [] aiHoldvalue = getAiHoldValue();
        if (aiHoldvalue[3] == 16 && aiHoldvalue[2] == 16){
            return true;
        }else if (aiHoldvalue[3] == 16 && aiHoldvalue[2] == 8 && aiHoldvalue[1] == 8){
            return true;
        }else if (aiHoldvalue[3] == 8 && aiHoldvalue[2] == 8 && aiHoldvalue[1] == 8 && aiHoldvalue[0] == 8){
            return true;
        }
        return false;
    }

}

