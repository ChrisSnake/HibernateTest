package hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity (name="model")
@Table (name="tbl_model")
public class Model
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int id;
	
	private String name;
	private int number;
	
	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "ts" )
	private Date date;
	
	public Model()
	{
		super();
	}
	
	@Override
	public String toString()
	{
		return "[ID: " + this.id + "; name: " + this.name + "; date: " + this.date + "]";
	}

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber( int number )
	{
		this.number = number;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}
	

}
