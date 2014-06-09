package org.darrylmiles.example.ee7.jca.eis.rar.cci;

import javax.resource.cci.ResourceAdapterMetaData;

import org.darrylmiles.example.ee7.jca.eis.rar.Constants;

public class ResourceAdapterMetaDataImpl implements ResourceAdapterMetaData {

	private String adapterVersion				= Constants.ADAPTER_VERSION;
	private String adapterVendorName			= Constants.ADAPTER_VENDOR_NAME;
	private String adapterName					= Constants.ADAPTER_NAME;
	private String adapterShortDescription		= Constants.ADAPTER_SHORT_DESCRIPTION;
	private String specVersion					= Constants.SPEC_VERSION;
	private String[] interactionSpecsSupported	= Constants.INTERACTION_SPECS_SUPPORTED;

			@Override
	public String getAdapterVersion() {
		return adapterVersion;
	}

	@Override
	public String getAdapterVendorName() {
		return adapterVendorName;
	}

	@Override
	public String getAdapterName() {
		return adapterName;
	}

	@Override
	public String getAdapterShortDescription() {
		return adapterShortDescription;
	}

	@Override
	public String getSpecVersion() {
		return specVersion;
	}

	@Override
	public String[] getInteractionSpecsSupported() {
		return interactionSpecsSupported;
	}

	/**
	 * @see InteractionImpl#execute(javax.resource.cci.InteractionSpec, javax.resource.cci.Record, javax.resource.cci.Record)
	 */
	@Override
	public boolean supportsExecuteWithInputAndOutputRecord() {
		return true;
	}

	/**
	 * @see InteractionImpl#execute(javax.resource.cci.InteractionSpec, javax.resource.cci.Record)
	 */
	@Override
	public boolean supportsExecuteWithInputRecordOnly() {
		return true;
	}

	@Override
	public boolean supportsLocalTransactionDemarcation() {
		// FIXME understand transactions
		return false;
	}

}
