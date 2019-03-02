import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;


public class Inspector {
	List<Class> toInspect = new LinkedList<Class>();
	List<Class> alreadyInspected = new LinkedList<Class>();
	
	public void inspect(Object obj, boolean recursive) {
		inspectWorker(obj, recursive, 0);
	}

	public void inspectWorker(Object obj, boolean recursive, int ind) {
		String str = "";
		Class classObj = obj.getClass();
		alreadyInspected.add(classObj);
		
		str += indent(ind) + "Declaring Class: " + classObj.getName() + "\n";
		
		Class superCl = classObj.getSuperclass();
		if (superCl != null) {
			str += indent(ind + 1) + "Superclass: " + superCl.getName() + "\n";
			toInspect.add(superCl);
		}
		
		Class[] interfaceCl = classObj.getInterfaces();
		if (interfaceCl.length > 0) {
			str += indent(ind + 1) + "Interfaces: \n";
		}
		for (int i = 0; i < interfaceCl.length; i++) {
			str += indent(ind + 2) + interfaceCl[i].getName() + "\n";
			toInspect.add(interfaceCl[i]);
		}
		
		Method[] methods = classObj.getDeclaredMethods();
		if (methods.length > 0) {
			str += indent(ind + 1) + "Methods: \n";
		}
		for (int i = 0; i < methods.length; i++) {
			str += indent(ind + 2) + "Method: " + methods[i].getName() + "\n";
			
			Class[] exceps = methods[i].getExceptionTypes();
			str += indent(ind + 3) + "Exceptions:\n";
			if (exceps.length == 0) {
				str += indent(ind + 4) + "None\n";
			}
			else {
				for (int j = 0; j < exceps.length; j++) {
					str += indent(ind + 4) + exceps[j].getName() + "\n";
				}
			}
			
			Class[] params = methods[i].getParameterTypes();
			str += indent(ind + 3) + "Parameters:\n";
			if (params.length == 0) {
				str += indent(ind + 4) + "None\n";
			}
			else {
				for (int j = 0; j < params.length; j++) {
					str += indent(ind + 4) + params[j].getTypeName() + ": " + params[j].getName() + "\n";
				}
			}
			
			Class metRet = methods[i].getReturnType();
			str += indent(ind + 3) + "Return Type: " + metRet.getTypeName() + "\n";
			
			String[] mods = (Modifier.toString(methods[i].getModifiers())).split(" ");
			str += indent(ind + 3) + "Modifiers:\n";
			if (mods.length == 0) {
				str += indent(ind + 4) + "None\n";
			}
			else {
				for (int j = 0; j < mods.length; j++) {
					str += indent(ind + 4) + mods[j] + "\n";
				}
			}
		}
		
		
		Constructor[] constr = classObj.getConstructors();
		for (int i = 0; i < constr.length; i++) {
			str += indent(ind + 1) + "Constructor: " + constr[i].getName() + "\n";
			
			Class[] params = constr[i].getParameterTypes();
			str += indent(ind + 2) + "Parameters:\n";
			if (params.length == 0) {
				str += indent(ind + 3) + "None\n";
			}
			else {
				for (int j = 0; j < params.length; j++) {
					str += indent(ind + 3) + params[j].getTypeName() + ": " + params[j].getName() + "\n";
				}
			}
			
			String[] mods = (Modifier.toString(constr[i].getModifiers())).split(" ");
			str += indent(ind + 2) + "Modifiers:\n";
			if (mods.length == 0) {
				str += indent(ind + 3) + "None\n";
			}
			else {
				for (int j = 0; j < mods.length; j++) {
					str += indent(ind + 3) + mods[j] + "\n";
				}
			}
		}
		
		
		Field[] fields = classObj.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			str += indent(ind + 1) + "Field: " + fields[i].getName() + "\n";
			
			Class type = fields[i].getType();
			str += indent(ind + 2) + "Type: " + type.getTypeName() + "\n";
			
			String[] mods = (Modifier.toString(fields[i].getModifiers())).split(" ");
			str += indent(ind + 2) + "Modifiers:\n";
			if (mods.length == 0) {
				str += indent(ind + 3) + "None\n";
			}
			else {
				for (int j = 0; j < mods.length; j++) {
					str += indent(ind + 3) + mods[j] + "\n";
				}
			}
			
			//TODO make this work with arrays
			
			if (!recursive) {
				str += indent(ind + 2) + "Current value: " + fields[i].toString();
			}
			else {
				//TODO  finish the recursive case
			}
		}
		
		
		
		
		
		
		System.out.println(str);
	}
	
	
	public String indent(int numIndents) {
		String str = "";
		for (int i = 0; i < numIndents; i++) {
			str += "    ";
		}
		return str;
	}
}
