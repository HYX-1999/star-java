package com.star.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.star.entity.User;
import com.star.model.vo.SystemUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    SystemUserVO getById(Object id);

    User selectNameAndPassword(@Param("username") String username, @Param("password") String password);
}
