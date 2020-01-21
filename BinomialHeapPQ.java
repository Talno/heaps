public class BinomialHeapPQ<T, P extends Comparable<P>> implements PriorityQueue<T,P>
{
		private int size;//max size of the binHeap array
		private int queueSize;//size of the queue
		private BinomialHeapPQ<T,P>.BinomialNode [] binHeap;//array of binomial tree heaps(roots)
    //constructor
    public BinomialHeapPQ()
    {
			size = 5;
			queueSize = 0;
			binHeap = new BinomialHeapPQ.BinomialNode[size];
    }
		/**
		*		Creates a heap with a given size
		*
		*		@param size  the size to initialize the binHeap array with
		**/
		public BinomialHeapPQ(int size)
		{
			this.size = size;
			queueSize = 0;
			binHeap = new BinomialHeapPQ.BinomialNode[size];
		}
		/**
    *		Doubles size of the tree array
		**/
		private void increaseCapacity()
		{
			size *= 2;
			BinomialHeapPQ<T,P>.BinomialNode temp[] = new BinomialHeapPQ.BinomialNode[size];
			
			for(int i = 0; i < (size / 2); i++)//(size / 2) to iterate through previous capacity
			{
				temp[i] = binHeap[i];
			}
			
			binHeap = temp;
		}
    /**
		*		Add the given value to the queue using the provided priority
		*
		*		@param value     the value of the element
		*		@param priority  the priority of the element
		**/
    public void enqueue(T value, P priority)
    {
			BinomialNode node = new BinomialNode(value, priority);
			
			queueSize++;
			enqueue(node, 0);
    }
		/**
		*		Adds the entire tree into the queue
		*
		*		@param root   the root of the tree to add
		*		@param index  the index to add the tree into
		**/
		private void enqueueNode(BinomialNode root, int index)
		{
			queueSize += root.size();
			enqueue(root, index);
		}
		/**
		*		Enqueues the node into the array at the given index, handling extra merges with existing trees of the same size as needed
		*
		*		@param n      the node to add into the array
		*		@param index  the index to add the node into
		**/
		private void enqueue(BinomialNode n, int index)
		{
			if(index == size)
				increaseCapacity();
			//if empty, add into new index
			if(binHeap[index] == null)
				binHeap[index] = n;
			else//not empty, combine heaps and move to next index
			{
				enqueue(binHeap[index].enqueue(n), index + 1);
				binHeap[index] = null;//remove existing references to the combined heaps
			}
		}
    /**
		*		Removes the value with the highest priority(lowest priority value)
		*
		*		@return the value of the highest priority element; null if queue is empty
		**/
    public T dequeue()
    {
			if(queueSize == 0) return null;
			queueSize--;
			BinomialNode min;
			int minIndex = findMin();
			min = binHeap[minIndex];
			//remove node from array, reinserting its children into the array
			binHeap[minIndex] = null;
			for(int i = 0; i < min.childSize; i++)
			{
				enqueue(min.child[i], i);
			}
      return min.data;
    }
		/**
		*		Finds the index of the highest priority node
		*
		*		@return the index of the heap with the smallest priority
		**/
		private int findMin()
		{
			
			int minIndex = -1;
			for(int i = 0; i < size; i++)
			{
				//check if a tree is at that location
				if(binHeap[i] == null) continue;
				//add new min if not found yet
				if(minIndex < 0) 
					minIndex = i;
				else if(binHeap[i].priority.compareTo(binHeap[minIndex].priority) < 0)//otherwise compare the two
					minIndex = i;
			}
			return minIndex;
		}
    /**
		*		Returns the value of the element with highest priority(smallest priority value)
		*
		*		@return the value of the element with the highest priority
		**/
    public T peek()
    {
			if(queueSize == 0 ) return null;
			
			int min = findMin();
			
      return binHeap[min].data;
    }
    /**
		*		return the priority of the element with highest priority(smallest priority value)
		*
		*		@return the priority of the highest priority element
		**/
    public P peekPriority()
    {
			if(queueSize == 0 ) return null;
			
      int min = findMin();
			
      return binHeap[min].priority;
    }
    /**
		*		Removes everything in the priority queue
		**/
    public void clear()
    {
			queueSize = 0;
			size = 5;
			binHeap = new BinomialHeapPQ.BinomialNode[size];
    }
    /**
		*		Merge two priority queues into one and return the merged priority queue
		*		
		*		@param other  the other queue to merge with
		*		@return the new queue containing elements from both old queues
		**/
    public BinomialHeapPQ merge(BinomialHeapPQ other)
    {
			BinomialHeapPQ newHeap = new BinomialHeapPQ(Math.max(size, other.size));
			//enqueue the nodes of this tree into heap
			for(int i = 0; i < size; i++)
			{
				if(binHeap[i] != null)
					newHeap.enqueueNode(binHeap[i], i);
			}
			//enqueue the nodes of the other tree
			for(int i = 0; i < other.size; i++)
			{
				if(other.binHeap[i] != null)
					newHeap.enqueueNode(other.binHeap[i], i);
			}
			
      return newHeap;
    }
    /**
		*		Returns the size of the given priority queue
		*
		*		@return the size of the queue
		**/
    public int size()
    {
        return queueSize;
    }
		//Node class holding the value, priority, and children of this node
		private class BinomialNode
		{
			private T data;//value
			private P priority;
			private BinomialHeapPQ<T,P>.BinomialNode [] child;
			private int cap;//child array capacity
			private int childSize;//# of children in array
			private int size;//total size of this node and its children
			
			public BinomialNode(T d, P p)
			{
				cap = 5;
				data = d;
				priority = p;
				child = new BinomialHeapPQ.BinomialNode[cap];
				childSize = 0;
				size = 1;//only contains itself(no children)
			}
			
			/**
			*		Combines the two trees into one, maintaining the heap and returns the new root
			*
			*		@param n  the root of the other heap to merge with
			*		@return the root of the new heap containing both old heaps
			*
			**/
			public BinomialNode enqueue(BinomialNode n)
			{
				BinomialNode newNode;
				//insert node based on priority
				if(priority.compareTo(n.priority) < 0)//this node has higher priority
				{
					newNode = this;
					return newNode.enqueueChild(n);
				}
				else//other node has higher priority
				{
					newNode = n;
					return newNode.enqueueChild(this);
				}
			}
			/**
			*		Merges another tree into this tree as a child of this tree
			*
			*		@param n  the other tree to add as a child
			**/
			private BinomialNode enqueueChild(BinomialNode n)
			{
				if(childSize == cap)
					increaseCap();
				child[childSize] = n;
				childSize++;
				size += n.size();
				
				return this;
			}
			/**
			*		Returns the size of the tree and its children
			*
			*		@return the size of the tree and its children
			**/
			public int size()
			{
				return size;
			}
			
			/**
			*		Doubles size of the child array
			**/
			private void increaseCap()
			{
				cap *= 2;
				BinomialHeapPQ<T,P>.BinomialNode temp[] = new BinomialHeapPQ.BinomialNode[cap];
				
				for(int i = 0; i < childSize; i++)
				{
					temp[i] = child[i];
				}
				
				child = temp;
			}
		}
		
    //merge two priority queues into one and return the merged priority queue
    public PriorityQueue  merge(PriorityQueue other)
    {
      return merge((BinomialHeapPQ)other);
    }
}
