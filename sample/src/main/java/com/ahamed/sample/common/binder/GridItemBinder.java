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

package com.ahamed.sample.common.binder;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.ahamed.multiviewadapter.SelectableBinder;
import com.ahamed.multiviewadapter.SelectableViewHolder;
import com.ahamed.sample.R;
import com.ahamed.sample.common.decorator.GridInsetDecoration;
import com.ahamed.sample.common.model.GridItem;

public class GridItemBinder extends SelectableBinder<GridItem, GridItemBinder.ItemViewHolder> {

  public GridItemBinder(int insetInPixels) {
    super(new GridInsetDecoration(insetInPixels));
  }

  @Override public ItemViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
    return new ItemViewHolder(layoutInflater.inflate(R.layout.item_grid, parent, false));
  }

  @Override public void bind(ItemViewHolder holder, GridItem item, boolean isSelected) {
    holder.itemView.setBackgroundColor(item.getColor());
    holder.ivIcon.setImageResource(item.getDrawable());
    if (isSelected) {
      holder.ivSelectionIndicator.setVisibility(View.VISIBLE);
    } else {
      holder.ivSelectionIndicator.setVisibility(View.GONE);
    }
  }

  @Override public boolean canBindData(Object item) {
    return item instanceof GridItem;
  }

  static class ItemViewHolder extends SelectableViewHolder<GridItem> {

    private ImageView ivIcon;
    private ImageView ivSelectionIndicator;

    ItemViewHolder(View itemView) {
      super(itemView);
      ivSelectionIndicator = (ImageView) itemView.findViewById(R.id.iv_selection_indicator);
      ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);

      setItemClickListener(new OnItemClickListener<GridItem>() {
        @Override public void onItemClick(View view, GridItem item) {
          toggleItemSelection();
          Toast.makeText(view.getContext(), item.getData(), Toast.LENGTH_SHORT).show();
        }
      });
    }

    @Override public int getDragDirections() {
      return ItemTouchHelper.LEFT
          | ItemTouchHelper.UP
          | ItemTouchHelper.RIGHT
          | ItemTouchHelper.DOWN;
    }
  }
}