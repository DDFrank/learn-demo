package adapter;

/**
 * @author 016039
 * @Package adapter
 * @Description: 魔法师类,持有魔术接口
 * @date 2018/9/4上午8:50
 */
public class Magician {
    private Magic magic;

    // 初始化使用的魔术
    public Magician(Magic magic) {
        this.magic = magic;
    }

    public void show () {
        System.out.println("开始表演了");
        magic.use();
    }
}
