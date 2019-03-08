import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Inspector {
	List<Class<?>> alreadyInspected = new LinkedList<Class<?>>();
	
	public void inspect(Object obj, boolean recursive) {
		String str;
		if (obj == null) {
			str = "Object provided is NULL\n";
		}
		else {
			Class<?> classObj = obj.getClass();
			str = inspectWorker(classObj, obj, recursive, 0);
		}
		System.out.println(str);
	}

	public String inspectWorker(Class<?> classObj, Object obj, boolean recursive, int ind) {
		/*if (obj == null) {
			return "NULL\n";
		}//*/
		
		List<Class<?>> toInspect = new ArrayList<Class<?>>();
		String str = "";

		alreadyInspected.add(classObj);
		
		str += indent(ind) + "Declared Class: " + classObj.getName() + "\n";
		
		Class<?> superCl = classObj.getSuperclass();
		if (superCl != null) {
			str += indent(ind + 1) + "Superclass: " + superCl.getName() + "\n";
			toInspect.add(superCl);
		}
		
		Class<?>[] interfaceCl = classObj.getInterfaces();
		if (interfaceCl.length > 0) {
			str += indent(ind + 1) + "Interfaces: \n";
		}
		for (int i = 0; i < interfaceCl.length; i++) {
			str += indent(ind + 2) + interfaceCl[i].getName() + "\n";
			toInspect.add(interfaceCl[i]);
		}
		
		if (classObj.isArray() && hasNotInspected(classObj.getComponentType())) {
			str += inspectArray(recursive, ind, classObj, obj);
		}//*/
		
		Method[] methods = classObj.getDeclaredMethods();
		if (methods.length > 0) {
			str += indent(ind + 1) + "Methods: \n";
		}
		for (int i = 0; i < methods.length; i++) {
			methods[i].setAccessible(true);
			str += indent(ind + 2) + "Method: " + methods[i].getName() + "\n";
			
			Class<?>[] exceps = methods[i].getExceptionTypes();
			str += indent(ind + 3) + "Exceptions: ";
			if (exceps.length == 0) {
				str += "None\n";
			}
			else {
				for (int j = 0; j < exceps.length; j++) {
					str += exceps[j].getName() + " ";
				}
				str += "\n";
			}
			
			Class<?>[] params = methods[i].getParameterTypes();
			str += indent(ind + 3) + "Parameters: ";
			if (params.length == 0) {
				str += "None\n";
			}
			else {
				for (int j = 0; j < params.length; j++) {
					str += params[j].getTypeName() + " ";
				}
				str += "\n";
			}
			
			Class<?> metRet = methods[i].getReturnType();
			str += indent(ind + 3) + "Return Type: " + metRet.getTypeName() + "\n";
			
			String[] mods = (Modifier.toString(methods[i].getModifiers())).split(" ");
			str += indent(ind + 3) + "Modifiers:\n";
			if (mods.length == 0) {
				str += indent(ind + 4) + "None\n";
			}
			else {
				str += indent(ind + 4);
				for (int j = 0; j < mods.length; j++) {
					str += mods[j] + " ";
				}
				str += "\n";
			}
		}
		
		
		Constructor<?>[] constr = classObj.getDeclaredConstructors();
		for (int i = 0; i < constr.length; i++) {
			if (Modifier.isPrivate(constr[i].getModifiers())) {
				//constr[i].setAccessible(true);
			}
			str += indent(ind + 1) + "Constructor: " + constr[i].getName() + "\n";
			
			Class<?>[] params = constr[i].getParameterTypes();
			str += indent(ind + 2) + "Parameters: ";
			if (params.length == 0) {
				str += "None\n";
			}
			else {
				for (int j = 0; j < params.length; j++) {
					str += params[j].getTypeName() + " ";
				}
				str += "\n";
			}
			
			String[] mods = (Modifier.toString(constr[i].getModifiers())).split(" ");
			str += indent(ind + 2) + "Modifiers:\n";
			if (mods.length == 0) {
				str += indent(ind + 3) + "None\n";
			}
			else {
				if (mods.length >= 0) {
					str += indent(ind + 3) + mods[0];
				}
				for (int j = 1; j < mods.length; j++) {
					str += ", " + mods[j];
				}
				str += "\n";
			}
		}
		
		
		Field[] fields = classObj.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			str += indent(ind + 1) + "Field: " + fields[i].getName() + "\n";
			
			Class<?> type = fields[i].getType();
			str += indent(ind + 2) + "Type: " + type.getTypeName() + "\n";
			
			String[] mods = (Modifier.toString(fields[i].getModifiers())).split(" ");
			str += indent(ind + 2) + "Modifiers: ";
			if (mods.length == 0) {
				str += "None\n";
			}
			else {
				for (int j = 0; j < mods.length; j++) {
					str += mods[j] + " ";
				}
				str += "\n";
			}
			
			try {
				if (obj == null || fields[i] == null || fields[i].get(obj) == null) {
					str += indent(ind + 2) + "Current value: null\n"; 
				}//*/
				else if (fields[i].getType().isArray()) {
					Object arr = fields[i].get(obj);
					str += inspectArray(recursive, ind, fields[i].getType().getComponentType(), arr);
				} 
				else {
					if (fields[i].get(obj) == null) {
						str += indent(ind + 2) + "Current value: null\n";
					}
					str += indent(ind + 2) + "Current value: " + fields[i].get(obj).toString() + "\n";
					if (recursive && !(isPrimOrWrapper(fields[i].getType()))
							&& hasNotInspected(fields[i].getType())) {
						str += inspectWorker(fields[i].get(obj).getClass(), fields[i].get(obj), recursive, ind + 3);
					}//*/
				}
			} catch (IllegalArgumentException e) {
				System.out.println("IllegalArgumentException classObj: " + classObj.toString() 
															+ "field: " + fields[i].toString() 
															+ ", obj: " + obj.toString() + "\n");
				e.printStackTrace();
				System.exit(-1);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				//System.exit(-1);
			}
		}
		
		str += indent(ind) + "========================================\n";
		
		///*
		for (int i = 0; i < toInspect.size(); i++) {
			Object supObj = createSuperObjFromClass(toInspect.get(i), obj);
			if (supObj == null) {
				supObj = obj;
			}
			else if (hasNotInspected(toInspect.get(i))) {
				str += "\n" + inspectWorker(toInspect.get(i), 
											supObj, 
											recursive, 
											ind);
			}
		}
		//*/
		return str;
	}

	public String inspectArray(boolean recursive, int ind, Class<?> arrayClass, Object arr) {
		String str = "";
		Class<?> arrayType = arrayClass.getComponentType();
		if (arr != null) {
			str += indent(ind + 2) + "Array dimensions: " + getDim(arr) + "\n";
		}
		int len = 0;
		if (arr != null) {
			len = Array.getLength(arr);
			str += indent(ind + 2) + "Array length (1st dimension): " + len + "\n";
		}
		else {
			str += indent(ind + 2) + "Array length: could not be determined (array is null)\n";
		}//*/
		str += indent(ind + 2) + "Current values: \n";
		for (int k = 0; k < len; k++) {
			if (Array.get(arr, k) == null) 
				str += indent(ind + 3) + "null \n";
			else {
				str += indent(ind + 3) + Array.get(arr, k).toString() + "\n";
			}
			if (Array.get(arr, k) != null && hasNotInspected(arrayType)) {
				str += inspectWorker(arrayType, null, recursive, ind + 4);
			}
			else if (recursive && (Array.get(arr, k) != null) 
					&& !(isPrimOrWrapper(Array.get(arr, k).getClass())) 
					&& hasNotInspected(Array.get(arr, k).getClass())) {
				str += inspectWorker(Array.get(arr, k).getClass(), Array.get(arr, k), recursive, ind + 4);
			}//*/
		}
		return str;
	}
	
	//public int getDim(Class<?> arrayClass) {
	public int getDim(Object array) {
	    int dim = 0;
	    Class<?> cl = array.getClass();
	    while (cl.isArray()) {
	      dim++;
	      cl = cl.getComponentType();
	    }
	    return dim;
	  }
	
	public boolean hasNotInspected(Class<?> c) {
		boolean ret = true; 
		
		for (int i = 0; i < alreadyInspected.size() && ret; i++) {
			if (alreadyInspected.get(i).equals(c)) {
				ret = false;
			}
		}
		
		return ret;
	}
	
	///*
	public Object createSuperObjFromClass(Class<?> cl, Object obj) {
		Object supObj = null;
		if (cl.isInterface() || obj == null) {
			return supObj;
		}
		try {
			Constructor<?> c = cl.getDeclaredConstructor(new Class<?>[] {});
			c.setAccessible(true);
			supObj = c.newInstance(new Object[] {});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.exit(-1);//*/
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.exit(-1);
		} catch (InstantiationException e) {
			/*System.out.println(cl.getName());
			System.out.println("interface?: " + cl.isInterface());
			e.printStackTrace();
			System.exit(-1);*/
			// if could not be create instance, return null.
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.exit(-1);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.exit(-1);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.exit(-1);//*/
		}
		
		Field[] fields = cl.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				Object temp = obj.getClass().getDeclaredField(fields[i].getName()).get(obj);
				fields[i].set(supObj, temp);
			} catch (IllegalArgumentException e) {
				//do nothing
			} catch (IllegalAccessException e) {
				//e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// do nothing
				//e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return supObj;
	}
	//*/
	
	public String indent(int numIndents) {
		String str = "";
		for (int i = 0; i < numIndents; i++) {
			str += "  |   ";
		}
		return str;
	}
	
	public boolean isPrimOrWrapper(Class<?> c) {
		return ((c.isPrimitive() && c != void.class) ||
		        c == Double.class || c == Float.class || c == Long.class ||
		        c == Integer.class || c == Short.class || c == Character.class ||
		        c == Byte.class || c == Boolean.class);
	}
}
