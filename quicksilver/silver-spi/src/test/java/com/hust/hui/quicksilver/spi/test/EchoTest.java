package com.hust.hui.quicksilver.spi.test;

import com.hust.hui.quicksilver.spi.SpiLoader;
import com.hust.hui.quicksilver.spi.exception.NoSpiMatchException;
import com.hust.hui.quicksilver.spi.selector.api.Context;
import com.hust.hui.quicksilver.spi.test.echo.IEcho;
import org.junit.Test;

/**
 * Created by yihui on 2017/5/24.
 */
public class EchoTest {

    @Test
    public void echoTest() throws NoSpiMatchException {
        SpiLoader<IEcho> loader = SpiLoader.load(IEcho.class);


        Context context = new Context();
        context.setParam("console");
        IEcho iEcho = loader.getService(context);
        iEcho.echo("[console]");


        context.clear();
        context.setParam("file");
        iEcho = loader.getService(context);
        iEcho.echo("[basicFile]");


        context.clear();
        context.setParam("file");
        context.setParam("type", "excel");
        iEcho = loader.getService(context);
        iEcho.echo("[excelFile]");


        context.clear();
        context.setParam("file");
        context.setParam("type", "csv");
        context.setParam("tag", "num");
        iEcho = loader.getService(context);
        iEcho.echo("[csvFile]");


        try {
            iEcho = loader.getService(23);
            iEcho.echo("error!");
        } catch (Exception e) {
            System.out.println("type error!");
        }


        try {
            iEcho = loader.getService("ConsoleEcho");
            iEcho.echo("[self console]");
        } catch (Exception e) {
            System.out.println("type error!");
        }
    }
}
