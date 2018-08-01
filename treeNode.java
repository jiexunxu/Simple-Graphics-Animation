public class treeNode<E> extends DNode{
	private doublyLinkedList<E> children;
	private treeNode<E> parent;
	
	public treeNode(E e, doublyLinkedList<E> l, treeNode<E> p){
		super(e);
		children=l;
		parent=p;
	}
	
	public treeNode(E e, treeNode<E> p){
		super(e);		
		parent=p;
		children=new doublyLinkedList<E>();
	}
	
	public treeNode(E e){
		super(e);
		element=e;
		children=new doublyLinkedList<E>();
		parent=null;
	}
	
	public boolean isExternal(){
		return children.isEmpty();
	}
	
	public childrenIterator<E> childrenIterator(){
		return new childrenIterator<E>((treeNode)children.getFront());
	}
	
	public void attachChild(treeNode<E> n){
		children.insertAtBack(n);
	}
	
	public void removeChild(treeNode<E> n){
		children.removeNode(n);
	}
	
	public void attachTree(Tree t){
		attachChild(t.root());
	}	
	
	public void removeTree(Tree t){
		removeChild(t.root());
	}
	
	public void attachTo(treeNode<E> t){
		t.attachChild(this);
	}
	
	public int childrenCount(){
		return children.getSize();
	}
}

class childrenIterator<E>{
	private treeNode<E> current;
	public childrenIterator(treeNode<E> n){
		current=n;
	}
	
	public boolean isValid(){
		return (current.getNext()!=null)&&(current.getPrev()!=null);
	}
	
	public treeNode<E> getNode(){
		return current;
	}
	
	public void moveForward(){
		current=(treeNode)(current.getNext());
	}
	
	public void moveBack(){
		current=(treeNode)(current.getPrev());		
	}
}