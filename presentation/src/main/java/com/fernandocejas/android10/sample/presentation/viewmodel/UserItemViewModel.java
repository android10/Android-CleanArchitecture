package com.fernandocejas.android10.sample.presentation.viewmodel;

import com.fernandocejas.android10.sample.presentation.model.UserModel;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * Created by Cheng Wei on 2014/9/29.
 */
public class UserItemViewModel implements ItemPresentationModel<UserModel> {
    private UserModel userModel;
    @Override
    public void updateData(UserModel userModel, ItemContext itemContext) {
        this.userModel = userModel;
    }

    public String getFullName() {
        return userModel.getFullName();
    }
}
