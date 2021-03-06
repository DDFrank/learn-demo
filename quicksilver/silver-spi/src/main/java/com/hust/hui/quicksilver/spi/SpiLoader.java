package com.hust.hui.quicksilver.spi;

import com.hust.hui.quicksilver.spi.api.Spi;
import com.hust.hui.quicksilver.spi.api.SpiAdaptive;
import com.hust.hui.quicksilver.spi.api.SpiConf;
import com.hust.hui.quicksilver.spi.compile.CodeUtil;
import com.hust.hui.quicksilver.spi.compile.GroovyCompile;
import com.hust.hui.quicksilver.spi.exception.NoSpiMatchException;
import com.hust.hui.quicksilver.spi.exception.SpiProxyCompileException;
import com.hust.hui.quicksilver.spi.selector.DefaultSelector;
import com.hust.hui.quicksilver.spi.selector.ParamsSelector;
import com.hust.hui.quicksilver.spi.selector.api.ISelector;
import com.hust.hui.quicksilver.spi.selector.api.SelectorWrapper;
import com.hust.hui.quicksilver.spi.selector.api.SpiImplWrapper;
import com.sun.tools.javac.util.Assert;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yihui on 2017/5/24.
 * SPI加载器
 */
public class SpiLoader<T> {
    private static final SelectorWrapper DEFAULT_SELECTOR = new SelectorWrapper(new DefaultSelector(), String.class);


    /**
     * spiLoader缓存, 其中key为 spi接口, value为对应的Loader对象
     */
    private static final ConcurrentMap<Class<?>, SpiLoader<?>> loaderCache = new ConcurrentHashMap<>();


    /**
     * spi的基类
     */
    private Class<T> spiInterfaceType;


    /**
     * spi动态代理类, 可以根据执行方法的参数来动态选择满足条件的 spiImpl 来执行
     */
    private T spiAdaptiveProxy;

    /**
     * name : spiImpl 的映射表
     */
    private Map<String, SpiImplWrapper<T>> spiImplClassCacheMap;


    /**
     * 自适应时, 根据方法选择实现; name : spiImpl 的映射表
     */
    private Map<String, SpiImplWrapper<T>> spiImplMethodCacheMap;


    /**
     * 选择器, 根据条件, 选择具体的 SpiImpl;
     * 这个是类选择器,每个SPI接口都有的一个
     */
    private SelectorWrapper currentSelector;


    /**
     * 自适应时, 方法对应的选择器
     * 方法选择器映射关系表，key为方法名，value为该方法对应的选择器； 所以spi接口中，不支持重载
     */
    private Map<String, SelectorWrapper> currentMethodSelector;


    /**
     * 每一个 SpiLoader 中, 每种类型的选择器, 只保存一个实例
     * 因此可以在选择器中, 如{@link ParamsSelector} 对spiImplMap进行处理并缓存结果
     * spi接口所有定义的选择器映射关系表，key为选择器类型，value是实例；用于保障每个spi接口中选择器只会有一个实例
     */
    private ConcurrentHashMap<Class, SelectorWrapper> selectorInstanceCacheMap = new ConcurrentHashMap<>();


    private SpiLoader(Class<T> type) {
        // 初始化默认的选择器, 为保留项目, 必然会提供的服务
        selectorInstanceCacheMap.putIfAbsent(DefaultSelector.class, DEFAULT_SELECTOR);

        this.spiInterfaceType = type;
        initSelector();
    }

    /**
    * @Description: 初始化选择器
    * @param
    * @return
    * @author 016039
    * @date 2018/10/29 上午7:07
    */
    private void initSelector() {
        Spi ano = this.spiInterfaceType.getAnnotation(Spi.class);
        // 使用默认的选择器或者使用指定的选择器
        if (ano == null) {
            currentSelector = initSelector(DefaultSelector.class);
        } else {
            currentSelector = initSelector(ano.selector());
        }


        Method[] methods = this.spiInterfaceType.getMethods();
        currentMethodSelector = new ConcurrentHashMap<>();
        SelectorWrapper temp;
        // 寻找被标注了 SpiAdaptive的注解的方法并进行初始化
        for (Method method : methods) {
            if (!method.isAnnotationPresent(SpiAdaptive.class)) {
                continue;
            }

            temp = initSelector(method.getAnnotation(SpiAdaptive.class).selector());
            if (temp == null) {
                continue;
            }

            currentMethodSelector.put(method.getName(), temp);
        }
    }


