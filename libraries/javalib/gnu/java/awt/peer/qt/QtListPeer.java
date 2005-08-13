/* QtListPeer.java --
   Copyright (C)  2005  Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package gnu.java.awt.peer.qt;

import java.awt.Dimension;
import java.awt.List;
import java.awt.peer.ListPeer;

public class QtListPeer extends QtComponentPeer implements ListPeer
{
  public QtListPeer( QtToolkit kit, List owner )
  {
    super( kit, owner );
  }

  public native void init();

  protected void setup()
  {
    super.setup();
    List o = (List)owner;
    String[] items = o.getItems();
    setMultipleMode(o.isMultipleMode());
    for (int i = 0; i < items.length; i++)
      add(items[i], i);
    int[] selected = o.getSelectedIndexes();
    for (int i = 0; i < selected.length; i++)
      select(selected[i]);
  }

  private native void select(int index, boolean selected);

  // ************ Public methods *********************

  public native void add(String item, int index);

  public void addItem(String item, int index)
  {
    add(item, index);
  }

  public void clear()
  {
    removeAll();
  }

  /**
   * Deletes items from the starting index to the ending index (inclusive).
   */
  public native void delItems(int start_index, int end_index);

  public void deselect(int index)
  {
    select(index, false);
  }

  public native int[] getSelectedIndexes();

  public native void makeVisible(int index);

  public Dimension minimumSize(int s)
  {
    return getMinimumSize(s);
  }

  public Dimension preferredSize(int s)
  {
    return getPreferredSize(s);
  }

  public void removeAll()
  {
    delItems(0, ((List)owner).getItemCount() - 1);
  }

  public void select(int index)
  {
    select(index, true);
  }

  /**
   * Sets multiple-selection mode.
   * WONTFIX: QAbstractItemView::MultiSelection does not work as it should
   * in Qt 4.0.0, Trolltech bug reporter says this will be fixed in 4.1.0.
   */
  public native void setMultipleMode(boolean multi);

  public void setMultipleSelections(boolean multi)
  {
    setMultipleMode(multi);
  }

  public Dimension getPreferredSize(int s)
  {
    // FIXME
    return getPreferredSize();
  }

  public Dimension getMinimumSize(int s)
  {
    // FIXME
    return getMinimumSize();
  }
}
