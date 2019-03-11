package com.iveiv.rbac.base.annotation;

/**
 * java.lang.annotation包中,可直接查看java注解
 *
 * JDK内置注解
 * @Override，表示当前的方法定义将覆盖超类中的方法。
 * @Deprecated，使用了注解为它的元素编译器将发出警告，
 * 因为注解@Deprecated是不赞成使用的代码，被弃用的代码。
 * @SuppressWarnings,关闭不当编辑器警告信息。
 *
 * 元注解:用来创建自定义注解的原始数据
 *      @Target  表示该注解可以用于什么地方,可选值有:
 *          TYPE                    标明该注解可以用于类、接口（包括注解类型）或enum声明
 *          FIELD                   标明该注解可以用于字段(域)声明，包括enum实例
 *          METHOD                  标明该注解可以用于方法声明
 *          PARAMETER               标明该注解可以用于参数声明
 *          CONSTRUCTOR             标明注解可以用于构造函数声明
 *          LOCAL_VARIABLE          标明注解可以用于局部变量声明(对于LOCAL_VARIABLE,不能用在runtime时期,没什么卵用)
 *          ANNOTATION_TYPE         标明注解可以用于注解声明(应用于另一个注解上)
 *          PACKAGE                 标明注解可以用于包声明
 *          TYPE_PARAMETER          标明注解可以用于类型参数声明（1.8新加入）
 *          TYPE_USE                类型使用声明（1.8新加入)
 *
 *      @Retention  约定注解的生命周期,可选值有:
 *          SOURCE                  源码编译期存在,编译完丢弃,
 *          CLASS                   编译后CLASS文件有,但是不会执行
 *          RUNTIME                 运行期存在,影响运行结果的注解,例如@Autowrid
 *
 *      @DOCUMENT                  注释生成DOC文档注解
 *
 *      @Inherited                 标记注解可以被继承,即父类用的注解@A是被@Inherited标记的,则子类与父类同时拥有@A
 *
 *      @Repeatable                可重复注解,容器,对某自定义注解包装后,可以多次在同一处使用,并可在容器中获取自定义注解值的数组.
 */