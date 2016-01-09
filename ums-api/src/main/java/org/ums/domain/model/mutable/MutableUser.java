package org.ums.domain.model.mutable;

import org.ums.domain.model.regular.Role;
import org.ums.domain.model.regular.User;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;

public interface MutableUser extends User, Mutable, MutableIdentifier<String> {
  void setPassword(final char[] pPassword);

  void setTemporaryPassword(final char[] pPassword);

  void setRoleId(final Integer pRoleId);
  
  void setRole(final Role pRole);

  void setActive(final boolean pActive);
}