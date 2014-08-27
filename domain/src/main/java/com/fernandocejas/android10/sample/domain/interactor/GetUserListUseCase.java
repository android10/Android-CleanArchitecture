/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.User;
import com.fernandocejas.android10.sample.domain.exception.ErrorBundle;
import java.util.Collection;

/**
 *
 */
public interface GetUserListUseCase extends Interactor {
  interface Callback {
    void onUserListLoaded(Collection<User> usersCollection);
    void onError(ErrorBundle errorBundle);
  }

  void getUserList(Callback callback);
}
