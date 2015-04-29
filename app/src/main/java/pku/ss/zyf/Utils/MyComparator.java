package pku.ss.zyf.Utils;

import java.util.Comparator;

import pku.ss.zyf.bean.Card;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-28
 * Time: 02:46
 */
public class MyComparator implements Comparator {
    @Override
    public int compare(Object arg1, Object arg2) {
        Card card1 = (Card) arg1;
        Card card2 = (Card) arg2;

        return card1.getValue() - card2.getValue();
    }
}
