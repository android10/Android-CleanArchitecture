package com.fernandocejas.android10.sample.presentation.viewmodel;

import com.fernandocejas.android10.sample.presentation.model.UserModel;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;

/**
 * Created by Cheng Wei on 2014/9/29.
 */
public class UserItemViewModel extends AbstractPresentationModel implements ItemPresentationModel<UserModel> {
    private UserModel userModel;
    @Override
    public void updateData(int i, UserModel userModel) {
        this.userModel = userModel;
    }

    public String getFullName() {
        return userModel.getFullName();
    }
}
