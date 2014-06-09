package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.resource.cci.MappedRecord;

public class MappedRecordImpl implements MappedRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4717151056124282742L;

	private String recordName;
	private String description;
	@SuppressWarnings("rawtypes")
	private Map mapDelegate;

	@SuppressWarnings("rawtypes")
	public MappedRecordImpl(String recordName) {
		this.recordName = recordName;
		mapDelegate = new HashMap();
	}

	//////////////////////////////

	@Override
	public String getRecordName() {
		return recordName;
	}

	@Override
	public void setRecordName(String name) {
		this.recordName = name;
	}

	@Override
	public void setRecordShortDescription(String description) {
		this.description = description;
	}

	@Override
	public String getRecordShortDescription() {
		return description;
	}

	///////////////////////////// java.util.Map delegate /////////////////////////////

	public int size() {
		return mapDelegate.size();
	}

	public boolean isEmpty() {
		return mapDelegate.isEmpty();
	}

	public boolean containsKey(Object key) {
		return mapDelegate.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return mapDelegate.containsValue(value);
	}

	public Object get(Object key) {
		return mapDelegate.get(key);
	}

	@SuppressWarnings("unchecked")
	public Object put(Object key, Object value) {
		return mapDelegate.put(key, value);
	}

	public Object remove(Object key) {
		return mapDelegate.remove(key);
	}

	@SuppressWarnings("unchecked")
	public void putAll(@SuppressWarnings("rawtypes") Map m) {
		mapDelegate.putAll(m);
	}

	public void clear() {
		mapDelegate.clear();
	}

	@SuppressWarnings("rawtypes")
	public Set keySet() {
		return mapDelegate.keySet();
	}

	@SuppressWarnings("rawtypes")
	public Collection values() {
		return mapDelegate.values();
	}

	@SuppressWarnings("rawtypes")
	public Set entrySet() {
		return mapDelegate.entrySet();
	}

	public boolean equals(Object o) {
		return mapDelegate.equals(o);
	}

	public int hashCode() {
		return mapDelegate.hashCode();
	}

	///////////////////////////// java.io.Cloneable /////////////////////////////

	public Object clone() {
		MappedRecordImpl dest = new MappedRecordImpl(getRecordName());
		copyTo(dest);
		return dest;
	}

	public void copyTo(MappedRecordImpl dest) {
		setRecordName(getRecordName());
		setRecordShortDescription(getRecordShortDescription());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("recordName=").append(recordName);
		sb.append(";description=").append(description);
		return sb.toString();
	}
}
