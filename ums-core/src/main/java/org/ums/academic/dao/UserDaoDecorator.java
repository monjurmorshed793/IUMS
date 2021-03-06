package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.readOnly.User;
import org.ums.manager.UserManager;

public class UserDaoDecorator extends ContentDaoDecorator<User, MutableUser, String, UserManager> implements UserManager {
  @Override
  public int setPasswordResetToken(String pToken, String pUserId) throws Exception {
    int modified = getManager().setPasswordResetToken(pToken, pUserId);
    if (modified <= 0) {
      throw new IllegalArgumentException("No entry updated");
    }
    return modified;
  }

  @Override
  public int updatePassword(String pUserId, String pPassword) throws Exception {
    int modified = getManager().updatePassword(pUserId, pPassword);
    if (modified <= 0) {
      throw new IllegalArgumentException("No entry updated");
    }
    return modified;
  }

  @Override
  public int clearPasswordResetToken(final String pUserId) throws Exception {
    int modified = getManager().clearPasswordResetToken(pUserId);
    if (modified <= 0) {
      throw new IllegalArgumentException("No entry updated");
    }
    return modified;
  }

}
