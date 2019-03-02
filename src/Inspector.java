import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;


public class Inspector {
	List<Class> toInspect = new LinkedList<Class>();
	List<Class> alreadyInspected = new LinkedList<Class>();

	public void inspect(Object obj, boolean recursive) {
		String str = "";
		Class classObj = obj.getClass();
		alreadyInspected.add(classObj);
		
		str += "Declaring Class: " + classObj.getName() + "\n";
		
		Class superCl = classObj.getSuperclass();
		if (superCl != null) {
			str += "Superclass: " + superCl.getName() + "\n";
		}
		Class[] interfaceCl = classObj.getInterfaces();
		for (int i = 0; i < interfaceCl.length; i++) {
			str += "Interface: " + interfaceCl[i].getName() + "\n";
		}
		
		
		
		
		
		
		
		
		System.out.println(str);
	}
}