    private SelectorWrapper initSelector(Class<? extends ISelector> clz) {
        // 优先从选择器缓存中获取类型对应的选择器
        if (selectorInstanceCacheMap.containsKey(clz)) {
            return selectorInstanceCacheMap.get(clz);
        }

        try {
            ISelector selector = clz.newInstance();
            Class paramClz = null;

            Type[] types = clz.getGenericInterfaces();
            // 遍历本类实现的接口
            for (Type t : types) {
                // 假如有一个是泛型接口
                if (t instanceof ParameterizedType) {
                    // 获取泛型中的类型
                    paramClz = (Class) ((ParameterizedType) t).getActualTypeArguments()[0];
                    break;
                }
            }

            Assert.check(paramClz != null);
            SelectorWrapper wrapper = new SelectorWrapper(selector, paramClz);
            selectorInstanceCacheMap.putIfAbsent(clz, wrapper);
            return wrapper;
        } catch (Exception e) {
            throw new IllegalArgumentException("illegal selector defined! yous:" + clz);
        }
    }

    /**
    * @Description: 加载SPI实现类
    * @param type 实现类.class
    * @return 实现类
    * @author 016039
    * @date 2018/10/28 下午7:33
    */
    @SuppressWarnings("unchecked")
    public static <T> SpiLoader<T> load(Class<T> type) {
        if (null == type) {
            throw new IllegalArgumentException("common cannot be null...");
        }

        if (!type.isInterface()) {
            throw new IllegalArgumentException("common class:" + type + " must be interface!");
        }


        if (!withSpiAnnotation(type)) {
            throw new IllegalArgumentException("common class:" + type + " must have the annotation of @Spi");
        }

        // 从缓存中取得加载器
        SpiLoader<T> spiLoader = (SpiLoader<T>) loaderCache.get(type);
        if (spiLoader == null) {
            loaderCache.putIfAbsent(type, new SpiLoader<>(type));
            spiLoader = (SpiLoader<T>) loaderCache.get(type);
        }

        return spiLoader;
    }

    /**
     * 是否打上了spi的标签
     *
     * @param type
     * @param <T>
     * @return
     */
    private static <T> boolean withSpiAnnotation(Class<T> type) {
        return type.isAnnotationPresent(Spi.class);
    }


    /**
     * 根据传入条件, 选择具体的spi实现类
     * <p/>
     * 这里要求conf的类型和选择器的参数类型匹配, 否则会尝试使用默认的选择器补救, 若补救失败, 则抛异常
     *
     * @param conf
     * @return
     * @throws NoSpiMatchException
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("unchecked")
    public T getService(Object conf) throws NoSpiMatchException {
        // 假如实现类缓存不存在的话，就先加载一遍该SPI接口的实现类
        if (spiImplClassCacheMap == null || spiImplClassCacheMap.size() == 0) {
            loadSpiService();
        }
        /*
        * 假如当前选择器的参数类型是传入参数的父类或接口或同一个类或接口就判断为匹配
        * */
        if (!currentSelector.getConditionType().isAssignableFrom(conf.getClass())) {

            /**
             * 参数类型不匹配时, 判断是否可以根据默认的选择器来获取
             */
            if (conf instanceof String) {
                return (T) DEFAULT_SELECTOR.getSelector().selector(spiImplClassCacheMap, conf);
            }


            /**
             * 参数类型完全不匹配, 则抛参数异常
             */
            throw new IllegalArgumentException("conf spiInterfaceType should be sub class of [" + currentSelector.getConditionType() + "] but yours:" + conf.getClass());
        }


