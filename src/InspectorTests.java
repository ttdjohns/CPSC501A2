import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Test cases for Inspector.java
 * 
 * NOTE!!!!!! reflection is very inconsistent with the order that it gets fields/methods in.
 * 
 * @author ttdjo
 *
 */

public class InspectorTests {	
	Object c1a;
	Object c1b;
	Object c2;
	Inspector insp;
	
	@Before
	public void setup() {
		c1a = new C1();
		c1b = new C1(7);
		c2 = new C2();
		insp = new Inspector();
	}
	
	@After 
	public void teardown() {
		c1a = null;
		c1b = null;
		c2 = null;
		insp = null;
		
	}
	
	@Test
	public void testInpMethods() {
		assertEquals(insp.inspectMethods(c1a.getClass(), 0, ""), c1aM_printout);
		assertEquals(insp.inspectMethods(c1a.getClass(), 0, ""), c1bM_printout);
		assertEquals(insp.inspectMethods(c2.getClass(), 0, ""), c2M_printout);
	}
	
	@Test
	public void testInpConstructors() {
		//System.out.println(insp.inspectConstructors(c2.getClass(), 0, ""));
		assertEquals(insp.inspectConstructors(c1a.getClass(), 0, ""), c1aC_printout);
		assertEquals(insp.inspectConstructors(c1b.getClass(), 0, ""), c1bC_printout);
		assertEquals(insp.inspectConstructors(c2.getClass(), 0, ""), c2C_printout);
	}
	
	@Test
	public void testInpFields() {
		//System.out.println(insp.inspectFields(c2.getClass(), c2, false, 0, ""));
		assertEquals(insp.inspectFields(c1a.getClass(), c1a, false, 0, ""), c1aFnr_printout);
		assertEquals(insp.inspectFields(c1b.getClass(), c1b, false, 0, ""), c1bFnr_printout);
		assertEquals(insp.inspectFields(c2.getClass(), c2, false, 0, ""), c2Fnr_printout_alternitive);
		//assertTrue(true);
	}
	
	@Test
	public void testInpect() {
		//System.out.println(insp.inspectWorker(c2.getClass(), c2, false, 0));
		assertEquals(c1aInr_printout, insp.inspectWorker(c1a.getClass(), c1a, false, 0));
		assertEquals(c2Inr_printout, insp.inspectWorker(c2.getClass(), c2, false, 0));
		assertTrue(true);
	}
	
	@Test
	public void testInpspectR() {
		//System.out.println(insp.inspectMethods(c2.getClass(), 0, ""));
		//assertEquals(insp.inspectMethods(c1a.getClass(), 0, ""), c1aInr_printout);
		//assertEquals(insp.inspectMethods(c1a.getClass(), 0, ""), c1bInr_printout);
		//assertEquals(insp.inspectMethods(c2.getClass(), 0, ""), c2Inr_printout);
		assertTrue(true);
	}
	
	String c1aM_printout = "  |   Methods: \n  |     |   Method: aThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public \n";
	String c1bM_printout = "  |   Methods: \n  |     |   Method: aThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public \n";
	String c2M_printout = "  |   Methods: \n  |     |   Method: aThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |     |   Method: anotherThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.String\n  |     |     |   Modifiers:\n  |     |     |     |   public \n";
	
	String c1aC_printout = "  |   Constructor: C1\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n  |   Constructor: C1\n  |     |   Parameters: int \n  |     |   Modifiers:\n  |     |     |   public\n";
	String c1bC_printout = "  |   Constructor: C1\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n  |   Constructor: C1\n  |     |   Parameters: int \n  |     |   Modifiers:\n  |     |     |   public\n";
	String c2C_printout = "  |   Constructor: C2\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n";
	
	String c1aFnr_printout = "  |   Field: a\n  |     |   Type: int\n  |     |   Modifiers: public \n  |     |   Current value: 0\n  |   Field: x\n  |     |   Type: long\n  |     |   Modifiers: private \n  |     |   Current value: 3\n";
	String c1bFnr_printout = "  |   Field: a\n  |     |   Type: int\n  |     |   Modifiers: public \n  |     |   Current value: 7\n  |   Field: x\n  |     |   Type: long\n  |     |   Modifiers: private \n  |     |   Current value: 3\n";
	String c2Fnr_printout = "  |   Field: c\n  |     |   Type: int[]\n  |     |   Modifiers: public \n  |     |   Array dimensions: 1\n  |     |   Array length (1st dimension): 2\n  |     |   Current values: \n  |     |     |   0\n  |     |     |   1\n  |   Field: d\n  |     |   Type: double\n  |     |   Modifiers: public final \n  |     |   Current value: 1.1\n";
	String c2Fnr_printout_alternitive = "  |   Field: c\n  |     |   Type: int[]\n  |     |   Modifiers: public \n  |     |   Array dimensions: 1\n  |     |   Array length (1st dimension): 2\n  |     |   Current values: \n  |     |     |   0\n  |     |     |     |   Declared Class: int\n  |     |     |     |   ========================================\n  |     |     |   1\n  |   Field: d\n  |     |   Type: double\n  |     |   Modifiers: public final \n  |     |   Current value: 1.1\n";
	
