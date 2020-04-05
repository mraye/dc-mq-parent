package com.github.vspro.persist.ext.mapper;

import com.github.vspro.persist.orm.dao.TransactionMessageDao;
import com.github.vspro.persist.orm.domain.TransactionMessageDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TransactionMessageMapper extends TransactionMessageDao {

    @Update("update t_transaction_message set status=2 where id=#{messageId} and status=4 and deleted=2")
    boolean confirmMessage(@Param("messageId") Long messageId);

    @Update("update t_transaction_message set status=3 where id=#{messageId} and status=1 and deleted=2")
    boolean closeMessage(@Param("messageId") Long messageId);

    @Select("select * from t_transaction_message where status=1 and deleted=2 limit #{limit}")
    List<TransactionMessageDo> loadUnAckMessageList(int limit);

    boolean refreshMessage(@Param("messageId") Long messageId, @Param("now") String now);

    boolean messageDelivered(@Param("messageId") Long messageId, @Param("now") String now);
}
