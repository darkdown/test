import org.junit.Test;


public class TestJNI {
	static {
		java.io.File currentLibFile = new java.io.File("lib/lib.so");
		String currentLibPath = currentLibFile.getAbsolutePath();
		System.out.println(currentLibPath);
		currentLibFile = null;
		System.load(currentLibPath);
		currentLibPath = null;
	}
	
	@Test
	public void testJNI(){
		System.out.println(test());
	}

	public native String test();

}
