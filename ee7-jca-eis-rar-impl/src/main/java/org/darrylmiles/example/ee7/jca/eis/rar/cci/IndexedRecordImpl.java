package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.resource.cci.IndexedRecord;

public class IndexedRecordImpl implements IndexedRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1155988988187374142L;

	private String recordName;
	private String description;
	@SuppressWarnings("rawtypes")
	private List listDelegate;

	@SuppressWarnings("rawtypes")
	public IndexedRecordImpl(String recordName) {
		this.recordName = recordName;
		listDelegate = new ArrayList();
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
		return listDelegate.size();
	}

	public boolean isEmpty() {
		return listDelegate.isEmpty();
	}

	public boolean contains(Object o) {
		return listDelegate.contains(o);
	}

	@SuppressWarnings("rawtypes")
	public Iterator iterator() {
		return listDelegate.iterator();
	}

	public Object[] toArray() {
		return listDelegate.toArray();
	}

	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
		return listDelegate.toArray(a);
	}

	@SuppressWarnings("unchecked")
	public boolean add(Object e) {
		return listDelegate.add(e);
	}

	public boolean remove(Object o) {
		return listDelegate.remove(o);
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(@SuppressWarnings("rawtypes") Collection c) {
		return listDelegate.containsAll(c);
	}

	@SuppressWarnings("unchecked")
	public boolean addAll(@SuppressWarnings("rawtypes") Collection c) {
		return listDelegate.addAll(c);
	}

	@SuppressWarnings("unchecked")
	public boolean addAll(int index, @SuppressWarnings("rawtypes") Collection c) {
		return listDelegate.addAll(index, c);
	}

	@SuppressWarnings("unchecked")
	public boolean removeAll(@SuppressWarnings("rawtypes") Collection c) {
		return listDelegate.removeAll(c);
	}

	@SuppressWarnings("unchecked")
	public boolean retainAll(@SuppressWarnings("rawtypes") Collection c) {
		return listDelegate.retainAll(c);
	}

	public void clear() {
		listDelegate.clear();
	}

	public boolean equals(Object o) {
		return listDelegate.equals(o);
	}

	public int hashCode() {
		return listDelegate.hashCode();
	}

	public Object get(int index) {
		return listDelegate.get(index);
	}

	@SuppressWarnings("unchecked")
	public Object set(int index, Object element) {
		return listDelegate.set(index, element);
	}

	@SuppressWarnings("unchecked")
	public void add(int index, Object element) {
		listDelegate.add(index, element);
	}

	public Object remove(int index) {
		return listDelegate.remove(index);
	}

	public int indexOf(Object o) {
		return listDelegate.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return listDelegate.lastIndexOf(o);
	}

	@SuppressWarnings("rawtypes")
	public ListIterator listIterator() {
		return listDelegate.listIterator();
	}

	@SuppressWarnings("rawtypes")
	public ListIterator listIterator(int index) {
		return listDelegate.listIterator(index);
	}

	@SuppressWarnings("rawtypes")
	public List subList(int fromIndex, int toIndex) {
		return listDelegate.subList(fromIndex, toIndex);
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
