package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.Entity;
import com.fernandocejas.android10.sample.domain.DomainObject;
import java.util.Collection;
import java.util.List;

/**
 * @author maciej@goraczka.com
 */
public interface EntityDataMapper<E extends Entity, D extends DomainObject> {

  D transform(E userEntity);

  List<D> transform(Collection<E> userEntityCollection);
}
