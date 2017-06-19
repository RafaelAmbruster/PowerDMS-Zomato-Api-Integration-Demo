package powerdms.forkspoon.data.dao;

import java.util.ArrayList;

public interface IOperationDAO<T> {

    int OPERATION_INSERT = 0;
    int OPERATION_INSERT_OR_UPDATE = 1;
    int OPERATION_INSERT_IF_NOT_EXISTS = 2;

    void Insert(final T object, int operation);

    T Get(T object);

    void Delete(T object);

    void Update(T object);



}
