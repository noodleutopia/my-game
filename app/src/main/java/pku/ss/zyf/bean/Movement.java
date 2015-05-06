package pku.ss.zyf.bean;

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

    public Movement(int moveName, int cardValue) {
        this.moveName = moveName;
        this.cardValue = cardValue;
    }
    public Movement(int moveName, int result, int cardValue) {
        this.moveName = moveName;
        this.cardValue = cardValue;
        this.result = result;
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
}
