package org.ums.manager;


import java.util.List;

public interface ContentManager<R, M, I> {      // for example : R=Course, M=MutableCourse, I=String
  List<R> getAll() throws Exception;

  R get(final I pId) throws Exception;

  R validate(final R pReadonly) throws Exception;

  int update(final M pMutable) throws Exception;

  int delete(final M pMutable) throws Exception;

  int create(final M pMutable) throws Exception;
}
