package adapter;

/**
 * @author 016039
 * @Package adapter
 * @Description: 杂技类的魔术接口适配类
 * @date 2018/9/4上午9:33
 */
public class AcrobatismMagicAdapter implements Magic{
    private Acrobatism acrobatism;

    public AcrobatismMagicAdapter(Acrobatism acrobatism) {
        this.acrobatism = acrobatism;
    }

    @Override
    public void use() {
        acrobatism.use();
    }
}
