# SQL执行器
SqlRunner可以配置在某些项目中，以保证在项目开始的运行时候会执行一部分必要的sql。
## 配置
1.配置其开关，true则为打开，false则为关闭，默认为关闭；    
2.可以配置数组类型的sql文件路径，执行多个sql文件；  
3.通过配置 **continueOnError** 来保证sql的有效性，配置策略：true 有错误继续向下执行，false 有错误不再执行；
## 执行器
用到了ApplicationRunner，还有一个可以参考的执行器CommandLineRunner。
## 执行顺序 
如果有多个实现类，而你需要他们按一定顺序执行的话，可以在实现类上加上@Order注解。@Order(value=整数值)。SpringBoot会按照@Order中的value值从小到大依次执行。
