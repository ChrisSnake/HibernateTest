package hibernate;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class Main
{

	public static void main( String[] args )
	{

		// deactivate Hibernate logging
		// even more silence: Level.OFF
		java.util.logging.Logger.getLogger( "org.hibernate" ).setLevel( Level.WARNING ); 

		List<Model> list = null;
		
		// fill DB with test values
		Helpers.populateDb();
		Helpers.populateDb2();

		// search for values
		int nr = 1;
		System.out.println( "Suche nach ID " + nr + "..." );
		Model m = Helpers.findModelById( 1 );
		System.out.println( "m: " + m );
		System.out.println( "" );

		nr = 5;
		System.out.println( "Suche nach ID " + nr + "..." );
		m = Helpers.findModelById( nr );
		System.out.println( "m: " + m );
		System.out.println( "" );

		System.out.println( "Suche nach Montag..." );
		m = Helpers.findModelByName( "Montag" );
		System.out.println( "m: " + m );
		System.out.println( "" );

		System.out.println( "Suche nach 06.05.2017..." );
		Date d = Helpers.getDate( 2017, 5, 6, 12, 13, 14 );
		list = Helpers.findModelsByDate( d );
		for ( Model model : list )
			System.out.println( "m: " + model );
		System.out.println( "" );
		
		System.out.println( "Suche nach 25.05.2017, 04:05:06..." );
		// 2017-05-25 04:05:06.499
		d = Helpers.getDate( 2017, 5, 25, 4, 5, 6 );
		list = Helpers.findModelsByDate( d );
		for ( Model model : list )
			System.out.println( "m: " + model );
		System.out.println( "" );

		System.out.println( "Suche nach 25.05.2017, 04:05:07..." );
		// 2017-05-25 04:05:06.500
		d = Helpers.getDate( 2017, 5, 25, 4, 5, 7 );
		list = Helpers.findModelsByDate( d );
		for ( Model model : list )
			System.out.println( "m: " + model );
		System.out.println( "" );

		// DB-Session muss geschlossen werden...
		if ( HibernateUtil.getSessionFactory() != null )
			HibernateUtil.getSessionFactory().close();

	}

}
