package org.d3s.alricg.held;

public interface HeldProzessor {
	
	boolean canAddElement(Object element);

	boolean canRemoveElement(Object element); 
	
	void addElement(Object element);
	
	void removeElement(Object element);
	
	int getMaxWert(Object element);

	int getMinWert(Object element);
}
