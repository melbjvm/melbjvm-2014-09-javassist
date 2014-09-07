package sys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An immutable list. All modifying operations will fail. NOTE: For all other
 * types which are considered immutable but do not implement the interface
 * another ofXXX(Collection c) method is needed. All those classes need to 
 * be final, otherwise people can break the system using mutable subclasses. 
 * 
 * @author ruwen
 *
 * @param <T>
 *            - see List<T>
 */
public final class ImmutableList<T> implements List<T> {

	private final List<T> delegate;

	private ImmutableList(Collection<T> c) {
		delegate = new ArrayList<T>(c);
	}
	
	/**
	 * 
	 * @param c
	 * @return an immutable List of c
	 */
	public static <T extends Immutable> ImmutableList<T> ofImmutables(Collection<T> c) {
		return new ImmutableList<>(c);
	}
	
	/**
	 * 
	 * @param c
	 * @return an immutable list of Strings
	 */
	public static ImmutableList<String> ofStrings(Collection<String> c) {
		return new ImmutableList<>(c);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		/*
		 * In the Iterator interface, the remove method throws by default an
		 * UnsupportedOperationException
		 */
		final Iterator<T> it = delegate.iterator();
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public T next() {
				return it.next();
			}
		};
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <X> X[] toArray(X[] a) {
		return delegate.toArray(a);

	}

	@Override
	public boolean add(T e) {
		throw new UnsupportedOperationException("Immutable -> no add");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Immutable -> no remove");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException("Immutable -> no addAll");
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException("Immutable -> no addAll");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("Immutable -> no removeAll");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("Immutable -> no retainAll");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Immutable -> no clear");
	}

	@Override
	public T get(int index) {
		return delegate.get(index);
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException("Immutable -> no set");
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException("Immutable -> no add");
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException("Immutable -> no remove");
	}

	@Override
	public int indexOf(Object o) {
		return delegate.indexOf(delegate);
	}

	@Override
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(delegate);
	}

	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		final ListIterator<T> it = delegate.listIterator(0);
		return new ListIterator<T>() {

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public T next() {
				return it.next();
			}

			@Override
			public boolean hasPrevious() {
				return it.hasPrevious();
			}

			@Override
			public T previous() {
				return it.previous();
			}

			@Override
			public int nextIndex() {
				return it.nextIndex();
			}

			@Override
			public int previousIndex() {
				return it.previousIndex();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void set(T e) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void add(T e) {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return new ImmutableList<T>(delegate.subList(fromIndex, toIndex));
	}

}
