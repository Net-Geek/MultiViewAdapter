/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ahamed.multiviewadapter;

import android.support.annotation.RestrictTo;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.ahamed.multiviewadapter.listener.ItemActionListener;

public class BaseViewHolder<M> extends ViewHolder
    implements View.OnClickListener, View.OnLongClickListener {

  private M item;
  private OnItemClickListener<M> itemClickListener;
  private OnItemLongClickListener<M> itemLongClickListener;
  private ItemActionListener actionListener;

  public BaseViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
    itemView.setOnLongClickListener(this);
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override public final void onClick(View view) {
    if (null == itemClickListener) return;
    itemClickListener.onItemClick(view, getItem());
  }

  @RestrictTo(RestrictTo.Scope.LIBRARY) @Override public final boolean onLongClick(View view) {
    return null != itemLongClickListener && itemLongClickListener.onItemLongClick(view, getItem());
  }

  ////////////////////////////////////////
  ///////// Public Methods ///////////////
  ////////////////////////////////////////

  /**
   * Returns the item object bounded by this {@link BaseViewHolder}
   *
   * @return item bounded by this {@link BaseViewHolder}
   */
  public final M getItem() {
    return item;
  }

  final void setItem(M item) {
    this.item = item;
  }

  /**
   * Can be called by the child view holders to toggle the selection.
   */
  protected final void toggleItemSelection() {
    actionListener.onItemSelectionToggled(getAdapterPosition());
  }

  /**
   * Can be called by the child view holders to toggle the {@link BaseViewHolder}'s expansion
   * status.
   */
  protected final void toggleItemExpansion() {
    actionListener.onItemExpansionToggled(getAdapterPosition());
  }

  /**
   * Can be called by the child view holders to toggle the {@link DataGroupManager}'s expansion
   * status.
   */
  protected final void toggleGroupExpansion() {
    actionListener.onGroupExpansionToggled(getAdapterPosition());
  }

  /**
   * Register a callback to be invoked when this {@link BaseViewHolder} is clicked. If this {@link
   * BaseViewHolder} is not clickable, it becomes clickable.
   *
   * @param itemClickListener The callback that will run
   */
  protected final void setItemClickListener(OnItemClickListener<M> itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  /**
   * Register a callback to be invoked when this {@link BaseViewHolder} is clicked and held. If this
   * {@link BaseViewHolder} is not long clickable, it becomes long clickable.
   *
   * @param itemLongClickListener The callback that will run
   */
  protected final void setItemLongClickListener(OnItemLongClickListener<M> itemLongClickListener) {
    this.itemLongClickListener = itemLongClickListener;
  }

  /**
   * @return boolean value indicating whether the viewholder is selected or not.
   * @see BaseViewHolder#toggleItemSelection()
   */
  public final boolean isItemSelected() {
    return actionListener.isItemSelected(getAdapterPosition());
  }

  /**
   * @return boolean value indicating whether the viewholder is expanded or not.
   * @see BaseViewHolder#toggleItemExpansion()
   */
  public final boolean isItemExpanded() {
    return actionListener.isItemExpanded(getAdapterPosition());
  }

  /**
   * @return boolean value indicating whether the adapter is in context mode or not.
   * @see RecyclerAdapter#startActionMode() ()
   * @see RecyclerAdapter#stopActionMode() ()
   */
  public final boolean isInActionMode() {
    return actionListener.isAdapterInActionMode();
  }

  /**
   * Returns the swipe directions for the provided ViewHolder.
   * Default implementation returns the swipe directions as 0.
   * This method can be overridden by child classes to provide valid swipe direction flags.
   *
   * @return A binary OR of direction flags.
   */
  public int getSwipeDirections() {
    return 0;
  }

  ////////////////////////////////////////
  ///////// Internal Methods ///////////////
  ////////////////////////////////////////

  /**
   * Returns the drag directions for the provided ViewHolder. Default implementation returns the
   * drag directions as 0.
   * This method can be overridden by child classes to provide valid drag direction flags.
   *
   * @return A binary OR of direction flags.
   */
  public int getDragDirections() {
    return 0;
  }

  final void setItemActionListener(ItemActionListener actionListener) {
    this.actionListener = actionListener;
  }

  /**
   * Interface definition for a callback to be invoked when a {@link BaseViewHolder} is clicked.
   */
  public interface OnItemClickListener<M> {
    /**
     * Called when a {@link BaseViewHolder} has been clicked.
     *
     * @param view The view that was clicked.
     * @param item Item that is bounded by the {@link BaseViewHolder}
     */
    void onItemClick(View view, M item);
  }

  /**
   * Interface definition for a callback to be invoked when a {@link BaseViewHolder} has been
   * clicked and held.
   */
  public interface OnItemLongClickListener<M> {
    /**
     * Called when a {@link BaseViewHolder} has been clicked and held.
     *
     * @param view The view that was clicked and held.
     * @param item Item that is bounded by the {@link BaseViewHolder}
     * @return true if the callback consumed the long click, false otherwise.
     */
    boolean onItemLongClick(View view, M item);
  }
}