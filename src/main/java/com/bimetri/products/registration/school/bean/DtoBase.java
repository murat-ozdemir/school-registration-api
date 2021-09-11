package com.bimetri.products.registration.school.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class DtoBase implements Serializable {
	private static final long serialVersionUID = -8348607091728382721L;

	private boolean recursive = false;

	@Override
	public String toString ()
	{
		Class < ? > clazz = this.getClass();
		StringBuilder sb = new StringBuilder( clazz.getSimpleName() ).append( " [" );
		while ( clazz != null && !clazz.equals( Object.class ) )
		{
			Field [] fields = clazz.getDeclaredFields();
			for ( Field field: fields )
			{
				if ( !Modifier.isStatic( field.getModifiers() ) && !field.getName().contains( "serialVersionUID" ) )
				{
					try
					{
						field.setAccessible( true );
						Object value = field.get( this );
						if ( value != null )
						{
							sb.append( field.getName() ).append( "=" ).append( value ).append( "|" );
						}
						field.setAccessible( false );
					}
					catch ( IllegalAccessException e )
					{
						e.printStackTrace();
					}
				}
			}

			if ( !recursive )
			{
				break;
			}
			clazz = clazz.getSuperclass();
		}

		int index = sb.lastIndexOf( "," );
		if ( index > -1 )
			sb.deleteCharAt( index );
		return sb.append( "]" ).toString();
	}
	
	public void importValues (DtoBase objectToCopy)
	{
		if ( this.getClass().isInstance( objectToCopy ) )
		{
			Class < ? > clazz = this.getClass();
			while ( clazz != null && !clazz.equals( Object.class ) )
			{
				Field [] fields = clazz.getDeclaredFields();
				for ( Field field: fields )
				{
					if ( !Modifier.isStatic( field.getModifiers() ) && !field.getName().contains( "serialVersionUID" ) )
					{
						try
						{
							field.setAccessible( true );
							Object valueToCopy = field.get( objectToCopy );
							if ( valueToCopy != null )
							{
								field.set( this, valueToCopy );
							}
							field.setAccessible( false );
						}
						catch ( IllegalAccessException e )
						{
							e.printStackTrace();
						}
					}
				}
				
				clazz = clazz.getSuperclass();
			}
			
		}
	}

	public void convertEmptyStringsToNull()
	{
		Class < ? > clazz = this.getClass();
		while ( clazz != null && !clazz.equals( Object.class ) )
		{
			Field [] fields = clazz.getDeclaredFields();
			for ( Field field: fields )
			{
				if ( !Modifier.isStatic( field.getModifiers() ) && !field.getName().contains( "serialVersionUID" ) )
				{
					try
					{
						field.setAccessible( true );
						Object value = field.get( this );
						if ( value != null && field.getClass().equals(String.class) && ((String) value).trim().equals("") )
						{
							field.set(this, null);
						}
						field.setAccessible( false );
					}
					catch ( IllegalAccessException e )
					{
						e.printStackTrace();
					}
				}
			}

			if ( !recursive )
			{
				break;
			}
			clazz = clazz.getSuperclass();
		}
	}
}
