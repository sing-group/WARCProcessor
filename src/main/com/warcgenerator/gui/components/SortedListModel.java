package com.warcgenerator.gui.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class SortedListModel<T> extends AbstractListModel<T> {
	private SortedSet<T> modelo;

	/**
	 * Constructor del Modelo
	 */
	public SortedListModel() {
		modelo = new TreeSet<T>();
	}

	/**
	 * Metodo para obtener el total de elementos
	 * 
	 * @return Total de elementos o 0 en su defecto
	 */
	public int getSize() {
		return modelo.size();
	}

	/**
	 * Metodo que regresa el objeto contenido en el indice indicado
	 * 
	 * @param i
	 *            indice a revisar
	 * @return Objecto contenido en el indice o null en su defecto
	 */
	public T getElementAt(int i) {
		if (i < modelo.size()) {
			return (T)modelo.toArray()[i];
		}
		return null;
	}

	/**
	 * Metodo que agrega un objeto a la lista ordenada
	 * 
	 * @param o
	 *            Objeto que se agregara
	 */
	public void add(T o) {
		if (o != null && modelo.add(o))
			fireContentsChanged(this, 0, getSize());
	}

	/**
	 * Metodo que agrega todo un arreglo a la lista
	 * 
	 * @param o
	 *            Arreglo a agregar
	 */
	public void addAll(T o[]) {
		Collection<T> c = Arrays.asList(o);
		modelo.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	/**
	 * Metodo que limpia la lista
	 */
	public void clear() {
		modelo.clear();
		fireContentsChanged(this, 0, getSize());
	}

	/**
	 * Metodo que dice si se contiene o no un objeto indicado en la lista
	 * 
	 * @param o
	 *            Objeto a buscar
	 * @return true en caso de encontrarlo o false en su defecto
	 */
	public boolean contains(Object o) {
		return modelo.contains(o);
	}

	/**
	 * Metodo que retorna el primer objeto de la lista
	 * 
	 * @return Objeto en el tope de la lista
	 */
	public Object first() {
		return modelo.first();
	}

	/**
	 * Metodo que retorna el ?ltimo elemento de la lista
	 * 
	 * @return Objeto en el final de la lista
	 */
	public Object last() {
		return modelo.last();
	}

	/**
	 * Metodo que retorna el iterator correspondiente a la lista
	 * 
	 * @return Iterator correspondiente a la lista
	 * @see java.util.Iterator
	 */
	public Iterator<T> iterator() {
		return modelo.iterator();
	}

	/**
	 * Metodo que quita un objeto de la lista
	 * 
	 * @param o
	 *            Objeto a borrar
	 * @return true si fue removido o false en su defecto
	 */
	public boolean removeElement(Object o) {
		if (o == null) return false;
		boolean r = modelo.remove(o);
		if (r)
			fireContentsChanged(this, 0, getSize());
		return r;
	}

	/**
	 * Metodo que retorna el contenido de la lista como un arreglo
	 * 
	 * @return Arreglo de Objetos de la lista
	 */
	public Object[] elements() {
		return modelo.toArray();
	}

	/**
	 * Metodo que retorna el contenido de la lista como un arreglo
	 * 
	 * @return Arreglo de Objetos de la lista
	 */
	public Object[] toArray() {
		return elements();
	}
}