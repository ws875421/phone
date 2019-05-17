package android.ord.model;

import java.util.List;

public interface OrdDAO_interface {


	public boolean isOrd(String ord_no, String MEM_NO, String vendor_no, String VERIF_CODE);
	
	public List<OrdVO> getord(String MEM_NO);

}
