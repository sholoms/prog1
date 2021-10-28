package test;
import java.io.*;
class A{
	   public void Method1(){
	     System.out.print("Class A method1");
	     }
	}

	  class B extends A{
	    public void Method2(){
	    System.out.print("Class B method2");
	    }
	 }

	class  C extend C{
	   public void Method2(){
	     System.out.print("Class C method2");
	    }
	   public void Method3(){
	     System.out.print("Class C method3");
	 }

	  public class Test {
	    public static void main(String args[]) {
	      A a = new A();
	      C c= new C();
	      c.method2();
	      a=c;
	      a.method3();
	  }
	} 
}