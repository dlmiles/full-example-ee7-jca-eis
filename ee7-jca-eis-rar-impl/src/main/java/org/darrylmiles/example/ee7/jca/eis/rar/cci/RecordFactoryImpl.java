package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.ResourceException;
import javax.resource.cci.IndexedRecord;
import javax.resource.cci.MappedRecord;
import javax.resource.cci.RecordFactory;

public class RecordFactoryImpl implements RecordFactory {

	@Override
	public MappedRecord createMappedRecord(String recordName) throws ResourceException {
		MappedRecord mappedRecord = new MappedRecordImpl(recordName);
		return mappedRecord;
	}

	@Override
	public IndexedRecord createIndexedRecord(String recordName) throws ResourceException {
		IndexedRecord indexedRecord = new IndexedRecordImpl(recordName);
		return indexedRecord;
	}

}
