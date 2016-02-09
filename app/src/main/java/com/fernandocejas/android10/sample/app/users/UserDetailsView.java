/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.app.users;

import com.fernandocejas.android10.sample.app.view.component.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a user profile.
 */
public interface UserDetailsView extends LoadDataView {
  /**
   * Render a user in the UI.
   *
   * @param user The {@link UserModel} that will be shown.
   */
  void renderUser(UserModel user);
}
