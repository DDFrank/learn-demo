package com.hust.hui.quicksilver.file.test;

import com.hust.hui.quicksilver.file.FileReadUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yihui on 2017/5/8.
 */
public class XmlReadTest {

    @Test
    public void test() throws IOException {
        String fileName = "demo.xml";
        InputStream inputStream = FileReadUtil.getStreamByFileName(fileName);

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(inputStream); //加载xml文件

            List peoples = doc.selectNodes("//*[@name]"); //选择所有具有name属性的节点(即demo.xml中的所有card节点)
            for (Iterator iter = peoples.iterator(); iter.hasNext(); ) {
                Element card = (Element) iter.next();
                List attrList = card.attributes();
                //输出每个card的所有属性
                for (Iterator attr = attrList.iterator(); attr.hasNext(); ) {
                    Attribute a = (Attribute) attr.next();
                    System.out.println(a.getName() + "=" + a.getValue());

                }
                System.out.println(
                        "----------------------------------------------------");
            }

            Element zhangsan = (Element) doc.selectSingleNode("//card[@id='2']"); //查找“id属性”=2的card元素
            System.out.println("张三的名称：" + zhangsan.attribute("name").getValue()); //输出zhangsan的name属性

            Node addrFamily = zhangsan.selectSingleNode("./address/item[2]"); //选择zhangsan元素下的address节点下的第2个item子节点
            System.out.println("张三的单位地址：" + addrFamily.getStringValue()); //输出cdata内容

            System.out.println(
                    "----------------------------------------------------");
            //为zhangsan下增加二个节点
            zhangsan.addElement("email").addAttribute("type",
                    "工作").addText("work@some-domain.com");
            zhangsan.addElement("email").addAttribute("type",
                    "私人").addCDATA("private@some-domain.com"); //设置CDATA内容

            System.out.println(zhangsan.asXML()); //打印zhangsan节点的xml内容(调试用)
            System.out.println(
                    "----------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

        inputStream.close();
    }
}
