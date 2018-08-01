public class DNode<E>{
	E element;
	DNode prev;
	DNode next;
	
	public DNode(E e, DNode p, DNode n){
		element=e;
		prev=p;
		next=n;
	}
	
	public DNode(E e){
		element=e;
	}
	
	public void setNext(DNode p){
		next=p;
	}
	
	public void setPrev(DNode p){
		prev=p;
	}
	
	public DNode getNext(){
		return next;
	}
	
	public DNode getPrev(){
		return prev;
	}
	
	public E element(){
		return element;
	}
	
	public void setElement(E e){
		element=e;
	}
}