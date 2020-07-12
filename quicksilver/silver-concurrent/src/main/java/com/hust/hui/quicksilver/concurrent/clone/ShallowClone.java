package com.hust.hui.quicksilver.concurrent.clone;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yihui on 2017/12/17.
 */
@Data
public class ShallowClone implements Cloneable {

    private String name;

    private Integer age;

    private List<String> books;


    public ShallowClone clone() {
        ShallowClone clone = null;
        try {
            clone = (ShallowClone) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    public ShallowClone deepClone() {
        ShallowClone clone = new ShallowClone();
        clone.name = this.name;
        clone.age = this.age;
        if (this.books != null) {
            clone.books = new ArrayList<>(this.books);
        }
        return clone;
    }


    public static void main(String[] args) {
        ShallowClone shallowClone = new ShallowClone();
        shallowClone.setName("SourceName");
        shallowClone.setAge(new Integer(1280));
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("c++");
        shallowClone.setBooks(list);


        ShallowClone cloneObj = shallowClone.deepClone();


        // 判断两个对象是否为同一个对象（即是否是新创建了一个实例）
        System.out.println(shallowClone == cloneObj);

        // 修改一个对象的内容是否会影响另一个对象
        shallowClone.setName("newName");
        shallowClone.setAge(2000);
        shallowClone.getBooks().add("javascript");
        System.out.println("source: " + shallowClone.toString() + "\nclone:" + cloneObj.toString());


        shallowClone.setBooks(Arrays.asList("hello"));
        System.out.println("source: " + shallowClone.toString() + "\nclone:" + cloneObj.toString());
    }

}
