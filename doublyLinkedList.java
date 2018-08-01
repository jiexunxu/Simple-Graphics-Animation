import java.util.*;

public class doublyLinkedList<E>{
	public DNode<E> header;
	public DNode<E> trailer;
	public int size;
	
	public boolean isEmpty(){
		return size==0;
	}
	
	public doublyLinkedList(){
		header=new DNode<E>(null);
		trailer=new DNode<E>(null);
		header.setNext(trailer);
		trailer.setPrev(header);
		size=0;
	}
	
	public DNode<E> getFront(){
		return header.getNext();
	}
	
	public void insertAtFront(DNode<E> n){
		n.setNext(header.getNext());
		header.getNext().setPrev(n);
		n.setPrev(header);
		header.setNext(n);
		size++;
	}
	
	public void insertAtBack(DNode<E> n){
		n.setPrev(trailer.getPrev());
		trailer.getPrev().setNext(n);
		n.setNext(trailer);
		trailer.setPrev(n);
		size++;
	}
	
	public void removeAtFront(){
		DNode<E> p=header.getNext();
		header.setNext(p.getNext());
		p.getNext().setPrev(header);
		p.setNext(null);
		p.setPrev(null);
		size--;
	}
		
	public void removeAtBack(){
		DNode<E> p=trailer.getPrev();
		trailer.setPrev(p.getPrev());
		p.getPrev().setNext(trailer);
		p.setNext(null);
		p.setPrev(null);
		size--;
	}
	
	public void removeNode(DNode<E> n){
		n.getNext().setPrev(n.getPrev());
		n.getPrev().setNext(n.getNext());
		n.setNext(null);
		n.setPrev(null);
		size--;
	}
	
	//Insert n before p
	public void insertBefore(DNode<E> p, DNode<E> n){
		n.setPrev(p.getPrev());
		p.getPrev().setNext(n);
		n.setNext(p);
		p.setPrev(n);
		size++;
	}
	
	//Insert n before p
	public void insertAfter(DNode<E> p, DNode<E> n){
		n.setNext(p.getNext());
		p.getNext().setPrev(n);
		n.setPrev(p);
		p.setNext(n);
		size++;
	}
	
	public int getSize(){
		return size;
	}
	
	public listIterator<E> getIterator(){
		return new listIterator<E>(header.getNext());
	}
	
	public void append(doublyLinkedList<E> list){
		DNode<E> front=list.getFront();
		DNode<E> last=this.trailer.getPrev();
		front.setPrev(last);
		last.setNext(front);
		this.trailer=list.trailer;
		list.header=null;
		this.size=this.size+list.size;
	}
	
	public void print(){
		listIterator<E> I=this.getIterator();
		while(I.isValid()){
			System.out.print(I.getNode().element()+" ");
			I.moveForward();
		}
	}
}

class listIterator<E>{
	private DNode<E> current;
	public listIterator(DNode<E> n){
		current=n;
	}
	
	public boolean isValid(){
		return (current.getNext()!=null)&&(current.getPrev()!=null);
	}
	
	public DNode<E> getNode(){
		return current;
	}
	
	public void moveForward(){
		current=current.getNext();
	}
	
	public void moveBack(){
		current=current.getPrev();
	}
}