        return (T) currentSelector.getSelector().selector(spiImplClassCacheMap, conf);
    }

    /**
    * @Description: 获取实现类, 根据匹配参数和方法名
    * @param
    * @return
    * @author 016039
    * @date 2018/10/29 上午7:46
    */
    @SuppressWarnings("unchecked")
    public T getService(Object conf, String methodName) throws NoSpiMatchException {
        // 就先加载一遍该SPI接口的实现类
        if (spiImplClassCacheMap == null || spiImplClassCacheMap.size() == 0) {
            loadSpiService();
        }


        // 首先获取对应的selector
        SelectorWrapper selector = currentMethodSelector.get(methodName);
        if (selector == null) { // 自适应方法上未定义选择器, 则默认继承类的
            selector = currentSelector;
            // 将类上的选择器添加缓存
            currentMethodSelector.putIfAbsent(methodName, selector);
        }
        // 判断选择器参数是否和传入的参数匹配,不匹配的话使用默认选择器
        if (!selector.getConditionType().isAssignableFrom(conf.getClass())) { // 选择器类型校验
            if (!(conf instanceof String)) {
                throw new IllegalArgumentException("conf spiInterfaceType should be sub class of [" + currentSelector.getConditionType() + "] but yours:" + conf.getClass());
            }

            selector = DEFAULT_SELECTOR;
        }

        // 假如实现类的方法缓存map有值的话，直接从中选择具体的实现类
        if (spiImplMethodCacheMap.size() == 0) {
            return (T) selector.getSelector().selector(spiImplClassCacheMap, conf);
        }

        try {
            // 采用默认的选择器,根据指定name 进行查询时, 需要兼容一下, 因为method对应的缓存key为  SpiImpName_methodName
            if (DEFAULT_SELECTOR.equals(selector)) {
                if (spiImplMethodCacheMap.containsKey(conf)) {
                    return (T) selector.getSelector().selector(spiImplMethodCacheMap, conf);
                }


                if (spiImplClassCacheMap.containsKey(conf)) {
                    return (T) selector.getSelector().selector(spiImplClassCacheMap, conf);
                }


                return (T) selector.getSelector().selector(spiImplMethodCacheMap, conf + "_" + methodName);
            } else {
                return (T) selector.getSelector().selector(spiImplMethodCacheMap, conf);
            }
        } catch (Exception e) {
            return (T) selector.getSelector().selector(spiImplClassCacheMap, conf);
        }
    }

    /**
    * @Description: 这里使用了SPI的规范，实际上只要能找到实现类，可以自定义规范。主要是接口实现类上的注解
    * @param
    * @return
    * @author 016039
    * @date 2018/10/28 下午7:39
    */
    private void loadSpiService() {
        List<SpiImplWrapper<T>> spiServiceList = new ArrayList<>();
        List<SpiImplWrapper<T>> spiServiceMethodList = new ArrayList<>();
        // 根据不同的SPI接口获取全部实现类, 这里可以更改为其它规范
        ServiceLoader<T> serviceLoader = ServiceLoader.load(spiInterfaceType);

        SpiConf spiConf;
        String implName;
        int implOrder;
        // 对每个SPI实现类进行处理
        for (T t : serviceLoader) {
            // 获取 spi 注解
            spiConf = t.getClass().getAnnotation(SpiConf.class);
            Map<String, String> map;
            if (spiConf == null) {
                // 如果没有标注SPI注解,那么名字默认为类名
                implName = t.getClass().getSimpleName();
                // 获取一个默认的优先级
                implOrder = SpiImplWrapper.DEFAULT_ORDER;


                // 参数选择器时, 要求spi实现类必须有 @SpiConf 注解, 否则选择器无法获取校验条件参数
                if (currentSelector.getSelector() instanceof ParamsSelector) {
                    throw new IllegalStateException("spiImpl must contain annotation @SpiConf!");
                }

                map = Collections.emptyMap();
            } else {
                // 有注解的话为其name 属性值
                implName = spiConf.name();
                if (StringUtils.isBlank(implName)) {
                    implName = t.getClass().getSimpleName();
                }
                // 假如参数的order<0 则改为默认值
                implOrder = spiConf.order() < 0 ? SpiImplWrapper.DEFAULT_ORDER : spiConf.order();
                // 将解析后的map封装为一个map
                map = parseParms(spiConf.params());
            }

            // 添加一个类级别的封装类
            spiServiceList.add(new SpiImplWrapper<>(t, implOrder, implName, map));


            // todo 改成 getMethods(), 但是过滤掉 Object类中的基础方法
            Method[] methods = t.getClass().getDeclaredMethods();
            String methodImplName;
            int methodImplOrder;
            Map<String, String> methodParams;
            // 解析方法上的SPIConf注解
            for (Method method : methods) {
                spiConf = method.getAnnotation(SpiConf.class);
                if (spiConf == null) {
                    continue;
                }

                // 方法上有自定义注解, 且定义的name与类实现名不同, 则直接采用
                // 否则采用  ServiceName_MethodName 方式定义
                if (StringUtils.isBlank(spiConf.name()) || implName.equals(spiConf.name())) {
                    methodImplName = implName + "_" + method.getName();
                } else {
                    methodImplName = spiConf.name();
                }

                // 优先级, 以最小的为准 （即一个类上的优先级很低, 也可以定义优先级高的方法）
                // 方法注解未定义顺序时, 继承类上的顺序
                methodImplOrder = Math.min(implOrder, spiConf.order() < 0 ? implOrder : spiConf.order());

                // 自适应方法的参数限制, 要求继承类上的参数
                methodParams = parseParms(spiConf.params());
                if (map.size() > 0) { // 方法的参数限定会继承类上的参数限定
                    if (methodParams.size() == 0) {
                        methodParams = map;
                    } else {
                        methodParams.putAll(map);
                    }
                }


                spiServiceMethodList.add(new SpiImplWrapper<>(t, methodImplOrder, methodImplName, methodParams));
            }
        }


        if (spiServiceList.size() == 0) {
            throw new IllegalStateException("no spiImpl implements spi: " + spiInterfaceType);
        }


        this.spiImplClassCacheMap = initSpiImplMap(spiServiceList);
        this.spiImplMethodCacheMap = initSpiImplMap(spiServiceMethodList);
    }


    private Map<String, String> parseParms(String[] params) {
        if (params.length == 0) {
            return Collections.emptyMap();
        }


        Map<String, String> map = new HashMap<>(params.length);
        String[] strs;
        for (String param : params) {
            strs = StringUtils.split(param, ":");

            if (strs.length >= 2) {
                map.put(strs[0].trim(), strs[1].trim());
            } else if (strs.length == 1) {
                map.put(strs[0].trim(), null);
            }
        }
        return map;
    }


    private Map<String, SpiImplWrapper<T>> initSpiImplMap(List<SpiImplWrapper<T>> list) {
        // 映射为map, 限定不能重名
        Map<String, SpiImplWrapper<T>> tempMap = new ConcurrentHashMap<>();
        for (SpiImplWrapper<T> wrapper : list) {
            if (tempMap.containsKey(wrapper.getName())) {
                throw new IllegalArgumentException("duplicate spiImpl name " + wrapper.getName());
            }

            tempMap.put(wrapper.getName(), wrapper);
        }
        return tempMap;
    }


    /**
     * 初始化代理类的锁
     */
    private final Boolean initLock = true;


    /**
     * 返回一个实现接口类的代理
     *
     * @return
     */
    public T getAdaptive() throws SpiProxyCompileException {
        if (spiAdaptiveProxy == null) {
            synchronized (initLock) {
                if (spiAdaptiveProxy == null) {
                    String code = CodeUtil.buildTempImpl(spiInterfaceType);
                    spiAdaptiveProxy = GroovyCompile.compile(code, spiInterfaceType, this.getClass().getClassLoader());
                }
            }
        }

        return spiAdaptiveProxy;
    }
}
