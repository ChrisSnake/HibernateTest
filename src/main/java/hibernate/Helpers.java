package hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Helpers
{
	/**
	 * 
	 * Creates a date object
	 * 
	 * @param year
	 * @param month 1...12
	 * @param day 1...31
	 * @param hour 0...23
	 * @param min 0...59
	 * @param sec 0...59
	 * @return
	 */
	public static Date getDate( int year, int month, int day, int hour, int min, int sec )
	{
		if ( month <= 0 || month > 12 || day <= 0 || day > 31 || hour < 0 || hour > 23 || min < 0 || min > 59 || sec < 0
				|| sec > 59 )
			return null;

		Calendar cl = new GregorianCalendar();
		cl.clear();
		cl.set( year, month - 1, day, hour, min, sec );
		Date dt = cl.getTime();

		return dt;
	}

	/**
	 * fills db with test values
	 * 
	 */
	public static void populateDb()
	{
		SessionFactory sessFact = null;
		Session session = null;
		try
		{
			int day = 0;
			SimpleDateFormat dateFormat = new SimpleDateFormat( "EEEE", Locale.GERMAN );
			sessFact = HibernateUtil.getSessionFactory();
			session = sessFact.openSession();

			for ( day = 1; day <= 10; day++ )
			{
				Model m = new Model();

				m.setNumber( day );
				m.setDate( Helpers.getDate( 2017, 5, day, 12, 13, 14 ) );
				String wDay = dateFormat.format( m.getDate() );
				m.setName( wDay );

				org.hibernate.Transaction tr = session.beginTransaction();
				session.save( m );
				tr.commit();
			}

		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if ( session != null && session.isOpen() )
				session.close();
		}
	}

	/**
	 * Fills more testdata to db, with millisecond precision as date (not to be
	 * seen in DB)
	 */
	public static void populateDb2()
	{
		SessionFactory sessFact = null;
		Session session = null;
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
		Date dt = null;

		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat( "EEEE", Locale.GERMAN );
			sessFact = HibernateUtil.getSessionFactory();
			session = sessFact.openSession();

			// gets rounded down
			dt = df.parse( "2017-05-25 04:05:06.400" );
			Model m = new Model();
			m.setNumber( 400 );
			m.setDate( dt );
			String wDay = dateFormat.format( m.getDate() );
			m.setName( wDay );
			org.hibernate.Transaction tr = session.beginTransaction();
			session.save( m );
			tr.commit();

			// gets rounded down
			dt = df.parse( "2017-05-25 04:05:06.499" );
			m = new Model();
			m.setNumber( 499 );
			m.setDate( dt );
			wDay = dateFormat.format( m.getDate() );
			m.setName( wDay );
			tr = session.beginTransaction();
			session.save( m );
			tr.commit();

			// gets rounded up
			dt = df.parse( "2017-05-25 04:05:06.500" );
			m = new Model();
			m.setNumber( 500 );
			m.setDate( dt );
			wDay = dateFormat.format( m.getDate() );
			m.setName( wDay );
			tr = session.beginTransaction();
			session.save( m );
			tr.commit();

			// gets rounded up
			dt = df.parse( "2017-05-25 04:05:06.501" );
			m = new Model();
			m.setNumber( 501 );
			m.setDate( dt );
			wDay = dateFormat.format( m.getDate() );
			m.setName( wDay );
			tr = session.beginTransaction();
			session.save( m );
			tr.commit();

			// gets rounded up
			dt = df.parse( "2017-05-25 04:05:06.600" );
			m = new Model();
			m.setNumber( 600 );
			m.setDate( dt );
			wDay = dateFormat.format( m.getDate() );
			m.setName( wDay );
			tr = session.beginTransaction();
			session.save( m );
			tr.commit();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( session != null && session.isOpen() )
				session.close();
		}
	}

	public static Model findModelById( int id )
	{
		Model m = null;

		Session session = null;
		SessionFactory sessFact = null;
		try
		{
			sessFact = HibernateUtil.getSessionFactory();
			session = sessFact.openSession();

			org.hibernate.Transaction tr = session.beginTransaction();
			m = session.createQuery( "select m from model m where m.id = :id", Model.class ).setParameter( "id", id )
					.getSingleResult();

			tr.commit();
		}
		catch (NoResultException e)
		{
			System.out.println( "nothing found" );
		}

		catch (HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if ( session != null && session.isOpen() )
				session.close();
		}

		return m;
	}
	

	/**
	 * Search model by name
	 * 
	 * @param name name to search for
	 * @return
	 */
	public static Model findModelByName( String name )
	{
		Model m = null;

		Session session = null;
		SessionFactory sessFact = null;
		try
		{
			sessFact = HibernateUtil.getSessionFactory();
			session = sessFact.openSession();

			org.hibernate.Transaction tr = session.beginTransaction();
			List<Model> list = session.createQuery( "select m from model m where m.name = :name", Model.class )
					.setParameter( "name", name ).getResultList();
			tr.commit();

			// only 1 match needed
			if ( !list.isEmpty() )
				m = list.get( list.size() - 1 );

		}
		catch (NoResultException e)
		{
			System.out.println( "nothing found" );
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if ( session != null && session.isOpen() )
				session.close();
		}

		return m;

	}

	/**
	 * Search model by date
	 * 
	 * @param d date to search for
	 * @return list with models; on error: null
	 */
	public static List<Model> findModelsByDate( Date d )
	{
		List<Model> list = null;

		Session session = null;
		SessionFactory sessFact = null;
		try
		{
			sessFact = HibernateUtil.getSessionFactory();
			session = sessFact.openSession();

			org.hibernate.Transaction tr = session.beginTransaction();
			// der Name einer Spalte bezieht sich auf das
			// Hibernate-Modell,
			// also auf das Pojo, nicht auf den Namen in der Datenbank
			list = session.createQuery( "select m from model m where m.date = :ts", Model.class )
					.setParameter( "ts", d ).getResultList();
			tr.commit();
		}
		catch (NoResultException e)
		{
			System.out.println( "nothing found" );
		}

		catch (HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if ( session != null && session.isOpen() )
				session.close();
		}

		return list;

	}

}
