package com.iveiv.test;

/**
 * 收集结果
 * @author irays
 *
 */
public class Demo8 {
	
//	Student[] students;
//	
//	
//	@Before
//	public void init(){
//	    students = new Student[100];
//	    for (int i=0;i<30;i++){
//	        Student student = new Student("user",i);
//	        students[i] = student;
//	    }
//	    for (int i=30;i<60;i++){
//	        Student student = new Student("user"+i,i);
//	        students[i] = student;
//	    }
//	    for (int i=60;i<100;i++){
//	        Student student = new Student("user"+i,i);
//	        students[i] = student;
//	    }
//	 
//	}
//	@Test
//	public void testCollect1(){
//	    /**
//	     * 生成List
//	     */
//	    List<Student> list = Arrays.stream(students).collect(toList());
//	    list.forEach((x)-> System.out.println(x));
//	    /**
//	     * 生成Set
//	     */
//	    Set<Student> set = Arrays.stream(students).collect(toSet());
//	    set.forEach((x)-> System.out.println(x));
//	    /**
//	     * 如果包含相同的key，则需要提供第三个参数，否则报错
//	     */
//	    Map<String,Integer> map = Arrays.stream(students).collect(toMap(Student::getName,Student::getScore,(s,a)->s+a));
//	    map.forEach((x,y)-> System.out.println(x+"->"+y));
//	}
//	 
//	/**
//	 * 生成数组
//	 */
//	@Test
//	public void testCollect2(){
//	    Student[] s = Arrays.stream(students).toArray(Student[]::new);
//	    for (int i=0;i<s.length;i++)
//	        System.out.println(s[i]);
//	}
//	 
//	/**
//	 * 指定生成的类型
//	 */
//	@Test
//	public void testCollect3(){
//	    HashSet<Student> s = Arrays.stream(students).collect(toCollection(HashSet::new));
//	    s.forEach(System.out::println);
//	}
//	 
//	/**
//	 * 统计
//	 */
//	@Test
//	public void testCollect4(){
//	    IntSummaryStatistics summaryStatistics = Arrays.stream(students).collect(Collectors.summarizingInt(Student::getScore));
//	    System.out.println("getAverage->"+summaryStatistics.getAverage());
//	    System.out.println("getMax->"+summaryStatistics.getMax());
//	    System.out.println("getMin->"+summaryStatistics.getMin());
//	    System.out.println("getCount->"+summaryStatistics.getCount());
//	    System.out.println("getSum->"+summaryStatistics.getSum());
//	}
	
	

}
