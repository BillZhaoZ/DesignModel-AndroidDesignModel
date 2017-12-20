# DesignModel
设计模式

一、单例模式
    
    1.普通单例模式
    /**
     * 单例模式
     * Created by Bill on 2017/12/15.
     */
    
    public class SigleInstance {
    
        public static void main(String[] args) {
    
            instanceOne();
    
            enmuSingleton instance = enmuSingleton.INSTANCE;
        }
    
        /**
         * 实例1
         */
        private static void instanceOne() {
            Company company = new Company();
    
            // ceo 只能通过getCEO 函数获取
            Staff ceoa1 = CEO.getCeo();
            Staff ceoa2 = CEO.getCeo();
    
            company.addStaff(ceoa1);
            company.addStaff(ceoa2);
    
            // 通过new 创建vp对象
            Staff vp1 = new VP();
            Staff vp2 = new VP();
    
            // 通过new 创建staff对象
            Staff staff1 = new Staff();
            Staff staff2 = new Staff();
            Staff staff3 = new Staff();
    
            company.addStaff(vp1);
            company.addStaff(vp2);
            company.addStaff(staff1);
            company.addStaff(staff2);
            company.addStaff(staff3);
    
            company.showAllStaff();
        }
    
    }
    
    2.懒汉和饿汉
       
        /**
         * CEo
         * 饿汉单例模式
         * 声明对象时就已经初始化了
         */
        public class CEO extends Staff {
        
            public static final CEO mCeo = new CEO();
        
            // 构造函数私有化
            private CEO() {
        
            }
        
            // 公有的静态方法  对外暴露获取单利的接口
            public static CEO getCeo() {
                return mCeo;
            }
        
            @Override
            public void work() {
                super.work();
                // 管理vp
            }
        
        }
        
        /**
         * 懒汉模式
         * 声明一个静态对象，并且在第一次调用getInstance 方法时进行初始化
         * Created by Bill on 2017/12/15.
         */
        public class LazySingleInstance {
        
            private static LazySingleInstance lazySingleInstance;
        
            private LazySingleInstance() {
        
            }
        
            // 保证多线程下的安全
            public static synchronized LazySingleInstance getLazySingleInstance() {
                if (lazySingleInstance == null) {
                    lazySingleInstance = new LazySingleInstance();
                }
        
                return lazySingleInstance;
            }
        
        }
        
    3.double check lock
        /**
         * double check lock
         * <p>
         * 优点：资源利用高，第一次执行getInstance()方法才会实例化对象，效率高。
         * 缺点：第一次加载反应稍慢，也由于Java内存模型的原因偶尔会失败。
         * Created by Bill on 2017/12/16.
         */
        
        public class Singleton {
        
            private static Singleton singleton = null;
        
            private Singleton() {
        
            }
        
            public static Singleton getSingleton() {
                if (singleton == null) {
                    synchronized (Singleton.class) { // 避免不必要的同步
                        if (singleton == null) { // 在null的情况下创建实例
                            singleton = new Singleton();
                        }
                    }
                }
        
                return singleton;
            }
        
        }
    
    4.枚举单例
        /**
         * 枚举单例
         * 写法简单  默认枚举都是线程安全的  在任何情况下都是单例
         * Created by Bill on 2017/12/16.
         */
        public enum enmuSingleton {
        
            INSTANCE;
        
            public void doSomething() {
                System.out.println("do it!!!");
            }
        }

    5.静态内部类
        /**
         * 静态内部类
         * Created by Bill on 2017/12/16.
         */
        
        public class InnerClassSingleton {
        
            private InnerClassSingleton() {
        
            }
        
            public static InnerClassSingleton getInstance() {
                return SingletonHolder.innerInstance;
            }
        
            /**
             * 静态内部类
             * 保证线程安全，也能保证单例对象的唯一性，同事也延迟加载了单利的实例化
             */
            private static class SingletonHolder {
                private static final InnerClassSingleton innerInstance = new InnerClassSingleton();
            }
        }
    
    6.容器实现单例
        /**
         * 容器管理单例
         * <p>
         * 在程序初始化的时候，将多种单例模式注入到一个统一的管理类中，在使用的时候根据key获取对象对应类型的单例对像。
         * 这种方式是得我们可以管理多种单例，并且在使用的时候通过统一的接口进行获取操作，降低了用户的使用成本；
         * <p>
         * Created by Bill on 2017/12/16.
         */
        
        public class SingletonManger {
        
            private static Map<String, Object> objectMap = new HashMap<>();
        
            private SingletonManger() {
        
            }
        
            public static void registerService(String key, Object instance) {
                if (!objectMap.containsKey(key)) {
                    objectMap.put(key, instance);
                }
            }
        
            public static Object getService(String key) {
                return objectMap.get(key);
            }
        }


