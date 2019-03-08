public class C2 extends C1 implements MyI {
		public int[] c = new int[2];
		public final double d = 1.1;
		public C2() {
			for (int i = 0; i < c.length; i++) {
				c[i] = i;
			}
		}
		public void aThing() {
		}
		@Override
		public String anotherThing() {
			return null;
		}
	}