package adapter;

/**
 * @author 016039
 * @Package adapter
 * @Description: 执行类
 * @date 2018/9/4上午8:57
 */
public class Action {

    public static void main(String[] args) {
        // 利用适配器创建了一个可以使用杂技的魔术师
        Magician magician = new Magician(new AcrobatismMagicAdapter(new Acrobatism()));
        // 魔术师表演的时候实际上表演的是杂技
        magician.show();

        /*
        * 所以适配器模式的应用场景
        * 当你想使用一个类，但是它没有继承你要的那个接口
        * 最常被用来适配第三方库，假如想复用，但是又不可能去修改第三方库的源码
        *
        * */
    }
}