二、Builder 模式
    
        1.Product 产品类
           /**
            * 计算机抽象类
            * product角色
            * Created by Bill on 2017/12/16.
            */
           public abstract class Computer {
           
               private String mBoard;
               private String mDisplay;
               String mOS;
           
               protected Computer() {
           
               }
           
               // 设置CPU核心数
               public void setBoard(String board) {
                   mBoard = board;
               }
           
               // 设置内存
               public void setDisplay(String display) {
                   mDisplay = display;
               }
           
               // 设置操作系统
               public abstract void setOS();
           
               @Override
               public String toString() {
                   return " My Computer:[ mBoard == " + mBoard + " ,mDisplay == " + mDisplay + " ,mOS" + mOS + "]";
               }
           }
           
           /**
            * 具体的Computer类
            * Created by Bill on 2017/12/16.
            */
           public class MacBook extends Computer {
           
               public MacBook() {
               }
           
               @Override
               public void setOS() {
                   mOS = "Mac OS X 10.13";
               }
           }
           
         2.Builder
            /**
             * 抽象Builder类
             * Created by Bill on 2017/12/16.
             */
            public abstract class Builder {
            
                public abstract void buildBoard(String board);
            
                public abstract void buildDisplay(String display);
            
                public abstract void buildOS();
            
                public abstract Computer create();
            
            }
            
            /**
             * 具体的Builder类
             * Created by Bill on 2017/12/16.
             */
            public class MacBookBuilder extends Builder {
            
                private Computer mComputer = new MacBook();
            
                @Override
                public void buildBoard(String board) {
                    mComputer.setBoard(board);
                }
            
                @Override
                public void buildDisplay(String display) {
                    mComputer.setDisplay(display);
                }
            
                @Override
                public void buildOS() {
                    mComputer.setOS();
                }
            
                @Override
                public Computer create() {
                    return mComputer;
                }
            
            }
            
         3.Director类
            /**
             * Director类，负责构建computer
             * Created by Bill on 2017/12/16.
             */
            public class Director {
            
                Builder mBuilder = null;
            
                public Director(Builder builder) {
                    mBuilder = builder;
                }
            
                public void construct(String board, String display) {
                    mBuilder.buildBoard(board);
                    mBuilder.buildDisplay(display);
                    mBuilder.buildOS();
                }
            }
            
         4.使用
            
            /**
             * Builder 模式
             * <p>
             * 通过具体的MacBookBuilder来构建MacBook对像，而Director封装了构建复杂产品的过程，对外隐藏了细节。
             * Builder和Director一起，将一个复杂对象的构建和表示分开，使得同样的构建过程  可以创建不同的对对象。
             * <p>
             * Created by Bill on 2017/12/15.
             */
            public class BuilderModel {
            
                public static void main(String[] args) {
                    // 实例1
                    // 构建器
                    MacBookBuilder builder = new MacBookBuilder();
            
                    // Director 表示器
                    Director director = new Director(builder);
                    director.construct("英特尔主板", "Retina显示器");
            
                    Computer computer = builder.create();
                    System.out.println("Computer Info:" + computer.toString());
            
            
                    // 例子2
                    HuaShuoBuilder builder1 = new HuaShuoBuilder();
            
                    Director director1 = new Director(builder1);
                    director1.construct("华硕主板", "4K高端显示器");
            
                    Computer computer1 = builder1.create();
                    System.out.println("Computer Info:" + computer1.toString());
            
                }
            
            }