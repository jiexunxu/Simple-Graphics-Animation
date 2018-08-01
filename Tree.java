public class Tree<E>{
	private treeNode<E> root;
	public Tree(treeNode<E> n){
		root=n;
	}
	
	public treeNode<E> root(){
		return root;
	}		
}

