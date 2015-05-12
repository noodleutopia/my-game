package pku.ss.zyf.mygame_2q48;

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
        Collections.sort(myHold, comparator);
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

    public String getAiMove() {
        return aiMove;
    }

    public void setAiMove(String aiMove) {
        this.aiMove = aiMove;
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
//            Log.d("TEST","AI move : " + movement.getMoveName());
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
    public List<Integer> getAiMoveSeq(){
        List<Integer> res = new ArrayList<>();
        int i = 0;
        while (i < aiMoveSeq.size()){
            res.add(aiMoveSeq.get(i).getMoveName());
            i++;
        }
        return res;
    }

    public void recordAiHold(){
        aiHoldSeq.add(this.aiHold);
    }
    public void recordMyHold(){
        myHoldSeq.add(this.myHold);
    }
}

