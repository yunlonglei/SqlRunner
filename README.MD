# SQL执行器
SqlRunner可以配置在某些项目中，以保证在项目开始的运行时候会执行一部分必要的sql。
## 配置
1.配置其开关，true则为打开，false则为关闭，默认为关闭；    
2.可以配置数组类型的sql文件路径，执行多个sql文件；  
3.通过配置** continueOnError **来保证sql的有效性，配置策略：true 有错误继续向下执行，false 有错误不再执行；