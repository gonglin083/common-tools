日志插件使用说明：

1.引用请在pom.xml文件中添加。。。如果仓库里有的话
<dependency>
    <groupId>com.panhai</groupId>
    <artifactId>log-helper</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

2.使用
 2.1 开启日志功能
    需在启动类上添加 @EnableLog 注解
 
 2.2 指定方法打印日志
    需在相应方法上添加 @MethodLog 注解

    例：
    @MethodLog
    public BaseResponseEntity getThematicData(String id, String eid) {
        return XXXXX;
    }


 2.3 开启表达式打印日志
    1.默认开启。如果不需要打印controller层日志，须在application.properties或等价配置文件中，添加属性 log.helper.expression.open=false 
    2.开启后，默认表达式为 "execution(public * com.panhai..controller.*Controller.*(..))" 。即打印com.panhai包下的controller层公共方法日志。
    3.自定义表达式需设置 @EnableLog 注解的value属性或expression属性值，如
        @EnableLog(expression="execution(public * com.panhai..service.*Impl.*(..))") 或
        @EnableLog(value="execution(public * com.panhai..service.*Impl.*(..))") 或
        @EnableLog("execution(public * com.panhai..service.*Impl.*(..))")
        
 2.4 logback.xml
    项目提供默认配置文件 logback-helper.xml 。 
    如果要直接引用此文件，须在application.properties或等价配置文件中，添加以下属性：
    
    ######日志配置文件 - 必填######
    1.logging.config=classpath:logback-helper.xml 
      
    ######日志路径 - 不填则默认为/usr/local/log/log-helper/######
    2.log.helper.path=XXXX (如：/usr/local/log/)  
    
    ######日志等级 - 不填则默认为INFO######
    3.log.helper.level=XXXX （如： info 或 debug等）
    
    ######文件名称（不要添加.log后缀） - 不填则默认为project_log######
    4.log.helper.filename=XXXX （如： gis-check）  
    不要求使用默认配置，可根据需要自行配置。

3.说明
 3.1 使用@MethodLog对指定方法日志打印，使用的是AOP切面技术。提供对入参，出参/异常的信息打印
 3.2 使用表达式进行日志打印，使用的是动态代理技术。提供对入参，出参/异常的信息打印
 3.3 日志信息使用ApplicationListener，得到监听信息后输出，为异步操作。如后续有持久化操作，可在LogRepositoryListener类中实现。
 3.4 使用SpringBoot默认日志框架logback
 3.5 默认logback文件配置，可根据文件大小自动拆分，不会产生过大文件
 
4.示例
    实现以下操作将打印项目中controller层公共方法的入参，出参/异常日志。日志结果置于/usr/local/log/gis/gis-check.log中
    
    步骤1：引用pom
    <dependency>
        <groupId>com.panhai</groupId>
        <artifactId>log-helper</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
      
    步骤2：启动类添加功能注解@EnableLog
    @EnableLog
    @SpringBootApplication
    public class GisInfcDemoApplication {
        public static void main(String[] args) {
            SpringApplication.run(GisInfcDemoApplication.class, args);
        }
    }
    
    步骤3：修改配置文件，添加以下属性 - application.yml
    logging:
        config: classpath:logback-helper.xml
    log:
        helper:
            expression:
                open: true ##可不填，默认为true。 为false时，根据表达式打印日志功能失效，仅标注@MethodLog方法会打印日志
            path: /usr/local/log/gis/  ##日志存放路径
            filename: gis-check  ##日志文件名