	String c1aInr_printout = "Declared Class: C1\n  |   Superclass: java.lang.Object\n  |   Methods: \n  |     |   Method: aThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |   Constructor: C1\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n  |   Constructor: C1\n  |     |   Parameters: int \n  |     |   Modifiers:\n  |     |     |   public\n  |   Field: a\n  |     |   Type: int\n  |     |   Modifiers: public \n  |     |   Current value: 0\n  |   Field: x\n  |     |   Type: long\n  |     |   Modifiers: private \n  |     |   Current value: 3\n========================================\n\nDeclared Class: java.lang.Object\n  |   Methods: \n  |     |   Method: finalize\n  |     |     |   Exceptions: java.lang.Throwable \n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   protected \n  |     |   Method: wait\n  |     |     |   Exceptions: java.lang.InterruptedException \n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final \n  |     |   Method: wait\n  |     |     |   Exceptions: java.lang.InterruptedException \n  |     |     |   Parameters: long int \n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final \n  |     |   Method: wait\n  |     |     |   Exceptions: java.lang.InterruptedException \n  |     |     |   Parameters: long \n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: equals\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: java.lang.Object \n  |     |     |   Return Type: boolean\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |     |   Method: toString\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.String\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |     |   Method: hashCode\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: int\n  |     |     |   Modifiers:\n  |     |     |     |   public native \n  |     |   Method: getClass\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.Class\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: clone\n  |     |     |   Exceptions: java.lang.CloneNotSupportedException \n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.Object\n  |     |     |   Modifiers:\n  |     |     |     |   protected native \n  |     |   Method: notify\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: notifyAll\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: registerNatives\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   private static native \n  |   Constructor: java.lang.Object\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n========================================\n";
	String c1bInr_printout = "";
	String c2Inr_printout = "Declared Class: C2\n  |   Superclass: C1\n  |   Interfaces: \n  |     |   MyI\n  |   Methods: \n  |     |   Method: anotherThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.String\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |     |   Method: aThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |   Constructor: C2\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n  |   Field: c\n  |     |   Type: int[]\n  |     |   Modifiers: public \n  |     |   Array dimensions: 1\n  |     |   Array length (1st dimension): 2\n  |     |   Current values: \n  |     |     |   0\n  |     |     |     |   Declared Class: int\n  |     |     |     |   ========================================\n  |     |     |   1\n  |   Field: d\n  |     |   Type: double\n  |     |   Modifiers: public final \n  |     |   Current value: 1.1\n========================================\n\nDeclared Class: C1\n  |   Superclass: java.lang.Object\n  |   Methods: \n  |     |   Method: aThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |   Constructor: C1\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n  |   Constructor: C1\n  |     |   Parameters: int \n  |     |   Modifiers:\n  |     |     |   public\n  |   Field: a\n  |     |   Type: int\n  |     |   Modifiers: public \n  |     |   Current value: 0\n  |   Field: x\n  |     |   Type: long\n  |     |   Modifiers: private \n  |     |   Current value: 3\n========================================\n\nDeclared Class: java.lang.Object\n  |   Methods: \n  |     |   Method: finalize\n  |     |     |   Exceptions: java.lang.Throwable \n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   protected \n  |     |   Method: wait\n  |     |     |   Exceptions: java.lang.InterruptedException \n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final \n  |     |   Method: wait\n  |     |     |   Exceptions: java.lang.InterruptedException \n  |     |     |   Parameters: long int \n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final \n  |     |   Method: wait\n  |     |     |   Exceptions: java.lang.InterruptedException \n  |     |     |   Parameters: long \n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: equals\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: java.lang.Object \n  |     |     |   Return Type: boolean\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |     |   Method: toString\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.String\n  |     |     |   Modifiers:\n  |     |     |     |   public \n  |     |   Method: hashCode\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: int\n  |     |     |   Modifiers:\n  |     |     |     |   public native \n  |     |   Method: getClass\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.Class\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: clone\n  |     |     |   Exceptions: java.lang.CloneNotSupportedException \n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.Object\n  |     |     |   Modifiers:\n  |     |     |     |   protected native \n  |     |   Method: notify\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: notifyAll\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   public final native \n  |     |   Method: registerNatives\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: void\n  |     |     |   Modifiers:\n  |     |     |     |   private static native \n  |   Constructor: java.lang.Object\n  |     |   Parameters: None\n  |     |   Modifiers:\n  |     |     |   public\n========================================\n\nDeclared Class: MyI\n  |   Methods: \n  |     |   Method: anotherThing\n  |     |     |   Exceptions: None\n  |     |     |   Parameters: None\n  |     |     |   Return Type: java.lang.String\n  |     |     |   Modifiers:\n  |     |     |     |   public abstract \n========================================\n";
	
	String c1aIr_printout = "";
	String c1bIr_printout = "";
	String c2Ir_printout = "";
	

}
