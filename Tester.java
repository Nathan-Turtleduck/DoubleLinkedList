import java.util.ListIterator;

public class Tester {

	public static void main(String[] args) {
		
		String str = "cc";
		System.out.println(fun(str));
		

	}
	
	public static String fun( String str ) {

	    if ( str.charAt(0) == 'A' ) {

	        return str;

	    }

	    if ( str.charAt(0) == 'a' ) {

	        return fun( str.substring(1) + "A" );

	    }

	    return fun( (char)(str.charAt(0) - 1) + str.substring(1) );

	}

}
