import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Inspector {
	List<Class> alreadyInspected = new LinkedList<Class>();
	
	public void inspect(Object obj, boolean recursive) {
		String str;
		if (obj == null) {
			str = "Object provided is NULL\n";
		}
		else {
			Class classObj = obj.getClass();
			str = inspectWorker(classObj, obj, recursive, 0);
		}
		System.out.println(str);
	}

	public String inspectWorker(Class classObj, Object obj, boolean recursive, int ind) {
		if (obj == null) {
			return "NULL\n";
		}
		
		List<Class> toInspect = new ArrayList<Class>();
		String str = "";

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
			methods[i].setAccessible(true);
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
		
		
		Constructor[] constr = classObj.getDeclaredConstructors();
		for (int i = 0; i < constr.length; i++) {
			constr[i].setAccessible(true);
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
			fields[i].setAccessible(true);
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
			
			try {
				if (fields[i].get(obj).getClass().isArray()) {
					//TODO make this work with arrays
					Object arr = fields[i].get(obj);
					int len = Array.getLength(arr);
					str += indent(ind + 2) + "Array length: " + len + "\n";
					str += indent(ind + 2) + "Current values: \n";
					for (int k = 0; k < len; k++) {
						str += indent(ind + 3) + Array.get(arr, k).toString() + "\n";
						if (recursive && !(Array.get(arr, k).getClass().isPrimitive())) {
							str += inspectWorker(Array.get(arr, k).getClass(), arr, recursive, ind + 4);
						}
					}
				} 
				else {
					str += indent(ind + 2) + "Current value: " + fields[i].get(obj).toString();
					if (recursive && !(fields[i].get(obj).getClass().isPrimitive())) {
						str += inspectWorker(fields[i].get(obj).getClass(), fields[i].get(obj), recursive, ind + 3);
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < toInspect.size(); i++) {
			//Object supObj = createSuperObjFromClass(toInspect.get(i), obj);
			str += "\n" + inspectWorker(toInspect.get(i), obj, recursive, ind);
		}
		
		
		
		
		return str;
	}
	
	/*
	public Object createSuperObjFromClass(Class cl, Object obj) {
		Object supObj = null;
		try {
			Constructor c = cl.getConstructor(null);
			c.setAccessible(true);
			supObj = c.newInstance(null);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Field[] fields = cl.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				fields[i].set(supObj, fields[i].get(obj));
			} catch (IllegalArgumentException e) {
				//do nothing
			} catch (IllegalAccessException e) {
				// should not happen
				e.printStackTrace();
			}
		}
		
		return supObj;
	}
	//*/
	
	public String indent(int numIndents) {
		String str = "";
		for (int i = 0; i < numIndents; i++) {
			str += "   ";
		}
		return str;
	}
}
