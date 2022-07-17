package br.com.firedev.util;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<I extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2164745858816155953L;

	public abstract I getId();

	public abstract void setId(I id);

	public abstract String getDesc();
}
