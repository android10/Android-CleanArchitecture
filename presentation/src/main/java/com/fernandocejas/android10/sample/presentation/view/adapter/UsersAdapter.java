/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.fernandocejas.android10.sample.presentation.R;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import java.util.Collection;
import java.util.List;

/**
 * Adaptar that manages a collection of {@link UserModel}.
 */
public class UsersAdapter extends BaseAdapter {

  private List<UserModel> usersCollection;
  private final LayoutInflater layoutInflater;

  public UsersAdapter(Context context, Collection<UserModel> usersCollection) {
    this.validateUsersCollection(usersCollection);
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.usersCollection = (List<UserModel>) usersCollection;
  }

  @Override public int getCount() {
    int count = 0;
    if (this.usersCollection != null && !this.usersCollection.isEmpty()) {
      count = this.usersCollection.size();
    }
    return count;
  }

  @Override
  public boolean isEmpty() {
    return (getCount() == 0);
  }

  @Override public Object getItem(int position) {
    return this.usersCollection.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    UserViewHolder userViewHolder;

    if (convertView == null) {
      convertView = this.layoutInflater.inflate(R.layout.row_user, parent, false);

      userViewHolder = new UserViewHolder();
      userViewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.title);

      convertView.setTag(userViewHolder);
    } else {
      userViewHolder = (UserViewHolder) convertView.getTag();
    }

    UserModel userModel = this.usersCollection.get(position);
    userViewHolder.textViewTitle.setText(userModel.getFullName());

    return convertView;
  }

  public void setUsersCollection(Collection<UserModel> usersCollection) {
    this.validateUsersCollection(usersCollection);
    this.usersCollection = (List<UserModel>) usersCollection;
    this.notifyDataSetChanged();
  }

  private void validateUsersCollection(Collection<UserModel> usersCollection) {
    if (usersCollection == null) {
      throw new IllegalArgumentException("The track list cannot be null");
    }
  }

  static class UserViewHolder {
    TextView textViewTitle;
  }
}
