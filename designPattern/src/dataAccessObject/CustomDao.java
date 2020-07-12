package dataAccessObject;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author 016039
 * @Package dataAccessObject
 * @Description: 持久化接口，只有如果有不同的数据访问机制的就继承它然后实现不同的访问策略
 * @date 2018/9/4下午4:27
 */
public interface CustomDao {
    Stream<Customer> getAll() throws Exception;
    Optional<Customer> getById(int id) throws Exception;
    boolean add(Customer customer) throws Exception;
    boolean update(Customer customer) throws Exception;
    boolean delete(Customer customer) throws Exception;

}
